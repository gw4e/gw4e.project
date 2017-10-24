package org.gw4e.eclipse.facade;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringTokenizer;
import java.util.stream.Stream;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteStreamHandler;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.gw4e.eclipse.message.MessageUtil;

public class ProcessFacade {

	 public static Stream<String> execute(File shell, long timeout)
			throws Exception {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayOutputStream err = new ByteArrayOutputStream();

		ExecuteStreamHandler streamHandler = new PumpStreamHandler(out, err, System.in);
		DefaultExecutor executor = new DefaultExecutor();
		executor.setStreamHandler(streamHandler);
		ExecuteWatchdog watchdog = new ExecuteWatchdog(timeout * 1000);
		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		executor.setExitValue(0);
		executor.setWatchdog(watchdog);
		String content = new String (Files.readAllBytes(shell.toPath()),Charset.forName("UTF-8"));
		 
		CommandLine cmdLine = CommandLine.parse(content);
		executor.execute(cmdLine, resultHandler);
		resultHandler.waitFor();
		
		watchdog.checkException();
		
		if (watchdog.killedProcess()) {
			Exception ex = new InterruptedException(MessageUtil.getString("timeoutofflineorcancelled"));
			Path path = Files.createTempFile("offlinedata_err", ".txt");
			Files.write(path, out.toByteArray());
			ResourceManager.logException(ex, "Process stopped. Offline data in : " + path.toFile().getAbsolutePath());
			throw new RuntimeException(ex);
		}
		
		Path path = Files.createTempFile("offlinedata", ".txt");
		Files.write(path, out.toByteArray());
  
		return  Files.lines(path).map(ProcessFacade::extract) ;
	}

	private static String extract(String s) {
		if (!s.startsWith("{\"currentElementName\":")) return "";
		StringTokenizer st = new StringTokenizer(s, ":");
		st.nextToken();
		String value = st.nextToken();
		return value.substring(1, value.lastIndexOf("\""));
	}

	private static boolean isWindows() {
		String OS = System.getProperty("os.name").toLowerCase();
		return OS.indexOf("win") >= 0;
	}

	private static String getShellCommand() {
		if (isWindows()) {
			return "cmd.exe /C ";
		}
		return "sh ";
	}

	public static File buildOfflineShellFile(IJavaProject jproject, IFile graphModel, String pathGenerator,
			String startElement) throws CoreException, IOException {
		IRuntimeClasspathEntry e = JavaRuntime.computeJREEntry(jproject);
		IVMInstall intall = JavaRuntime.getVMInstall(e.getPath());

		StringBuilder sb = new StringBuilder();
		File javaLocation = intall.getInstallLocation();

		sb.append(javaLocation.getAbsolutePath()).append(File.separator).append("bin").append(File.separator)
				.append("java").append(" -cp ").append("\"");

		String cpSeparator = "";
		String[] classpath = JavaRuntime.computeDefaultRuntimeClassPath(jproject);
		for (String cpElement : classpath) {
			sb.append(cpSeparator).append(cpElement);
			cpSeparator = System.getProperty("path.separator");
		}

		sb.append("\"").append(" org.graphwalker.cli.CLI ").append(" offline ").append(" -m ")
				.append(ResourceManager.toFile(graphModel.getFullPath()).getAbsolutePath()).append(" \"")
				.append(pathGenerator).append("\" ").append(" -e ").append(startElement).append(" ");

		 
		String extension = isWindows() ? "bat" : "sh";

		Path path = Files.createTempFile("offlineShellRunner", "." + extension);

		Files.write(path, sb.toString().getBytes(StandardCharsets.UTF_8));

		File file = path.toFile();
		 
		ResourceManager.logInfo(jproject.getProject().getName(), "Shell file : " + file.getAbsolutePath());
		return file;
	}

}

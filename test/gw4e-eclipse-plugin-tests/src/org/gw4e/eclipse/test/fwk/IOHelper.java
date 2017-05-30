package org.gw4e.eclipse.test.fwk;

/*-
 * #%L
 * gw4e
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2017 gw4e-project
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.gw4e.eclipse.builder.Location;

public class IOHelper {

	public static void replace(IFile file, String target, String replacement) throws CoreException, IOException {
		String newline = System.getProperty("line.separator");
		InputStream input = file.getContents();
		StringBuffer sb = new StringBuffer();
		Scanner scanner2 = null;
		try {
			scanner2 = new Scanner(input, "utf-8");
			Scanner scanner = scanner2.useDelimiter(newline);

			while (scanner.hasNext()) {
				sb.append(scanner.next()).append(newline);
			}
		} finally {
			scanner2.close();
			if (input != null)
				input.close();
		}
		String ret = sb.toString().replace(target, replacement);
		
		System.out.println(ret);
		
		file.setContents(new ByteArrayInputStream(ret.getBytes()), true, true, new NullProgressMonitor());
		file.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		
		////
		
		
		  input = file.getContents();
		  sb = new StringBuffer();
		  scanner2 = null;
		try {
			scanner2 = new Scanner(input, "utf-8");
			Scanner scanner = scanner2.useDelimiter(newline);

			while (scanner.hasNext()) {
				sb.append(scanner.next()).append(newline);
			}
		} finally {
			scanner2.close();
			if (input != null)
				input.close();
		}
		System.out.println("XXXXXXXXXXXXXXXXXXXX");
		System.out.println("XXXXXXXXXXXXXXXXXXXX");
		System.out.println("XXXXXXXXXXXXXXXXXXXX");
		
		System.out.println(ret);
	}

	public static boolean findInFile(IFile file, String target) throws CoreException, IOException {
		String newline = System.getProperty("line.separator");
		InputStream input = file.getContents();
		Scanner scanner2 = null;
		try {
			scanner2 = new Scanner(input, "utf-8");
			Scanner scanner = scanner2.useDelimiter(newline);

			while (scanner.hasNext()) {
				String s = scanner.next();
				if (s.contains(target))
					return true;
			}
		} finally {
			scanner2.close();
			if (input != null)
				input.close();
		}
		return false;
	}

	public static int findLocationLineInFile(IFile file, String target) throws CoreException, IOException {
		String newline = System.getProperty("line.separator");
		InputStream input = file.getContents();
		Scanner scanner2 = null;
		try {
			scanner2 = new Scanner(input, "utf-8");
			Scanner scanner = scanner2.useDelimiter(newline);
			int count = 0;
			while (scanner.hasNext()) {
				String s = scanner.next();
				count++;
				if (s.contains(target)) {
					return count;
				}
			}
		} finally {
			scanner2.close();
			if (input != null)
				input.close();
		}
		return -1;
	}

	public static Location findLocationInFile(IFile file, int line, String target) throws CoreException, IOException {
		String newline = System.getProperty("line.separator");
		InputStream input = file.getContents();
		Scanner scanner2 = null;
		try {
			scanner2 = new Scanner(input, "utf-8");
			Scanner scanner = scanner2.useDelimiter(newline);
			int count = 0;
			int consumed = 0;
			while (scanner.hasNext()) {
				String s = scanner.next();

				count++;
				if (s.contains(target) && count == line) {
					int start = consumed + s.indexOf(target);
					int end = start + target.length();
					return new Location(line, start, end);
				}
				consumed = consumed + s.length() + newline.length();
			}
		} finally {
			scanner2.close();
			if (input != null)
				input.close();
		}
		return null;
	}

	public static String getContent(IFile file) throws CoreException, IOException {
		String newline = System.getProperty("line.separator");
		InputStream input = file.getContents();
		StringBuffer sb = new StringBuffer();
		Scanner scanner2 = null;
		try {
			scanner2 = new Scanner(input, "utf-8");
			Scanner scanner = scanner2.useDelimiter(newline);

			while (scanner.hasNext()) {
				sb.append(scanner.next());
				if (scanner.hasNext()) sb.append(newline);
			}
		} finally {
			scanner2.close();
			if (input != null)
				input.close();
		}
		String ret = sb.toString();
		 
		return ret;
	}

	public static void appendParseGeneratorCall(IFile file) throws CoreException, IOException {
		String method = "public void parseTest () {"
				+ " org.graphwalker.core.generator.PathGenerator generator = org.graphwalker.dsl.antlr.generator.GeneratorFactory.parse(\"random(edge_coverage(100))\");"
				+ "}";
		ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom(file);
		compilationUnit.getTypes()[0].createMethod(method, null, false, new NullProgressMonitor());
		compilationUnit.commitWorkingCopy(true, new NullProgressMonitor ());
	}

	public static  void deleteOptions() throws IOException {
		File dir = new File(".");
		File options = new File(dir, ".options");
		options.delete();
	}
	
	public static  void copyOptions() throws IOException {
		String newline = System.getProperty("line.separator");
		URL url = IOHelper.class.getResource(".options");
		InputStream input = url.openStream();
		StringBuffer sb = new StringBuffer ();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String line = null;
		while ((line = reader.readLine()) != null) {
		       sb.append(line).append(newline);
		}
		String data = sb.toString();
		File dir = new File(".");
		File options = new File(dir, ".options");
		Path p = Paths.get(options.getAbsolutePath());
		OutputStream out = new BufferedOutputStream(Files.newOutputStream(p, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) ;
		out.write(data.getBytes(), 0, data.length());
		out.close();
	}

}

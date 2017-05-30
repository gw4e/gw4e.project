package org.gw4e.eclipse.launching.test;

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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.gw4e.eclipse.facade.ResourceManager;

/**
 *
 */
public class ClasspathSerializer {

	/**
	 * Write lines from to a file to serialize a list of string representing a
	 * classpath
	 * 
	 * @param classpath
	 * @return
	 */
	public static Path serialize(String[] classpath) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < classpath.length; i++) {
			sb.append(classpath[i]).append(System.getProperty("line.separator"));
		}
		String s = sb.toString();
		Path tempFile = null;
		BufferedWriter writer = null;
		try {
			tempFile = Files.createTempFile(null, ".gwcp");
			System.out.println("--> "+tempFile);
			Charset charset = Charset.forName("US-ASCII");
			writer = Files.newBufferedWriter(tempFile, charset);
			writer.write(s, 0, s.length());
		} catch (IOException ex) {
			ResourceManager.logException(ex);
			throw new RuntimeException(ex);
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException ex) {
					ResourceManager.logException(ex);
				}
		}
		return tempFile;
	}

	/**
	 * Read lines from a file to build a list of string representing a classpath
	 * 
	 * @param file
	 * @return
	 */
	public static List<String> deserialize(Path file) {
		List<String> ret = new ArrayList<String>();
		BufferedReader reader = null;
		InputStreamReader isr = null;
		InputStream in = null;
		try {
			in = Files.newInputStream(file);
			isr = new InputStreamReader(in);
			reader = new BufferedReader(isr);
			String line = null;
			while ((line = reader.readLine()) != null) {
				ret.add(line);
			}
		} catch (IOException ex) {
			ResourceManager.logException(ex);
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (Exception ex) {
			}
			try {
				if (isr != null)
					isr.close();
			} catch (Exception ex) {
			}
			try {
				if (in != null)
					in.close();
			} catch (Exception ex) {
			}
		}
		return ret;
	}

}

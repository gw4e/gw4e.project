package org.gw4e.eclipse.xl.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class a {

	public static void main(String[] args) {
		Pattern p = Pattern.compile("'([^\"]*)'");
		Matcher m = p.matcher("'Case Search Happy Path - 1_0'!A1");
		while (m.find()) {
		  System.out.println(m.group(1));
		}

	}

}

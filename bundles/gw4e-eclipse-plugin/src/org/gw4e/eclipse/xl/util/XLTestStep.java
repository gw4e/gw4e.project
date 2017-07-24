package org.gw4e.eclipse.xl.util;

public class XLTestStep {
	String name;
	String expected;
	String actual;
	String status;

	public XLTestStep(String name, String expected, String actual, String status) {
		super();
		this.name = name;
		this.expected = expected;
		this.actual = actual;
		this.status = status;
	}

	public String getName() {
		if (name == null || name.trim().length() == 0)
			return "";
		return name;
	}

	public String getExpected() {
		if (expected == null || expected.trim().length() == 0)
			return "";
		return expected;
	}

	public String getActual() {
		if (actual == null || actual.trim().length() == 0)
			return "";
		return actual;
	}

	public String getStatus() {
		return status;
	};
}

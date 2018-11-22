package org.venkat.api.core;

import java.util.Arrays;

import org.testng.TestNG;

public class TestExecutor {
	public static void main(String[] args) {
		Utility util = Utility.newInstance();
		try {
			TestNG testNG = new TestNG();
			testNG.setXmlSuites(Arrays.asList(util.getTestNgSuite()));
			testNG.run();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeDriver();
		}
	}
}
package org.venkat.test;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.venkat.api.core.Utility;
import org.venkat.api.pojo.TestData;
import org.venkat.pages.Way2AutomationPage;

public class Way2AutomationTest {
	protected Utility util = Utility.newInstance();
	protected Way2AutomationPage page = new Way2AutomationPage();
	protected TestData testData;
	protected String testName;
	protected String testCaseId;

	@BeforeMethod
	public void setUpMethodlevel(Method method) {
		String methArray[] = method.getName().split("_");
		testCaseId = methArray[0];
		testName = methArray[1];
		testData = util.getTestData(testCaseId);
		System.out.println("testCaseId:: " + testCaseId + " testName:: " + testName);
	}
	
	@Test(groups= {"all", "ui", "addUser1"})
	public void tc001_addUserAndVerify() {
		try {
			page.lanchApplication();
			page.addUser(testData);
			page.verifyUser(testData);
		} catch (Exception e) {
			Assert.assertEquals(true, false, e.getMessage());
			e.printStackTrace();
		} 
	}
	
	@Test(groups= {"all", "ui", "addUser2"})
	public void tc002_addUserAndVerify() {
		try {
			page.lanchApplication();
			page.addUser(testData);
			page.verifyUser(testData);
		} catch (Exception e) {
			Assert.assertEquals(true, false, e.getMessage());
			e.printStackTrace();
		}
	}
	
	@AfterTest
	public void close() {
		util.closeDriver();
	}

}

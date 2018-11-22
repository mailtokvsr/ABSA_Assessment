package org.venkat.api.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.venkat.api.pojo.ConfigAPI;
import org.venkat.api.pojo.TestCasesData;
import org.venkat.api.pojo.TestData;

public class Utility {
	private String configurationFilePath = GlobalConstants.configurationFilePath;
	private Properties prop = new Properties();
	private ReadTestData readTestData = new ReadTestData();
	private ConfigAPI configApi;
	private Map<String, TestData> testDataMap;
	private Map<String, String> xpathData;
	private Map<String, TestCasesData> testCasesDataMap;
	private static XmlSuite xmlSuite = new XmlSuite();
	private WebDriver driver;
	private WebDriverWait wait;
	private static Utility util;
	private RestClient client;

	private Utility() {
		loadGlobalConstants();
		configApi = readTestData.getConfigAPIObject();
		testCasesDataMap = readTestData.getTestCaseData();
		testDataMap = readTestData.getTestData();
		xpathData = readTestData.getXpathData();
	}
	
	public ConfigAPI getConfigApi() {
		return configApi;
	}

	public RestClient getClient() {
		if (client == null) {
			synchronized (RestClient.class) {
				if (client == null)
					client = new RestClient();
			}
		}
		return client;
	}
	
	public static Utility newInstance() {
		if (util == null) {
			synchronized (Utility.class) {
				if (util == null)
					util = new Utility();
			}
		}
		return util;
	}

	private void loadGlobalConstants() {
		try {
			prop.load(new FileInputStream(configurationFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		GlobalConstants.suiteType = prop.getProperty("suiteType");
		GlobalConstants.group = prop.getProperty("group");
		GlobalConstants.locale = prop.getProperty("locale");
		GlobalConstants.browserName = prop.getProperty("browserName");
		GlobalConstants.headlessBrowser = prop.getProperty("headlessBrowser");
		GlobalConstants.environment = prop.getProperty("environment");
		GlobalConstants.prodUrl = prop.getProperty("prodUrl");
		GlobalConstants.uatUrl = prop.getProperty("uatUrl");
	}

	private void loadDriver() {
		if ("chrome".equalsIgnoreCase(GlobalConstants.browserName)) {
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
			if ("true".equalsIgnoreCase(GlobalConstants.headlessBrowser)) {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("headless");
				options.addArguments("window-size=1200x600");
				driver = new ChromeDriver(options);
			} else {
				driver = new ChromeDriver();
			}
		} else if ("firefox".equalsIgnoreCase(GlobalConstants.browserName)) {
			driver = new FirefoxDriver();
		}
		System.out.println("Succesfully driver loaded..!");
	}

	/*
	 * private void loadDriver() {
	 * 
	 * System.setProperty("webdriver.chrome.driver",
	 * "src/main/resources/chromedriver.exe");
	 * 
	 * driver = new ChromeDriver();
	 * 
	 * }
	 */

	public WebDriver getDriver() {
		if (driver == null) {
			synchronized (Utility.class) {
				if (driver == null)
					loadDriver(); loadWait();
			}
		}
		return driver;
	}

	public void closeDriver() {
		if(driver != null) driver.quit();
	}

	private void loadWait() {
		wait = new WebDriverWait(driver, 20);
	}

	public WebDriverWait getWait() {
		return wait;
	}

	public XmlSuite getTestNgSuite() {
		try {
			xmlSuite.setName(GlobalConstants.applicationName + "_AutomationSuite");
			XmlTest xmlTest = new XmlTest(xmlSuite);
			xmlTest.setName(GlobalConstants.applicationName + "_test");
			if ("CustomSuite".equalsIgnoreCase(GlobalConstants.suiteType)) {
				System.out.println("suiteType  :: CustomSuite");
				Map<String, ArrayList<String>> classMap = new HashMap<String, ArrayList<String>>();
				Set<String> tcSet = testCasesDataMap.keySet();
				for (String tcID : tcSet) {
					TestCasesData testCasesData = testCasesDataMap.get(tcID);
					if ("Y".equalsIgnoreCase(testCasesData.getExecutionFlag())) {
						String className = testCasesData.getClassName();
						String testCaseName = testCasesData.getTestCasesName();
						if (!className.equalsIgnoreCase("") && !testCaseName.equalsIgnoreCase("")) {
							ArrayList<String> testCaseArray = new ArrayList<String>();
							if (classMap.get(className) != null)
								testCaseArray = classMap.get(className);
							testCaseArray.add(testCaseName);
							classMap.put(className, testCaseArray);
						} else {
							throw new Exception("Testcases not present in xlsxfile");
						}
					}
				}
				ArrayList<XmlClass> xmlClasses = new ArrayList<XmlClass>();
				for (Map.Entry<String, ArrayList<String>> entry : classMap.entrySet()) {
					XmlClass testClass = new XmlClass("org.venkat.test." + entry.getKey());
					ArrayList<String> testCases = new ArrayList<String>();
					testCases = entry.getValue();
					List<XmlInclude> xmlIncludes = new ArrayList<XmlInclude>();
					for (int i = 0; i < testCases.size(); i++)
						xmlIncludes.add(new XmlInclude(testCases.get(i)));
					testClass.setIncludedMethods(xmlIncludes);
					xmlClasses.add(testClass);
				}
				xmlTest.setXmlClasses(xmlClasses);
			} else {
				System.out.println("suiteType  :: DefinedSuite");
				List<XmlPackage> packages = new ArrayList<XmlPackage>();
				packages.add(new XmlPackage("org.venkat.test"));
				xmlTest.setXmlPackages(packages);
				xmlTest.addIncludedGroup(GlobalConstants.group);
				// xmlTest.addIncludedGroup(getPropData("SuiteType"));
			}
		} catch (Exception e) {
			System.out.println("Exception in prepareTestNgSuite ");
			e.printStackTrace();
		}
		return xmlSuite;
	}

	public TestData getTestData(String key) {
		return testDataMap.get(key);
	}

	public String getXpathData(String key) {
		return xpathData.get(key);
	}

}

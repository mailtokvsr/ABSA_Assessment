package org.venkat.pages;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.venkat.api.core.Utility;
import org.venkat.api.pojo.TestData;

public class Way2AutomationPage extends BasePage {

	private static Utility util = Utility.newInstance();
	private static WebDriver driver = util.getDriver();

	public Way2AutomationPage() {
		super(driver);
	}

	private By addButton = By.cssSelector("button[type='add']");;
	private By roleId = By.cssSelector("select[name='RoleId']");
	private By saveButton = By.cssSelector("button.btn-success");
	private String inputWithName = "input[name='%s']";
	private String company = "//label[contains(., '%s')]";
	private String trElement = "//td[text() = '%s']/ancestor::tr";
	Random rand = new Random();
	// private int randomNo = rand.nextInt(100);

	public void addUser(TestData testData) {
		click(addButton);
		sendKeys(By.cssSelector(String.format(inputWithName, "FirstName")), testData.getfName());
		sendKeys(By.cssSelector(String.format(inputWithName, "LastName")), testData.getlName());
		sendKeys(By.cssSelector(String.format(inputWithName, "UserName")), testData.getuName());
		sendKeys(By.cssSelector(String.format(inputWithName, "Password")), testData.getPassword());
		click(By.xpath(String.format(company, testData.getCustomor())));
		selectByVisibleText(roleId, testData.getRole());
		sendKeys(By.cssSelector(String.format(inputWithName, "Email")), testData.getEmail());
		sendKeys(By.cssSelector(String.format(inputWithName, "Mobilephone")), testData.getCell());
		click(saveButton);
	}

	public void verifyUser(TestData testData) {
		By trEle = By.xpath(String.format(trElement, testData.getfName()));
		waitForElemet(trEle);
		WebElement row = driver.findElement(trEle);
		List<WebElement> elements = row.findElements(By.tagName("td"));
		Iterator<WebElement> eleIterator = elements.listIterator();
		int cellIndex = 0;
		while (eleIterator.hasNext()) {
			WebElement webElement = (WebElement) eleIterator.next();
			String text = webElement.getText();
			if (cellIndex == 0)
				Assert.assertEquals(text, testData.getfName(),
						"First Name:: '" + text + "' not maching with '" + testData.getfName() + "'");
			else if (cellIndex == 1)
				Assert.assertEquals(text, testData.getlName(),
						"Last Name:: '" + text + "' not maching with '" + testData.getlName() + "'");
			else if (cellIndex == 2)
				Assert.assertEquals(text, testData.getuName(),
						"user Name:: '" + text + "' not maching with '" + testData.getuName() + "'");
			else if (cellIndex == 5)
				Assert.assertEquals(text, testData.getRole(),
						"Role:: '" + text + "' not maching with '" + testData.getRole() + "'");
			else if (cellIndex == 6)
				Assert.assertEquals(text, testData.getEmail(),
						"Email:: '" + text + "' not maching with '" + testData.getEmail() + "'");
			else if (cellIndex == 7)
				Assert.assertEquals(text, testData.getCell(),
						"Mobilephone:: '" + text + "' not maching with '" + testData.getCell() + "'");
			cellIndex++;

		}
	}
}
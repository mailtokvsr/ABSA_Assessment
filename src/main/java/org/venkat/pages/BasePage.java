package org.venkat.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.venkat.api.core.GlobalConstants;

public class BasePage {

	protected WebDriver driver;
	protected WebDriverWait wait;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, 20);
	}

	public void lanchApplication() {
		String url;
		if ("prod".equals(GlobalConstants.environment))
			url = GlobalConstants.prodUrl;
		else
			url = GlobalConstants.uatUrl;
		driver.get(url);
	}

	public WebElement waitForElemet(By element) {
		return wait.until(ExpectedConditions.presenceOfElementLocated(element));
	}

	public void click(By element) {
		waitForElemet(element).click();
	}

	public void sendKeys(By element, String data) {
		waitForElemet(element).clear();
		waitForElemet(element).sendKeys(data);
	}

	public String getText(By element) {
		return waitForElemet(element).getText();
	}

	public void selectByVisibleText(By element, String text) {
		Select dropdown = new Select(waitForElemet(element));
		dropdown.selectByVisibleText(text);
	}

	public void selectByValue(By element, String text) {
		Select dropdown = new Select(waitForElemet(element));
		dropdown.selectByValue(text);
	}

}

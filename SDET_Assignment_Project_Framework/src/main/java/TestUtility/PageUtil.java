package TestUtility;

import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;
import com.aventstack.extentreports.Status;

import BaseExecutor.Runner;

import org.openqa.selenium.StaleElementReferenceException;

public class PageUtil {
	WebDriver driver;
	private static Logger logger = Logger.getLogger(PageUtil.class);
	SoftAssert sAssert;
	Assertion hAssert;
	String url = PropertyFiles.propertiesFile().getProperty("url");
	

	public PageUtil(WebDriver driver) 
	{
		this.driver = driver;
        sAssert = new SoftAssert();
		hAssert = new Assertion();
	}

	public void implicitWait(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	public void explicitWaitLink(WebDriver driver, String linkText) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText(linkText)));
	}

	public void explicitWaitForWebElement(WebDriver driver, By locator) {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 120);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		}

		catch (NoSuchElementException e) {
			// e.printStackTrace();
			System.err.format("No Element Found: " + e);
			Runner.test.log(Status.FAIL, "No Element Found after wait" + e);
		}

	}

	public void explicitWaitForWebElementAttribute(WebDriver driver, By locator, String attribute, String value) {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.attributeContains(locator, attribute, value));
		}

		catch (NoSuchElementException e) {
			// e.printStackTrace();
			System.err.format("No Element Found: " + e);
			Runner.test.log(Status.FAIL, "No Element Found after wait" + e);
		}

	}

	public void explicitWaitForAlertPopUp(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.alertIsPresent());

	}

	public void explicitWaitUntilClickable(WebDriver driver, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public String getCurrentPageTitle() {
		String title = null;
		try {

			title = driver.getTitle();
			// System.out.println(title);
			logger.info("User gets the Current Page Title " + title);

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Element is not found");
		}
		return title;
	}

	public void verifyCurrentPageTitle(String expectedTitle) {

		String actualTitle = getCurrentPageTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
		Runner.test.log(Status.PASS,
				"The expected title: " + expectedTitle + " matches with the title: " + actualTitle);
		logger.info("User verifies the current Page Title as " + expectedTitle);
		// sAssert.assertAll();

	}

	public String getCurrentPageUrl() {
		String pageUrl = driver.getCurrentUrl();
		logger.info("User gets the Current Page URL as " + pageUrl);
		return pageUrl;
	}

	public int getXCoordinates(By locator) throws Exception {
		// Locate element for which you wants to retrieve x y coordinates.
		WebElement element = driver.findElement(locator);
		// Used points class to get x and y coordinates of element.
		org.openqa.selenium.Point classname = element.getLocation();
		int xcordi = classname.getX();
		// System.out.println("Element's Position from left side"+xcordi +"
		// pixels.");
		return xcordi;
	}

	public int getYCoordinates(By locator) throws Exception {
		// Locate element for which you wants to retrieve x y coordinates.
		WebElement element = driver.findElement(locator);
		// Used points class to get x and y coordinates of element.
		org.openqa.selenium.Point classname = element.getLocation();
		int ycordi = classname.getY();
		// System.out.println("Element's Position from top"+ycordi +" pixels.");
		return ycordi;
	}

	public void assertCurrentPageUrl(String expectedUrl) {
		String actualUrl = getCurrentPageUrl();
		Assert.assertTrue(actualUrl.contains(expectedUrl));
		// hAssert.assertTrue(actualUrl.contains(expectedUrl), "The URL
		// validation matched");
		// hAssert.assertEquals(actualUrl, expectedUrl);
		logger.info("User verifies the current Page URL as " + expectedUrl);
		Runner.test.log(Status.PASS, "The expected URL: " + expectedUrl + " matches with the URL: " + actualUrl);
		// return getCurrentPageUrl().contains(expectedUrl);
	}

	public boolean verifyElementPresent(By locator)

	{
		boolean bValue = false;
		WebElement element = null;
		try {
			element = driver.findElement(locator);
			if (element.isDisplayed() || element.isEnabled())
				Runner.test.log(Status.PASS, "The element " + element.toString() + " is found");
			bValue = true;
		}

		catch (NoSuchElementException e) {
			e.printStackTrace();
			logger.info("The element is not found");
			Runner.test.log(Status.FAIL, "The element " + element.toString() + " is not found");
			bValue = false;
		}

		catch (StaleElementReferenceException e) {
			e.printStackTrace();
			Runner.test.log(Status.FAIL, "The element " + element.toString() + "is not available in Dom");
			logger.info("The element is not available in Dom");
			bValue = false;
		}

		return bValue;

	}

	public boolean verifyElementPresent(WebElement element)

	{
		boolean bValue = false;

		try {
			if (element.isDisplayed() || element.isEnabled())
				Runner.test.log(Status.PASS, "The element " + element.toString() + " is found");
			bValue = true;
		}

		catch (NoSuchElementException e) {
			e.printStackTrace();
			logger.info("The element is not found");
			Runner.test.log(Status.FAIL, "The element " + element.toString() + " is not found");
			bValue = false;
		}

		catch (StaleElementReferenceException e) {
			e.printStackTrace();
			Runner.test.log(Status.FAIL, "The element " + element.toString() + "is not available in Dom");
			logger.info("The element is not available in Dom");
			bValue = false;
		}

		return bValue;

	}

	public boolean verifyElementNotPresent(By locator) {

		try {
			driver.findElement(locator);
			return false;
		} catch (NoSuchElementException e) {
			// e.getStackTrace();
			return true;
		}
	}

	public boolean verifyElementVisible(By locator) {

		try {
			driver.findElement(locator).isDisplayed();
			return true;
		} catch (NoSuchElementException e) {
			e.getStackTrace();
			return false;
		}
	}

	public boolean verifyElementVisible(WebElement element) {

		try {
			element.isDisplayed();
			return true;
		} catch (NoSuchElementException e) {
			e.getStackTrace();
			return false;
		}
	}

	public boolean verifyElementNotVisible(By locator) {

		try {
			if (driver.findElement(locator).isDisplayed() == false)
				;
			Runner.test.log(Status.PASS, locator + " : element is not displaying on the page");

			return false;
		} catch (NoSuchElementException e) {
			Runner.test.log(Status.FAIL, locator + " : element is displaying on the page");
			e.getStackTrace();
			return true;
		}
	}

	public boolean verifyElementNotEnabled(By locator) {

		try {

			if (!(driver.findElement(locator).isEnabled()))
				logger.info(locator + " : This element is  enabled");
			Runner.test.log(Status.FAIL, locator + " : element is enabled");

			return true;
		} catch (NoSuchElementException e) {
			e.getStackTrace();
			logger.info(locator + " : This element is not enabled");
			Runner.test.log(Status.FAIL, locator + " : element not enabled");
			return false;
		}

	}

	// To get the Locator Text
	public String getLocatorText(By locator) {
		try {
			String sText = driver.findElement(locator).getText();
			logger.info("Element Text is " + sText);

		} catch (NoSuchElementException e) {
			e.printStackTrace();
			logger.info(locator + " : This element is not found");
			Runner.test.log(Status.FAIL, locator + " : element not found");
		}
		return driver.findElement(locator).getText().toString();
	}

	public boolean isElementDisplayed(By locator) {
		if (driver.findElements(locator).size() > 0 && driver.findElement(locator).isDisplayed()) {

			Runner.test.log(Status.PASS, getLocatorText(locator) + " is displayed on the Page");
			return true;

		} else {
			Runner.test.log(Status.FAIL, locator + " is not Displayed on the Page");
			return false;
		}
	}

	public boolean isElementDisplayed(WebElement element) {

		if (element.isDisplayed()) {

			Runner.test.log(Status.PASS, element.getText() + " is displayed on the Page");
			return true;

		} else {
			Runner.test.log(Status.FAIL, element.getText() + " is not Displayed on the Page");
			return false;
		}
	}

	public boolean isElementEnabled(By locator) {

		WebElement element = driver.findElement(locator);

		if (element.isEnabled()) {

			Runner.test.log(Status.PASS, getLocatorText(locator) + " is enabled on the Page");
			return true;
		}

		else

		{
			Runner.test.log(Status.FAIL, locator + " is not enabled on the Page");
			return false;
		}
	}

	public boolean isElementDisabled(By locator) {

		WebElement element = driver.findElement(locator);

		if (!element.isEnabled()) {

			Runner.test.log(Status.PASS, getLocatorText(locator) + " is disabled on the Page");
			return true;
		}

		else

		{
			Runner.test.log(Status.FAIL, locator + " is not disabled on the Page");
			return false;
		}
	}

	public static boolean isSorted(float[] a) {
		boolean isSorted = false;
		boolean isAscending = a[1] > a[0];
		if (isAscending == true) {
			for (int i = 0; i < a.length - 1; i++) {
				if (a[i] < a[i + 1]) {
					isSorted = true;
					break;
				}
			}
			Runner.test.log(Status.INFO, " Sorting is based on the Descending Order");
		} else {// descending
			for (int i = 0; i < a.length - 1; i++) {
				if (a[i] > a[i + 1]) {
					isSorted = true;
					break;
				}
			}
			Runner.test.log(Status.INFO, " Sorting is based on the Ascending Order");
		}
		return isSorted;
	}

	public boolean isLinkOrIconDisplayed(By locator, String elementName) {
		if (driver.findElements(locator).size() > 0 && driver.findElement(locator).isDisplayed()) {

			Runner.test.log(Status.PASS, getLocatorText(locator) + " " + elementName + " is displayed on the Page");
			return true;

		} else {
			Runner.test.log(Status.FAIL, elementName + " is not displayed on the Page");
			return false;
		}
	}

	public boolean isElementHidden(By locator) {

		WebElement element = driver.findElement(locator);
		if (element.getAttribute("style") == "display:none!important;") {
			Runner.test.log(Status.PASS, getLocatorText(locator) + " " + element + " is hidden on the Page");
			return true;

		}

		else if (element.getAttribute("style") == "display:none") {
			Runner.test.log(Status.FAIL, element + " is not displaying on the Page");
			return true;
		}

		else {
			Runner.test.log(Status.FAIL, element + " is not hidden on the Page");
			return false;
		}
	}

	public boolean isElementNotDisplayed(By locator) {
		if (driver.findElements(locator).isEmpty()) {
			Runner.test.log(Status.PASS, locator + " : is not displayed on the Page");
			return true;

		} else {
			Runner.test.log(Status.PASS, locator + " : is displayed on the Page");
			return false;
		}
	}

	/*
	 * public void chooseHomePage() {
	 * 
	 * 
	 * logger.info("Go To Home Page");
	 * 
	 * 
	 * 
	 * 
	 * if(getCurrentPageUrl().contains("://m.hays.")) {
	 * mobileHeaderFooterPage=new MobileHeaderFooterPage( driver);
	 * mobileHeaderFooterPage.clickLogoLink();
	 * logger.info("User clicks the logo link of the Mobile Page"); } else {
	 * WebSiteHeaderFooterPage webHomePage = new WebSiteHeaderFooterPage(
	 * driver); webHomePage.clickLogoLink();
	 * logger.info("User clicks the logo link of the website Page"); }
	 * 
	 * 
	 * }
	 */

	public void deleteCookies() {
		driver.manage().deleteAllCookies();

	}

	public String getParentWindow() {
		String parent = driver.getWindowHandle();
		return parent;
	}

	public String getObjectlLabel(By locator) {

		String title = null;

		try {
			driver.findElement(locator).isDisplayed();
			title = driver.findElement(locator).getText();
			logger.info("User gets the test object Label as: " + title);
			// Runner.test.log(Status.PASS, "User gets the test object Label
			// as: " + title);
		}

		catch (Exception e) {
			e.printStackTrace();
			logger.info("User gets a blank test object Label");
			Runner.test.log(Status.INFO, "User gets a blank test object Label");
		}

		return title;

	}

	public String getObjectlLabel(WebElement element) {

		String sTitle = null;

		try {
			element.isDisplayed();
			sTitle = element.getText();
//			System.out.println(sTitle);
			logger.info("User gets the test object Label as: " + sTitle);
			// Runner.test.log(Status.PASS, "User gets the test object Label
			// as: " + title);
		}

		catch (Exception e) {
			e.printStackTrace();
			logger.info("User gets a blank test object Label");
			Runner.test.log(Status.FAIL, "User gets a blank test object Label");
		}

		return sTitle;

	}

	public void verifyObjectLabel(By locator, String expectedObjectLabel) {

		String actualObjectLabel = getObjectlLabel(locator); // return
		getObjectlLabel(locator).contains(expectedObjectLabel);
		sAssert.assertEquals(actualObjectLabel, expectedObjectLabel);
		logger.info("User verifies the test object Label as: " + expectedObjectLabel);
		Runner.test.log(Status.PASS, "The expected object label: " + expectedObjectLabel
				+ " matches with the actual object label: " + actualObjectLabel);
	}
	
	public void verifyObjectLabel(WebElement element, String expectedObjectLabel) {

		String actualObjectLabel = getObjectlLabel(element); // return
		getObjectlLabel(element).contains(expectedObjectLabel);
		Assert.assertEquals(actualObjectLabel, expectedObjectLabel);
		logger.info("User verifies the test object Label as: " + expectedObjectLabel);
		Runner.test.log(Status.PASS, "The expected object label: " + expectedObjectLabel
				+ " matches with the actual object label: " + actualObjectLabel);
	}

	// public void verifyObjectLabel(By locator, String expectedObjectLabel) {
	//
	// String actualObjectLabel = getObjectlLabel(locator);
	// // return getObjectlLabel(locator).contains(expectedObjectLabel);
	//// if(actualObjectLabel.equals(expectedObjectLabel))
	//// {
	//// logger.info("User verifies the test object Label as: " +
	// expectedObjectLabel);
	//// Runner.test.log(Status.PASS, "The expected object label: " +
	// expectedObjectLabel
	//// + " matches with the actual object label: " + actualObjectLabel);
	//// }
	//// else
	//// {
	//// logger.info("User verifies the test object Label as: " +
	// expectedObjectLabel);
	//// Runner.test.log(Status.FAIL, "The expected object label: " +
	// expectedObjectLabel
	//// + " does not match with the actual object label: " +
	// actualObjectLabel);
	//// }
	// Assert.assertEquals(actualObjectLabel, expectedObjectLabel);
	// logger.info("User verifies the test object Label as: " +
	// expectedObjectLabel); Runner.test.log(Status.PASS,
	// "The expected object label: " + expectedObjectLabel +
	// " matches with the actual object label: " + actualObjectLabel);
	// }
	//
	//

	public void verifyObjectLabelContains(By locator, String expectedValue) {

		hAssert.assertTrue(getObjectlLabel(locator).contains(expectedValue),
				"The label contains the value" + expectedValue);
		logger.info("User verifies the test object Label contains: " + expectedValue);
		Runner.test.log(Status.PASS, "The expected object label contains: " + expectedValue);
	}
	
	public void verifyObjectLabelContains(WebElement element, String expectedValue) {

		Assert.assertTrue(getObjectlLabel(element).contains(expectedValue),
				"The label contains the value" + expectedValue);
		logger.info("User verifies the test object Label contains: " + expectedValue);
		Runner.test.log(Status.PASS, "The expected object label contains: " + expectedValue);
	}

	public void verifyObjectLabelNotContains(By locator, String expectedValue) {

		hAssert.assertFalse(getObjectlLabel(locator).contains(expectedValue),
				"The label does not contain the value" + expectedValue);
		logger.info("User verifies the test object Label does not contain: " + expectedValue);
		Runner.test.log(Status.PASS, "The expected object label does not contain: " + expectedValue);
	}

	public void verifyObjectLabelContains(String expectedValue, String actualValue) {

		sAssert.assertTrue(expectedValue.contains(actualValue), "The label contains the value" + actualValue);
		logger.info("User verifies the test object Label contains: " + actualValue);
		Runner.test.log(Status.PASS, "The expected object label" + expectedValue + "contains: " + actualValue);
	}

	public void assertObjectLabel(By locator, String expectedObjectLabel) {

		String actualObjectLabel = getObjectlLabel(locator);
		sAssert.assertEquals(actualObjectLabel, expectedObjectLabel);
		logger.info("User verifies the test object Label as: " + expectedObjectLabel);
		Runner.test.log(Status.PASS, "The expected object label: " + expectedObjectLabel
				+ " matches with the actual object label: " + actualObjectLabel);
	}

	public void assertObjectLabel(String actualObjectLabel, String expectedObjectLabel) {

		// return getObjectlLabel(locator).contains(expectedObjectLabel);
		sAssert.assertEquals(actualObjectLabel, expectedObjectLabel);
		logger.info("User verifies the test object Label as: " + expectedObjectLabel);
		Runner.test.log(Status.PASS, "The expected object label: " + expectedObjectLabel
				+ " matches with the actual object label: " + actualObjectLabel);
	}

	public void enterTextValue(By locator, String expectedData) {
		try {
			WebElement element = driver.findElement(locator);
			if (verifyElementPresent(locator))
			// element.isDisplayed())
			{
				element.clear();
				element.sendKeys(expectedData);
				logger.info("User enters " + expectedData + " in the " + element.getText() + " text box");
				Runner.test.log(Status.PASS,
						"User enters " + expectedData + " in the " + element.getText() + " text box");
			}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			System.err.format("No Element Found to enter text" + e);
			Runner.test.log(Status.FAIL, "No Element Found to enter text" + e);
		}
	}

	public void enterTextValue(By locator, int expectedData) {
		try {
			WebElement element = driver.findElement(locator);
			if (verifyElementPresent(locator))
			// element.isDisplayed())
			{
				element.clear();
				WebElement wb = element;
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("arguments[0].value='" + expectedData + "';", wb);
				// element.sendKeys(expectedData);
				logger.info("User enters " + expectedData + " in the " + element.getText() + " text box");
				Runner.test.log(Status.PASS,
						"User enters " + expectedData + " in the " + element.getText() + " text box");
			}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			System.err.format("No Element Found to enter text" + e);
			Runner.test.log(Status.FAIL, "No Element Found to enter text" + e);
		}
	}

	public void pressEnter(By locator) {
		try {
			WebElement element = driver.findElement(locator);
			if (verifyElementPresent(locator))

			{
				element.sendKeys(Keys.RETURN);
				logger.info("User presses the enter key");
				Runner.test.log(Status.PASS, ("User presses the enter key"));
			}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			System.err.format("No Element Found to press enter" + e);
			Runner.test.log(Status.FAIL, "No Element Found to press enter" + e);
		}
	}

	public void clearTextBox(By locator) {
		try {
			WebElement element = driver.findElement(locator);
			if (verifyElementPresent(locator))
			// element.isDisplayed())
			{
				element.clear();

				logger.info("User clears the  " + element.getText() + " text box");
				Runner.test.log(Status.PASS, "User clears the  " + element.getText() + " text box");
			}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			System.err.format("No Element Found to enter text" + e);
			Runner.test.log(Status.FAIL, "No Element Found to enter text" + e);
		}
	}

	public void click(By locator) {

		for (int iCount = 0; iCount < 3; iCount++) {
			try {
				WebElement element = driver.findElement(locator);
				if (verifyElementPresent(locator)) {
					logger.info("User clicks on " + element.getText() + " button");
					Runner.test.log(Status.PASS, "User clicks on " + element.getText() + " button");
					element.click();

				}
				break;
			} catch (NoSuchElementException e) {
				e.printStackTrace();
				System.err.format("No Element Found to perform click" + e);
				Runner.test.log(Status.FAIL, "No Element Found to enter click" + e);
			}
			// explicitWaitUntilClickable(driver, locator);
		}

	}

	public static void click(WebElement element) {
		for (int iCount = 0; iCount < 3; iCount++) {
			try {

				if (element.isDisplayed()) {
					logger.info("User clicks on " + element.getText() + " button");
					Runner.test.log(Status.PASS, "User clicks on " + element.getText() + " button");
					element.click();

				}
				break;

			} catch (NoSuchElementException e) {
				e.printStackTrace();
				System.err.format("No Element Found to perform click" + e);
				Runner.test.log(Status.FAIL, "No Element Found to enter click" + e);
			}
		}
	}
	public static void enterTextValue(WebElement element, int expectedData) 
	{

			try {

				if (element.isDisplayed()) {
					logger.info("User clicks on " + element.getText() + " button");
					System.out.println(element.getText());
					Runner.test.log(Status.PASS, "User clicks on " + element.getText() + " button");
					
					//element.clear();
					element.sendKeys("expectedData");

				}
				

			} catch (NoSuchElementException e) {
				e.printStackTrace();
				System.err.format("No Element Found to perform click" + e);
				Runner.test.log(Status.FAIL, "No Element Found to enter click" + e);
			}
		
}

	public void selectRadio(By locator) {
		try {
			WebElement element = driver.findElement(locator);
			if (verifyElementPresent(locator)) {
				logger.info("User clicks on " + element.getText() + " button");
				Runner.test.log(Status.PASS, "User clicks on " + element.getText() + " button");
				element.click();

			}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			System.err.format("No Element Found to perform click" + e);
			Runner.test.log(Status.FAIL, "No Element Found to enter click" + e);
		}
	}

	public void checkMultiSelect(By locator)
	{
		try
		{
			WebElement element = driver.findElement(locator);
			List<WebElement> allOptions = element.findElements(By.tagName("input"));
			for (WebElement option : allOptions) {
				System.out.println("Option value " + option.getText());
				String typeValue= option.getAttribute("Gas Meter");
				if (typeValue.equals(option.getText())) {
					option.click();
					break;
				}
			}
			
		}
		catch(NoSuchElementException e)
		{
			e.printStackTrace();
			System.err.format("Unable to select the checkbox: " + e);
			
		}
		}
	
	public void check(By locator) {
		try {
			WebElement element = driver.findElement(locator);
			if (verifyElementPresent(locator) && element.isSelected()) {
				logger.info("The checkbox " + element.getText() + " is already selected");
				Runner.test.log(Status.PASS, "The checkbox " + element.getText() + " is already selected");

			}

			else {
				element.click();
				logger.info("User clicks on " + element.getText() + " checkbox");
				Runner.test.log(Status.PASS, "User clicks on " + element.getText() + " checkbox");

			}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			System.err.format("Unable to select the checkbox: " + e);
			Runner.test.log(Status.FAIL, "Unable to select the checkbox: " + e);
		}
	}

	public void unCheckCheckbox(By locator) {
		try {
			WebElement element = driver.findElement(locator);
			if (verifyElementPresent(locator) && element.isSelected()) {
				// De-select the checkbox
				element.click();
				logger.info("User clicks on " + element.getText() + " checkbox");
				Runner.test.log(Status.PASS, "User clicks on " + element.getText() + " checkbox");
			} else {
				logger.info("The checkbox " + element.getText() + " is already deselected");
				Runner.test.log(Status.PASS, "The checkbox " + element.getText() + " is already deselected");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.format("Unable to select the checkbox: " + e);
			Runner.test.log(Status.FAIL, "Unable to select the checkbox: " + e);
		}
	}

	public void unCheckCheckbox(WebElement element) {
		try {

			if (verifyElementPresent(element) && element.isSelected()) {
				// De-select the checkbox
				element.click();
				logger.info("User clicks on " + element.getText() + " checkbox");
				Runner.test.log(Status.PASS, "User clicks on " + element.getText() + " checkbox");
			} else {
				logger.info("The checkbox " + element.getText() + " is already deselected");
				Runner.test.log(Status.PASS, "The checkbox " + element.getText() + " is already deselected");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.format("Unable to select the checkbox: " + e);
			Runner.test.log(Status.FAIL, "Unable to select the checkbox: " + e);
		}
	}

	// Below method is used to Select a Checkbox, if it is not selected already
	public void selectCheckBoxFromList(By locator, String valueToSelect) {
		WebElement element = driver.findElement(locator);
		List<WebElement> allOptions = element.findElements(By.tagName("input"));
		for (WebElement option : allOptions) {
			System.out.println("Option value " + option.getText());
			if (valueToSelect.equals(option.getText())) {
				option.click();
				break;
			}
		}
	}

	public void pressTabKey(By locator) {
		try {
			WebElement element = driver.findElement(locator);
			if (verifyElementPresent(locator)) {
				element.sendKeys(Keys.TAB);
				element.sendKeys(Keys.ENTER);
				logger.info("User presses the tab key");
				Runner.test.log(Status.PASS, "User presses the tab key");

			}

		} catch (NoSuchElementException e) {
			e.printStackTrace();
			System.err.format("No Element Found to perform tab key press" + e);
			Runner.test.log(Status.FAIL, "No Element Found to tab key press" + e);
		}
	}

	public void selectFromList(By locator) {
		try {
			WebElement element = driver.findElement(locator);
			if (verifyElementPresent(locator)) {
				logger.info("User clicks on " + element.getText() + " button");
				Runner.test.log(Status.PASS, "User clicks on " + element.getText() + " button");
				element.click();

			}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			System.err.format("No Element Found to perform click" + e);
			Runner.test.log(Status.FAIL, "No Element Found to enter click" + e);
		}
	}

	//select the dropdown using "select by visible text", so pass VisibleText

	public void selectFromDropDownByVisibleText(By locator, String visibleText) {
		try {
			WebElement element = driver.findElement(locator);
			if (element.isDisplayed()) {
				Select selObj = new Select(element);
				selObj.selectByVisibleText(visibleText);
				logger.info("User selects the visible text as  " + visibleText + "from Dropdown");
				Runner.test.log(Status.PASS, "User selects the visible text as  " + visibleText + "from Dropdown");
			}
		}

		catch (NoSuchElementException e)

		{
			e.printStackTrace();
			System.err.format("No Element Found to perform selection in the dropdown" + e);
			Runner.test.log(Status.FAIL, "No Element Found to perform selection in the dropdown" + e);
		}

	}

	// select the dropdown using "select by index", so pass IndexValue as '2'

	public void selectFromDropDownByIndex(By locator, int indexValue) {
		try {
			WebElement element = driver.findElement(locator);
			if (element.isDisplayed()) {
				Select selObj = new Select(element);
				selObj.selectByIndex(indexValue);
				logger.info("User selects the index as  " + indexValue + " from Dropdown");
				Runner.test.log(Status.PASS, "User selects the index as  " + indexValue + " from Dropdown");
			}
			else {
				
				Runner.test.log(Status.FAIL, "Element not displaying");
			}
		}

		catch (NoSuchElementException e)

		{
			e.printStackTrace();
			System.err.format("No Element Found to perform selection" + e);
			Runner.test.log(Status.FAIL, "No Element Found to perform selection in the dropdown" + e);
		}

	}

	// select the dropdown using "select by value", so pass Value as

	public void selectFromDropDownByValue(By locator, String value) {
		try {
			WebElement element = driver.findElement(locator);
			if (element.isDisplayed()) {
				Select selObj = new Select(element);
				selObj.selectByValue(value);
				System.out.println(element.getText());
				logger.info("User selects the value as  " + value + "from Dropdown");
				Runner.test.log(Status.PASS, "User selects the value as  " + value + "from Dropdown");
			}
		}

		catch (NoSuchElementException e)

		{
			e.printStackTrace();
			System.err.format("No Element Found to perform selection" + e);
			Runner.test.log(Status.FAIL, "No Element Found to perform selection in the dropdown" + e);
		}

	}

	public int countDropDownItems(By locator) {
		int intCount = 0;
		List<WebElement> optionCount;
		try {
			WebElement element = driver.findElement(locator);
			if (element.isDisplayed()) {
				Select selObj = new Select(element);
				optionCount = selObj.getOptions();
				intCount = optionCount.size();
				logger.info("The listbox has " + intCount + " in the dropdown box");
				Runner.test.log(Status.PASS, "The listbox has " + intCount + " in the dropdown box");
			}

		}

		catch (NoSuchElementException e)

		{
			e.printStackTrace();
			System.err.format("No Element Found to perform selection" + e);
			Runner.test.log(Status.FAIL, "No Element Found to perform selection in the dropdown" + e);
		}

		return intCount;

	}

	public String getSelectedValueInDropBox(By locator, String value) {
		WebElement selectedOption = null;
		String selectedText = null;
		try {
			WebElement element = driver.findElement(locator);
			if (element.isDisplayed()) {
				Select selObj = new Select(element);
				selObj.selectByValue(value);
			selectedOption = selObj.getFirstSelectedOption();//getFirstSelectedOption();
				selectedText =selectedOption.getText();
				logger.info("User gets the slection as  " + selectedText + "from Dropdown");
				Runner.test.log(Status.PASS, "User gets the slection as  " + selectedText + "from Dropdown");
			}
		}

		catch (NoSuchElementException e)

		{
			e.printStackTrace();
			System.err.format("No Element Found to perform selection" + e);
			Runner.test.log(Status.FAIL, "No Element Found to perform selection in the dropdown" + e);
		}
		return selectedText;

	}

	public void verifySelectedValueInDropBox(By locator, String expectedValSelected) {
		String actualValSelected = getSelectedValueInDropBox(locator, expectedValSelected);
		Assert.assertEquals(expectedValSelected, actualValSelected);
		logger.info("the expected value: " + expectedValSelected + " matches the actual value: " + actualValSelected);
		Runner.test.log(Status.PASS,
				"the expected value: " + expectedValSelected + " matches the actual value: " + actualValSelected);

	}

	public String getTextFromTextBox(By locator) {
		String value = null;
		try {
			WebElement element = driver.findElement(locator);
			if (element.isDisplayed()) {
				value = element.getAttribute("value");
				// System.out.println(value);
				logger.info("User gets the value as  " + value + " the text box");
				Runner.test.log(Status.PASS, "User gets the value as  " + value + " the text box");
			}
		}

		catch (NoSuchElementException e)

		{
			e.printStackTrace();
			System.err.format("No Element Found to perform selection" + e);
			Runner.test.log(Status.FAIL, "No Element Found to perform selection in the dropdown" + e);
		}
		return value;

	}

	public void verifyTextValueInTextBox(By locator, String expectedValDisplayed) {
		String actualValDisplayed = getTextFromTextBox(locator);
		Assert.assertEquals(expectedValDisplayed, actualValDisplayed);
		logger.info("the expected value: " + expectedValDisplayed + " matches the actual value: " + actualValDisplayed);
		Runner.test.log(Status.PASS,
				"the expected value: " + expectedValDisplayed + " matches the actual value: " + actualValDisplayed);

	}

	public String getAttributeValue(By locator, String attributeName) {
		String attributeValue = null;
		try {
			WebElement element = driver.findElement(locator);
			if (element.isDisplayed()) {
				attributeValue = element.getAttribute(attributeName);
				// System.out.println(value);
				logger.info("User gets the value as  " + attributeValue + " for the webelement");
				Runner.test.log(Status.PASS, "User gets the value as  " + attributeValue + "for the webelement");
			}
		}

		catch (NoSuchElementException e)

		{
			e.printStackTrace();
			System.err.format("No Element Found to perform selection" + e);
			Runner.test.log(Status.FAIL, "No Element Found to perform selection in the dropdown" + e);
		}
		return attributeValue;

	}

	public final int nextRandomIntegerInRange(int iStart, int iEnd) {
		Random random = new Random();

		return showRandomInteger(iStart, iEnd, random);
	}

	private static int showRandomInteger(int aStart, int aEnd, Random aRandom) {
		if (aStart > aEnd) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}
		// get the range, casting to long to avoid overflow problems
		long range = (long) aEnd - (long) aStart + 1;
		// compute a fraction of the range, 0 <= frac < range
		long fraction = (long) (range * aRandom.nextDouble());
		int randomNumber = (int) (fraction + aStart);
		logger.info("Generated Random No-> " + randomNumber);
		return randomNumber;
	}

	public String getCssValue(By locator, String attributeName) {
		String attributeValue = null;
		try {
			WebElement element = driver.findElement(locator);
			if (element.isDisplayed()) {
				attributeValue = element.getCssValue(attributeName);
				// System.out.println(value);
				logger.info("User gets the value as  " + attributeValue + "for the webelement");
				Runner.test.log(Status.PASS, "User gets the value as  " + attributeValue + "for the webelement");
			}
		}

		catch (NoSuchElementException e)

		{
			e.printStackTrace();
			System.err.format("No Element Found to perform selection" + e);
			Runner.test.log(Status.FAIL, "No Element Found to perform selection in the dropdown" + e);
		}
		return attributeValue;

	}

	public void verifyCssValue(By locator, String attributeName, String expectedAttributeValue) {
		String actualAttributeValue = getCssValue(locator, attributeName);
		Assert.assertEquals(expectedAttributeValue, actualAttributeValue);
		logger.info(
				"the expected value: " + expectedAttributeValue + " matches the actual value: " + actualAttributeValue);
		Runner.test.log(Status.PASS,
				"the expected value: " + expectedAttributeValue + " matches the actual value: " + actualAttributeValue);

	}

	public String getAttributeValue(WebElement element, String attributeName) {
		String attributeValue = null;
		try {
			if (element.isDisplayed()) {
				attributeValue = element.getAttribute(attributeName);
				System.out.println(attributeValue);
				logger.info("User gets the value as  " + attributeValue + "for the webelement");
				Runner.test.log(Status.PASS, "User gets the value as  " + attributeValue + "for the webelement");
			}
		}

		catch (NoSuchElementException e)

		{
			e.printStackTrace();
			System.err.format("No Element Found to perform selection" + e);
			Runner.test.log(Status.FAIL, "No Element Found to perform selection in the dropdown" + e);
		}
		return attributeValue;

	}
	
	

	public void assertAttributeValue(By locator, String attributeName, String expectedAttributeValue) {
		String actualAttributeValue = getAttributeValue(locator, attributeName);
		Assert.assertEquals(actualAttributeValue, expectedAttributeValue);
		logger.info(
				"the expected value: " + expectedAttributeValue + " matches the actual value: " + actualAttributeValue);
		Runner.test.log(Status.PASS,
				"the expected value: " + expectedAttributeValue + " matches the actual value: " + actualAttributeValue);

	}

	public boolean compareAttributeValue(By locator, String attributeName, String expectedAttributeValue) {
		String actualAttributeValue;
		boolean value = false;
		try {
			WebElement element = driver.findElement(locator);
			if (element.isDisplayed()) {
				actualAttributeValue = element.getAttribute(attributeName);
				if (actualAttributeValue == expectedAttributeValue) {
					logger.info("the expected value: " + expectedAttributeValue + " matches the actual value: "
							+ actualAttributeValue);
					Runner.test.log(Status.PASS, "the expected value: " + expectedAttributeValue
							+ " matches the actual value: " + actualAttributeValue);
					value = true;
				}

			}
		} catch (NoSuchElementException e)

		{
			e.printStackTrace();
			System.err.format("No Element Found to perform selection" + e);
			Runner.test.log(Status.FAIL, "No Element Found to perform selection in the dropdown" + e);
		}
		return value;

	}
	
	
	public String getCssValue(WebElement element, String sPropertyName) {
		String propertyValue = null;
		try {
			if (element.isDisplayed()) {
				propertyValue = element.getCssValue(sPropertyName);
//				System.out.println(propertyValue);
				logger.info("User gets the value as  " + propertyValue + "for the webelement");
				Runner.test.log(Status.PASS, "User gets the value as  " + propertyValue + "for the webelement");
			}
		}

		catch (NoSuchElementException e)

		{
			e.printStackTrace();
			System.err.format("No Element Found to perform selection" + e);
			Runner.test.log(Status.FAIL, "No Element Found to perform selection in the dropdown" + e);
		}
		return propertyValue;

	}

	public boolean getCheckBoxState(By locator) {
		boolean value = false;
		try {
			WebElement element = driver.findElement(locator);
			if (element.isDisplayed()) {
				value = element.isSelected();
				// System.out.println(value);
				logger.info("User gets the value as  " + value + " from the check box");
				Runner.test.log(Status.PASS, "User gets the value as  " + value + " from the check box");
			}
		}

		catch (NoSuchElementException e)

		{
			e.printStackTrace();
			System.err.format("No Element Found to perform selection" + e);
			Runner.test.log(Status.FAIL, "No Element Found to perform selection in the dropdown" + e);
		}
		return value;

	}

	public boolean getRadioButtonState(By locator) {
		boolean value = false;
		try {
			WebElement element = driver.findElement(locator);
			if (element.isDisplayed()) {
				value = element.isSelected();
				// System.out.println(value);
				logger.info("User gets the value as  " + value + " from the radio button");
				Runner.test.log(Status.PASS, "User gets the value as  " + value + " from the radio button");
			}
		}

		catch (NoSuchElementException e)

		{
			e.printStackTrace();
			System.err.format("No Element Found to perform selection" + e);
			Runner.test.log(Status.FAIL, "No Element Found to perform selection in the dropdown" + e);
		}
		return value;

	}

	public void pageRefresh() throws InterruptedException {
		driver.navigate().refresh();
		Thread.sleep(1000);
		logger.info("User refreshes the page");
		Runner.test.log(Status.PASS, "User refreshes the page");
	}

	public void pageBack() {
		driver.navigate().back();
		logger.info("User clicks the back button of the browser");
		Runner.test.log(Status.PASS, "User clicks the back button of the browser");
	}

	public void pageForward() {
		driver.navigate().forward();
		logger.info("User clicks the forward button of the browser");
		Runner.test.log(Status.PASS, "User clicks the forward button of the browser");
	}

	public void launchApplication() throws InterruptedException {

		driver.navigate().to(url);
		logger.info("User navigates to the URL: " + url);
		Runner.test.log(Status.PASS, "User navigates to the URL: " + url);

		if (isAlertPresent()) {
			acceptAlert();
			Thread.sleep(1000);
		}

	}

	public void launchAdminApplication() {

		/*driver.navigate().to(adminapplicationURL);
		logger.info("User navigates to the URL: " + adminapplicationURL);
		Runner.test.log(Status.PASS, "User navigates to the URL: " + adminapplicationURL);*/
		driver.navigate().to(url);
		logger.info("User navigates to the URL: " + url);
		Runner.test.log(Status.PASS, "User navigates to the URL: " + url);

	}

	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} // try
		catch (NoAlertPresentException Ex) {
			return false;
		} // catch
	}

	public void verifyStringValue(String expected, String actual) {

		Assert.assertEquals(expected, actual);
		logger.info("the expected value: " + expected + " matches the actual value: " + actual);
		Runner.test.log(Status.PASS, "the expected value: " + expected + " matches the actual value: " + actual);

	}

	public void verifyIntegerValue(int expected, double d) {
		sAssert.assertEquals(expected, d);
		// Assert.assertEquals(expected, actual);
		logger.info("the expected value: " + expected + " matches the actual value: " + d);
		Runner.test.log(Status.PASS, "the expected value: " + expected + " matches the actual value: " + d);

	}

	public void verifyCount(double expected, double actual) {

		Assert.assertEquals(expected, actual);
		logger.info("the expected value: " + expected + " matches the actual value: " + actual);
		Runner.test.log(Status.PASS, "the expected value: " + expected + " matches the actual value: " + actual);

	}

	public void verifyCount(int expected, int actual) {

		Assert.assertEquals(expected, actual);
		logger.info("the expected value: " + expected + " matches the actual value: " + actual);
		Runner.test.log(Status.PASS, "the expected value: " + expected + " matches the actual value: " + actual);

	}

	public void assertCollection(Collection actual, Collection expected) {
		Assert.assertEquals(actual, expected);
		logger.info("The actual collection value: " + expected + " matches the expected collection value: " + actual);
		Runner.test.log(Status.PASS,
				"The actual collection value: " + expected + " matches the expected collection value: " + actual);

	}

	public void verifyCountValue(int expected, int actual) {

		if (expected == actual) {

			logger.info("the expected value: " + expected + " matches the actual value: " + actual);
			Runner.test.log(Status.PASS, "the expected value: " + expected + " matches the actual value: " + actual);
		} else if (expected < actual) {
			logger.info("the expected value: " + expected + " matches the actual value: " + actual);
			Runner.test.log(Status.PASS, "the expected value: " + expected + " matches the actual value: " + actual);

		} else {
			logger.info("the expected value: " + expected + " does not match  the actual value: " + actual);
			Runner.test.log(Status.FAIL,
					"the expected value: " + expected + " does not match the actual value: " + actual);
		}

	}

	public void verifyWebElementStateDiffers(boolean expected, boolean actual) {
		Assert.assertNotSame(expected, actual);
		logger.info("the expected value: " + expected + " does not match the actual value: " + actual);
		Runner.test.log(Status.PASS,
				"the expected value: " + expected + " does not match matche the actual value: " + actual);

	}

	public String getAlertMessage() {
		String alertText = null;
		boolean isAlertAvailable = isAlertPresent();
		if (isAlertAvailable) {
			alertText = driver.switchTo().alert().getText();
			// System.out.println(alertText);
		}
		return alertText;
	}

	public void verifyAlertMessage(String expectedMessage) {
		Assert.assertEquals(expectedMessage, getAlertMessage());

	}
	

	public void acceptAlert() {
		boolean isAlertAvailable = isAlertPresent();
		if (isAlertAvailable) {
			driver.switchTo().alert().accept();
		}
	}

	public void dissmisAlert() {
		boolean isAlertAvailable = isAlertPresent();
		if (isAlertAvailable) {
			driver.switchTo().alert().dismiss();
		}
	}

	public void switchToChildWindow() {

		Set<String> s1 = driver.getWindowHandles();
		Iterator<String> I1 = s1.iterator();

		while (I1.hasNext()) {

			String child_window = I1.next();

			// Here we will compare if parent window is not equal to child
			// window then we will close

			if (!getParentWindow().equals(child_window)) {
				driver.switchTo().window(child_window);

			}
		}
	}

	public String getMainWindowHandle(WebDriver driver) {
		return driver.getWindowHandle();
	}

	public static boolean closeAllOtherWindows(WebDriver driver, String openWindowHandle) {
		Set<String> allWindowHandles = driver.getWindowHandles();
		for (String currentWindowHandle : allWindowHandles) {
			if (!currentWindowHandle.equals(openWindowHandle)) {
				driver.switchTo().window(currentWindowHandle);
				driver.close();
			}
		}

		driver.switchTo().window(openWindowHandle);
		if (driver.getWindowHandles().size() == 1)
			return true;
		else
			return false;
	}

	public void closeChildWindow() {
		driver.close();
	}

	public void switchMainWindow(String wparent) {
		driver.switchTo().window(wparent);
	}

	public boolean verifyObjectWaterMark(By locator, String expectedObjectWaterMark) {

		logger.info("User verifies the test object Label as: " + expectedObjectWaterMark);
		return getObjectlLabel(locator).contains(expectedObjectWaterMark.toLowerCase());
	}

	public int getAllRowsInTable(By locator) {

		Integer intRow;
		List<WebElement> rows = driver.findElements(locator);
		// System.out.println("Total number of rows :"+ rows.size());
		intRow = rows.size();
		return intRow;
	}

	public String getHiddenValue(By locator)

	{

		WebElement hiddenElement = driver.findElement(locator);
		String value = hiddenElement.getAttribute("value");
		// System.out.println(value);
		return value;
	}

	public void verifyTextPresentInPageSource(String expectedText)

	{
		boolean b = false;
		logger.info("The text value to be searched inside page source is: " + expectedText);

		try {
			b = driver.getPageSource().contains(expectedText);
			if (b == true) {
				Runner.test.log(Status.PASS, "the expected text : " + expectedText + " found in the Page Source");
			}

		} catch (Exception e) {
			Runner.test.log(Status.FAIL,
					"Something Went wrong with finding the Text on the Page, Exception Occured : " + e);
		}

	}

	public void verifyChkBoxSelected(By locator)

	{
		WebElement checkBox = driver.findElement(locator);
		boolean chkboxValue = checkBox.isSelected();
		logger.info("The checkbox value is: " + chkboxValue);
		Assert.assertTrue(chkboxValue);
	}

	public boolean verifyRadioButtonSelected(By locator)

	{
		WebElement rdoBtn = driver.findElement(locator);
		boolean rdoBtnValue = rdoBtn.isSelected();
		logger.info("The Radio button value is: " + rdoBtnValue);
		Assert.assertTrue(rdoBtnValue);
		Runner.test.log(Status.PASS, "The checkbox value is: " + rdoBtnValue);
		return rdoBtnValue;
	}

	public boolean verifyRadioButtonSelected(WebElement element)

	{
		boolean rdoBtnValue = element.isSelected();
		logger.info("The Radio button value is: " + rdoBtnValue);
		Assert.assertTrue(rdoBtnValue);
		Runner.test.log(Status.PASS, "The checkbox value is: " + rdoBtnValue);
		return rdoBtnValue;
	}

	public void verifyRadioButtonNotSelected(By locator)

	{
		WebElement rdoBtn = driver.findElement(locator);
		boolean rdoBtnValue = rdoBtn.isSelected();
		logger.info("The Radio button value is: " + rdoBtnValue);
		Assert.assertFalse(rdoBtnValue);
		Runner.test.log(Status.PASS, "The checkbox value is: " + rdoBtnValue);
	}

	public void checkRadioButtonSelected(By locator)

	{
		WebElement rdoBtn = driver.findElement(locator);
		boolean rdoBtnValue = rdoBtn.isSelected();
		String rdoBtnChecked = rdoBtn.getAttribute("checked");
		logger.info("The Radio button value is: " + rdoBtnValue);
		if (rdoBtnValue = true || rdoBtnChecked == "checked") {
			Runner.test.log(Status.PASS, "The radio button value is: " + rdoBtnChecked);
		}
		// Assert.assertTrue(rdoBtnValue) ;
		Runner.test.log(Status.PASS, "The radio button is selected");
	}

	public void checkRadioButtonNotSelected(By locator)

	{
		WebElement rdoBtn = driver.findElement(locator);
		boolean rdoBtnValue = rdoBtn.isSelected();
		String rdoBtnChecked = rdoBtn.getAttribute("checked");
		logger.info("The Radio button value is: " + rdoBtnValue);
		if (rdoBtnValue = false || rdoBtnChecked == "") {
			Runner.test.log(Status.PASS, "The radio button value is: " + rdoBtnChecked);
		}
		// Assert.assertTrue(rdoBtnValue) ;
		Runner.test.log(Status.PASS, "The radio button is not selected");
	}

	public void verifyChkBoxNotSelected(By locator)
    {
		WebElement checkBox = driver.findElement(locator);
		boolean chkboxValue = checkBox.isSelected();
		logger.info("The checkbox value is: " + chkboxValue);
		Assert.assertFalse(chkboxValue);
	}

	// Click on the left apply button
	public void clickElementUsingJsExecutor(By locator) {
		WebElement element = driver.findElement(locator);
		/*
		 * if (butnJobApplyLeft.isDisplayed()){
		 * logger.info("Left Job apply button webelement is displayed");
		 */
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
		// js.executeScript("document.querySelector(\"a[id$='JD_main_apply_button']\").click()");
		logger.info("User clicks the job Right job apply button in the Job Details page");
		// butnJobApplyLeft.click();
	}

	public void nameOfLinks() {
		String linkText = null;
		ArrayList<WebElement> links = new ArrayList<WebElement>();

		links = (ArrayList<WebElement>) driver.findElements(By.tagName("a"));

		Iterator<WebElement> I1 = links.iterator();

		while (I1.hasNext()) {

			linkText = I1.next().getText();

			System.out.println(linkText);

		}

	}

	public List<WebElement> findAllLinks(WebDriver driver)

	{

		List<WebElement> elementList = new ArrayList<WebElement>();

		elementList = driver.findElements(By.tagName("a"));

		elementList.addAll(driver.findElements(By.tagName("img")));

		List<WebElement> finalList = new ArrayList<WebElement>();

		for (WebElement element : elementList)

		{

			if (element.getAttribute("href") != null)

			{

				finalList.add(element);

			}

		}

		return finalList;

	}

	public static List<WebElement> collectAllSimillarElements(WebDriver driver, By locator)

	{

		List<WebElement> elementList = new ArrayList<WebElement>();

		elementList = driver.findElements(locator);
		return elementList;

	}

	public List<WebElement> countAllSimillarElements(WebDriver driver, By locator)

	{
		
		List<WebElement> elementList = new ArrayList<WebElement>();
		
		elementList = driver.findElements(locator);
		for (int i = 0; i < elementList.size(); i++) 
		{
			Runner.test.log(Status.INFO, ": is displayed on the Page\n"+ elementList.get(1));
			//countelements = elementList.size();
		}
		
		return elementList;

	}

	public WebElement getNthElements(WebDriver driver, By locator, int Index)

	{
		// int countelements;
		List<WebElement> elementList = new ArrayList<WebElement>();
		elementList = driver.findElements(locator);
		// countelements=elementList.size();
		WebElement element = elementList.get(Index);
		return element;

	}

	public List<String> getAllOptionsInListBox(By locator) {
		List<String> options = new ArrayList<String>();
		for (WebElement option : new Select(driver.findElement(locator)).getOptions()) {
			String txt = option.getText();
			if (option.getAttribute("value") != "")
				options.add(option.getText());
		}
		Runner.test.log(Status.INFO, "The list box contains the following values : " + options);
		return options;
	}

	public String isLinkBroken(URL url) throws Exception

	{

		// url = new URL("http://yahoo.com");

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		try

		{

			connection.connect();

			String response = connection.getResponseMessage();

			connection.disconnect();

			return response;

		}

		catch (Exception exp)

		{

			return exp.getMessage();

		}

	}

	public void retryingFindClick(By locator) {
		boolean result = false;
		int attempts = 0;
		WebElement element = driver.findElement(locator);
		while (attempts < 2) {
			try {
				element.click();
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}
		// return result;
	}

	public void verifyTextPresentInPage(String expectedText)

	{
		logger.info("The text value to be searched  inside page: " + expectedText);
		String bodyText = driver.findElement(By.tagName("body")).getText();
		Assert.assertTrue(bodyText.contains(expectedText), "Text not found!");
		Runner.test.log(Status.PASS, "The text: " + expectedText + " appears in the page.");

	}

	public void CheckBoxAll() {
		List<WebElement> checkBoxList = driver
				.findElements(By.xpath("//input[@type='checkbox' and @checked='checked']"));
		for (WebElement checkBox : checkBoxList) {
			checkBox.click();
		}

	}

	
	public String getLocatorProperty(By locator)
	{
		String strValueLocator = null;
		strValueLocator = locator.toString();
		return strValueLocator;
	}
	public String getObjectText(By locator) {

		String title = null;

		try {
			driver.findElement(locator).isDisplayed();
			title = driver.findElement(locator).getText();
			//title =driver.findElement(locator).getAttribute("value");
			logger.info("Message displaying in the Grid: " + title);
			
			System.out.println(title);
			Runner.test.log(Status.PASS, "Message displaying in the Grid: : " + title);
		}

		catch (Exception e) {
			e.printStackTrace();
			logger.info("User gets a blank test object Label");
			Runner.test.log(Status.FAIL, "User gets a blank test object Label");
		}

		
	return title;
	}	
		public void nameOfLinks(By locator)
		{
			String linkText = null;
			ArrayList<WebElement> links = new ArrayList<WebElement>();

			links = (ArrayList<WebElement>) driver.findElements(locator);

			Iterator<WebElement> I1 = links.iterator();

			while (I1.hasNext()) {

				linkText = I1.next().getText();

				//System.out.println(linkText);
				Runner.test.log(Status.PASS, "Module visible : " + linkText);
				

			}

		}
	
		
		public String getsubModulesText(By locator) {

			String title = null;

			try {
				driver.findElement(locator).isDisplayed();
				title = driver.findElement(locator).getText();
				//title =driver.findElement(locator).getAttribute("value");
				logger.info("Message displaying in the Grid: " + title);
				
				System.out.println(title);
				Runner.test.log(Status.PASS, "SubModules Visible : \n"+title);
			}

			catch (Exception e) {
				e.printStackTrace();
				logger.info("User gets a blank test object Label");
				Runner.test.log(Status.FAIL, "User gets a blank test object Label");
			}

			return title;

		}

	}
	
	
	


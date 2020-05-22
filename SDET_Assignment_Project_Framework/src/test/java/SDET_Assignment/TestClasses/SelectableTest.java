package SDET_Assignment.TestClasses;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import BaseExecutor.BaseSetup;
import BaseExecutor.Runner;
import SDET_Assignment.PageClasses.SelectablePageObjects;
import TestUtility.PageUtil;

public class SelectableTest extends BaseSetup
{
   private PageUtil pageUtil;
   private SelectablePageObjects  selectablePage;
	
public SelectableTest(WebDriver driver)
{
this.pageUtil = new PageUtil(driver);
this.selectablePage = new SelectablePageObjects(driver);
}

public void testSelectable() throws Exception
{
Runner.test.log(Status.INFO,"This is DemoQA Selectable Test case");
pageUtil.launchApplication();
pageUtil.click(selectablePage.getSelectebale());
List<WebElement>items= PageUtil.collectAllSimillarElements(driver, selectablePage.getItemNames());
for(int i=0;i<items.size();i++)
{
	PageUtil.click(items.get(i));
	System.out.println(items.get(i).getText());
}

} 
public void testContactForm() throws InterruptedException
{
	Runner.test.log(Status.INFO,"This is DemoQA Contact Form Test case");
	pageUtil.launchApplication();
	pageUtil.click(selectablePage.getHTMLForm());
	pageUtil.enterTextValue(selectablePage.EnterfirstName(), "Nikhil");
	pageUtil.enterTextValue(selectablePage.EnterlastName(), "Kumar");
	pageUtil.enterTextValue(selectablePage.EnterCountryName(), "India");
	pageUtil.enterTextValue(selectablePage.EnterSubject(), "this is test subject");
	String text= driver.findElement(By.partialLinkText("Google")).getText();
    System.out.println(text);
    WebElement link = driver.findElement(By.partialLinkText("Google"));
    Actions action = new Actions(driver);
    action.keyDown(Keys.CONTROL).click(link).keyUp(Keys.CONTROL).build().perform();
    ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
    driver.switchTo().window(tabs.get(1)); //switches to new tab
    System.out.println(driver.getTitle());
    pageUtil.verifyCurrentPageTitle("Google");
   pageUtil.getCurrentPageUrl();
    driver.close();
    driver.switchTo().window(tabs.get(0));
   
    pageUtil.verifyCurrentPageTitle("HTML contact form – ToolsQA – Demo Website to Practice Automation");
    System.out.println(driver.getTitle());
    pageUtil.getCurrentPageUrl();
     String text1= driver.findElement(By.partialLinkText("is here")).getText();
	  System.out.println(text1);
	  WebElement link2 = driver.findElement(By.partialLinkText("is here"));
	  Actions action1 = new Actions(driver);
	  action1.keyDown(Keys.CONTROL).click(link2).keyUp(Keys.CONTROL).build().perform();
	  ArrayList<String> tabs1 = new ArrayList<String> (driver.getWindowHandles());
	  driver.switchTo().window(tabs1.get(1)); //switches to new tab
	  pageUtil.verifyCurrentPageTitle("Google");
	   pageUtil.getCurrentPageUrl();
	  driver.close();
     driver.switchTo().window(tabs1.get(0));
     pageUtil.verifyCurrentPageTitle("HTML contact form – ToolsQA – Demo Website to Practice Automation");
     pageUtil.getCurrentPageUrl();
	pageUtil.click(selectablePage.SubmitForm());

}
public void testDragandDrop() throws InterruptedException
{
	Runner.test.log(Status.INFO,"This is DemoQA Drag and Drop Test case");
	pageUtil.launchApplication();
	pageUtil.click(selectablePage.getDroppable());
	Actions act=new Actions(driver);
	String droptext= pageUtil.getObjectlLabel(selectablePage.getDropDestination());
	Runner.test.log(Status.INFO, "Before Drop text visible >"+droptext);
	act.dragAndDrop(driver.findElement((selectablePage.getDragSource())),driver.findElement(selectablePage.getDropDestination())).build().perform();
	Thread.sleep(2000);
	//act.dragAndDrop(selectablePage.getDragSource(), selectablePage.getDropDestination()).build().perform();
	String droptextAfter= pageUtil.getObjectlLabel(selectablePage.getDropDestination());
	Runner.test.log(Status.INFO, "After Drop text visible >"+droptextAfter);
	
}
public void selectMenuTest() throws InterruptedException
{
	Runner.test.log(Status.INFO,"This is DemoQA Drag and Drop Test case");
	pageUtil.launchApplication();
	pageUtil.click(selectablePage.getSelectMenu());
	Thread.sleep(2000);
	List<WebElement>dropdowntriangle = PageUtil.collectAllSimillarElements(driver, selectablePage.selectdropdown1());
	PageUtil.click(dropdowntriangle.get(0));
	//pageUtil.click(selectablePage.selectdropdown1());
	List<WebElement>dropdownvalues = PageUtil.collectAllSimillarElements(driver, selectablePage.getDropdownvalue());
	for(int i=0;i<dropdownvalues.size();i++)
	{ 
		System.out.println(dropdownvalues.get(i).getText());
		if(dropdownvalues.get(i).getText().equalsIgnoreCase("Slow"))
		{
		PageUtil.click(dropdownvalues.get(i));
		break;
		}
	}
	Thread.sleep(2000);
	PageUtil.click(dropdowntriangle.get(1));
	List<WebElement>dropdownvalues1 = PageUtil.collectAllSimillarElements(driver, selectablePage.getDropdownvalue());
	for(int i=0;i<dropdownvalues1.size();i++)
	{
		if(dropdownvalues1.get(i).getText().equalsIgnoreCase("ui.jQuery.js"))
		{
		PageUtil.click(dropdownvalues1.get(i));
		break;
		}
	}
	Thread.sleep(2000);
	PageUtil.click(dropdowntriangle.get(2));
	List<WebElement>dropdownvalues2 = PageUtil.collectAllSimillarElements(driver, selectablePage.getDropdownvalue());
	for(int i=0;i<dropdownvalues2.size();i++)
	{
		if(dropdownvalues2.get(i).getText().equalsIgnoreCase("5"))
		{
		PageUtil.click(dropdownvalues2.get(i));
		break;
		}
	}
	Thread.sleep(2000);
	PageUtil.click(dropdowntriangle.get(3));
	List<WebElement>dropdownvalues3 = PageUtil.collectAllSimillarElements(driver, selectablePage.getDropdownvalue());
	for(int i=0;i<dropdownvalues3.size();i++)
	{
		if(dropdownvalues3.get(i).getText().equalsIgnoreCase("Mr."))
		{
		PageUtil.click(dropdownvalues3.get(i));
		break;
		}
	}
	

}
public void selectdatePicker() throws InterruptedException
{
	Runner.test.log(Status.INFO,"This is DemoQA DatePicker Test case");
	pageUtil.launchApplication();
	pageUtil.click(selectablePage.getDatePicker());
	pageUtil.enterTextValue(selectablePage.enterDate(), "07/13/1988");
	

	}

public void testRentalCarBlock() throws InterruptedException
{
	Runner.test.log(Status.INFO,"This is DemoQA Rental Car Block Test case");
	pageUtil.launchApplication();
	pageUtil.click(selectablePage.getControlGroup());
	pageUtil.click(selectablePage.selectcarDropdown());
	pageUtil.click(selectablePage.selectcarType());
	List<WebElement>features = PageUtil.collectAllSimillarElements(driver, selectablePage.getFeatureList());
	PageUtil.click(features.get(0));
	PageUtil.click(features.get(1));
	PageUtil.click(features.get(2));
	pageUtil.enterTextValue(selectablePage.enterNoOfCars(), 5);
	pageUtil.click(selectablePage.getBookNow1());
	

}
}

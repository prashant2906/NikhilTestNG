package BaseExecutor;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import SDET_Assignment.TestClasses.SelectableTest;
import TestUtility.CaptureScreenShot;

public class Runner extends BaseSetup {
	ExtentReports reports;
	public static ExtentTest test = null;
	public static String sPathforProperty;
	public ExtentHtmlReporter htmlReporter;
	String reportName;
	private SelectableTest testSelectable;
	
	@BeforeClass
	public void startup(ITestContext context)
	{
		testSelectable = new SelectableTest(driver);
	}
	@BeforeTest
	public void startReport() {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		List<String> aa = Arrays.asList(dateFormat.format(date).split(" "));
		String name = (aa.get(0) + "(" + aa.get(1) + ")").replace(":", "-");
		//reportFolderName = reportFilePath + "\\" + "Extent-Report-" + aa.get(0);
		reportName =  System.getProperty("user.dir") + "\\ExtentReports\\"+name+""+".html";
		htmlReporter = new ExtentHtmlReporter(new File(reportName));		
		htmlReporter.loadXMLConfig(new File(System.getProperty("user.dir") + "\\Configurations\\extent-config.xml"));
		htmlReporter.config().setChartVisibilityOnOpen(true);
	
			htmlReporter.setAppendExisting(true);
			reports = new ExtentReports();
			reports.setSystemInfo("Environment", "QA");
			reports.setSystemInfo("Operating System", 	System.getProperty("os.name"));
			reports.setSystemInfo("", "");	
			reports.attachReporter(htmlReporter);
	}
	@Test(enabled = true, priority = 1,description="To verify the Selectable")
	public void testDemoQA1() throws Exception
	{
		test = reports.createTest("DemoQA-Selectable");
	    testSelectable.testSelectable();
	   
	}
	@Test(enabled = true, priority = 2,description="To verify the Html Form")
	public void testDemoQA2() throws Exception
	{
		test = reports.createTest("DemoQA-HTMLFORM");
	    testSelectable.testContactForm();
	}
	/*@Test(enabled = true, priority = 3,description="To verify the drag and drop")
	public void testDemoQA3() throws Exception
	{
		test = reports.createTest("DemoQA-Drag&Drop");
	    testSelectable.testDragandDrop();
	}
	@Test(enabled = true, priority = 4,description="To verify the datePicker")
	public void testDemoQA4() throws Exception
	{
		test = reports.createTest("DemoQA-DatePicker");
	    testSelectable.selectdatePicker();
	}
	@Test(enabled = true, priority = 5,description="To verify the selectMenu")
	public void testDemoQA5() throws Exception
	{
		test = reports.createTest("DemoQA-SelectMenu");
	    testSelectable.selectMenuTest();
	}
	@Test(enabled = true, priority = 6,description="To verify the Rental car")
	public void testDemoQA6() throws Exception
	{
		test = reports.createTest("DemoQA-RentalCarBlock");
	    testSelectable.testRentalCarBlock();
	}*/
	
	
	@AfterMethod
	public void getResult(ITestResult result) throws Exception {
		
		try {
			if (result.getStatus() == ITestResult.SUCCESS) {
				test.log(Status.PASS, "The Test Case named as : " + result.getName() + " is Passed");

			} else if (result.getStatus() == ITestResult.FAILURE) {
				test.log(Status.FAIL, "The Test Case named as : " + result.getName() + " is Failed");
				test.log(Status.FAIL, "Test Failure : " + result.getThrowable());
				//String filename = result.getMethod().getMethodName();
				String screenshotPath = CaptureScreenShot.captureScreenshot(driver, result.getName());
				if (!(driver == null))// if driver closed by manually or api failed then screenshot will not taken.
					test.addScreenCaptureFromPath(screenshotPath);

				
			} else if (result.getStatus() == ITestResult.SKIP) {
				test.log(Status.SKIP, "The Test Case named as : " + result.getName() + " is Skipped");

			}
		//driver.close();
		}
		
		catch (Exception e) 
		{
			// If user close the browser manually then will show the exception and quite the
			// driver
			System.out.println(e);
			driver.quit();
			driver = null;

		}
//	if (!(driver == null)) {
			// driver.manage().deleteAllCookies();
			//driver.close();
			//driver.quit();
			//driver = null;
	//	}
	
		//reports.removeTest(test);
		
		
	}
	
		/*if (result.getStatus() == ITestResult.FAILURE) {
			String screenshotPath = CaptureScreenShot.captureScreenshot(driver, result.getName());

			test.log(Status.FAIL, "The Test Case named as : " + result.getName() + " is Failed");
			test.log(Status.FAIL, "Test Failure : " + result.getThrowable());
			//String filename = result.getMethod().getMethodName();
			//ScreenShotLib sLib = new ScreenShotLib();
			if (!(driver == null))// if driver closed by manually or api failed then screenshot will not taken.
				test.addScreenCaptureFromPath(screenshotPath);
*/
	
	
	
	@AfterTest
	public void endreport() {
		reports.flush();
         driver.quit();
		
	}

}

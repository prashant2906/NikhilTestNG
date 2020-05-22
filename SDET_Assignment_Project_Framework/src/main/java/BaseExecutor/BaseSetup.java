package BaseExecutor;


import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import TestUtility.BrowserUtil;

public class BaseSetup {
	public static WebDriver driver;
	ExtentReports extentreps;
    ExtentTest extentTest;
	BrowserUtil browserUtil = new BrowserUtil(driver);
	//private static Logger logger = Logger.getLogger(BaseSetup.class);
	
	public WebDriver getDriver() 
	{
		return driver;
		
	}
	private void setDriver(String browserType, String appURL) throws Exception {
		System.out.println(browserType);
		switch (browserType) {
		case "chrome":
			driver = BrowserUtil.initChromeDriver(appURL);
			break;
			
		case "ie":
			driver = BrowserUtil.initIeDriver(appURL);
			break;
			
		case "firefox":
			driver = BrowserUtil.initFirefoxDriver(appURL);
			break;
			
		case "gecko":
			driver = BrowserUtil.initFirefoxGeckoDriver(appURL);
			break;
		
			
		default:
			System.out.println("browser : " + browserType
					+ " is invalid, Launching Firefox as browser of choice..");
			driver = BrowserUtil.initFirefoxDriver(appURL);
		}

}
	@Parameters({ "browserType", "appURL"})
	
	@BeforeTest()
	public void initializeTestBaseSetup(String browserType, String appURL )  {

		try {
			setDriver(browserType, appURL);
			
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
	       
	
		} catch (Exception e) {
			//logger.info("Error....." + e.getStackTrace());
		}
	}
	
	@BeforeMethod
	public void deleteCookies() 
	{
		driver.manage().deleteAllCookies();
	
	}
	
	
	/*@AfterClass
	public void tearDown() throws Exception {


		  driver.quit();
		 
	}*/
}

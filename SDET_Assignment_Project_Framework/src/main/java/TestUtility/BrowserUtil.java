package TestUtility;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class BrowserUtil {
	private static Logger logger = Logger.getLogger(BrowserUtil.class);

	static String chromeDriverPath = "..//SDET_Assignment_Project_Framework//Executables//";
	static String ieDriverPath = "E://Software//";
	static String geckoDriverPath = "E://Software//";
	WebDriver driver;

	public BrowserUtil(WebDriver driver) 
	{

		this.driver = driver;

	}

	public static WebDriver initChromeDriver(String appURL) {
		// System.out.println("Launching google chrome with new profile..");
		logger.info("Launching google chrome with new profile..");
		System.setProperty("webdriver.chrome.driver", chromeDriverPath + "chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("test-type");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();

		driver.navigate().to(appURL);
		return driver;
	}

	public static WebDriver initIeDriver(String appURL) {
		// System.out.println("Launching internet explorer with new profile..");
		logger.info("Launching internet explorer with new profile..");
		System.setProperty("webdriver.ie.driver", ieDriverPath + "IEDriverServer.exe");
		WebDriver driver = new InternetExplorerDriver();
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		return driver;
	}

	public static WebDriver initFirefoxDriver(String appURL) {
		logger.info("Launching Firefox browser..");
		FirefoxProfile profile = new FirefoxProfile();
		logger.info("Before Profile Creation");
		profile.setPreference("network.http.phishy-userpass-length", 255);
		profile.setPreference("network.automatic-ntlm-auth.trusted-uris", "https://sts.hays.com");
		logger.info("Before Initializing Firefox Driver Complete");
		WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		return driver;

	}

	public static WebDriver initFirefoxGeckoDriver(String appURL) {
		logger.info("Launching Firefox Gecko browser..");
		System.setProperty("webdriver.gecko.driver", geckoDriverPath + "geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		return driver;
	}

	public void deleteCookies() 
	{
		driver.manage().deleteAllCookies();
	}

}

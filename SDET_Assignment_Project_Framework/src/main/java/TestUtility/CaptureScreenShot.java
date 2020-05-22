package TestUtility;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class CaptureScreenShot {
	
	public static  String captureScreenshot(WebDriver driver,String screenshotName)
	{
	 
	try 
	{
	TakesScreenshot ts=(TakesScreenshot)driver;
	 
	File source=ts.getScreenshotAs(OutputType.FILE);
	String dest=	System.getProperty("user.dir") + "\\ExtentReports\\FailedCasesScreenshot\\"+screenshotName+ ".png";
	//String dest ="C:\\Automation Project\\SDET_Assignment_Project_Framework\\ExtentReports\\FailedCasesScreenshot\\"+screenshotName+ ".png";
			//+ "//  ..//SDET_Assignment_Project_Framework//ExtentReports//FailedCasesScreenshot//"+screenshotName+ ".png";
	
	File destination = new File(dest);
			
	FileUtils.copyFile(source, destination);
	 
	System.out.println("Screenshot taken");
	
	return dest;
	} 
	catch (Exception e)
	{
	 
	System.out.println("Exception while taking screenshot "+e.getMessage());
	
	return e.getMessage(); 
	}
	
}

}
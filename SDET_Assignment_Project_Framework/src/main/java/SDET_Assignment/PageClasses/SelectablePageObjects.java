package SDET_Assignment.PageClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import TestUtility.PropertyFiles;

public class SelectablePageObjects 
{
WebDriver driver;
public SelectablePageObjects(WebDriver driver) {
	this.driver= driver;
	
}
By linkselectable= By.cssSelector(PropertyFiles.propertiesFile().getProperty("lnkSelectable"));
By selectItems= By.xpath(PropertyFiles.propertiesFile().getProperty("selectItems"));
By linkHtmlContactForm= By.cssSelector(PropertyFiles.propertiesFile().getProperty("lnkhtmlform"));
By Firstname = By.cssSelector(PropertyFiles.propertiesFile().getProperty("FirstName"));
By LastName = By.cssSelector(PropertyFiles.propertiesFile().getProperty("LastName"));
By Country = By.cssSelector(PropertyFiles.propertiesFile().getProperty("Country"));
By Subject= By.cssSelector(PropertyFiles.propertiesFile().getProperty("Subject"));
By SubmitButton=By.cssSelector(PropertyFiles.propertiesFile().getProperty("SubmitButton"));
By partial1 = By.partialLinkText("Google");
By partial2 = By.partialLinkText("is here");
By droplink =  By.cssSelector(PropertyFiles.propertiesFile().getProperty("lnkdroppable"));
By dragItemSource =   By.cssSelector(PropertyFiles.propertiesFile().getProperty("draggable"));
By dropDest =   By.cssSelector(PropertyFiles.propertiesFile().getProperty("droppable"));
By lnkSelectMenu =   By.cssSelector(PropertyFiles.propertiesFile().getProperty("lnkSelectMenu"));
By dropdown1 =   By.cssSelector(PropertyFiles.propertiesFile().getProperty("dropdown1"));
By dropdown2 =   By.cssSelector(PropertyFiles.propertiesFile().getProperty("dropdown2"));
By dropdown3 =   By.cssSelector(PropertyFiles.propertiesFile().getProperty("dropdown3"));
By dropdown4 =   By.cssSelector(PropertyFiles.propertiesFile().getProperty("dropdown4"));
By datepicker = By.cssSelector(PropertyFiles.propertiesFile().getProperty("datepicker"));
By dateField =  By.cssSelector(PropertyFiles.propertiesFile().getProperty("dateField"));
By selectMenu =By.cssSelector(PropertyFiles.propertiesFile().getProperty("lnkSelectMenu"));
By getdropdownvalue = By.cssSelector(PropertyFiles.propertiesFile().getProperty("getdropdownvalue"));
By lnkControlGroup = By.cssSelector(PropertyFiles.propertiesFile().getProperty("lnkControlGroup"));
By dropdownCarselect =By.cssSelector(PropertyFiles.propertiesFile().getProperty("dropdownCarselect"));
By cartype =By.cssSelector(PropertyFiles.propertiesFile().getProperty("cartype"));
By listofSelections=By.cssSelector(PropertyFiles.propertiesFile().getProperty("listofSelections"));
By enterNoOfcars =By.cssSelector(PropertyFiles.propertiesFile().getProperty("enterNoOfcars"));
By BookNow1 =By.cssSelector(PropertyFiles.propertiesFile().getProperty("BookNow1"));
By BookNow2 =By.cssSelector(PropertyFiles.propertiesFile().getProperty("BookNow2"));


public By getBookNow1()
{
	return BookNow1;
	
}
public By getBookNow2()
{
	return BookNow2;
	
}
public By enterNoOfCars()
{
	return enterNoOfcars;
	
}

public By getFeatureList()
{
	return listofSelections;
	
}
public By selectcarDropdown()
{
	return dropdownCarselect;
	
}
public By selectcarType()
{
	return cartype;
	
}
public By getControlGroup()
{
	return lnkControlGroup;
	
}
public By getDropdownvalue()
{
	return getdropdownvalue;
	
}
public By selectdropdown1()
{
	return dropdown1;
	
}
public By selectdropdown2()
{
	return dropdown2;
	
}
public By selectdropdown3()
{
	return dropdown3;
	
}
public By selectdropdown4()
{
	return dropdown4;
	
}
public By getSelectMenu()
{
	return selectMenu;
	
}
public By enterDate()
{
	return dateField;
	
}
public By getDatePicker()
{
	return datepicker;
	
}


public By getDragSource()
{
	return dragItemSource;
	
}
public By getDropDestination()
{
	return dropDest;
	
}
public By getDroppable()
{
	return droplink;
	
}
public By EnterfirstName()
{
	return Firstname;
	
}
public By getPartialText1()
{
	return partial1;
	
}
public By getPartialText2()
{
	return partial2;
	
}
public By EnterlastName()
{
	return LastName;
	
}
public By EnterCountryName()
{
	return Country;
	
}
public By EnterSubject()
{
	return Subject;
	
}
public By SubmitForm()
{
	return SubmitButton;
	
}

public By getHTMLForm()
{
	return linkHtmlContactForm;
	
}
public By getItemNames()
{
	return selectItems;
}
public By getSelectebale()
{
	return linkselectable;			
}
}

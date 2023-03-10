package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

public class ElementUtil {
	
	private WebDriver driver;
	private JavaScriptUtil jsUtil;
	
	/**
	 * The Constructor is used to maintain same webdriver instance while calling ElementUtil in other class
	 * @param driver
	 */
	
	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}
	
	//Find Element Method
	public WebElement getElement(By locator) {
		   WebElement element = driver.findElement(locator);
		   if (Boolean.parseBoolean(DriverFactory.highlight)) {
		   jsUtil.flash(element);
		   }
		   return element;
	}
	
	//Find Element Method (over loading) 
	public WebElement getElement(By locator , int timeout) {
		return WaitForElementVisible(timeout,locator);
	}
	
	//Find Elements Method
	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}
	
	//Sendkeys Method
	public void doSendkeys(By locator, String value) {
		//getElement(locator).sendKeys(value);
		WebElement element = getElement(locator);
		element.clear();
		element.sendKeys(value);
		
		
	}
	
	//Sendkeys Using Actions Class
	public void doactionssendkeys(By Locator , String name) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(Locator), name).build().perform();
	}
	
	//Click Method
	public void doClick(By locator) {
		getElement(locator).click();
	}
	
	//Click Using Actions Class
	public void doactionsclick(By Locator) {
		Actions act = new Actions (driver);
		act.click(getElement(Locator)).build().perform();
	}
	
	
	//Get text Method
	public String dogettext(By locator) {
		return getElement(locator).getText();
	}
	
	//Is Displayed
	public boolean doisdisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}
	
	//Get Element Attribute
	public String getElementAttribute(By locator,String attributename) {
		String attributevalue = getElement(locator).getAttribute(attributename);
		System.out.println("The Attribute value:"+attributevalue);
		return attributevalue;
	}
	
	//Get Element Attributes
	public void getElementAttributes(By locator,String attributenames) {
		List<WebElement> alllinksattrbutes = getElements(locator);
		for (WebElement eleattributes : alllinksattrbutes) {
			String attributes = eleattributes.getAttribute(attributenames);
			String text = eleattributes.getText();
			System.out.println(attributes +"<--------->" +text);
		}
	}
	
	//Get Total Elements count
	public int getTotalElementCount(By locator) {
		List <WebElement> linkscount= getElements(locator);
		int count = linkscount.size();
		System.out.println("The Count of Elements:"+locator +"===" +count);
		return count;
	}
	
	//Get Elements Text List
    public List <String> getElementsTextList(By Locator) {
    	List <String> lists =new ArrayList<String>();
    	List <WebElement> linkscount= getElements(Locator);
    	for (WebElement textlist : linkscount) {
    		String text = textlist.getText();
    		lists.add(text);
    	}
    	return lists;
    }
	
    //Select by Index
    public void doSelectDropdownByIndex(By Locator,int index) {
    	Select select = new Select(getElement(Locator));
    	select.selectByIndex(index);
    }
    
    //Select by Value
    public void doSelectDropdownByValue(By Locator,String value) {
    	Select select = new Select(getElement(Locator));
    	select.selectByValue(value);
    }
    
    //Select by Visibletext
    public void doSelectDropdownByVisibleText(By Locator,String Text) {
    	Select select = new Select(getElement(Locator));
    	select.selectByVisibleText(Text);
    }
    
    //Return Dropdown options
    public List<WebElement> getDropdownOptionsList(By Locator) {
    	Select select = new Select(getElement(Locator)); 
    	return select.getOptions();
    }
    
    //Get the Size of Dropdown values
    public int getTotalDropDownOptions(By Locator) {
    	int optionscount = getDropdownOptionsList(Locator).size();
    	return optionscount;
    }
    
    //Get Text of optionslist
    public List<String> getDropdownOptionsTextList(By Locator) {
    	List<String> optionslist = new ArrayList<String>();
    	List<WebElement> optionstext = getDropdownOptionsList(Locator);
    	for(WebElement e : optionstext) {
    		String acttext = e.getText();
    		System.out.println("The Actual Text values:"+acttext);
    		optionslist.add(acttext);
    	}
    	return optionslist;
    	
    }
    
    //Select Dropdown Value
    public void SelectDropDownValue(By Locator,String countryname) {
    	List<WebElement> optionstext = getDropdownOptionsList(Locator);
    	for(WebElement e : optionstext) {
    		String acttext = e.getText();
    		System.out.println("The Actual Text values:"+acttext);
    		if (acttext.equals(countryname)) {
    			e.click();
    			break;
    		}
    	}
    }
    
    //Get list without using selecttag
    public void getDropdownoptionsfindelements(By Locator) {
    	List<WebElement> alllists = getElements(Locator);
    	for(WebElement e :alllists) {
    		String text = e.getText();
    		System.out.println(text);
    	}
    }
    
    //Get the list of all Google Footer links 
    public List<String> getgooglefooterlist(By Locator) {
    	List<String> googlefotterlist = new ArrayList<String>();
    	List<WebElement> footerlinks = getElements(Locator);
    	for(WebElement e : footerlinks) {
    		String footertext = e.getText();
    		googlefotterlist.add(footertext);
    		
    	}
    	return googlefotterlist;
    }
    	
    //Get the Googlesearch
    public void dosearch(By Locator , String keyword) {
    	List<WebElement> googlesearch = getElements(Locator);
    	for(WebElement e : googlesearch) {
    		System.out.println("The count of google search:"+googlesearch.size());
    		String googlesearchtext = e.getText();
    		if(googlesearchtext.contentEquals(keyword)) {
    			e.click();
    			break;
    		}
    	}
    }
    
    //##################################Wait Utils##################################
    
    /**
	 * An expectation for checking that an element is present on the DOM of a page. 
	 * This does notnecessarily mean that the element is visible.
	 * @param TimeOut
	 * @param Locator
	 * @return
	 */
	
	//Custom Utility 
	public  WebElement WaitForElementPresence(int TimeOut,By Locator) {
		
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(TimeOut));
		
		return wait.until(ExpectedConditions.presenceOfElementLocated(Locator));
	}

	
	//#######################################################################
	
	/**
	 * An expectation for checking that an element is present on the DOM of a page and visible.
	 * Visibility means that the element is not only displayed but also 
	 * has a height and width that isgreater than 0.
	 * @param TimeOut
	 * @param Locator
	 * @return
	 */
	
	//Custom Utility 
        public  WebElement WaitForElementVisible(int TimeOut,By Locator) {
		
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(TimeOut));
		
		return wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
	}
        
   //#############################Waits for Alerts################################
        
        public Alert WaitForAlertPresence(int timeOut) {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeOut));
    		return wait.until(ExpectedConditions.alertIsPresent());
    	}
    	
    	public String getAlertText(int timeOut) {
    		return WaitForAlertPresence(timeOut).getText();
    	}
    	
    	public void acceptAlert(int timeOut) {
    		 WaitForAlertPresence(timeOut).accept();
    	}
    	
    	public void dismessAlert(int timeout) {
    		WaitForAlertPresence(timeout).dismiss();
    	}
    	
    	public void alertSendkeys(int timeout,String value) {
    		WaitForAlertPresence(timeout).sendKeys(value);
    	}

    //#################################Waits for Title##############################
    	
    	public  String WaitForTitleContainsAndFetch(int timeout,String titleFractionvalue) {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
    		wait.until(ExpectedConditions.titleContains(titleFractionvalue));   //Checks the title contains password
    		return driver.getTitle();
    	}
    	
    	public  String WaitForTitleIsAndFetch(int timeout,String titlevalue) {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
    		wait.until(ExpectedConditions.titleIs(titlevalue));   //Exact match of title
    		return driver.getTitle();
    	}

    //################################Waits for URL#####################################
    	
    	public String WaitForURLContainsAndFetch(int timeout,String UrlFractionvalue) {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
    		wait.until(ExpectedConditions.urlContains(UrlFractionvalue));   //Checks the url contains <text>
    		return driver.getCurrentUrl();
    	}
    	
    	public  Boolean WaitForURLContains(int timeout,String UrlFractionvalue) {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
    		return wait.until(ExpectedConditions.urlContains(UrlFractionvalue));   //Checks the url contains <text>
    	}

    	
    	public String WaitForURLAndFetch(int timeout, String urlValue) {
           WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
    		wait.until(ExpectedConditions.urlToBe(urlValue));   //Checks the exact URL match
    		return driver.getCurrentUrl();
    		
    	}
    //###############################Waits for Find Elemments############################
    	/**
    	 * An expectation for checking that there is at least one element present on a web page.
    	 * @param timeout
    	 * @param Locator
    	 * @return
    	 */
    	
    	public List<WebElement> WaitForElementsPresence(int timeout , By Locator) {
    		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
    		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(Locator));
    	}
    	
    	/**
    	 * An expectation for checking that an element, known to be present on the DOM of a page, isvisible. 
    	 * Visibility means that the element is not only displayed but also has a height andwidth that is 
    	 * greater than 0.
    	 * @param timeout
    	 * @param Locator
    	 * @return
    	 */
    	public List<WebElement> WaitForElementsvisible(int timeout , By Locator) {
    		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
    		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(Locator));
    	}
    	
    //#############################Waits For Frames####################################
    	
    	public void WaitForFrameAndSwitchToItByIDOrName(int timeout,String IDorName) {
    		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
    		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(IDorName));
    	}
    	
    	public void WaitForFrameAndSwitchToItByFrameLocator(int timeout,By FrameLocator) {
    		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
    		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(FrameLocator));
    	}
    	
    	public void WaitForFrameAndSwitchToItByFrameIndex(int timeout,int index) {
    		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
    		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
    	}
    	
    	public void WaitForFrameAndSwitchToItByFrameIndex(int timeout,WebElement Frameelement) {
    		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
    		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(Frameelement));
    	}
    	
    //###############################Waits For Clickable#############################
    	
    	public void WaitForElementtobeClickable(int timeout , By Locator) {
    		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10)) ;
    		wait.until(ExpectedConditions.elementToBeClickable(Locator)).click();
    	}
    	
    	public WebElement WaitForElementclick(int timeout,By Locator) {
    		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10)) ;
    		return wait.until(ExpectedConditions.elementToBeClickable(Locator));
    	}
    	
    	public void doClickwithActionsAndWait(int timeout , By Locator) {
    		WebElement eleclick = WaitForElementclick(timeout,Locator);
    		Actions actions = new Actions(driver);
    		actions.moveToElement(eleclick).click().build().perform();
    	}
    	
    //###############################Fluent Wait#####################################
    	
    	public WebElement WaitForElementPresenceWithFluentWait(int timeout , int pollinngTime , By Locator) {
    		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
	                   .withTimeout(Duration.ofSeconds(timeout))
	                   .ignoring(NoSuchElementException.class)
	                   .ignoring(StaleElementReferenceException.class)
	                   .pollingEvery(Duration.ofSeconds(pollinngTime))
	                   .withMessage("...element is not found on the page");
    		return wait.until(ExpectedConditions.presenceOfElementLocated(Locator));
    	}
    	
    	public Alert WaitForAlertWithFluentWait(int timeout , int pollinngTime) {
    		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
	                   .withTimeout(Duration.ofSeconds(timeout))
	                   .ignoring(NoAlertPresentException.class)
	                   .pollingEvery(Duration.ofSeconds(pollinngTime))
	                   .withMessage("...Alert is not found on the page");
    		return wait.until(ExpectedConditions.alertIsPresent());
    	}

}

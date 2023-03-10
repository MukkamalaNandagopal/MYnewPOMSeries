package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {
	
	//SRP : Single Responsilibility Principle 
	//Each class is responsible for each page
	
	//Each page should have seperate class 
	
	private WebDriver driver;
	
	private ElementUtil eleUtil;
	
	private By logoutLnk = By.xpath("//div[@class='list-group']/a[text()='Logout']");
	
	private By accsHeaders = By.cssSelector("div#content h2");
	
	private By search = By.name("search");
	
	private By searchIcon = By.xpath("//div[@id='search']//button[@type='button']");
	
	//Creating Constructor
	public AccountsPage (WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	//Page Actions 
	public String getAccpageTitle() {
		//String title = driver.getTitle();
		String title = eleUtil.WaitForTitleIsAndFetch(AppConstants.DEFAULT_MEDIUM_TIME_OUT, AppConstants.ACCOUNTS_PAGE_TITLE_VALUE);
		System.out.println("Accounts page title:"+title);
		return title;
	}
	
	public String getAccpageUrl() {
		//String url = driver.getCurrentUrl();
		String url = eleUtil.WaitForURLContainsAndFetch(AppConstants.DEFAULT_MEDIUM_TIME_OUT, AppConstants.ACCOUNTS_PAGE_URL_FRACTION_VALUE);
		System.out.println("Accounts page url:"+url);
		return url;
	}
	
	public boolean isLogoutLinkExist() {
		//return driver.findElement(logoutLnk).isDisplayed();
		return eleUtil.WaitForElementVisible(AppConstants.DEFAULT_MEDIUM_TIME_OUT, logoutLnk).isDisplayed();
	}
	
	public boolean isSearchExist() {
		return eleUtil.WaitForElementVisible(AppConstants.DEFAULT_MEDIUM_TIME_OUT, search).isDisplayed();
	}
	
	public List<String> getAccountPageHeadersList() {
		//List<WebElement> accHeaderList = driver.findElements(accsHeaders);
		List<WebElement> accHeaderList = eleUtil.WaitForElementsvisible(AppConstants.DEFAULT_MEDIUM_TIME_OUT,accsHeaders);
		List<String> accHeadervalList = new ArrayList<String>();
		for(WebElement e : accHeaderList) {
			String text =e.getText();
			accHeadervalList.add(text);
		}
		return accHeadervalList;
	}
	
	//Searching for a product using top search 
	//The Top search is common for all pages so therefore we are writing the code in the Acct page 
	public SearchPage performsearch(String searchKey) {
		if (isSearchExist()) {
			eleUtil.doSendkeys(search, searchKey);
			eleUtil.doClick(searchIcon);
			return new SearchPage(driver);
		} else {
			System.out.println("Seaerch filed is not present on the page ");
			return null;
		}
	}
	

}

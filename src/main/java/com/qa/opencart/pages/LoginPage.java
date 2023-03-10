package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	
	private ElementUtil eleUtil;
	
	//1. Private By Locators	
	//Private : So that these By locators cannot be accessed by other pages
	private By emailId = By.xpath("//input[@id='input-email']");
	
	private By password = By.xpath("//input[@id='input-password']");
	
	private By forgotLnk = By.xpath("//div[@class='form-group']//a[text()='Forgotten Password']");
	
	private By loginBtn = By.xpath("//input[@value='Login']");
	
	private By loginsectionTxt = By.xpath("//h2[text()='Returning Customer']");
	
	//Register link on login page 
	private By registerLink = By.linkText("Register"); 
	
	//2. Page Const
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		eleUtil = new ElementUtil(driver);
	}
	
	//3.Page actions/methods 
	@Step(".....getting th elogin page title.....")
	public String getLoginpageTitle() {
		//String title = driver.getTitle();
		String title = eleUtil.WaitForTitleIsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT,AppConstants.LOGIN_PAGE_TITLE_VALUE );
		System.out.println("Login Page title:"+title);
		return title;
	}
	
	@Step("......getting th elogin page url....")
	public String getLoginpageUrl() {
		//String url = driver.getCurrentUrl();
		String url = eleUtil.WaitForURLContainsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE);
		System.out.println("Login page Url:"+url);
		return url;
		
	}
	
	public String getLoginsectionTitle() {
		String loginsectiontxt = driver.findElement(loginsectionTxt).getText();
		System.out.println("The Login section Text:"+loginsectiontxt);
		return loginsectiontxt;
	}
	
	//Encapsulation : calling the private type in public 
	//Also using elementUtil class
	@Step("geeting the forgot pwd link")
	public boolean isForgotpwdLinkExist() {
		//return driver.findElement(forgotLnk).isDisplayed();
		return eleUtil.WaitForElementVisible(AppConstants.DEFAULT_MEDIUM_TIME_OUT, forgotLnk).isDisplayed();
	}
	
	/**
	 * 1.Here we are checking clicking on loginbutton after entering username & password is Accounts page
	 * 2.The doLogin returns AccountPage 
	 * 3.Mouse hove on New AccountsPage(driver) will give a suggestion to Create class'Accountspage' and 
	 * this approach is know as TDD Approach
	 * 4. After creating constructor in Accounts page the error in doLogin method is resolved
	 * 5. One method return nextlanging page object this concept is called Method chaining
	 * 6. Also using Element Util class
	 * @param usr
	 * @param pwd
	 * @return
	 */
	@Step("login with username:{0} and password: {1}") //0 for user name & 1 for password in allure 
	//reports it will display
	public AccountsPage doLogin(String usr,String pwd) {
		System.out.println("App Credentials are :" +usr + ":" +pwd);
		//driver.findElement(emailId).sendKeys(usr);
		eleUtil.WaitForElementVisible(AppConstants.DEFAULT_MEDIUM_TIME_OUT, emailId).sendKeys(usr);
		//driver.findElement(password).sendKeys(pwd);
		eleUtil.doSendkeys(password, pwd);
		//driver.findElement(loginBtn).click();
		eleUtil.doClick(loginBtn);
		return new AccountsPage(driver);
	}
	
	public RegisterPage navigateToRegisterPage() {
		eleUtil.doClick(registerLink);
		return new RegisterPage(driver);
	}
	
	


}

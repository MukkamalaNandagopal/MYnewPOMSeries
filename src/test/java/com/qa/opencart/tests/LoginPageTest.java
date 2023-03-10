package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("EPIC - 100: design login for open cart app")
@Story("US-Login: 101: design loginpage features for open cart")
public class LoginPageTest extends BaseTest{
	
	//As per the POM type the rule is to have add assertions only in Test class 
	//Always better to use the allure annotation (@Severity @Description)
	@Severity(SeverityLevel.TRIVIAL)
	@Description("......getting the title of the page ....")
	@Test(priority=1)
	public void loginpagetitleTest() {
		String actualtitle = loginpage.getLoginpageTitle();
		Assert.assertEquals(actualtitle,AppConstants.LOGIN_PAGE_TITLE_VALUE);
	}
	@Severity(SeverityLevel.NORMAL)
	@Description("......Checking the url of the page ....")
	@Test(priority=2)
	public void loginpageurlTest() {
		String actualurl = loginpage.getLoginpageUrl();
		Assert.assertTrue(actualurl.contains(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE));
	}
	
	@Severity(SeverityLevel.CRITICAL)
	@Description("......Checking login text of the page ....")
	@Test(priority=3)
	public void loginsectiontextTest() {
		String actualtxt = loginpage.getLoginsectionTitle();
		Assert.assertEquals(actualtxt, "Returning Customer");
	}
	
	@Severity(SeverityLevel.BLOCKER)
	@Description("......Checking forgot password link exists ....")
	@Test(priority=4)
	public boolean forgotpwdlinkTest() {
		return loginpage.isForgotpwdLinkExist();
	}
	
	/**
	 * Here we are checking if the login page is success and checking if logout link is displayed
	 * in Accounts page
	 */
	@Severity(SeverityLevel.CRITICAL)
	@Description("......Checking the user is able to login with correct username & password")
	@Test(priority=5)
	public void loginTest() {
		accPage = loginpage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}

}

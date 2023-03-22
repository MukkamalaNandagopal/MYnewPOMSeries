package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.SearchPage;

public class BaseTest {
	
	//Here creating object for Driver factory , Login page 
	
	DriverFactory df;
	WebDriver driver;
	protected Properties prop;
	protected LoginPage loginpage;
	protected AccountsPage accPage;
	protected SearchPage searchpage;
	protected ProductInfoPage productInfopage;
	protected RegisterPage registerPage;
	
	protected SoftAssert softAssert;
	
	@Parameters({"browser","browserversion","testcasename"})
	@BeforeTest
	public void setup(String browserName,String browserVersion,String testCaseName) {
		df = new DriverFactory();
		prop = df.initprop();
		  if (browserName!=null) {
			  prop.setProperty("browser", browserName);
			  prop.setProperty("browserversion", browserVersion);
			  prop.setProperty("testcasename", testCaseName);
		  }
		driver = df.initDriver(prop);
		loginpage =new LoginPage(driver);
		softAssert = new SoftAssert();
		
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}

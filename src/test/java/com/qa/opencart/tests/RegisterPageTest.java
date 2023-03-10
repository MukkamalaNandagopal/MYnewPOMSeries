package com.qa.opencart.tests;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest {
	
	@BeforeClass
	public void regPageSetup() {
		registerPage = loginpage.navigateToRegisterPage();
	}
	
	//For Random email generation 
    //1.Delete Email column from excel
	//2.Delete email parameter from userRegTest & registerUser
	
	//It randomly Generates numbers between 1 - 1000
//	public String getRandomEmail() {
//		Random random = new Random();
//		String email = "automation" + random.nextInt(1000)+"@gmail.com";
//		return email;
//	}
	
	//Recommanded way to generate random numbers (Which will be unique always)
	public String getRandomEmail() {
		//Random random = new Random();
		//String email = "automation" + random.nextInt(1000)+"@gmail.com";
		String email = "automation" + System.currentTimeMillis()+"@gmail.com";
		return email;
	}
	
	
	
	
	//Passing data from excel to dataprovider
	@DataProvider
	public Object[][] getRegTestData() {
		Object regData[][] = ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
		return regData;
	}
	

	@Test(dataProvider = "getRegTestData")
	public void userRegTest(String firstName , String lastName,
            String telephone , String password, String subscribe) {
		
		Assert.assertTrue(registerPage.registerUser(firstName, lastName,getRandomEmail(),telephone, password, subscribe));
	}
	
	//Email not removed 
//	@Test(dataProvider = "getRegTestData")
//	public void userRegTest(String firstName , String lastName, String email,
//            String telephone , String password, String subscribe) {
//		
//		Assert.assertTrue(registerPage.registerUser(firstName, lastName, email, telephone, password, subscribe));
//	}
	
	
	//With out Excel & Dataprovider (Normal way)
//	public void userRegTest() {
//		Assert.assertTrue(registerPage.registerUser("Lomi", "opm", "yyyyy@gmailtest.com", "6789056890", "tghytest@123", "yes"));
//	}
	
	
	
}

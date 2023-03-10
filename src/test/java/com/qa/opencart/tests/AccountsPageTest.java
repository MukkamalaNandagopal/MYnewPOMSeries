package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class AccountsPageTest extends BaseTest{
	
	//The 1st thing to display the Accounts page is to Login..here we are calling doLogin using 
	//loginpage reference in Basetest
	
	//The doLogin return value is Accounts page so we are storing in Accounts page reference 
	@BeforeClass
	public void accpageSetup() {
		accPage = loginpage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());	
	}
	
	@Test
	public void accPageTitleTest() {
		String actTitle = accPage.getAccpageTitle();
		Assert.assertEquals(actTitle, AppConstants.ACCOUNTS_PAGE_TITLE_VALUE);
	}
	
	@Test
	public void accPageURLTest() {
		String actURL = accPage.getAccpageUrl();
		Assert.assertTrue(actURL.contains(AppConstants.ACCOUNTS_PAGE_URL_FRACTION_VALUE));
	}
	
	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
	
	@Test
	public void accPageHeadersCountTest() {
		List<String> actualaccPageHeadersList = accPage.getAccountPageHeadersList();
		System.out.println("Account Page Headers List:"+actualaccPageHeadersList);
		Assert.assertEquals(actualaccPageHeadersList.size(), AppConstants.ACCOUNTS_PAGE_HEADERS_COUNT);
	}
	
	//To execute Specific test select the method name and right click then run as testngtest
	@Test
	public void accPageHeadersValueTest() {
		List<String> actualaccPageHeadersList = accPage.getAccountPageHeadersList();
		System.out.println("Account Page Headers Actual List:"+actualaccPageHeadersList);
		System.out.println("Account Page Headers Expected List:"+AppConstants.EXPECTED_ACCOUNTS_PAGE_HEADERS_LIST);
		Assert.assertEquals(actualaccPageHeadersList, AppConstants.EXPECTED_ACCOUNTS_PAGE_HEADERS_LIST);
	}
	
	@DataProvider
	public Object[][]getProductData() {
		return new Object[][] {
			{"Macbook"},
			{"iMac"},
			{"Apple"},
			{"Samsung"},
			{"nanda"}
		};
	}
	
	@Test (dataProvider = "getProductData")
	public void searchProductCountTest(String searchKey) {
		searchpage = accPage.performsearch(searchKey);
	    Assert.assertTrue(searchpage.getSearchProductsCount()>0) ;		
	}
	
	@DataProvider
	public Object[][]getProductTestData() {
		return new Object[][] {
			{"Macbook","MacBook Pro"},
			{"Macbook","MacBook Air"},
			{"iMac","iMac"},
			{"Apple","Apple Cinema 30\""},
			{"Samsung","Samsung SyncMaster 941BW"},
			{"Samsung","Samsung Galaxy Tab 10.1"},
		};
	}
	
	@Test(dataProvider="getProductTestData")
	public void searchProductTest(String searchkey,String productName) {
		searchpage = accPage.performsearch(searchkey);
		if(searchpage.getSearchProductsCount()>0) {
			productInfopage = searchpage.selectproduct(productName);
			String actProductHeader = productInfopage.getProductHeaderValue();
			Assert.assertEquals(actProductHeader, productName);
			
		}
	}
	

}
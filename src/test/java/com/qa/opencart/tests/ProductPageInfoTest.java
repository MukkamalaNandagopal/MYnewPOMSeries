package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductPageInfoTest extends BaseTest{
	
	@BeforeClass
	public void productInfoPageSetup() {
		accPage = loginpage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());	
	}
	
	@DataProvider
	public Object[][]getProductImagesTestData() {
		return new Object[][] {
			{"Macbook","MacBook Pro",4},
			{"iMac","iMac",3},
			{"Apple","Apple Cinema 30\"",6},
			{"Samsung","Samsung SyncMaster 941BW",1},
		};
	}

	
	@Test(dataProvider="getProductImagesTestData")
	public void productImagesCountTest(String searchkey,String productName,int imagesCount) {
		searchpage = accPage.performsearch(searchkey);
		productInfopage = searchpage.selectproduct(productName);
		int actImagesCount = productInfopage.getProductImagesCount();
		Assert.assertEquals(actImagesCount, imagesCount);
		Assert.assertTrue(productInfopage.getProductDescription(), "Prod Desc not exists");
	}
	
//	@Test
//	public void ProductDescriptionTest() {
//		String desc = productInfopage.getProductDescription();
//		System.out.println("The desc:"+desc);
//	}
	
	@Test
	public void productInfoTest() {
		searchpage = accPage.performsearch("MacBook");
		productInfopage = searchpage.selectproduct("MacBook Pro");
		Map<String, String> actProductInfoMap = productInfopage.getProductInfo();
		System.out.println(actProductInfoMap);
		
		//With the soft Assertion we can continue even if one assert fail 
		
		//Assert.assertEquals(actProductInfoMap.get("Brand"), "Apple11");
		softAssert.assertEquals(actProductInfoMap.get("Brand"), "Apple");
		
		//Assert.assertEquals(actProductInfoMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(actProductInfoMap.get("Product Code"), "Product 18");
		
		//Assert.assertEquals(actProductInfoMap.get("productname"), "MacBook Pro");
		softAssert.assertEquals(actProductInfoMap.get("productname"), "MacBook Pro");
		
		//Assert.assertEquals(actProductInfoMap.get("productname"), "MacBook Pro");
		softAssert.assertEquals(actProductInfoMap.get("productname"), "MacBook Pro");
		
		softAssert.assertAll();
		
	}
	
	//Assert Vs Verify (Soft assertion) 

    @Test
    public void addToCartTest() {
    	searchpage = accPage.performsearch("MacBook");
		productInfopage = searchpage.selectproduct("MacBook Pro");
		productInfopage.enterQuantity(2);
		String actCartMesg = productInfopage.addProductToCart();
		softAssert.assertTrue(actCartMesg.contains("Success"));
		
		//softAssert.assertTrue(actCartMesg.indexOf("Success")>=0);
		
		softAssert.assertTrue(actCartMesg.contains("MacBook Pro"));
		//softAssert.assertTrue(actCartMesg.indexOf("MacBook Pro")>=0);
		
		softAssert.assertEquals(actCartMesg, "Success: You have added MacBook Pro to your shopping cart!");
		
		softAssert.assertAll();
    }
}

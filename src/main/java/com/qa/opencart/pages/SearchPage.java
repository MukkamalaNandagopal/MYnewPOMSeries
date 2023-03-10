package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class SearchPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By searchProductsResults = By.xpath("//div[@id='content']//div[@class='product-layout product-grid col-lg-3 col-md-3 col-sm-6 col-xs-12']");
	
	public SearchPage (WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public int getSearchProductsCount() {
		int productcount=  eleUtil.WaitForElementsvisible(AppConstants.DEFAULT_MEDIUM_TIME_OUT,searchProductsResults).size();
	    System.out.println("Product count ::::"+productcount);
	    return productcount;
	}

	public ProductInfoPage selectproduct(String productName) {
		By productLocator = By.linkText(productName);
		eleUtil.WaitForElementVisible(AppConstants.DEFAULT_MEDIUM_TIME_OUT, productLocator).click();
		return new ProductInfoPage(driver);
		
	}
}

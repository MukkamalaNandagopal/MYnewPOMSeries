package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {
	
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	private By productHeader = By.tagName("h1");
	private By productImages = By.cssSelector("ul.thumbnails img");
	private By productdescription = By.xpath("//div[@id='tab-description']");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=2]/li");
	
	private By quantity = By.id("input-quantity");
	private By addToCartBtn = By.id("button-cart");
	private By cartSuccessMessg = By.cssSelector("alert.alert-success");
	
	
	//To Seperate Hash map method 
	private Map<String,String>productInfoMap;
	
	public ProductInfoPage (WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	 public String getProductHeaderValue() {
		 String productHeaderval = eleUtil.dogettext(productHeader);
		 System.out.println("product header:"+productHeaderval);
		 return productHeaderval;
	 }
	 
	 public int getProductImagesCount() {
		 int imagesCount = eleUtil.WaitForElementsvisible(AppConstants.DEFAULT_MEDIUM_TIME_OUT, productImages).size();
		 System.out.println("Product images count:"+imagesCount);
		 return imagesCount;
	 }
	 
	 public boolean getProductDescription() {
		 return eleUtil.doisdisplayed(productdescription);
	 }
	 
	 //Using Hashmap for Meta data
	 //Below we are splitting the text by : and passing to a hashmap as key , value
	 
	 public Map<String, String> getProductInfo() {
//	 Brand: Apple
//	 Product Code: Product 18
//	 Reward Points: 800
//	 Availability: In Stock
		 
		 //Map<String , String> productInfoMap = new HashMap<String,String>();
		 
		 //HashMap
		 //productInfoMap = new HashMap<String,String>();
		 
		 //LinkedHashMap (It maintains the same order the way it is inserted)
		 //eg : {productname=MacBook Pro, Brand=Apple, Product Code=Product 18, Reward Points=800, Availability=In Stock, productprice=$2,000.00, ExTax=$2,000.00}
		 productInfoMap = new LinkedHashMap<String,String>();
		 
		 //TreeMap (It sorts the key Alphabetically and store)
		 productInfoMap = new TreeMap<String , String>();
		 
		 //Hash Map for Header
		 //eg: {Availability=In Stock, Brand=Apple, ExTax=$2,000.00, Product Code=Product 18, Reward Points=800, productname=MacBook Pro, productprice=$2,000.00}
		 productInfoMap.put("productname", getProductHeaderValue());
		 
		 //Calling both the methods 
		 getProductMetaData();
		 getProductPriceData();	
		 
		 return productInfoMap;
		 
	 }
	 
	 //Seperated the Product & Price data in seperate ,methods 
	 private void getProductMetaData() {
		 //Hash Map for metadata
		 List<WebElement> metaList = eleUtil.getElements(productMetaData);
		 for (WebElement e : metaList) {
			 String meta = e.getText();
			 String metaInfo[] = meta.split(":");
			 String key = metaInfo[0].trim();
			 String value = metaInfo[1].trim();
			 productInfoMap.put(key, value);
		 }
		 
	 }
		 
		private void getProductPriceData() {
			
			//Hashmap for Price data 
			 List<WebElement> priceList = eleUtil.getElements(productPriceData);
			 String price = priceList.get(0).getText();
			 String exTax = priceList.get(1).getText();
			 String exTaxval = exTax.split(":")[1].trim();
			 
			 //No key so we use our own key (Custom Key)
			 productInfoMap.put("productprice",price);
			 productInfoMap.put("ExTax",exTaxval);

			
		}
		 //string.valueof(qty) converts string to int
		public void enterQuantity(int qty) {
			System.out.println("Product Quantity:" +qty);
			eleUtil.doSendkeys(quantity, String.valueOf(qty));
		}
		
		//substring will get remove the x in the success message 
		public String addProductToCart() {
			eleUtil.doClick(addToCartBtn);
			String successMessg = eleUtil.WaitForElementVisible(AppConstants.DEFAULT_SHORT_TIME_OUT, cartSuccessMessg).getText();
		    System.out.println("cart Success Mesg:" +successMessg);
		    StringBuilder sb = new StringBuilder(successMessg);
		    String mesg = sb.substring(0, successMessg.length()-1).replace("\n", "");
		    System.out.println("cart Success Mesg:" +sb);
		    return mesg;
		}
		
	 
	 
	 
	 

}

package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.aspectj.util.FileUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;

import com.qa.opencart.exception.FrameworkException;

public class DriverFactory {
	
	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;
	
	public static String highlight;
	
	//In Multi thread env .. We might see a deadlock ... One thread is waiting for webdriver
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	/**
	 * This method initializing the driver on the basis on fiven browser name 
	 * @param prop
	 * @return driver
	 */
	public WebDriver initDriver(Properties prop) {
		optionsManager = new OptionsManager(prop);
		highlight = prop.getProperty("highlight");
		String browserName = prop.getProperty("browser").toLowerCase().trim();
		//String browsewrName = system.getProperty("browser"); (use this to use -Dbrowser in command line
		System.out.println("Browser Name is :" + browserName);
		
		if(browserName.equalsIgnoreCase("chrome")) {
			//driver = new ChromeDriver(optionsManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions())); 
		}
		else if (browserName.equalsIgnoreCase("firefox")) {
			//driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
		}
		else if (browserName.equalsIgnoreCase("edge")) {
			//driver = new EdgeDriver(optionsManager.getEdgeOptions());
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
		}
		else {
			System.out.println("Please pass right browser:"+browserName);
		}
		
		//driver.manage().deleteAllCookies();
		getDriver().manage().deleteAllCookies();
		//driver.manage().window().maximize();
		getDriver().manage().window().maximize();
		//driver.get(prop.getProperty("url"));
		getDriver().get(prop.getProperty("url"));
		
		//return driver;
		return getDriver();
	}
	
	/**
	 * get the local thread copy of the driver
	 * @return 
	 */
	
	public synchronized static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	/**
	 * This method is reading the properties from the .properties file 
	 * @return
	 * mvn clean install -Denv="stage" (Command to execute in Stage)
	 */
	public Properties initprop() {
		
		prop = new Properties();
		FileInputStream ip = null;
		//To Read Environment variables 
		String envName = System.getProperty("env");
		System.out.println("Running test cases on Env:" + envName);
		
		//with out env variable (normal config properties) 
//		try {
//			FileInputStream ip = new FileInputStream("./src/test/resources/config/config.properties");
//			try {
//				prop.load(ip);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return prop;
//	
		//*****To execute the mvn clean -Denv="qa" from eclipse 
		//1. Make sure the window->preference->java->complier-> set compiler compliance level to 1.8 
		//2. Then Set the path of Installed JRE's to JDK path
		
		try {
		
		if(envName == null ) {
			System.out.println("No env is passed..Running tests on QA env");
			
				 ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} 
			
		else {
			switch (envName.toLowerCase().trim()) {
			case "qa":
				 ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
				break;
			case "stage":
				 ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
				break;	
			case "dev":
				 ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
				break;	
			default:
				System.out.println("Wrong env is passwed ... No need to run the test cases...");
				throw new FrameworkException("WRONG ENV IS PASSED");
				//break;
			}
			
		}
		
		} catch (FileNotFoundException e) {
			
		}
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
		
		
	
	/**
	 * Take Screenshot 
	 */
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir")+"/screenshot/"+System.currentTimeMillis()+".png";
		File destination = new File(path);
		try {
			FileUtil.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	return path;
	}
}

package com.qa.opencart.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

//To use retry logic we have to add a this listeners to testNG.xml file 
//Retry tests are shown as Skipped in Extent & Allure reports 
//In Allure the retry tests shown under Retries tab 
public class AnnotationTransformer implements IAnnotationTransformer {
	@Override
	public void transform(ITestAnnotation annotation, 
			Class testClass, 
			Constructor testConstructor, 
			Method testMethod) {
		annotation.setRetryAnalyzer(Retry.class);
	}
}

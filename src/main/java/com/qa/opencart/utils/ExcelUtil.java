package com.qa.opencart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
	
	//Specify the path of .xlsx file 
	private static final String TEST_DATA_SHEET_PATH = "./src/test/resources/testdata/operncart.xlsx";
	private static Workbook book;
	private static Sheet sheet;
	
	public static Object[][] getTestData(String sheetName) {
		
		System.out.println("reading data from sheet:"+sheetName);
		
		Object data[][] = null;
		
		try {
			FileInputStream ip = new FileInputStream(TEST_DATA_SHEET_PATH);
			book = WorkbookFactory.create(ip);
			sheet = book.getSheet(sheetName);
			
			//3 rows 6 columns (This is hard coded)
			data = new Object[3][6];
			
			//Below is the way to use to get rows / columns dynamically
			data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
			
			for (int i = 0; i < sheet.getLastRowNum();i++) {
				for (int j = 0; j < sheet.getRow(0).getLastCellNum();j++) {
					//Here i + 1 means leave column headers
					data[i][j] = sheet.getRow(i + 1).getCell(j).toString();
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return data;
		
	}
	

}

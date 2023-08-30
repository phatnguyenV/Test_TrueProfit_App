package pkg

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcel {
	@Keyword
	public void saveEmailToExcel(String name) throws IOException{
		String fileName = "TestData/temp_email_mca.xlsx";
		File excelFile = new File(fileName);
		XSSFWorkbook workbook;

		if (!excelFile.exists()) {
			workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Sheet1");
			Row headerRow = sheet.createRow(0);
			Cell headerCell = headerRow.createCell(0);
			headerCell.setCellValue("Email");
		} else {
			FileInputStream fis = new FileInputStream(excelFile);
			workbook = new XSSFWorkbook(fis);
		}

		XSSFSheet sheet = workbook.getSheet("Sheet1");
		int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
		//	  Row row = sheet.createRow(rowCount+1);
		Row row = sheet.createRow(rowCount+1);
		Cell cell = row.createCell(0);
		cell.setCellType(cell.CELL_TYPE_STRING);
		cell.setCellValue(name);
		FileOutputStream fos = new FileOutputStream("TestData/temp_email_mca.xlsx");
		workbook.write(fos);
		fos.close();
	}

	@Keyword
	public void saveDomainToExcel(String name) throws IOException{
		String fileName = "TestData/created_store_domain.xlsx";
		File excelFile = new File(fileName);
		XSSFWorkbook workbook;

		if (!excelFile.exists()) {
			workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Sheet1");
			Row headerRow = sheet.createRow(0);
			Cell headerCell = headerRow.createCell(0);
			headerCell.setCellValue("Domain");
		} else {
			FileInputStream fis = new FileInputStream(excelFile);
			workbook = new XSSFWorkbook(fis);
		}

		XSSFSheet sheet = workbook.getSheet("Sheet1");
		int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
		//	  Row row = sheet.createRow(rowCount+1);
		Row row = sheet.createRow(rowCount+1);
		Cell cell = row.createCell(0);
		cell.setCellType(cell.CELL_TYPE_STRING);
		cell.setCellValue(name);
		FileOutputStream fos = new FileOutputStream("TestData/created_store_domain.xlsx");
		workbook.write(fos);
		fos.close();
	}

	@Keyword
	public void writeSummaryToExcel(Map<String, Double> variableMap) {
		Workbook workbook = new XSSFWorkbook()
		Sheet sheet = workbook.createSheet("Summary")

		// Create a header row
		Row headerRow = sheet.createRow(0)
		headerRow.createCell(0).setCellValue("Variable")
		headerRow.createCell(1).setCellValue("Value")

		int rowIndex = 1
		variableMap.each { variableName, value ->
			Row dataRow = sheet.createRow(rowIndex++)
			dataRow.createCell(0).setCellValue(variableName)
			dataRow.createCell(1).setCellValue(value)

		}

		// Save the workbook to a file
		def excelFilePath = "TestData/Report/finances_summary_report.xlsx"
		FileOutputStream fileOut = new FileOutputStream(excelFilePath)
		workbook.write(fileOut)
		fileOut.close()
		workbook.close()

		println "Values written to Excel file: $excelFilePath"
	}


	@Keyword
	public void writeDashboardMetricToExcel(Map<String, Double> dashboardMap, Map<String, Double> doubleCheckMap) {
		Workbook workbook = new XSSFWorkbook()
		Sheet sheet = workbook.createSheet("Summary")

		// Create a header row
		Row headerRow = sheet.createRow(0)
		headerRow.createCell(0).setCellValue("Metric Name")
		headerRow.createCell(1).setCellValue("Overview")
		headerRow.createCell(2).setCellValue("QC Check")
		headerRow.createCell(3).setCellValue("Is Failed?")

		int rowIndex = 1
		doubleCheckMap.each { variableName, value ->
			Row dataRow = sheet.createRow(rowIndex++)
			dataRow.createCell(0).setCellValue(variableName)
			Double metricValueForVariable = dashboardMap[variableName]
			dataRow.createCell(1).setCellValue(metricValueForVariable)
			dataRow.createCell(2).setCellValue(value)
			// Compare the values and set "Passed" or "Failed" accordingly
			String result = (metricValueForVariable == value) ? "" : "Failed"
			dataRow.createCell(3).setCellValue(result)
		}

		// Save the workbook to a file
		def excelFilePath = "TestData/Report/dashboard_metric_report.xlsx"
		FileOutputStream fileOut = new FileOutputStream(excelFilePath)
		workbook.write(fileOut)
		fileOut.close()
		workbook.close()

		println "Values written to Excel file: $excelFilePath"
	}

}

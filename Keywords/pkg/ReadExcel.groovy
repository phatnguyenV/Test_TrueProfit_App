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
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

public class ReadExcel {
	@Keyword
	public static List<Map<String, String>> readExcelData(String filePath, String sheetName) {
		List<Map<String, String>> dataList = []

		FileInputStream fis = new FileInputStream(filePath)
		Workbook workbook = new XSSFWorkbook(fis)
		Sheet sheet = workbook.getSheet(sheetName)

		Row headerRow = sheet.getRow(0)
		List<String> headerCells = headerRow.collect { it.toString() }

		for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			Row dataRow = sheet.getRow(rowIndex)
			Map<String, String> rowData = [:]

			for (int cellIndex = 0; cellIndex < headerCells.size(); cellIndex++) {
				Cell cell = dataRow.getCell(cellIndex)
				rowData[headerCells[cellIndex]] = cell?.toString() ?: ""
			}

			dataList.add(rowData)
		}

		workbook.close()
		return dataList
	}
}

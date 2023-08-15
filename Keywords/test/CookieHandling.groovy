package test

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.google.gson.JsonArray
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Cookie;
import com.kms.katalon.core.webui.driver.DriverFactory
import org.json.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import groovy.json.JsonSlurper

@Keyword
def storeCookiesAsJson(String filePath) {

	File existingFile = new File(filePath)
	if (existingFile.exists()) {
		existingFile.delete()
	}

	Set<Cookie> cookiesSet = DriverFactory.getWebDriver().manage().getCookies()
	List cookiesList = []

	cookiesSet.each { cookie ->
		def cookieJson = [
			name: cookie.getName(),
			value: cookie.getValue(),
			domain: cookie.getDomain(),
			path: cookie.getPath(),
			expiry: formatDate(cookie.getExpiry()),
			isSecure: cookie.isSecure()
		]
		cookiesList.add(cookieJson)
	}

	try {
		def jsonString = new groovy.json.JsonBuilder(cookiesList).toPrettyString()
		new File(filePath).write(jsonString)
	} catch (IOException e) {
		e.printStackTrace()
	}
}

@Keyword
def loadCookiesFromFile(String filePath) {
	try {
		String cookiesJson = new File(filePath).text
		List<Cookie> cookiesList = new JsonSlurper().parseText(cookiesJson)

		cookiesList.each { cookieData ->
			Date expirationDate = null
			if (cookieData.expirationDate) {
				expirationDate = new Date(cookieData.expirationDate.toLong() * 1000) // Convert to milliseconds
			}
			boolean secure = Boolean.parseBoolean(cookieData.secure)

			// Set the domain explicitly to match the current domain of the page
			String currentDomain = "https://develop.trueprofit-web.pages.dev/" // Replace with the actual domain of the page being tested

			Cookie cookie = new Cookie(cookieData.name, cookieData.value, currentDomain, cookieData.path, expirationDate, secure)
			DriverFactory.getWebDriver().manage().addCookie(cookie)
		}
	} catch (IOException e) {
		e.printStackTrace()
	}
}

def formatDate(Date date) {
	// Format the date to a specific format (e.g., "yyyy-MM-dd HH:mm:ss")
	// You can choose any format that suits your requirements
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	return sdf.format(date)
}


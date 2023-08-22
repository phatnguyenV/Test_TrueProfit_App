import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

import internal.GlobalVariable
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

class TestListener {
	/**
	 * Executes before every test case starts.
	 * @param testCaseContext related information of the executed test case.
	 */
//	@BeforeTestCase
	def maximizeBrowserForTestCase() {
		WebUI.openBrowser('')
		WebUI.maximizeWindow()
	}
	
	@BeforeTestCase
	def openBrowserWithCustomizedProfile() {
		//Specify the installed folder to get chromedrider.exe
		def projectDir = RunConfiguration.getProjectDir()
		def parentDir = new File(projectDir).parentFile
		def katalonInstalledFolder = new File(parentDir, "Katalon_Studio_Windows_64-8.6.5")
		def pathToChromeDriver = "${katalonInstalledFolder}\\Katalon_Studio_Windows_64-8.6.5\\configuration\\resources\\drivers\\chromedriver_win32\\chromedriver.exe"
		println("Path to ChromeDriver: ${pathToChromeDriver}")
		System.setProperty("webdriver.chrome.driver", pathToChromeDriver)
		// It is only OK if all chrome browsers are closed
		//def userProfile = System.getenv("USERPROFILE");
		//def chromeProfilePath = userProfile  + "AppData\\Local\\Google\\Chrome\\User Data";

		// Solution 1: Copy the "Default" and "Profile X" in User Data to newly folder,
		// then change the path and you can use these profiles separately with Chrome.
		def chromeProfilePath = "C:\\User Data"
		println(chromeProfilePath)

		ChromeOptions options  = new ChromeOptions();
		// Solution 2: Using Chrome "--headless" without GUI
		//options.addArguments('--headless')
		options.addArguments("--no-sandbox");// Bypass OS security model
		options.addArguments("user-data-dir=" + chromeProfilePath);
		options.addArguments("profile-directory=Profile 3");
		options.addArguments("start-maximized"); // Open Browser in maximized mode
		options.addArguments("--disable-dev-shm-usage");  // Overcome limited resource problems
		options.addArguments("disable-infobars"); // Disabling infobars
		options.addArguments("--disable-gpu"); // applicable to Windows OS only
		//options.addArguments("--disable-extensions"); // Disabling extensions
		ChromeDriver driver = new ChromeDriver(options);
		driver.get("https://google.com");
		DriverFactory.changeWebDriver(driver)
		
	}

	/**
	 * Executes after every test case ends.
	 * @param testCaseContext related information of the executed test case.
	 */
	@AfterTestCase
	def sampleAfterTestCase(TestCaseContext testCaseContext) {
		println testCaseContext.getTestCaseId()
		println testCaseContext.getTestCaseStatus()
	}

	/**
	 * Executes before every test suite starts.
	 * @param testSuiteContext: related information of the executed test suite.
	 */
	@BeforeTestSuite
	def maximizeBrowserForTestSuite() {
		WebUI.openBrowser('')
		WebUI.maximizeWindow()
	}

	/**
	 * Executes after every test suite ends.
	 * @param testSuiteContext: related information of the executed test suite.
	 */
	@AfterTestSuite
	def sampleAfterTestSuite(TestSuiteContext testSuiteContext) {
		println testSuiteContext.getTestSuiteId()
	}
}
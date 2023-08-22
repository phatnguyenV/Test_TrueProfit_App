import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import org.openqa.selenium.Rectangle as Rectangle
import org.openqa.selenium.remote.server.DriverFactory as DriverFactory
import org.openqa.selenium.remote.server.handler.GetAllWindowHandles as GetAllWindowHandles
import org.openqa.selenium.remote.server.handler.WebDriverHandler as WebDriverHandler
import internal.GlobalVariable as GlobalVariablvere
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.chrome.ChromeDriver as ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions as ChromeOptions
import org.openqa.selenium.Capabilities as Capabilities
import org.openqa.selenium.remote.DesiredCapabilities as DesiredCapabilities
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Cookie as Cookie

//// https://shtp-XXXX.trueprofit-web.pages.dev
//def urlTrueProfit = GlobalVariable.protocal + GlobalVariable.test_subdomain + GlobalVariable.domain_name
def urlTrueProfit = GlobalVariable.urlTrueProfit

WebUI.navigateToUrl(urlTrueProfit)

// If already logged in to TrueProfit, it will be logged out
if (WebUI.verifyElementPresent(GlobalVariable.main_trueprofit, 1, FailureHandling.OPTIONAL)) {
    WebUI.click(gear_option)

    WebUI.waitForElementVisible(btn_logout_trueprofit, 3)

    WebUI.click(btn_logout_trueprofit)

    WebUI.waitForElementVisible(btn_ok_logout, 3)

    WebUI.click(btn_ok_logout)

    WebUI.waitForElementVisible(btn_signin, 5)
}
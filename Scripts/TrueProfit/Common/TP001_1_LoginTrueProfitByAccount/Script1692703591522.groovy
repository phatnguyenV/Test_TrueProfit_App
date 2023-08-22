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

//// Check if the browser is already open with the target URL
//boolean isBrowserOpened = WebUI.verifyElementPresent(menubar, 1, FailureHandling.OPTIONAL) || 
//WebUI.verifyElementPresent(btn_sign_in, 1, FailureHandling.OPTIONAL)
//
//// If the browser is not already opened with the target URL, execute the code
//if (!isBrowserOpened) {
//	WebUI.executeJavaScript('window.open();', [])
//	currentWindow = WebUI.getWindowIndex()
//	WebUI.switchToWindowIndex(currentWindow + 1)
//	WebUI.navigateToUrl(urlTrueProfit)
//}

	WebUI.navigateToUrl(urlTrueProfit)
	
// Check if the app required login, execute this one
if (WebUI.verifyElementPresent(input_email, 3, FailureHandling.OPTIONAL)) {
		
        WebUI.sendKeys(input_email, Keys.chord(Keys.CONTROL, 'a', Keys.BACK_SPACE) + account.email_1)

        WebUI.setEncryptedText(input_password, GlobalVariable.default_password)

        WebUI.click(btn_sign_in)
}

WebUI.waitForElementVisible(GlobalVariable.main_trueprofit, 10)

WebUI.takeFullPageScreenshot('screenshot/loginTrueProfit_success.png')
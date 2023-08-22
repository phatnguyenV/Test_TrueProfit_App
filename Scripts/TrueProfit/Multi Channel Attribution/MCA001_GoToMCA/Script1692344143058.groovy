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

def path_MCA = 'multi-channel-attribution'

WebUI.navigateToUrl(urlTrueProfit + path_MCA)

// Call the login test case if required by the app
while (WebUI.verifyElementPresent(input_email, 1, FailureHandling.OPTIONAL)) {
    try {
        WebUI.callTestCase(findTestCase('TrueProfit/Common/TP001_1_LoginTrueProfitByAccount'), [('account') : [('email_2') : 'phatnt@firegroup.io'
                    , ('email_1') : 'demo_pa2@gmail.com'], ('btn_sign_in') : findTestObject('TrueProfit/Common/btn_signin'), ('btn_sign_in_with_shopify') : findTestObject(
                    'TrueProfit/btn_sign_in_with_shopify'), ('input_email') : findTestObject('TrueProfit/Common/input_email'), ('input_password') : findTestObject(
                    'TrueProfit/input_password'), ('main_trueprofit') : findTestObject('TrueProfit/Common/main_trueprofit')], FailureHandling.STOP_ON_FAILURE)
		WebUI.navigateToUrl(urlTrueProfit + path_MCA)
		
        break
    }
    catch (Exception e) {
        println(e)
    } 
}

WebUI.waitForElementVisible(GlobalVariable.main_trueprofit, 10)

WebUI.refresh()

WebUI.waitForElementVisible(GlobalVariable.main_trueprofit, 10)

WebUI.refresh()

WebUI.waitForElementVisible(GlobalVariable.main_trueprofit, 10)
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.util.ResourceBundle.Control as Control
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
import internal.GlobalVariable as GlobalVariable
import internal.GlobalVariable as GlobalVariablvere
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.chrome.ChromeDriver as ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions as ChromeOptions
import org.openqa.selenium.Capabilities as Capabilities
import org.openqa.selenium.remote.DesiredCapabilities as DesiredCapabilities

def partner_nonprod = GlobalVariable.partner_nonprod

def dev_store_creation = GlobalVariable.dev_store_creation

def storeDomain = GlobalVariable.store_domain

//def storeDomain = "new-new-new-store-1"

WebUI.navigateToUrl(dev_store_creation)

// Login to Shopify admin if it is required
while (WebUI.verifyElementPresent(GlobalVariable.txt_email, 1, FailureHandling.OPTIONAL)) {
    try {
        WebUI.callTestCase(findTestCase('Shopify Admin/Common/SFA001_LoginShopify'), [('url') : 'https://accounts.shopify.com/lookup?rid=d50c60a2-1d7c-469c-a15f-39bc5d99f8bf'
                , ('h2_yourstore') : findTestObject('Shopify Admin/LoginShopify/h2_yourstore'), ('username') : 'phatnt@firegroup.io'
                , ('password') : '0BXcTMSMNdRd23Ml/vw4nA==', ('btn_continue') : findTestObject('Shopify Admin/LoginShopify/btn_continue_with_email')
                , ('btn_login') : findTestObject('Shopify Admin/LoginShopify/btn_login'), ('txt_email') : findTestObject(
                    'Shopify Admin/LoginShopify/txt_email'), ('txt_password') : findTestObject('Shopify Admin/LoginShopify/txt_password')
                , ('a_remind_later') : findTestObject('Shopify Admin/LoginShopify/a_remind_later')], FailureHandling.STOP_ON_FAILURE)

        WebUI.navigateToUrl(dev_store_creation)

        break
    }
    catch (Exception e) {
        println(e)
    } 
}

// Select the first account on Shopify if required
if (WebUI.verifyElementPresent(GlobalVariable.a_select_1st_account, 1, FailureHandling.OPTIONAL)) {
    WebUI.click(GlobalVariable.a_select_1st_account)
}

WebUI.waitForElementVisible(txt_store_name, 5)

WebUI.scrollToPosition(0, 350)

WebUI.sendKeys(txt_store_name, storeDomain)

def number = CustomKeywords.'pkg.ExtractNumber.extractNumber'(storeDomain)

// If store domain is available, extract the number and increase it in the string
// Then try the new one until error-free
while (true) {
    def newInput = CustomKeywords.'pkg.ReplaceNumber.replaceNumber'(storeDomain, number)

    try {
        number++

        WebUI.sendKeys(txt_store_name, Keys.chord(Keys.CONTROL, 'a', Keys.BACK_SPACE) + newInput)

        WebUI.click(span_store_name)

        if (!(WebUI.verifyElementPresent(txt_error, 3, FailureHandling.OPTIONAL))) {
            // Transfer the store domain for the next test case of Test Suite
            // In Katalon, this will be reversed to the orginal value as the global variable in Profies
            GlobalVariable.storeDomain = newInput
			CustomKeywords.'pkg.WriteExcel.saveDomainToExcel'(newInput)
            break
        }
    }
    catch (Exception e) {
        println(e)
    } 
}

WebUI.scrollToElement(btn_create, 5)

WebUI.waitForElementVisible(btn_create, 5)

WebUI.click(btn_create)

WebUI.waitForElementPresent(div_dashboard, 90)

WebUI.takeFullPageScreenshot('screenshot/create_store_success.png')
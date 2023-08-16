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
import internal.GlobalVariable as GlobalVariable
import internal.GlobalVariable as GlobalVariablvere
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.chrome.ChromeDriver as ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions as ChromeOptions
import org.openqa.selenium.Capabilities as Capabilities
import org.openqa.selenium.remote.DesiredCapabilities as DesiredCapabilities

def shopDomain = 'new-new-new-store-0'

def full_link = (GlobalVariable.protocal + GlobalVariable.test_subdomain) + GlobalVariable.domain_name

WebUI.navigateToUrl(full_link)

WebUI.waitForElementVisible(btn_login_with_shopify, 10)

WebUI.click(btn_login_with_shopify)

WebUI.waitForElementVisible(txt_domain, 10)

WebUI.sendKeys(txt_domain, shopDomain)

WebUI.click(btn_signin)

//Login to Shopify admin if it is required
while (WebUI.verifyElementPresent(txt_email, 1, FailureHandling.OPTIONAL)) {
    try {
        WebUI.callTestCase(findTestCase('Shopify Admin/SFA001_LoginShopify'), [('url') : 'https://accounts.shopify.com/lookup?rid=d50c60a2-1d7c-469c-a15f-39bc5d99f8bf'
                , ('h2_yourstore') : findTestObject('Shopify Admin/LoginShopify/h2_yourstore'), ('username') : 'phatnt@firegroup.io'
                , ('password') : '0BXcTMSMNdRd23Ml/vw4nA==', ('btn_continue') : findTestObject('Shopify Admin/LoginShopify/btn_continue_with_email')
                , ('btn_login') : findTestObject('Shopify Admin/LoginShopify/btn_login'), ('txt_email') : findTestObject(
                    'Shopify Admin/LoginShopify/txt_email'), ('txt_password') : findTestObject('Shopify Admin/LoginShopify/txt_password')
                , ('a_remind_later') : findTestObject('Shopify Admin/LoginShopify/a_remind_later')], FailureHandling.STOP_ON_FAILURE)

        WebUI.navigateToUrl(full_link)

        WebUI.waitForElementVisible(btn_login_with_shopify, 10)

        WebUI.click(btn_login_with_shopify)

        WebUI.waitForElementVisible(txt_domain, 10)

        WebUI.sendKeys(txt_domain, shopDomain)

        WebUI.click(btn_signin)

        break
    }
    catch (Exception e) {
        println(e)
    } 
}

//Select the first account on Shopify
if (WebUI.verifyElementPresent(a_select_1st_account, 1, FailureHandling.OPTIONAL)) {
    WebUI.click(a_select_1st_account)
}

//Check the store is available, execute to install True Profit
if (!(WebUI.verifyElementPresent(shop_not_found, 3, FailureHandling.OPTIONAL))) {
    WebUI.waitForElementVisible(btn_install, 15)

    WebUI.click(btn_install)

    WebUI.waitForPageLoad(15)

    WebUI.waitForElementPresent(btn_get_started, 15)

    WebUI.delay(3)

    WebUI.takeFullPageScreenshot('screenshot/install_success.png')
} else {
    println('This shop is currently unavailable.')
}
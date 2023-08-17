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
import test.ReadExcel as ReadExcel

def url = GlobalVariable.url

////Link "SHTP-XXXX" for testing
//def url = GlobalVariable.protocal + GlobalVariable.test_subdomain + GlobalVariable.storeDomain_name
def storeDomain = ''

// Read data from Excel using the custom keyword
List<Map> excelData = ReadExcel.readExcelData('TestData/created_storeDomain.xlsx', 'Sheet1')

// Loop through the data and perform test steps
for (Map rowData : excelData) {
    storeDomain = (rowData['Domain'])
}

WebUI.navigateToUrl(url)

// If already logged in to TrueProfit, it will be logged out to install app
if (!(WebUI.verifyElementPresent(GlobalVariable.main_trueprofit, 1, FailureHandling.OPTIONAL))) {
    WebUI.waitForElementVisible(btn_login_with_shopify, 10)

    WebUI.click(btn_login_with_shopify)

    WebUI.waitForElementVisible(txt_domain, 10)

    WebUI.sendKeys(txt_domain, storeDomain)

    WebUI.click(btn_signin)
} else {
    WebUI.click(gear_option)

    WebUI.waitForElementVisible(btn_logout_trueprofit, 3)

    WebUI.click(btn_logout_trueprofit)

    WebUI.waitForElementVisible(btn_ok_logout, 3)

    WebUI.click(btn_ok_logout)

    WebUI.waitForElementVisible(btn_login_with_shopify, 10)

    WebUI.click(btn_login_with_shopify)

    WebUI.waitForElementVisible(txt_domain, 10)

    WebUI.sendKeys(txt_domain, storeDomain)

    WebUI.click(btn_signin)
}

//Login to Shopify admin if it is required
if (WebUI.verifyElementPresent(GlobalVariable.txt_email, 1, FailureHandling.OPTIONAL)) {
    WebUI.callTestCase(findTestCase('Shopify Admin/SFA001_LoginShopify'), [('url') : 'https://accounts.shopify.com/lookup?rid=d50c60a2-1d7c-469c-a15f-39bc5d99f8bf'
            , ('h2_yourstore') : findTestObject('Shopify Admin/LoginShopify/h2_yourstore'), ('username') : 'phatnt@firegroup.io'
            , ('password') : '0BXcTMSMNdRd23Ml/vw4nA==', ('btn_continue') : findTestObject('Shopify Admin/LoginShopify/btn_continue_with_email')
            , ('btn_login') : findTestObject('Shopify Admin/LoginShopify/btn_login'), ('txt_email') : findTestObject('Shopify Admin/LoginShopify/txt_email')
            , ('txt_password') : findTestObject('Shopify Admin/LoginShopify/txt_password'), ('a_remind_later') : findTestObject(
                'Shopify Admin/LoginShopify/a_remind_later')], FailureHandling.STOP_ON_FAILURE)

    WebUI.navigateToUrl(url)

    WebUI.waitForElementVisible(btn_login_with_shopify, 10)

    WebUI.click(btn_login_with_shopify)

    WebUI.waitForElementVisible(txt_domain, 10)

    WebUI.sendKeys(txt_domain, storeDomain)

    WebUI.click(btn_signin)
}

// Select the first account on Shopify if required
if (WebUI.verifyElementPresent(GlobalVariable.a_select_1st_account, 1, FailureHandling.OPTIONAL)) {
    WebUI.click(GlobalVariable.a_select_1st_account)
}

// Go to authorize app install permission if not allowed
if (WebUI.verifyElementPresent(btn_disabled_install, 1, FailureHandling.OPTIONAL)) {
    WebUI.navigateToUrl(GlobalVariable.partner_nonprod)

    WebUI.waitForElementPresent(filter_store, 3)

    WebUI.sendKeys(filter_store, storeDomain)

    WebUI.click(btn_fitlers_option)

    WebUI.click(filter_first_choice)

    WebUI.click(filter_first_choice)

    WebUI.click(header_title)

    WebUI.waitForElementPresent(store_list, 3)

    WebUI.click(p_install_app)

    WebUI.waitForElementPresent(btn_install_app_popup, 3)

    WebUI.click(btn_install_app_popup)
}

// If the store is already installed, do nothing
if (WebUI.verifyElementPresent(btn_install, 1, FailureHandling.OPTIONAL) || WebUI.verifyElementPresent(btn_enabled_install, 
    1, FailureHandling.OPTIONAL)) {
    WebUI.click(btn_install)

    WebUI.waitForPageLoad(15)

    WebUI.waitForElementPresent(btn_get_started, 15)

    WebUI.takeFullPageScreenshot('screenshot/install_success.png')
} else {
    WebUI.comment(('Store ' + storeDomain) + ' is already installed to TrueProfit')
}
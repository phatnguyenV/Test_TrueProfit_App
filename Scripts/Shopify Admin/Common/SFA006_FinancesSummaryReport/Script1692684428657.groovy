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
import pkg.ReplaceNumber as ReplaceNumber

def storeDomain = GlobalVariable.store_domain

def storeAdmin = (GlobalVariable.protocal + storeDomain +
	 GlobalVariable.SPF_admin_domain_name) + '/reports/finances'

WebUI.navigateToUrl(storeAdmin)

// Login to Shopify admin if it is required
while (WebUI.verifyElementPresent(GlobalVariable.txt_email, 1, FailureHandling.OPTIONAL)) {
    try {
        WebUI.callTestCase(findTestCase('Shopify Admin/Common/SFA001_LoginShopify'), [('url') : 'https://accounts.shopify.com/lookup?rid=d50c60a2-1d7c-469c-a15f-39bc5d99f8bf'
                , ('h2_yourstore') : findTestObject('Shopify Admin/LoginShopify/h2_yourstore'), ('username') : 'phatnt@firegroup.io'
                , ('password') : '0BXcTMSMNdRd23Ml/vw4nA==', ('btn_continue') : findTestObject('Shopify Admin/LoginShopify/btn_continue_with_email')
                , ('btn_login') : findTestObject('Shopify Admin/LoginShopify/btn_login'), ('txt_email') : findTestObject(
                    'Shopify Admin/LoginShopify/txt_email'), ('txt_password') : findTestObject('Shopify Admin/LoginShopify/txt_password')
                , ('a_remind_later') : findTestObject('Shopify Admin/LoginShopify/a_remind_later')], FailureHandling.STOP_ON_FAILURE)

        WebUI.navigateToUrl(storeAdmin)

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

WebUI.waitForElementVisible(div_layout, 10)

// Filter data for today 
WebUI.waitForElementClickable(btn_summary_date_range, 3)

WebUI.click(btn_summary_date_range)

WebUI.waitForElementClickable(li_today_filter, 3)

WebUI.click(li_today_filter)

WebUI.delay(1)

WebUI.click(btn_apply)

WebUI.delay(3)

// Store the numeric values and their corresponding element locators
Map numericValueElements = [('gross_sales') : gross_sales, ('discounts') : discounts, ('refunds') : refunds, ('shipping_charge') : shipping_charge
    , ('taxes') : taxes, ('gift_card') : gift_card, ('tips') : tips]

// Extract and store numeric values
Map variableMap = [:]

numericValueElements.each({ def variableName, def element ->
        def numericValue = ReplaceNumber.removeDollarSymbol(WebUI.getText(element)).toDouble()

        (variableMap[variableName]) = numericValue
    })

// Call the function to write the values to an Excel file
CustomKeywords.'pkg.WriteExcel.writeSummaryToExcel'(variableMap)

WebUI.takeFullPageScreenshot(('screenshot/summary_report/' + storeDomain) + '_finances_summary_report.png')
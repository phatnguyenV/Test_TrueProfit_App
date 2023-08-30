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
import util.ValueCollections as ValueCollections

def storeDomain = GlobalVariable.store_domain.toString().replaceAll("_", "-")

def storeAdmin = (GlobalVariable.protocal + storeDomain + GlobalVariable.SPF_admin_domain_name)

def date_option = GlobalVariable.date_range_select

def date_range

WebUI.navigateToUrl(storeAdmin + '/reports/finances')

// Login to Shopify admin if it is required
if(WebUI.verifyElementPresent(GlobalVariable.txt_email, 2, FailureHandling.OPTIONAL)) {
        WebUI.callTestCase(findTestCase('Shopify Admin/Common/SFA001_LoginShopify'), [('url') : 'https://accounts.shopify.com/lookup?rid=d50c60a2-1d7c-469c-a15f-39bc5d99f8bf'
                , ('h2_yourstore') : findTestObject('Shopify Admin/LoginShopify/h2_yourstore'), ('username') : 'phatnt@firegroup.io'
                , ('password') : '0BXcTMSMNdRd23Ml/vw4nA==', ('btn_continue') : findTestObject('Shopify Admin/LoginShopify/btn_continue_with_email')
                , ('btn_login') : findTestObject('Shopify Admin/LoginShopify/btn_login'), ('txt_email') : findTestObject(
                    'Shopify Admin/LoginShopify/txt_email'), ('txt_password') : findTestObject('Shopify Admin/LoginShopify/txt_password')
                , ('a_remind_later') : findTestObject('Shopify Admin/LoginShopify/a_remind_later')], FailureHandling.STOP_ON_FAILURE)

        WebUI.navigateToUrl(storeAdmin)   
		
		WebUI.delay(3)
}

// Select the first account on Shopify if required
if (WebUI.verifyElementPresent(GlobalVariable.a_select_1st_account, 1, FailureHandling.OPTIONAL)) {
    WebUI.click(GlobalVariable.a_select_1st_account)
	// Skip two-factor authentication if reminded
	if (WebUI.verifyElementPresent(GlobalVariable.a_remind_later, 1, FailureHandling.OPTIONAL)) {
		WebUI.click(GlobalVariable.a_remind_later, FailureHandling.STOP_ON_FAILURE)
	}
}


WebUI.waitForElementVisible(div_layout, 10)

// Filter data for today 
WebUI.waitForElementClickable(btn_summary_date_range, 3)

WebUI.click(btn_summary_date_range)

switch(date_option) {
	case 'today':
	date_range = li_today_filter
		WebUI.click(date_range)
		break
	case 'yesterday':
	date_range = li_yesterday_filter
		WebUI.click(date_range)
		break
	case 'last7days':
	date_range = li_last_7_days_filter
		WebUI.click(date_range)
		break
	case 'last30days':
	date_range = li_last_30_days_filter
		WebUI.click(date_range)
		break
	case 'thismonth':
	date_range = li_this_month_filter
		WebUI.click(date_range)
		break
	case 'thisyear':
	date_range = li_this_year_filter
		WebUI.click(date_range)
		break
	case 'lastmonth':
	date_range = li_last_month_filter
		WebUI.click(date_range)
		break
	case 'lastyear':
	date_range = li_last_year_filter
		WebUI.click(date_range)
		break
	default:
	date_range = li_today_filter
		WebUI.click(date_range)
}

WebUI.delay(1)

WebUI.click(btn_apply)

WebUI.delay(1)

// Store the numeric values and their corresponding element locators
Map numericValueElements = [('total_orders') : 'placeholder' ,('gross_sales') : gross_sales, ('discounts') : discounts, ('refunds') : refunds, ('shipping_charge') : shipping_charge
    , ('taxes') : taxes, ('gift_card') : gift_card, ('tips') : tips]

// Extract and store numeric values
Map variableMap = [:]

numericValueElements.each({ def variableName, def element ->
      if (element instanceof TestObject) {
        numericValue = ReplaceNumber.removeDollarSymbol(WebUI.getText(element)).toDouble()
    } else if (variableName == 'total_orders') {
        numericValue = 0  
    }

        (variableMap[variableName]) = numericValue
    })

// Store results to re-use between test cases
ValueCollections.map.put('summary_gross_sales', variableMap['gross_sales'])

ValueCollections.map.put('summary_discounts', variableMap['discounts'])

ValueCollections.map.put('summary_refunds', variableMap['refunds'])

ValueCollections.map.put('summary_shipping_charge', variableMap['shipping_charge'])

ValueCollections.map.put('summary_taxes', variableMap['taxes'])

ValueCollections.map.put('summary_gift_card', variableMap['gift_card'])

ValueCollections.map.put('summary_tips', variableMap['tips'])

//println('summary_gross_sales: ' + (variableMap['gross_sales']))
//
//println('summary_discounts: ' + (variableMap['discounts']))
//
//println('summary_refunds: ' + (variableMap['refunds']))
//
//println('summary_shipping_charge: ' + (variableMap['shipping_charge']))
//
//println('summary_taxes: ' + (variableMap['taxes']))
//
//println('summary_gift_card: ' + (variableMap['gift_card']))
//
//println('summary_tips: ' + (variableMap['tips']))

WebUI.navigateToUrl(storeAdmin + "/reports/orders_over_time")

WebUI.waitForElementPresent(btn_oot_date_range, 4)

WebUI.click(btn_oot_date_range)

WebUI.click(date_range)
	
WebUI.delay(1)

WebUI.click(btn_apply)

WebUI.delay(1)

def totalOrders = ReplaceNumber.removeDollarSymbol(WebUI.getText(span_total_orders)).toDouble()

variableMap['total_orders'] = totalOrders

ValueCollections.map.put('summary_total_orders', variableMap['total_orders'])

// Call the function to write the values to an Excel file
CustomKeywords.'pkg.WriteExcel.writeSummaryToExcel'(variableMap)

WebUI.takeFullPageScreenshot(('screenshot/summary_report/' + storeDomain) + '_finances_summary_report.png')
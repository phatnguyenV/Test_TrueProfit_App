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
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable

import org.openqa.selenium.By
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.WebElement

def storeDomain = GlobalVariable.store_domain
//def storeDomain = 'phatnt-newstore-1'

//Go to the store's Shopify Admin
def storeAdmin = (GlobalVariable.protocal + storeDomain) + GlobalVariable.SPF_admin_domain_name

def isOrderCustomer = GlobalVariable.isOrderCustomer

def customerInfo = [('name') : 'Adam Lewis', ('discount') : '50', ('shipping_charge') : '20']

def productItems = ['Antique Drawers','Cream Sofa','Pink Armchair']

WebUI.navigateToUrl(storeAdmin)

//Login to Shopify admin if it is required
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

//Select the first account on Shopify
if (WebUI.verifyElementPresent(GlobalVariable.a_select_1st_account, 1, FailureHandling.OPTIONAL)) {
    WebUI.click(GlobalVariable.a_select_1st_account)
}

WebUI.waitForElementVisible(span_orders, 3)
WebUI.click(span_orders)

//Click "Leave page" if already opened the create order page
if (WebUI.waitForElementVisible(btn_leave_page, 1, FailureHandling.OPTIONAL)) {
	WebUI.click(btn_leave_page)
	WebUI.waitForElementVisible(span_orders, 1)
	WebUI.click(span_orders)
}

WebUI.waitForElementVisible(span_create_order, 1)

WebUI.click(span_create_order)

// Start to browse and select products to buy
WebUI.waitForElementVisible(span_browser, 1)

WebUI.click(span_browser)

WebUI.waitForElementVisible(input_search_products, 1)

WebUI.sendKeys(input_search_products, productItems[0])

WebUI.waitForElementVisible(select_first_item, 1)

WebUI.click(select_first_item)

WebUI.sendKeys(input_search_products_has_value, Keys.chord(Keys.CONTROL, 'a', Keys.BACK_SPACE) + productItems[1])

WebUI.waitForElementVisible(select_first_item, 1)

WebUI.click(select_first_item)

WebUI.sendKeys(input_search_products_has_value, Keys.chord(Keys.CONTROL, 'a', Keys.BACK_SPACE) + productItems[2])

WebUI.waitForElementVisible(select_first_item, 1)

WebUI.click(select_first_item)

WebUI.waitForElementVisible(btn_add, 3)

WebUI.click(btn_add)

WebUI.delay(3)

WebUI.sendKeys(input_quantity, Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE) + "2")

//// Find all input fields matching the xpath and sendKeys to them
//def driver = DriverFactory.getWebDriver()
//List<WebElement> textfields = driver.findElements(By.xpath("//*[@class='Polaris-TextField__Input_30ock'][@min]"));
//
//// Iterate through the input fields and send keys to each one
//	textfields.each { textField ->
//    textField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE) + "2")
//}

// Add user info to the order if required
if(isOrderCustomer) {
	
	WebUI.sendKeys(input_customer, customerInfo.name)
	
	WebUI.delay(1)
	
	WebUI.clickOffset(input_customer, 0, 70)
	
	WebUI.delay(2)
	
}

// Start to add discount for the order
WebUI.waitForElementVisible(span_add_discount, 3)

WebUI.delay(4)

WebUI.click(span_add_discount)

WebUI.sendKeys(discount_value, customerInfo.discount)

WebUI.click(btn_apply)

WebUI.delay(2)

// Start to add shipping charge for the order
WebUI.click(span_add_shipping_charge)

WebUI.waitForElementVisible(radio_custom_shipping, 3)

WebUI.delay(2)

WebUI.click(radio_custom_shipping)

// If shipping charged, Shopify will re-calulate the tax based on it
WebUI.sendKeys(shipping_charge_value, customerInfo.shipping_charge)

WebUI.click(btn_apply)

WebUI.delay(2)

// Start to make a payment
WebUI.scrollToElement(btn_collect_payment, 2)

WebUI.click(btn_collect_payment)

WebUI.waitForElementVisible(mark_as_paid, 3)

WebUI.delay(1.5)

WebUI.click(mark_as_paid)

WebUI.waitForElementVisible(span_create_order, 1)

WebUI.delay(1)

WebUI.click(span_create_order)

// Start to refund items
WebUI.delay(2)

WebUI.waitForElementVisible(btn_refund, 3)

WebUI.delay(2)

WebUI.click(btn_refund)

WebUI.waitForElementVisible(first_refund_quantity_2, 3)

WebUI.sendKeys(first_refund_quantity_2, '1')

WebUI.delay(2)

WebUI.click(btn_refund_items)

WebUI.delay(2)
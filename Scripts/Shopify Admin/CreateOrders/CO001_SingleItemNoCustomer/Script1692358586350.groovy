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
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

def storeDomain = GlobalVariable.store_domain

//def storeDomain = 'phatnt-newstore-1'
//Go to the store's Shopify Admin
def storeAdmin = (GlobalVariable.protocal + storeDomain) + GlobalVariable.SPF_admin_domain_name

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
if (WebUI.verifyElementPresent(GlobalVariable.a_select_1st_account, 2, FailureHandling.OPTIONAL)) {
    WebUI.click(GlobalVariable.a_select_1st_account)
}



WebUI.waitForElementPresent(span_orders, 1)
WebUI.click(span_orders)

//Click "Leave page" if already opened the create order page
if (WebUI.verifyElementPresent(btn_leave_page, 1, FailureHandling.OPTIONAL)) {
	WebUI.click(btn_leave_page)
	WebUI.waitForElementPresent(span_orders, 1)
	WebUI.click(span_orders)
}

WebUI.waitForElementPresent(span_create_order, 1)

WebUI.click(span_create_order)

WebUI.waitForElementPresent(span_browser, 1)

WebUI.click(span_browser)

WebUI.waitForElementPresent(input_search_products, 1)

WebUI.sendKeys(input_search_products, 'Antique Drawers')

WebUI.waitForElementPresent(select_antique, 1)

WebUI.click(select_antique)

WebUI.waitForElementPresent(btn_add, 1)

WebUI.click(btn_add)

WebUI.click(btn_collect_payment)

WebUI.waitForElementPresent(mark_as_paid, 1)

WebUI.click(mark_as_paid)

WebUI.waitForElementPresent(span_create_order, 1)

WebUI.click(span_create_order)

WebUI.waitForElementPresent(div_order_list, 1)

WebUI.delay(2)

WebUI.click(tr_latest_order)

WebUI.waitForElementPresent(section_order_details, 1)
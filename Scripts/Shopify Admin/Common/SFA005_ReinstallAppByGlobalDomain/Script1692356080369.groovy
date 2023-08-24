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

def urlTrueProfit = GlobalVariable.urlTrueProfit

def storeDomain = GlobalVariable.store_domain

//Go to the store's Shopify Admin
def storeAdmin = (GlobalVariable.protocal + storeDomain) + GlobalVariable.SPF_admin_domain_name

WebUI.navigateToUrl(storeAdmin)

// Login to Shopify admin if it is required
while (WebUI.verifyElementPresent(GlobalVariable.txt_email, 1, FailureHandling.OPTIONAL)) {
    try {
        WebUI.callTestCase(findTestCase('Shopify Admin/Common/SFA001_LoginShopify'), [('h2_yourstore') : findTestObject(
                    'Shopify Admin/LoginShopify/h2_yourstore'), ('btn_continue') : findTestObject('Shopify Admin/LoginShopify/btn_continue_with_email')
                , ('btn_login') : findTestObject('Shopify Admin/LoginShopify/btn_login'), ('txt_email') : findTestObject(
                    'Shopify Admin/LoginShopify/txt_email'), ('txt_password') : findTestObject('Shopify Admin/LoginShopify/txt_password')
                , ('a_remind_later') : findTestObject('Shopify Admin/LoginShopify/a_remind_later'), ('captcha') : findTestObject(
                    'Shopify Admin/LoginShopify/div_captcha'), ('a_select_1st_account') : findTestObject('Shopify Admin/CreateStore/a_select_1st_account')], 
            FailureHandling.STOP_ON_FAILURE)

        WebUI.navigateToUrl(storeAdmin)

        break
    }
    catch (Exception e) {
        println(e)
    } 
}

// Select the first account on Shopify
if (WebUI.verifyElementPresent(GlobalVariable.a_select_1st_account, 1, FailureHandling.OPTIONAL)) {
    WebUI.click(GlobalVariable.a_select_1st_account)
}

// Open Shopify settings to uninstall app
WebUI.waitForElementPresent(span_settings, 3)

WebUI.click(span_settings)

WebUI.waitForElementPresent(span_apps_and_sales, 3)

WebUI.click(span_apps_and_sales)

// Execute the code if shop available in Shopify admin settings to uninstall
if (WebUI.verifyElementPresent(GlobalVariable.btn_option_tp, 1, FailureHandling.OPTIONAL)) {
    WebUI.waitForElementPresent(GlobalVariable.btn_option_tp, 1)

    WebUI.click(GlobalVariable.btn_option_tp)

    WebUI.waitForElementPresent(GlobalVariable.btn_uninstall_dropdown, 3)

    WebUI.click(GlobalVariable.btn_uninstall_dropdown)

    WebUI.waitForElementPresent(btn_uninstall_confirmation, 3)

    WebUI.click(btn_uninstall_confirmation)

    WebUI.waitForElementPresent(uninstalled_successfully, 3)
	
	WebUI.delay(2)
} else {
    WebUI.comment('Shop is already uninstalled or unavailable now')
}

// Sign in with Shopify on TrueProfit to reinstall
WebUI.navigateToUrl(urlTrueProfit)

// Logout the current shop if user is signing in
if (WebUI.verifyElementPresent(GlobalVariable.main_trueprofit, 1, FailureHandling.OPTIONAL)) {
    WebUI.callTestCase(findTestCase('TrueProfit/Common/TP002_LogoutTrueProfit'), [('menubar') : findTestObject('TrueProfit/Common/div_menubar')
            , ('gear_option') : findTestObject('TrueProfit/Common/btn_gear_option'), ('btn_logout_trueprofit') : findTestObject(
                'TrueProfit/Common/btn_logout_trueprofit'), ('btn_ok_logout') : findTestObject('TrueProfit/Common/btn_ok_logout')
            , ('btn_signin') : findTestObject('TrueProfit/Common/btn_signin')], FailureHandling.STOP_ON_FAILURE)
	
	WebUI.delay(3)
} 

WebUI.waitForElementVisible(btn_login_with_shopify, 10)

WebUI.click(btn_login_with_shopify)

WebUI.waitForElementVisible(input_shopify_domain, 10)

WebUI.sendKeys(input_shopify_domain, GlobalVariable.store_domain)

WebUI.click(btn_signin)

// Select the first account on Shopify
if (WebUI.verifyElementPresent(GlobalVariable.a_select_1st_account, 1, FailureHandling.OPTIONAL)) {
    WebUI.click(GlobalVariable.a_select_1st_account)
}

// If not direct to the install page, it do install app 
if (!(WebUI.verifyElementPresent(btn_install, 1, FailureHandling.OPTIONAL))) {
    WebUI.navigateToUrl(urlTrueProfit)

    WebUI.waitForElementVisible(btn_login_with_shopify, 10)

    WebUI.click(btn_login_with_shopify)

    WebUI.waitForElementVisible(input_shopify_domain, 10)

    WebUI.sendKeys(input_shopify_domain, GlobalVariable.store_domain)

    WebUI.click(btn_signin)
}

// Check the store is available, execute to install True Profit
if (!(WebUI.verifyElementPresent(shop_not_found, 1, FailureHandling.OPTIONAL))) {
    WebUI.waitForElementVisible(btn_install, 5)

    WebUI.click(btn_install)

    WebUI.waitForElementPresent(GlobalVariable.main_trueprofit, 15)
} else {
    println('This shop is currently unavailable.')
}
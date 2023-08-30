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

def shopDomain = GlobalVariable.store_domain.toString().replaceAll('-', '_')

WebUI.navigateToUrl(urlTrueProfit)

// Call the login test case if required by the app
if (WebUI.verifyElementPresent(input_email, 1, FailureHandling.OPTIONAL)) {
    WebUI.callTestCase(findTestCase('TrueProfit/Common/TP001_1_LoginTrueProfitByAccount'), [('account') : [('email_2') : 'phatnt@firegroup.io'
                , ('email_1') : 'demo_pa2@gmail.com', ('email_3') : 'triettm@firegroup.io'], ('btn_sign_in') : findTestObject(
                'TrueProfit/General/btn_signin'), ('input_email') : findTestObject('TrueProfit/Common/input_email'), ('input_password') : findTestObject(
                'TrueProfit/General/input_password'), ('main_trueprofit') : findTestObject('TrueProfit/Common/main_trueprofit')
            , ('menubar') : findTestObject('TrueProfit/Common/div_menubar')], FailureHandling.STOP_ON_FAILURE)
}

if (WebUI.verifyElementPresent(div_left_nav_menu, 1, FailureHandling.OPTIONAL)) {
    if (WebUI.verifyElementPresent(btn_get_started, 1, FailureHandling.OPTIONAL)) {
        WebUI.click(btn_get_started)
    }
    
    if (WebUI.verifyElementPresent(btn_save_and_continue, 1, FailureHandling.OPTIONAL)) {
        WebUI.click(btn_save_and_continue)
    }
    
    if (WebUI.verifyElementPresent(btn_start_importing, 1, FailureHandling.OPTIONAL)) {
        WebUI.click(btn_start_importing)
    }
}

WebUI.delay(3)

// Check whether the store has completed the onboarding steps or not
if (WebUI.verifyElementPresent(section_pricing, 1, FailureHandling.OPTIONAL)) {
    // Skip two-factor authentication if reminded
    if (WebUI.verifyElementPresent(GlobalVariable.a_remind_later, 1, FailureHandling.OPTIONAL)) {
        WebUI.click(GlobalVariable.a_remind_later)
    }
    
    // Select plan base on the global variable value
    switch (GlobalVariable.pricingPlan.toString().toLowerCase()) {
        case 'basic':
            WebUI.click(btn_basic_plan)

            break
        case 'advanced':
            WebUI.click(btn_advanced_plan)

            break
        case 'ultimate':
            WebUI.click(btn_ultimate_plan)

            break
        case 'enterprise':
            if (GlobalVariable.billingPlan.toString().toLowerCase() == 'yearly') {
                WebUI.click(radio_yearly)
            } else {
                WebUI.click(radio_monthly)
            }
            
            WebUI.click(btn_enterprise_plan)

            break
        default:
            WebUI.click(btn_enterprise_plan)}
    
    WebUI.waitForElementPresent(btn_approve_charge, 10)

    WebUI.click(btn_approve_charge)

    WebUI.waitForElementPresent(GlobalVariable.main_trueprofit, 15)

    WebUI.navigateToUrl(urlTrueProfit)

    WebUI.waitForElementPresent(GlobalVariable.main_trueprofit, 15)
} else {
    WebUI.comment('This store has completed the onboarding steps')
}

// Skip linking to account
if (WebUI.verifyElementPresent(p_no_thanks, 1, FailureHandling.OPTIONAL)) {
    WebUI.click(p_no_thanks)
}

WebUI.click(div_billing_details)
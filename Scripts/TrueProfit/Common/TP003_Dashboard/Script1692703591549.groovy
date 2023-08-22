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

WebUI.navigateToUrl(urlTrueProfit)

// Call the login test case if required by the app
if (WebUI.verifyElementPresent(input_email, 1, FailureHandling.OPTIONAL)) {
    WebUI.callTestCase(findTestCase('TrueProfit/Common/TP001_1_LoginTrueProfitByAccount'), [('account') : [('email_2') : 'phatnt@firegroup.io'
                , ('email_1') : 'demo_pa2@gmail.com', ('email_3') : 'triettm@firegroup.io'], ('btn_sign_in') : findTestObject(
                'TrueProfit/General/btn_signin'), ('input_email') : findTestObject('TrueProfit/Common/input_email'), ('input_password') : findTestObject(
                'TrueProfit/General/input_password'), ('main_trueprofit') : findTestObject('TrueProfit/Common/main_trueprofit')
            , ('menubar') : findTestObject('TrueProfit/Common/div_menubar')], FailureHandling.STOP_ON_FAILURE)
}

WebUI.waitForElementVisible(GlobalVariable.main_trueprofit, 15)




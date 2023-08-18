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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

def AdFacebook = [('antique_drawers_1111') : '?tp_source=facebook&tp_adid=1111&tp_productid=8326381666626', ('antique_drawers_1121') : '?tp_source=facebook&tp_adid=1121&tp_productid=8326381666626'
    , ('antique_drawers_1211') : '?tp_source=facebook&tp_adid=1211&tp_productid=8326381666626', ('antique_drawers_1112') : '?tp_source=facebook&tp_adid=1112&tp_productid=8326381666626'
    , ('black_bean_1111') : '?tp_source=facebook&tp_adid=1111&tp_productid=8326382715202']

def AdGoogle = [('antique_drawers_2111') : '?tp_source=google&tp_adid=2111&tp_productid=8326381666626', ('antique_drawers_2112') : '?tp_source=google&tp_adid=2112&tp_productid=8326381666626']

def AdTiktok = [('antique_drawers_3111') : '?tp_source=tiktok&tp_adid=3111&tp_productid=8326381666626', ('antique_drawers_3112') : '?tp_source=tiktok&tp_adid=3112&tp_productid=8326381666626'
    , ('brown_throw_pillows_3111') : '?tp_source=tiktok&tp_adid=3111&tp_productid=8326381928770']

def AdSnapchat = [('antique_drawers_4111') : '?tp_source=snapchat&tp_adid=4111&tp_productid=8326381666626', ('antique_drawers_4112') : '?tp_source=snapchat&tp_adid=4112&tp_productid=8326381666626'
    , ('antique_drawers_41122') : '?tp_source=snapchat&tp_adid=41122&tp_productid=8326381666626', ('antique_drawers_4113') : '?tp_source=snapchat&tp_adid=4113&tp_productid=8326381666626'
    , ('copper_light_4111') : '?tp_source=snapchat&tp_adid=4111&tp_productid=8326381535554']

def AdPinterest = [('antique_drawers_5111') : '?tp_source=pinterest&tp_adid=5111&tp_productid=8326381666626', ('antique_drawers_5112') : '?tp_source=pinterest&tp_adid=5112&tp_productid=8326381666626'
    , ('cream_sofa_5111') : '?tp_source=pinterest&tp_adid=5111&tp_productid=8326381601090',('black_bean_5111') : '?tp_source=pinterest&tp_adid=5111&tp_productid=8326382715202']

def AdBing = [('gardening_hand_trowel_6112') : '?tp_source=bing&tp_adid=6112&tp_productid=8326382092610', ('gardening_hand_trowel_6113') : '?tp_source=bing&tp_adid=6113&tp_productid=8326382092610']

WebUI.navigateToUrl(store_url)

WebUI.waitForPageLoad(5)

while (WebUI.waitForElementPresent(input_password, 1, FailureHandling.OPTIONAL)) {
    try {
        WebUI.setEncryptedText(input_password, GlobalVariable.default_password)

        WebUI.click(btn_enter)

        break
    }
    catch (Exception e) {
        println(e)
    } 
}

WebUI.waitForElementVisible(main_content, 5)

WebUI.navigateToUrl((store_url + 'products/antique-drawers') + AdSnapchat.antique_drawers_4111)

WebUI.waitForElementPresent(add_to_cart, 3)

WebUI.click(plus_quantity)

WebUI.click(add_to_cart)

WebUI.navigateToUrl((store_url + 'products/gardening-hand-trowel') + AdBing.gardening_hand_trowel_6112)

WebUI.waitForElementPresent(add_to_cart, 3)

WebUI.click(plus_quantity)

WebUI.click(add_to_cart)

WebUI.navigateToUrl((store_url + 'products/black-bean-bag') + AdPinterest.black_bean_5111)

WebUI.refresh()

WebUI.waitForElementPresent(add_to_cart, 3)

WebUI.click(add_to_cart)

WebUI.click(view_cart)

WebUI.waitForElementPresent(checkout_in_cart, 3)

WebUI.click(checkout_in_cart)

//WebUI.waitForElementPresent(btn_buy_it_now, 5)
//WebUI.click(btn_buy_it_now)
WebUI.verifyElementPresent(email, 3)

currentWindow = WebUI.getWindowIndex()

WebUI.executeJavaScript('window.open("https://10minutemail.net/");', [])

WebUI.switchToWindowIndex(currentWindow + 1)
//
//WebUI.navigateToUrl('https://10minutemail.net/')

def email_10m = ''

while (true) {
    try {
        DriverFactory.getWebDriver().manage().deleteAllCookies()

        WebUI.refresh()

        WebUI.waitForElementPresent(temp_email, 5)

        email_10m = WebUI.getAttribute(temp_email, 'value')

        break
    }
    catch (Exception e) {
        println(e)
    } 
}

email_10m = WebUI.getAttribute(temp_email, 'value')

CustomKeywords.'pkg.WriteExcel.saveEmailToExcel'(email_10m)

WebUI.closeWindowIndex(currentWindow + 1)

WebUI.switchToWindowIndex(currentWindow)

WebUI.waitForElementVisible(email, 3)

WebUI.sendKeys(email, email_10m)

WebUI.sendKeys(lastname, email_10m)

Random random = new Random()

def randomNumber = random.nextInt(999) + 1

//Convert randonNumber to a string
def randomInput = Integer.toString(randomNumber)

println(randomInput)

WebUI.waitForElementPresent(address, 5)

WebUI.sendKeys(address, randomInput)

WebUI.waitForElementPresent(first_address_suggestion, 5)

WebUI.click(first_address_suggestion)

//WebUI.click(btn_close_suggestion)
// click Continue to shipping
WebUI.scrollToPosition(0, 400)

WebUI.click(btn_continue)

// click Continue to payment
WebUI.waitForElementPresent(btn_continue, 2)

WebUI.click(btn_continue)

// click Continue to payment
//WebUI.waitForElementPresent(iframe_card_number, 2)
WebUI.switchToFrame(iframe_card_number, 2)

WebUI.scrollToElement(card_number, 2)

WebUI.sendKeys(card_number, '1')

WebUI.switchToDefaultContent()

WebUI.switchToFrame(iframe_name_on_card, 2)

WebUI.sendKeys(name_on_card, 'Bogus Gateway')

WebUI.switchToDefaultContent()

WebUI.switchToFrame(iframe_expiration, 2)

WebUI.sendKeys(expiration, '03')

WebUI.sendKeys(expiration, '39')

WebUI.switchToDefaultContent()

WebUI.switchToFrame(iframe_security_code, 2)

WebUI.sendKeys(security_code, '111')

WebUI.switchToDefaultContent()

WebUI.click(btn_continue)

WebUI.waitForElementPresent(span_continue_shopping, 10)


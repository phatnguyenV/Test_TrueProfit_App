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
import pkg.ReadExcel as ReadExcel

def AdFacebook = [('antique_drawers_1111') : '?tp_source=facebook&tp_adid=1111&tp_productid=8326381666626', ('antique_drawers_1121') : '?tp_source=facebook&tp_adid=1121&tp_productid=8326381666626'
    , ('antique_drawers_1211') : '?tp_source=facebook&tp_adid=1211&tp_productid=8326381666626', ('antique_drawers_1112') : '?tp_source=facebook&tp_adid=1112&tp_productid=8326381666626'
    , ('black_bean_1111') : '?tp_source=facebook&tp_adid=1111&tp_productid=8326382715202', ('black_bean_1112') : '?tp_source=facebook&tp_adid=1112&tp_productid=8326382715202']

def AdGoogle = [('antique_drawers_2111') : '?tp_source=google&tp_adid=2111&tp_productid=8326381666626', ('antique_drawers_2112') : '?tp_source=google&tp_adid=2112&tp_productid=8326381666626']

def AdTiktok = [('antique_drawers_3111') : '?tp_source=tiktok&tp_adid=3111&tp_productid=8326381666626', ('antique_drawers_3112') : '?tp_source=tiktok&tp_adid=3112&tp_productid=8326381666626'
    , ('brown_throw_pillows_3111') : '?tp_source=tiktok&tp_adid=3111&tp_productid=8326381928770']

def AdSnapchat = [('antique_drawers_4111') : '?tp_source=snapchat&tp_adid=4111&tp_productid=8326381666626', ('antique_drawers_4112') : '?tp_source=snapchat&tp_adid=4112&tp_productid=8326381666626'
    , ('antique_drawers_41122') : '?tp_source=snapchat&tp_adid=41122&tp_productid=8326381666626', ('antique_drawers_4113') : '?tp_source=snapchat&tp_adid=4113&tp_productid=8326381666626'
    , ('copper_light_4111') : '?tp_source=snapchat&tp_adid=4111&tp_productid=8326381535554']

def AdPinterest = [('antique_drawers_5111') : '?tp_source=pinterest&tp_adid=5111&tp_productid=8326381666626', ('antique_drawers_5112') : '?tp_source=pinterest&tp_adid=5112&tp_productid=8326381666626'
    , ('cream_sofa_5111') : '?tp_source=pinterest&tp_adid=5111&tp_productid=8326381601090']

def AdBing = [('antique_drawers_6112') : '?tp_source=bing&tp_adid=6112&tp_productid=8326381666626', ('gardening_hand_trowel_6112') : '?tp_source=bing&tp_adid=6112&tp_productid=8326382092610'
    , ('gardening_hand_trowel_6113') : '?tp_source=bing&tp_adid=6113&tp_productid=8326382092610']

WebUI.navigateToUrl(store_url)

DriverFactory.getWebDriver().manage().deleteAllCookies()

WebUI.refresh()

WebUI.waitForPageLoad(5)

//Insert password to store front if required
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

//Create Last-clicked order for Facebook ad
WebUI.navigateToUrl((store_url + 'products/antique-drawers') + AdFacebook.antique_drawers_1111)

WebUI.waitForElementPresent(btn_buy_it_now, 5)

WebUI.click(btn_buy_it_now)

generateAndSaveTempmail()

WebUI.waitForElementVisible(email, 3)

makePayment()

//Create Last-clicked order for Gooogle ad
WebUI.navigateToUrl((store_url + 'products/antique-drawers') + AdGoogle.antique_drawers_2111)

WebUI.waitForElementPresent(btn_buy_it_now, 5)

WebUI.click(btn_buy_it_now)

WebUI.waitForElementVisible(email, 3)

makePayment()

//Create Last-clicked order for Tiktok ad
WebUI.navigateToUrl((store_url + 'products/antique-drawers') + AdTiktok.antique_drawers_3111)

WebUI.waitForElementPresent(btn_buy_it_now, 5)

WebUI.click(btn_buy_it_now)

WebUI.waitForElementVisible(email, 3)

makePayment()

//Create Last-clicked order for Snapchat ad
WebUI.navigateToUrl((store_url + 'products/antique-drawers') + AdSnapchat.antique_drawers_4111)

WebUI.waitForElementPresent(btn_buy_it_now, 5)

WebUI.click(btn_buy_it_now)

WebUI.waitForElementVisible(email, 3)

makePayment()

//Create Last-clicked order for Pinterest ad
WebUI.navigateToUrl((store_url + 'products/antique-drawers') + AdPinterest.antique_drawers_5111)

WebUI.waitForElementPresent(btn_buy_it_now, 5)

WebUI.click(btn_buy_it_now)

WebUI.waitForElementVisible(email, 3)

makePayment()

//Create Last-clicked order for Bing ad
WebUI.navigateToUrl((store_url + 'products/antique-drawers') + AdBing.antique_drawers_6112)

WebUI.waitForElementPresent(btn_buy_it_now, 5)

WebUI.click(btn_buy_it_now)

WebUI.waitForElementVisible(email, 3)

makePayment()


//Functions
def makePayment() {
    def excelFilePath = 'TestData/temp_email_mca.xlsx'

    def sheetName = 'Sheet1'

    def getEmail = ''

    List<Map> excelData = ReadExcel.readExcelData(excelFilePath, sheetName)

    for (Map rowData : excelData) {
        getEmail = (rowData['Email'])
    }
    
    WebUI.comment(getEmail)

    WebUI.sendKeys(email, getEmail)

    WebUI.sendKeys(lastname, getEmail)

    Random random = new Random()

    def randomNumber = random.nextInt(999) + 1

    def randomInput = Integer.toString(randomNumber)

    println(randomInput)

    WebUI.waitForElementPresent(address, 5)

    WebUI.sendKeys(address, randomInput)

    WebUI.waitForElementPresent(first_address_suggestion, 3)
	
	WebUI.delay(2)

    WebUI.click(first_address_suggestion)

    WebUI.scrollToPosition(0, 400)
	
	WebUI.delay(2)

    WebUI.click(btn_continue)

    WebUI.waitForElementPresent(btn_continue, 3)

    WebUI.click(btn_continue)

    WebUI.waitForElementPresent(iframe_card_number, 2)

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
}

def generateAndSaveTempmail() {
    def currentWindow = WebUI.getWindowIndex()

    WebUI.executeJavaScript('window.open("https://10minutemail.net/");', [])

    WebUI.switchToWindowIndex(currentWindow + 1)

    DriverFactory.getWebDriver().manage().deleteAllCookies()

    WebUI.refresh()

    WebUI.waitForElementPresent(temp_email, 5)

    def email_10m = WebUI.getAttribute(temp_email, 'value')

    CustomKeywords.'pkg.WriteExcel.saveEmailToExcel'(email_10m)

    WebUI.comment(email_10m)

    WebUI.closeWindowIndex(currentWindow + 1)

    WebUI.switchToWindowIndex(currentWindow)
}
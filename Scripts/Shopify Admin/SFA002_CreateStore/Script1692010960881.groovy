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

//def storeName = "phatnt-newstore-31"
//WebUI.openBrowser("https://admin.shopify.com/")
//DriverFactory.getWebDriver().manage().deleteAllCookies()
//CustomKeywords.'test.CookieHandling.loadCookiesFromFile'("Cookies.data")
WebUI.navigateToUrl(url)

WebUI.waitForElementVisible(a_select_1st_account, 10)

WebUI.click(a_select_1st_account)

WebUI.waitForElementVisible(txt_store_name, 5)

WebUI.delay(3)

WebUI.scrollToPosition(0, 300)

WebUI.delay(5)

//WebUI.sendKeys(txt_store_name, GlobalVariable.new_input)
//WebUI.sendKeys(txt_store_name, domain)
WebUI.sendKeys(txt_store_name, 'phatnt-newstore-50')
def number = CustomKeywords.'test.ExtractNumber.extractNumber'(GlobalVariable.store_domain)

// Vòng lặp check nếu domain đã tồn tại thì extract số đuôi + 1
// Xong xoá text và thử nhập lại domain mới
// Thoát vòng lặp đến khi không còn báo lỗi
while (true) {
    def newInput = CustomKeywords.'test.ReplaceNumber.replaceNumber'(GlobalVariable.store_domain, number)

    try {
        number++

        WebUI.sendKeys(txt_store_name, Keys.chord(Keys.CONTROL, 'a', Keys.BACK_SPACE, newInput))

        WebUI.click(span_store_name)


        if (!WebUI.verifyElementPresent(txt_error, 3, FailureHandling.OPTIONAL)) {
            // Trước khi thoát vòng lặp, lưu lại domain này cho next test case 
            // Katalon chỉ lưu value này trong lúc chạy, chạy xong sẽ reverse về orginal value như Profies
            GlobalVariable.store_domain = newInput
            break
        }
    }
    catch (Exception e) {
        println(e)
    } 
}

WebUI.scrollToElement(btn_create, 5)

WebUI.waitForElementVisible(btn_create, 5)

WebUI.click(btn_create)

WebUI.waitForElementPresent(div_dashboard, 90)

WebUI.takeFullPageScreenshot('screenshot/create_store_success.png')


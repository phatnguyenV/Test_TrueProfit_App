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
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook as XSSFWorkbook
import java.io.FileInputStream as FileInputStream
import java.io.FileNotFoundException as FileNotFoundException
import java.io.FileOutputStream as FileOutputStream
import java.io.IOException as IOException
import pkg.ReplaceNumber as ReplaceNumber
import java.text.DecimalFormat as DecimalFormat
import util.ValueCollections as ValueCollections
import org.openqa.selenium.By as By
import org.openqa.selenium.chrome.ChromeDriver as ChromeDriver

def urlTrueProfit = GlobalVariable.urlTrueProfit

WebUI.callTestCase(findTestCase('Shopify Admin/Common/SFA006_FinancesSummaryReport'), [('btn_create') : findTestObject('Shopify Admin/CreateStore/btn_create_store')
        , ('txt_store_name') : findTestObject('Shopify Admin/CreateStore/txt_store_name'), ('div_dashboard') : findTestObject(
            'Shopify Admin/CreateStore/div_dashboard'), ('txt_error') : findTestObject('Shopify Admin/CreateStore/txt_error')
        , ('span_store_name') : findTestObject('Shopify Admin/CreateStore/span_store_name'), ('domain') : '', ('btn_summary_date_range') : findTestObject(
            'Shopify Admin/SummaryReport/btn_summary_date_range'), ('div_layout') : findTestObject('Shopify Admin/SummaryReport/div_layout')
        , ('li_today_filter') : findTestObject('Shopify Admin/SummaryReport/li_today_filter'), ('btn_apply') : findTestObject(
            'Shopify Admin/Orders/btn_apply'), ('div_shopify_menu_bar') : findTestObject('Shopify Admin/SummaryReport/div_shopify_menu_bar')
        , ('div_finances_layout') : findTestObject('Shopify Admin/SummaryReport/div_finances_layout'), ('gross_sales') : findTestObject(
            'Shopify Admin/SummaryReport/td_gross_sales'), ('discounts') : findTestObject('Shopify Admin/SummaryReport/td_discounts')
        , ('refunds') : findTestObject('Shopify Admin/SummaryReport/td_refunds'), ('net_sales') : findTestObject('Shopify Admin/SummaryReport/td_net_sales')
        , ('shipping_charge') : findTestObject('Shopify Admin/SummaryReport/td_shipping_charge'), ('taxes') : findTestObject(
            'Shopify Admin/SummaryReport/td_taxes'), ('total_sales') : findTestObject('Shopify Admin/SummaryReport/td_total_sales')
        , ('gift_card') : findTestObject('Shopify Admin/SummaryReport/td_gift_card'), ('tips') : findTestObject('Shopify Admin/SummaryReport/td_tips')], 
    FailureHandling.STOP_ON_FAILURE)

WebUI.navigateToUrl(urlTrueProfit)

// If already logged in to TrueProfit with another shop, it will be logged out and signed in again
if (WebUI.verifyElementPresent(GlobalVariable.main_trueprofit, 10, FailureHandling.OPTIONAL)) {
    WebUI.click(dropdown_store_selection)

    WebUI.delay(2)

    if (GlobalVariable.store_domain.toString().replaceAll('-', '_') != WebUI.getText(span_shop_name)) {
        println(GlobalVariable.store_domain.toString().replaceAll('-', '_'))

        println(WebUI.getText(span_shop_name))

        WebUI.click(gear_option)

        WebUI.waitForElementVisible(btn_logout_trueprofit, 3)

        WebUI.click(btn_logout_trueprofit)

        WebUI.waitForElementVisible(btn_ok_logout, 3)

        WebUI.click(btn_ok_logout)

        WebUI.delay(3)

        WebUI.waitForElementVisible(btn_signin, 5)

        WebUI.click(btn_sign_in_with_shopify)

        WebUI.waitForElementPresent(input_shopify_domain, 3)

        WebUI.sendKeys(input_shopify_domain, Keys.chord(Keys.CONTROL, 'a', Keys.BACK_SPACE) + GlobalVariable.store_domain.toString().replaceAll("_", "-"))

        WebUI.click(btn_signin)
    }
} else {
    WebUI.waitForElementVisible(btn_signin, 5)

    WebUI.click(btn_sign_in_with_shopify)

    WebUI.waitForElementPresent(input_shopify_domain, 3)

    WebUI.sendKeys(input_shopify_domain, Keys.chord(Keys.CONTROL, 'a', Keys.BACK_SPACE) + GlobalVariable.store_domain.toString().replaceAll("_", "-"))

    WebUI.click(btn_signin)
}

// Select the first account on Shopify if required
if (WebUI.verifyElementPresent(GlobalVariable.a_select_1st_account, 1, FailureHandling.OPTIONAL)) {
	WebUI.delay(1)
    WebUI.click(GlobalVariable.a_select_1st_account)
	// Skip two-factor authentication if reminded
	if (WebUI.verifyElementPresent(GlobalVariable.a_remind_later, 1, FailureHandling.OPTIONAL)) {
		WebUI.delay(1)
		WebUI.click(GlobalVariable.a_remind_later, FailureHandling.STOP_ON_FAILURE)
	}
}


// If the store has linked to an account, try to sign in
if (WebUI.verifyElementPresent(input_email, 1, FailureHandling.OPTIONAL)) {
	WebUI.delay(1)
    WebUI.sendKeys(input_email, Keys.chord(Keys.CONTROL, 'a', Keys.BACK_SPACE) + GlobalVariable.account.email_1)

    WebUI.setEncryptedText(input_password, GlobalVariable.default_password)

    WebUI.click(btn_signin)

    WebUI.waitForElementVisible(GlobalVariable.main_trueprofit, 15)

    WebUI.click(dropdown_store_selection)

    WebUI.delay(1)

    def driver = DriverFactory.getWebDriver()

    String storeNameToSwitch = ('//span[contains(text(),"' + GlobalVariable.store_domain.toString().replaceAll('-', '_')) + 
    '")]'

    List<WebElement> textfields = driver.findElements(By.xpath(storeNameToSwitch))

    // Make sure the list contains elements before trying to click
    if (!(textfields.isEmpty())) {
        // Click on the first element in the list
        (textfields[0]).click()
    }
}

WebUI.waitForElementVisible(GlobalVariable.main_trueprofit, 15)

// Go to the customized dashboard and re-select options
// If infinitive loading, refresh the webpage to try again


def dateOptions = [('today') : btn_today, ('yesterday') : btn_yesterday, ('last7days') : btn_last_7_days, ('last30days') : btn_last_30_days
    , ('thismonth') : btn_this_month, ('thisyear') : btn_this_year, ('lastmonth') : btn_last_month, ('lastyear') : btn_last_year]

def count = 0

while (count < 2) {
    if (WebUI.verifyElementPresent(overview_metrics, 10, FailureHandling.OPTIONAL)) {
        WebUI.click(div_date_range)

        WebUI.waitForElementVisible(div_calendar_picker, 5)

        def date_option = GlobalVariable.date_range_select
		
		def date_range = dateOptions.getOrDefault(date_option, btn_today)
		
		WebUI.click(date_range)

        WebUI.waitForElementVisible(overview_metrics, 3)

        WebUI.click(i_customized_dashboard)

        break
    } else {
        WebUI.navigateToUrl(urlTrueProfit)

        count++
    }
}

def sections = [['Overview', is_overview_all_checked, is_overview_mixed_checked], ['Cost', is_cost_all_checked, is_cost_mixed_checked]
    , ['Order Summary', is_order_summary_all_checked, is_order_summary_mixed_checked], ['Customer Summary', is_customer_summary_all_checked
        , is_customer_summary_mixed_checked], ['Others', is_others_all_checked, is_others_mixed_checked], ['Ads Spend', is_ads_spend_all_checked
        , is_ads_spend_mixed_checked]]

for (def section : sections) {
    def sectionName = section[0]

    def allChecked = section[1]

    def mixedChecked = section[2]

    //    println(sectionName)
    //
    //    println(allChecked)
    //
    //    println(mixedChecked)
    // Clear all options that are selected
    if (WebUI.verifyElementPresent(allChecked, 1, FailureHandling.OPTIONAL)) {
        WebUI.click(allChecked)
    } else if (WebUI.verifyElementPresent(mixedChecked, 1, FailureHandling.OPTIONAL)) {
        WebUI.click(mixedChecked)

        WebUI.click(allChecked)
    }
}

// List of labels to click for selecting customized options
def labelsToClick = [lablel_total_order, lablel_gross_sale, lablel_discount, lablel_refund, label_shipping_charged, label_taxes_collected
    , label_gift_card_sales, label_tips, label_cogs, label_taxes_paid, label_handling_fee, label_shipping_cost, label_ad_spend
    , label_custom_cost, label_transaction_fees, label_revenue, label_total_cost, label_gross_profit, label_gross_margin
    , label_net_profit, label_net_margin, label_avg_order_value, label_avg_order_cost, label_avg_order_profit, btn_done]

// Click the specified labels to select customized options 
for (def label : labelsToClick) {
    WebUI.click(label)
}

//WebUI.delay(5)

def doubleCheckRevenue, doubleCheckTotalCost, doubleCheckGrossProfit, doubleCheckGrossMargin, doubleCheckNetProfit = 0.0
def totalOrders, grossSale, discount, shopifyRefund, tip, giftCardSale, cogs, taxesCollected, shippingCharged, handlingFee, shippingCost, totalAdSpend, transactionFee, customCost, taxesPaid = 0.0
def roundedRevenue, roundedTotalCost, roundedGrossProfit, roundedNetProfit = 0.0
def formattedRevenue, formattedTotalCost, formattedGrossProfit, formattedNetProfit, formattedGrossMargin, formattedNetMargin, formattedAvgOrderValue, formattedAvgOrderCost, formattedAvgOrderProfit = 0.0

// Store the metrics name & numeric values of the Overview to excel file
Map dashboardValue = [('total_orders') : metric_total_orders, ('gross_sale') : metric_gross_sale, ('discount') : metric_discount
    , ('shopify_refund') : metric_shopify_refund, ('shipping_charged') : metric_shipping_charged, ('taxes_collected') : metric_taxes_collected
    , ('gift_card_sale') : metric_gift_card_sale, ('tip') : metric_tip, ('cogs') : metric_cogs, ('taxes_paid') : metric_taxes_paid
    , ('handling_fee') : metric_handling_fee, ('shipping_cost') : metric_shipping_cost, ('total_ad_spend') : metric_total_ad_spend
    , ('custom_cost') : metric_custom_cost, ('transaction_fee') : metric_transaction_fee, ('revenue') : metric_revenue, ('total_cost') : metric_total_cost
    , ('gross_profit') : metric_gross_profit, ('gross_margin') : metric_gross_margin, ('net_profit') : metric_net_profit
    , ('net_margin') : metric_net_margin, ('avg_order_value') : metric_avg_order_value, ('avg_order_cost') : metric_avg_order_cost, ('avg_order_profit') : metric_avg_order_profit]

// Extract and store numeric values
Map dashboardMap = [:]

dashboardValue.each({ def metricName, def testObject ->
		def getValue = ReplaceNumber.removeDollarSymbol(WebUI.getText(testObject))

		def numericValue = Double.parseDouble(getValue)

		if (metricName in ['gross_margin', 'net_margin']) {
			def formattedNumericValue = numericValue.toString().replaceAll('%', '')

			def convertedNumericValue = Double.parseDouble(formattedNumericValue)

			(dashboardMap[metricName]) = convertedNumericValue
		} else {
			(dashboardMap[metricName]) = numericValue
		}
	})


// Store the numeric values based on Shopify's summary and re-calculate them as double-checked
Map doubleCheckValues = [('total_orders') : metric_total_orders, ('gross_sale') : metric_gross_sale, ('discount') : metric_discount
    , ('shopify_refund') : metric_shopify_refund, ('shipping_charged') : metric_shipping_charged, ('taxes_collected') : metric_taxes_collected
    , ('gift_card_sale') : metric_gift_card_sale, ('tip') : metric_tip, ('cogs') : metric_cogs, ('taxes_paid') : metric_taxes_paid
    , ('handling_fee') : metric_handling_fee, ('shipping_cost') : metric_shipping_cost, ('total_ad_spend') : metric_total_ad_spend
    , ('custom_cost') : metric_custom_cost, ('transaction_fee') : metric_transaction_fee, ('revenue') : metric_revenue, ('total_cost') : metric_total_cost
    , ('gross_profit') : metric_gross_profit, ('gross_margin') : metric_gross_margin, ('net_profit') : metric_net_profit
    , ('net_margin') : metric_net_margin, ('avg_order_value') : metric_avg_order_value, ('avg_order_cost') : metric_avg_order_cost, ('avg_order_profit') : metric_avg_order_profit]

// Extract and store numeric values
Map doubleCheckMap = [:]

doubleCheckValues.each({ def variableName, def element ->
        def getValue = ReplaceNumber.removeDollarSymbol(WebUI.getText(element))

        cogs = (doubleCheckMap['cogs'])

        handlingFee = (doubleCheckMap['handling_fee'])

        shippingCost = (doubleCheckMap['shipping_cost'])

        totalAdSpend = (doubleCheckMap['total_ad_spend'])

        transactionFee = (doubleCheckMap['transaction_fee'])

        customCost = (doubleCheckMap['custom_cost'])

        taxesPaid = (doubleCheckMap['taxes_paid'])

		if (variableName == 'total_orders') {
			totalOrders = ValueCollections.map.get('summary_total_orders')

			println("New value for $variableName: $totalOrders")

			(doubleCheckMap[variableName]) = totalOrders
			
		}else if (variableName == 'gross_sale') {
            grossSale = ValueCollections.map.get('summary_gross_sales')

            println("New value for $variableName: $grossSale")

            (doubleCheckMap[variableName]) = grossSale 
			
        } else if (variableName == 'discount') {
            discount = ValueCollections.map.get('summary_discounts')

            println("New value for $variableName: $discount")

            (doubleCheckMap[variableName]) = discount
        } else if (variableName == 'shopify_refund') {
            shopifyRefund = ValueCollections.map.get('summary_refunds')

            println("New value for $variableName: $shopifyRefund")

            (doubleCheckMap[variableName]) = shopifyRefund
        } else if (variableName == 'taxes_collected') {
            taxesCollected = ValueCollections.map.get('summary_taxes')

            println("New value for $variableName: $taxesCollected")

            (doubleCheckMap[variableName]) = taxesCollected
        } else if (variableName == 'shipping_charged') {
            shippingCharged = ValueCollections.map.get('summary_shipping_charge')

            println("New value for $variableName: $shippingCharged")

            (doubleCheckMap[variableName]) = shippingCharged
        } else if (variableName == 'gift_card_sale') {
            giftCardSale = ValueCollections.map.get('summary_gift_card')

            println("New value for $variableName: $giftCardSale")

            (doubleCheckMap[variableName]) = giftCardSale
        } else if (variableName == 'tip') {
            tip = ValueCollections.map.get('summary_tips')

            println("New value for $variableName: $tip")

            (doubleCheckMap[variableName]) = tip
        } else if (variableName == 'revenue') {
			// Calculate the new revenue using this formula below to double check
			
            doubleCheckRevenue = (grossSale - Math.abs(discount) - Math.abs(shopifyRefund) + taxesCollected + shippingCharged)
			// Round the total cost to 2 decimal place
            roundedRevenue = (Math.round(doubleCheckRevenue * 100.0) / 100.0)
			// This avoids "E" notation for large values
            formattedRevenue = String.format('%.2f', roundedRevenue)

            (doubleCheckMap[variableName]) = formattedRevenue.toDouble()
        } else if (variableName == 'total_cost') {
			// Total Cost = Cost of Goods Sold + Handling Fees + Shipping Cost + Ad Spend + Transaction Fees + Custom Spend + Taxes Paid
            doubleCheckTotalCost = ((Math.abs(cogs) + Math.abs(handlingFee) + Math.abs(shippingCost) + Math.abs(totalAdSpend) + 
            Math.abs(transactionFee) + Math.abs(customCost) + Math.abs(taxesPaid)) * -1)

            roundedTotalCost = new BigDecimal(doubleCheckTotalCost).setScale(2, BigDecimal.ROUND_HALF_UP)

            formattedTotalCost = String.format('%.2f', roundedTotalCost)
			
			formattedTotalCost = formattedTotalCost.toDouble()
			
            (doubleCheckMap[variableName]) = formattedTotalCost
        } else if (variableName == 'gross_profit') {
			// Gross Profit = Revenue + Tips + Gift Card Sales - Total COGS - Shipping Cost - Transaction Fees - Handling Fees
            doubleCheckGrossProfit = ((((((doubleCheckRevenue + tip) + giftCardSale) - Math.abs(cogs)) - Math.abs(shippingCost)) - 
            Math.abs(transactionFee)) - Math.abs(handlingFee))

            roundedGrossProfit = new BigDecimal(doubleCheckGrossProfit).setScale(2, BigDecimal.ROUND_HALF_UP)

            formattedGrossProfit = String.format('%.2f', roundedGrossProfit)

            (doubleCheckMap[variableName]) = formattedGrossProfit.toDouble()
        } else if (variableName == 'gross_margin') {
			// Gross Margin = (Gross profit / Revenue) * 100
            if (doubleCheckRevenue != 0) {
                doubleCheckGrossMargin = ((doubleCheckGrossProfit / doubleCheckRevenue) * 100)

               BigDecimal roundedGrossMargin = new BigDecimal(doubleCheckGrossMargin).setScale(2, BigDecimal.ROUND_DOWN)

			   formattedGrossMargin = roundedGrossMargin
			   
			   (doubleCheckMap[variableName]) = roundedGrossMargin.doubleValue()
            } else {
                roundedGrossMargin = 0
            }
        } else if (variableName == 'net_profit') {
			// Net Profit = Revenue + Tips + Gift Card Sales - Total Cost - Taxes Collected
			// Net Profit (Leave taxes out) = Revenue + Tips + Gift Card Sales - Total Cost
            doubleCheckNetProfit = ((((doubleCheckRevenue + tip) + giftCardSale) - Math.abs(roundedTotalCost)) - taxesCollected)

            roundedNetProfit = new BigDecimal(doubleCheckNetProfit).setScale(2, BigDecimal.ROUND_HALF_UP)

            formattedNetProfit = String.format('%.2f', roundedNetProfit)
			
			formattedNetProfit = formattedNetProfit.toDouble()
			
            (doubleCheckMap[variableName]) = formattedNetProfit
        } else if (variableName == 'net_margin') {
			// Net Margin = (Net Profit / Revenue) * 100
            if (doubleCheckRevenue != 0) {
                doubleCheckNetMargin = ((doubleCheckNetProfit / doubleCheckRevenue) * 100)
				
				BigDecimal roundedNetMargin = new BigDecimal(doubleCheckNetMargin).setScale(2, BigDecimal.ROUND_DOWN)
				
				formattedNetMargin = roundedNetMargin
				
				(doubleCheckMap[variableName]) = roundedNetMargin.doubleValue()							 
            } else {
                roundedNetMargin = 0
            }
        }else if (variableName == 'avg_order_value') {
			// Average Order Value = Revenue / Total Orders
            doubleCheckAvgOrderValue = (doubleCheckRevenue / totalOrders)

            roundedAvgOrderValue = new BigDecimal(doubleCheckAvgOrderValue).setScale(2, BigDecimal.ROUND_HALF_UP)

            formattedAvgOrderValue = String.format('%.2f', roundedAvgOrderValue)

            (doubleCheckMap[variableName]) = formattedAvgOrderValue.toDouble()
		}
			else if (variableName == 'avg_order_cost') {
			// Average Order Cost = Total Cost / Total Orders
			doubleCheckAvgOrderCost = (Math.abs(formattedTotalCost) / totalOrders)
	
			roundedAvgOrderCost = new BigDecimal(doubleCheckAvgOrderCost).setScale(2, BigDecimal.ROUND_HALF_UP)
	
			formattedAvgOrderCost = String.format('%.2f', roundedAvgOrderCost)
	
			(doubleCheckMap[variableName]) = formattedAvgOrderCost.toDouble()
		}
		else if (variableName == 'avg_order_profit') {
			// Average Order Profit = Net Profit / Total Orders
			doubleCheckAvgOrderProfit = (formattedNetProfit / totalOrders)
		
			roundedAvgOrderProfit = new BigDecimal(doubleCheckAvgOrderProfit).setScale(2, BigDecimal.ROUND_HALF_UP)
		
			formattedAvgOrderProfit = String.format('%.2f', roundedAvgOrderProfit)
		
			(doubleCheckMap[variableName]) = formattedAvgOrderProfit.toDouble()
		}
		 else {
            (doubleCheckMap[variableName]) = getValue.toDouble()
        }
    })

// Call the keyword to write data to excel
CustomKeywords.'pkg.WriteExcel.writeDashboardMetricToExcel'(dashboardMap, doubleCheckMap)

//WebUI.comment("Gross sale: " + grossSale)
//WebUI.comment("Discount: " + discount)
//WebUI.comment("Shopify refund: " + shopifyRefund)
//WebUI.comment("Shipping Charged: " + shippingCharged)
//WebUI.comment("Taxes Collected: " + taxesCollected)
//WebUI.comment("Gift Card Sale: " + giftCardSale)
//WebUI.comment("Tip: " + tip)
//WebUI.comment("COGS: " + cogs)
//WebUI.comment("Handling Fee: " + handlingFee)
//WebUI.comment("Shipping Cost: " + shippingCost)
//WebUI.comment("Total Ad Spend: " + totalAdSpend)
//WebUI.comment("Transaction Fee: " + transactionFee)
//WebUI.comment("Custom Cost: " + customCost)
//WebUI.comment("Taxes Paid: " + taxesPaid)
//WebUI.comment("Revenue: " + formattedRevenue)
//WebUI.comment("Total Cost: " + formattedTotalCost)
//WebUI.comment("Gross Profit: " + formattedGrossProfit)
//WebUI.comment("Gross Marign: " + roundedGrossMargin)
//WebUI.comment("Net Profit: " + formattedNetProfit)
//WebUI.comment("Net Marign: " + roundedNetMargin)


// Verify raw metric values between Shopify summary and TrueProfit
WebUI.verifyEqual(ReplaceNumber.removeDollarSymbol(WebUI.getText(metric_gross_sale)), ValueCollections.map.get('summary_gross_sales').toString().replaceAll(
        ',', ''), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyEqual(ReplaceNumber.removeDollarSymbol(WebUI.getText(metric_discount)), ValueCollections.map.get('summary_discounts').toString().replaceAll(
        ',', ''), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyEqual(ReplaceNumber.removeDollarSymbol(WebUI.getText(metric_shopify_refund)), ValueCollections.map.get('summary_refunds').toString().replaceAll(
        ',', ''), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyEqual(ReplaceNumber.removeDollarSymbol(WebUI.getText(metric_shipping_charged)), ValueCollections.map.get('summary_shipping_charge').toString().replaceAll(
        ',', ''), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyEqual(ReplaceNumber.removeDollarSymbol(WebUI.getText(metric_taxes_collected)), ValueCollections.map.get('summary_taxes').toString().replaceAll(
        ',', ''), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyEqual(ReplaceNumber.removeDollarSymbol(WebUI.getText(metric_gift_card_sale)), ValueCollections.map.get('summary_gift_card').toString().replaceAll(
        ',', ''), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyEqual(ReplaceNumber.removeDollarSymbol(WebUI.getText(metric_tip)), ValueCollections.map.get('summary_tips').toString().replaceAll(
        ',', ''), FailureHandling.CONTINUE_ON_FAILURE)

// Double-check the calculated metric values on TrueProfit based on raw input values above
WebUI.verifyEqual(ReplaceNumber.removeDollarSymbol(WebUI.getText(metric_revenue)), formattedRevenue.toString().replaceAll(
        ',', ''), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyEqual(ReplaceNumber.removeDollarSymbol(WebUI.getText(metric_total_cost)), formattedTotalCost.toString().replaceAll(
        ',', ''), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyEqual(ReplaceNumber.removeDollarSymbol(WebUI.getText(metric_gross_profit)), formattedGrossProfit.toString().replaceAll(
        ',', ''), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyEqual(ReplaceNumber.removeDollarSymbol(WebUI.getText(metric_gross_margin)), formattedGrossMargin.toString().replaceAll(
        ',', ''), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyEqual(ReplaceNumber.removeDollarSymbol(WebUI.getText(metric_net_profit)), formattedNetProfit.toString().replaceAll(
        ',', ''), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyEqual(ReplaceNumber.removeDollarSymbol(WebUI.getText(metric_net_margin)), formattedNetMargin.toString().replaceAll(
        ',', ''), FailureHandling.STOP_ON_FAILURE)

WebUI.verifyEqual(ReplaceNumber.removeDollarSymbol(WebUI.getText(metric_avg_order_value)), formattedAvgOrderValue.toString().replaceAll(
	',', ''), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyEqual(ReplaceNumber.removeDollarSymbol(WebUI.getText(metric_avg_order_cost)), formattedAvgOrderCost.toString().replaceAll(
	',', ''), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyEqual(ReplaceNumber.removeDollarSymbol(WebUI.getText(metric_avg_order_profit)), formattedAvgOrderProfit.toString().replaceAll(
	',', ''), FailureHandling.CONTINUE_ON_FAILURE)

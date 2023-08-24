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
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook as XSSFWorkbook
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import pkg.ReplaceNumber as ReplaceNumber
import java.math.BigDecimal
import java.text.DecimalFormat

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


// Go to the customized dashboard and re-select options
WebUI.waitForElementClickable(i_customized_dashboard, 5)

WebUI.click(i_customized_dashboard)

def sections = [['Overview', is_overview_all_checked, is_overview_mixed_checked], ['Cost', is_cost_all_checked, is_cost_mixed_checked]
    , ['Order Summary', is_order_summary_all_checked, is_order_summary_mixed_checked]
	, ['Customer Summary', is_customer_summary_all_checked, is_customer_summary_mixed_checked]
	, ['Others', is_others_all_checked, is_others_mixed_checked],['Ads Spend', is_ads_spend_all_checked, is_ads_spend_mixed_checked]]

for (def section : sections) {
    def sectionName = section[0]

    def allChecked = section[1]

    def mixedChecked = section[2]

    println(sectionName)

    println(allChecked)

    println(mixedChecked)

    if (WebUI.verifyElementPresent(allChecked, 1, FailureHandling.OPTIONAL)) {
        WebUI.click(allChecked)
    } else if (WebUI.verifyElementPresent(mixedChecked, 1, FailureHandling.OPTIONAL)) {
        WebUI.click(mixedChecked)

        WebUI.click(allChecked)
    }
}

def labelsToClick = [lablel_total_order, lablel_gross_sale, lablel_discount, lablel_refund
	, label_shipping_charged, label_taxes_collected, label_gift_card_sales, label_tips
	, label_cogs, label_taxes_paid, label_handling_fee, label_shipping_cost, label_ad_spend
	, label_custom_cost, label_transaction_fees, label_revenue, label_total_cost
	, label_gross_profit, label_gross_margin, label_net_profit, label_net_margin, btn_done]

for (def label : labelsToClick) {
    WebUI.click(label)
	
}

WebUI.delay(5)

// After that, store the metrics name & numeric values to excel file
Map metricValue = [('total_order') : metric_total_orders, ('gross_sale') : metric_gross_sale, ('discount') : metric_discount
    , ('shopify_refund') : metric_shopify_refund, ('shipping_charged') : metric_shipping_charged, ('taxes_collected') : metric_taxes_collected,
    , ('gift_card_sale') : metric_gift_card_sale, ('tip') : metric_tip, ('cogs') : metric_cogs, ('taxes_paid') : metric_taxes_paid
	, ('handling_fee') : metric_handling_fee, ('shipping_cost') : metric_shipping_cost
    , ('total_ad_spend') : metric_total_ad_spend, ('custom_cost') : metric_custom_cost, ('transaction_fee') : metric_transaction_fee
    , ('revenue') : metric_revenue, ('total_cost') : metric_total_cost
    , ('gross_profit') : metric_gross_profit, ('gross_margin') : metric_gross_margin, ('net_profit') : metric_net_profit
    , ('net_margin') : metric_net_margin]


Map doubleCheckValue = [('total_order') : metric_total_orders, ('gross_sale') : metric_gross_sale, ('discount') : metric_discount
	, ('shopify_refund') : metric_shopify_refund, ('shipping_charged') : metric_shipping_charged, ('taxes_collected') : metric_taxes_collected,
	, ('gift_card_sale') : metric_gift_card_sale, ('tip') : metric_tip, ('cogs') : metric_cogs, ('taxes_paid') : metric_taxes_paid
	, ('handling_fee') : metric_handling_fee, ('shipping_cost') : metric_shipping_cost
	, ('total_ad_spend') : metric_total_ad_spend, ('custom_cost') : metric_custom_cost, ('transaction_fee') : metric_transaction_fee
	, ('revenue') : metric_revenue, ('total_cost') : metric_total_cost
	, ('gross_profit') : metric_gross_profit, ('gross_margin') : metric_gross_margin, ('net_profit') : metric_net_profit
	, ('net_margin') : metric_net_margin]

// Extract and store numeric values
Map variableMap = [:]

def doubleCheckRevenue, doubleCheckTotalCost, doubleCheckGrossProfit, doubleCheckGrossMargin,
	doubleCheckNetProfit,grossSale, discount, shopifyRefund, 
	tip, giftCardSale, cogs, taxesCollected, shippingCharged, handlingFee, shippingCost, totalAdSpend, transactionFee, 
	customCost, taxesPaid = 0
	
def roundedRevenue, roundedTotalCost, roundedGrossMargin, roundedNetMargin, roundedGrossProfit, roundedNetProfit = 0
def formattedRevenue, formattedTotalCost, formattedGrossProfit, formattedNetProfit = 0

metricValue.each({ def variableName, def element ->
        def getValue = ReplaceNumber.removeDollarSymbol(WebUI.getText(element))
		
		if (variableName == 'revenue') {
			// Calculate the new revenue using the formula
			grossSale = variableMap['gross_sale']
			discount = variableMap['discount']
			shopifyRefund = variableMap['shopify_refund']
			taxesCollected = variableMap['taxes_collected']
			shippingCharged = variableMap['shipping_charged']
			
			doubleCheckRevenue = grossSale - Math.abs(discount) - Math.abs(shopifyRefund) + taxesCollected + shippingCharged
			roundedRevenue = Math.round(doubleCheckRevenue * 100.0) / 100.0;
			formattedRevenue = String.format("%.2f", roundedRevenue)
			variableMap[variableName] =  formattedRevenue
//			println "gross_sale: " + grossSale
//			println "discount: " + discount
//			println "shopify_refund: " + shopifyRefund
//			println "shippingCharged: " + shippingCharged
//			println "taxes_collected: " + taxesCollected
			println "Revenue: " + roundedRevenue
		} 
		else if (variableName == 'total_cost') {
			// Calculate the new total_cost using the formula
			cogs = variableMap['cogs']
			handlingFee = variableMap['handling_fee']
			shippingCost = variableMap['shipping_cost']
			totalAdSpend = variableMap['total_ad_spend']
			transactionFee = variableMap['transaction_fee']
			customCost = variableMap['custom_cost']
			taxesPaid = variableMap['taxes_paid']
			doubleCheckTotalCost = (Math.abs(cogs) + Math.abs(handlingFee) + Math.abs(shippingCost) + Math.abs(totalAdSpend)
                     			 + Math.abs(transactionFee) + Math.abs(customCost) + Math.abs(taxesPaid)) * -1
			roundedTotalCost = new BigDecimal(doubleCheckTotalCost).setScale(2, BigDecimal.ROUND_HALF_UP)
			roundedTotalCost = roundedTotalCost.doubleValue()
			formattedTotalCost = String.format("%.2f", roundedTotalCost)
			variableMap[variableName] = formattedTotalCost
			println "cogs: " + cogs
			println "handlingFee: " + handlingFee
			println "shippingCost: " + shippingCost
			println "totalAdSpend: " + totalAdSpend
			println "transactionFee: " + transactionFee
			println "customCost: " + customCost
			println "taxesPaid: " + taxesPaid
			println "Total Cost: " + formattedTotalCost
		}
		else if (variableName == 'gross_profit') {
			tip = variableMap['tip']
			giftCardSale = variableMap['gift_card_sale']
			doubleCheckGrossProfit = doubleCheckRevenue + tip + giftCardSale - Math.abs(cogs) - 
			Math.abs(shippingCost) - Math.abs(transactionFee) - Math.abs(handlingFee)
			roundedGrossProfit = new BigDecimal(doubleCheckGrossProfit).setScale(2, BigDecimal.ROUND_HALF_UP)
			roundedGrossProfit = roundedGrossProfit.doubleValue()
			formattedGrossProfit = String.format("%.2f", roundedGrossProfit)
			variableMap[variableName] =  formattedGrossProfit
			println "Gross Profit: " + formattedGrossProfit
		}
		else if (variableName == 'gross_margin') {
			doubleCheckGrossMargin = (doubleCheckGrossProfit / doubleCheckRevenue) * 100
			roundedGrossMargin = new BigDecimal(doubleCheckGrossMargin).setScale(2, BigDecimal.ROUND_DOWN)
			DecimalFormat decimalFormat = new DecimalFormat("#0.00")
			roundedGrossMargin = decimalFormat.format(doubleCheckGrossMargin)
			variableMap[variableName] =  decimalFormat.format(getValue.toDouble()) + "%"
			println "Gross Marign: " + roundedGrossMargin
		} 
		else if(variableName ==  'net_profit') {
			taxesCollected = variableMap['taxes_collected']
			doubleCheckNetProfit = doubleCheckRevenue + tip + giftCardSale - Math.abs(roundedTotalCost) - taxesCollected
			roundedNetProfit = new BigDecimal(doubleCheckNetProfit).setScale(2, BigDecimal.ROUND_HALF_UP)
			roundedNetProfit = roundedNetProfit.doubleValue()
			formattedNetProfit = String.format("%.2f", roundedNetProfit)
			variableMap[variableName] = getValue.toDouble()
			println "Net Profit: " + formattedNetProfit
		}
		else if(variableName ==  'net_margin') {
			doubleCheckNetMargin = (doubleCheckNetProfit / doubleCheckRevenue) * 100
			roundedNetMargin = new BigDecimal(doubleCheckNetMargin).setScale(2, BigDecimal.ROUND_UP)
			DecimalFormat decimalFormat = new DecimalFormat("#0.00")
			roundedNetMargin = decimalFormat.format(doubleCheckNetMargin)
			variableMap[variableName] =  decimalFormat.format(getValue.toDouble()) + "%"
			println "Net Marign: " + roundedNetMargin
		}
	 else {
        variableMap[variableName] = getValue.toDouble()
    }
})

//doubleCheckValue.each({ def variableName, def element ->
//	def getValue = ReplaceNumber.removeDollarSymbol(WebUI.getText(element))
//	
//	if (variableName in ['gross_margin', 'net_margin']) {
//		variableMap[variableName] = getValue
//	}else{
//	variableMap[variableName] = getValue.toDouble()
//	}
//})

// Call the keyword to write data to excel
CustomKeywords.'pkg.WriteExcel.writeDashboardMetricToExcel'(variableMap)

WebUI.verifyEqual(ReplaceNumber.removeDollarSymbol(WebUI.getText(metric_revenue)), formattedRevenue.toString().replaceAll(",", ""), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyEqual(ReplaceNumber.removeDollarSymbol(WebUI.getText(metric_total_cost)), formattedTotalCost.toString().replaceAll(",", ""), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyEqual(ReplaceNumber.removeDollarSymbol(WebUI.getText(metric_gross_profit)), formattedGrossProfit.toString().replaceAll(",", ""), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyEqual(ReplaceNumber.removeDollarSymbol(WebUI.getText(metric_gross_margin)), roundedGrossMargin.toString().replaceAll(",", ""), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyEqual(ReplaceNumber.removeDollarSymbol(WebUI.getText(metric_net_profit)), formattedNetProfit.toString().replaceAll(",", ""), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyEqual(ReplaceNumber.removeDollarSymbol(WebUI.getText(metric_net_margin)), roundedNetMargin.toString().replaceAll(",", ""), FailureHandling.STOP_ON_FAILURE)

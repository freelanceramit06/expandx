package org.expandx.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class ProformaInvoicesPage extends BasePage{

    public ProformaInvoicesPage() {
        super();
    }

    @SuppressWarnings("unused")
    @FindBy(xpath = "//div[@id='createProformaInvoice']//button[normalize-space()='Create']")
    private WebElement createButton;

    // Use the container element rather than selecting a text() node (Selenium locators must return elements)
    @SuppressWarnings("unused")
    @FindBy(id = "swal2-html-container")
    private WebElement displayedMessage;

    public void clickCreateButton() {
        createButton.click();
    }

    public void enterValueInField(String value, String fieldName) {

        WebElement inputField = driver.findElement(
                By.xpath("(//tr[.//strong[normalize-space()='"+fieldName+"']])[2]//input[@type='text']")
        );
        inputField.clear();
        inputField.sendKeys(value + Keys.TAB);
    }

    public void verifyProformaInvoiceCreation(String arg0, String arg1, String arg2) {
        String customerValue = utility.returnColumnValueByHeader("Customer");
        System.out.println("customerValue: " + customerValue);

        Assert.assertEquals(
                customerValue.trim(),
                arg0.trim(),
                "Customer value does not match!"
        );

        String OANumberValue = utility.returnColumnValueByHeader("OA Number");
        System.out.println("OANumberValue: " + OANumberValue);

        Assert.assertEquals(
                OANumberValue.trim(),
                arg1.trim(),
                "OA Number value does not match!"
        );

        // Use arg2 as part of the assertion message to avoid unused-parameter warning
        String note = (arg2 == null || arg2.trim().isEmpty()) ? "" : " (" + arg2 + ")";

        // New: compare Proforma Amount with previously stored Total Proforma Amount
        try {
            String storedTotal = utility.getScenarioValue("Total Proforma Amount ($) [A%+B%+C]");
            if (storedTotal != null && !storedTotal.trim().isEmpty()) {
                String proformaAmountText = utility.returnColumnValueByHeader("Proforma Amount");
                System.out.println("Stored Total: " + storedTotal + "; Proforma Amount on UI: " + proformaAmountText);

                double storedVal = parseCurrencyToDouble(storedTotal);
                double uiVal = parseCurrencyToDouble(proformaAmountText);

                double tolerance = 0.01; // small tolerance for floating point
                Assert.assertTrue(Math.abs(storedVal - uiVal) <= tolerance,
                        String.format("Proforma amount mismatch: expected %s but found %s%s", storedTotal, proformaAmountText, note));
            } else {
                System.out.println("No stored Total Proforma Amount found in ScenarioContext; skipping amount comparison.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed while comparing Proforma Amount: " + e.getMessage(), e);
        }
    }

    private double parseCurrencyToDouble(String text) {
        if (text == null) return 0.0;
        // Remove currency symbols, brackets, percentage labels etc. Keep digits, dot and minus
        String cleaned = text.replaceAll("[^0-9.\\-]", "");
        if (cleaned.isEmpty()) return 0.0;
        return Double.parseDouble(cleaned);
    }

    public void verifyProformaInvoiceCancellation(String expectedMessage) {

        // Wait for the displayedMessage element to be visible and contain the expected text
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOf(displayedMessage));
        wait.until(ExpectedConditions.textToBePresentInElement(displayedMessage, expectedMessage));

        String actualMessage = displayedMessage.getText().trim();

        // Assert that actualMessage contains the expected message
        Assert.assertTrue(
                actualMessage.contains(expectedMessage.trim()),
                "Cancellation message does not match! Expected to contain: " + expectedMessage + " but was: " + actualMessage
        );
    }

    public String returnQuickFilterValue(String filterName) {
        // Locator for the span that contains the quick-filter value next to the provided label
        try {
            Thread.sleep(1000); // brief pause to allow UI update
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        By locator = By.xpath("//p[br and normalize-space(text()[last()])='" + filterName + "']//span");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            // Wait until the element is present and its text becomes a numeric value
            String text = wait.until(d -> {
                WebElement el = d.findElement(locator);
                String t = el.getText();
                // Treat blank/empty text as not-ready yet
                if (t.trim().isEmpty()) return null;
                // Clean the text: remove all characters except digits and minus sign
                String cleaned = t.trim().replaceAll("[^0-9\\-]", "");
                // Return original text when cleaned text contains digits
                return (!cleaned.isEmpty()) ? t.trim() : null;
            });

            if (text == null) {
                throw new RuntimeException("Quick filter value did not become numeric for filter: " + filterName);
            }

            return text;
        } catch (Exception e) {
            throw new RuntimeException("Timed out waiting for quick filter '" + filterName + "' to have a numeric value.", e);
        }
    }
}

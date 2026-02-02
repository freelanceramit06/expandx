package org.expandx.stepdefs;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.expandx.pages.ProformaInvoicesPage;
import org.testng.Assert;

public class ProformaInvoiceDefs {

    ProformaInvoicesPage proformaInvoicesPage = new ProformaInvoicesPage();

    static String storedCancelledQuickFilterValue, storedSubmitQuickFilterValue;
    static String submitQuickFilterValueAfterInvoiceCreated, cancelledQuickFilterValueAfterInvoiceCreated;

    @When("the user clicks on Create button")
    public void the_user_click_on_create_button() throws InterruptedException {
        Thread.sleep(5000);
        proformaInvoicesPage.clickCreateButton();
    }

    @When("the user enters {string} in the {string} field on the PROFORMA INVOICE page")
    public void the_user_enter_proforma_invoice_values(String invoiceValue, String fieldName) {
        proformaInvoicesPage.enterValueInField(invoiceValue, fieldName);
    }

    @Then("the user should verify that Proforma Invoice is created successfully with {string}, {string}, {string}")
    public void theUserShouldVerifyThatProformaInvoiceIsCreatedSuccessfullyWith(String arg0, String arg1, String arg2) {
        // Write code here that turns the phrase above into concrete actions
        proformaInvoicesPage.verifyProformaInvoiceCreation(arg0, arg1, arg2);
    }

    @Then("the user should verify that Proforma Invoice is cancelled successfully with {string} message")
    public void theUserShouldVerifyThatProformaInvoiceIsCreatedSuccessfullyWith(String expectedMessage) {
        proformaInvoicesPage.verifyProformaInvoiceCancellation(expectedMessage);
    }

    @When("the user reads the quick filter value for {string} and stores it for future use")
    public void i_read_value_in_textarea(String textLabel){
        if ("Submit".equals(textLabel)) {
            storedSubmitQuickFilterValue = proformaInvoicesPage.returnQuickFilterValue(textLabel);
            System.out.println("storedSubmitQuickFilterValue: " + storedSubmitQuickFilterValue);
        } else if ("Cancelled".equals(textLabel)) {
            storedCancelledQuickFilterValue = proformaInvoicesPage.returnQuickFilterValue(textLabel);
            System.out.println("storedCancelledQuickFilterValue: " + storedCancelledQuickFilterValue);
        } else {
            System.out.println("Unsupported quick filter label: " + textLabel);
        }
    }

    @When("verify the {string} quick filter value is incremented by 1")
    public void i_verify_value_in_textarea(String textLabel) {
        // Use braces to ensure assertions are executed only for intended branch.
        String actualValue;
        String storedValue;

        if ("Submit".equals(textLabel)) {
            submitQuickFilterValueAfterInvoiceCreated = proformaInvoicesPage.returnQuickFilterValue(textLabel);
            actualValue = submitQuickFilterValueAfterInvoiceCreated;
            storedValue = storedSubmitQuickFilterValue;
        } else if ("Cancelled".equals(textLabel)) {
            cancelledQuickFilterValueAfterInvoiceCreated = proformaInvoicesPage.returnQuickFilterValue(textLabel);
            actualValue = cancelledQuickFilterValueAfterInvoiceCreated;
            storedValue = storedCancelledQuickFilterValue;
        } else {
            Assert.fail("Unsupported quick filter label: " + textLabel);
            return;
        }

        System.out.println("actualValue: " + actualValue);
        System.out.println("storedValue: " + storedValue);

        // Validate values are present and numeric
        if (storedValue == null) {
            Assert.fail("Stored quick filter value for '" + textLabel + "' is null. Ensure it was read and stored before verification.");
        }

        try {
            int actualInt = Integer.parseInt(actualValue);
            int storedInt = Integer.parseInt(storedValue);
            Assert.assertEquals(
                    actualInt,
                    storedInt + 1,
                    textLabel + " quick filter value is not incremented by 1"
            );
        } catch (NumberFormatException e) {
            Assert.fail("Quick filter values are not valid integers. stored='" + storedValue + "', actual='" + actualValue + "'", e);
        }
    }
}

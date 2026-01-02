package org.expandx.stepdefs;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.expandx.pages.ProformaInvoicesPage;

public class ProformaInvoiceDefs {

    ProformaInvoicesPage proformaInvoicesPage = new ProformaInvoicesPage();

    @When("the user clicks on Create button")
    public void the_user_click_on_create_button() {
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
}

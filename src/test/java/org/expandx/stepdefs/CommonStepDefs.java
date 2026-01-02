package org.expandx.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import utils.Utility;

public class CommonStepDefs {

    Utility utility ;

    public CommonStepDefs() {
        utility = new Utility();
    }

    @And("the user clicks on the {string} button")
    public void the_user_clicks_on_the_element(String elementName) {
        utility.clickElementBasedOnName(elementName);
    }

    @When("the user enter {string} in the {string} text field")
    public void i_enter_value_in_text_field(String value, String fieldName) {
        // Reusable method to enter value based on header/field name
        utility.enterValueInColumnByHeader(fieldName, value);
    }


    @When("the user selects {string} value from drop down for {string} field")
    public void the_user_selects_dropdown_value(String dropdownValue, String fieldName) {
        utility.enterDropDownValue(fieldName, dropdownValue);
    }

    @When("the user switches to a new tab")
    public void the_user_switches_to_new_tab() {
        utility.switchToNewTab();
    }

    @When("the user select {string} check box with value as {string}")
    public void the_user_switches_to_new_tab(String checkBoxLabel, String checkBoxValue) {
        utility.selectCheckBoxByName(checkBoxLabel, checkBoxValue);
    }

    @When("the user reads the text value for {string} field and stores it for future use")
    public void the_user_switches_to_new_tab(String textFieldLabel) {
        utility.readValueFromTextField(textFieldLabel);
    }

    @When("the user enter {string} in the textarea based on label {string}")
    public void i_enter_value_in_textarea(String value, String textLabel) {
        utility.enterValueInTextAreaBasedOnLabel(value, textLabel);
    }
}

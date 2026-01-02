package org.expandx.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.expandx.pages.LoginPage;
import org.testng.Assert;

public class LoginPageStepDefs {

    private final LoginPage loginPage = new LoginPage();

    @Given("the user navigates to the login page URL")
    public void the_user_navigates_to_the_login_page_url() {
        loginPage.navigateTo();
    }

    @When("the user enters username {string}")
    public void the_user_enters_username(String username) {
        loginPage.enterUsername(username);
    }

    @And("the user enters password {string}")
    public void the_user_enters_password(String password) {
        loginPage.enterPassword(password);
    }

    @Then("the login result should be {string}")
    public void the_login_result_should_be(String expectedResult) {
        String actual = loginPage.getLoginResult();
        Assert.assertEquals(actual, expectedResult, "Login result mismatch");
    }
}

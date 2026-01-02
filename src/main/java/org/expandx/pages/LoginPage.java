package org.expandx.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Objects;

@SuppressWarnings("unused")
public class LoginPage extends BasePage {

    @FindBy(id = "loginform-username")
    private WebElement usernameInput;

    @FindBy(id = "loginform-password")
    private WebElement passwordInput;

    @FindBy(xpath = "//*[@id='login-form']/div[4]/button")
    private WebElement loginButton;

    @FindBy(xpath = "//*[@class='logo-mini']")
    private WebElement dashboardLogo;

    public LoginPage() {
        super();
    }

    public void navigateTo() {
        getDriver().get("https://prelive.expandx.in/site/login");
    }

    public void enterUsername(String username) {
        usernameInput.clear();
        usernameInput.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public String getLoginResult() {
        getWait().until(ExpectedConditions.visibilityOf(dashboardLogo));
        return Objects.requireNonNull(getDriver().getCurrentUrl()).contains("dashboard") ? "success" : "failure";
    }
}

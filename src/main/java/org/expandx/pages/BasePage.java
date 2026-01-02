package org.expandx.pages;

import com.epam.healenium.SelfHealingDriver;
import driver.DriverManager;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Utility;

import java.time.Duration;

public class BasePage {

    public SelfHealingDriver driver;
    public WebDriverWait wait;
    public Utility utility;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
        this.utility = new Utility();
    }

    protected SelfHealingDriver getDriver() {
        return driver;
    }

    protected WebDriverWait getWait() {
        return wait;
    }
}
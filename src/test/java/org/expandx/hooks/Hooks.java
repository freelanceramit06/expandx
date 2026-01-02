package org.expandx.hooks;

import driver.DriverManager;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

/**
 * TestNG suite-level hooks to manage WebDriver lifecycle.
 */
public class Hooks {

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("[Hooks] Initializing WebDriver before test suite");
        DriverManager.getDriver();
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("[Hooks] Quitting WebDriver after test suite");
        DriverManager.quitDriver();
    }
}

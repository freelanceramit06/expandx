package utils;

import context.ScenarioContext;
import driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Objects;

/**
 * Small collection of UI utility helpers.
 */
public final class Utility {

    private static WebDriver driver;

    private static final ScenarioContext scenarioContext = new ScenarioContext();


    public Utility() {
        // Prevent instantiation
    }

    private static WebDriver getDriverInstance() {
        if (driver == null) {
            driver = DriverManager.getDriver();
        }
        return driver;
    }

    /**
     * Click element by visible text using provided WebDriver instance.
     */
    public void clickElementBasedOnName(String name) {
        WebDriver drv = getDriverInstance();

        if (drv == null) {
            throw new IllegalStateException("Driver must not be null");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name must not be null or empty");
        }

        String trimmedName = name.trim();

        String[] xpaths = {
                String.format("//*[normalize-space(text())='%s']", trimmedName),
                String.format("(//*[@title='%s'])", trimmedName),
                String.format("(//a[@title='%s'])[1]", trimmedName),
                String.format("//li[normalize-space(text())='%s']", trimmedName)
        };

        for (String xpath : xpaths) {
            try {
                WebElement element = drv.findElement(By.xpath(xpath));
                element.click();
                return; // stop once clicked successfully
            } catch (Exception ignored) {
                // Try next XPath
            }
        }

        throw new RuntimeException("Element not found using any XPath for name: " + trimmedName);
    }

    public void enterValueInColumnByHeader(String headerName, String value) {
        WebDriver drv = getDriverInstance();
        // Step 1: Get the 'data-col-seq' for the given header
        String dataColSeq = getColumnIndexByHeader(headerName, drv);

        System.out.println(headerName + " Column Index: " + dataColSeq);

        // Step 2: Locate the input field in that column and enter the value
        WebElement inputElement = drv.findElement(By.xpath("//td[@data-col-seq='" + dataColSeq + "']//input"));
        inputElement.sendKeys(value);

        // Step 3: Press the Tab key to move to the next field
        inputElement.sendKeys(Keys.TAB);

    }

    public void enterValueInTextAreaBasedOnLabel(String value, String textLabel) {
        WebDriver drv = getDriverInstance();
        WebElement textAreaElement = drv.findElement(
                By.xpath("//label[normalize-space()='"+textLabel+"']/following-sibling::textarea")
        );
        textAreaElement.sendKeys(value);
        // Press Tab to move focus away
        textAreaElement.sendKeys(Keys.TAB);

    }

    // Helper method to get column index based on header name
    private String getColumnIndexByHeader(String headerName, WebDriver drv) {
        WebElement thElement = drv.findElement(By.xpath("//th[a[normalize-space()='" + headerName + "']]") );
        return thElement.getAttribute("data-col-seq");
    }

    public void enterDropDownValue(String fieldName, String dropdownValue)  {

        WebDriver drv = getDriverInstance();

        System.out.println("Selecting '" + dropdownValue + "' from dropdown for field '" + fieldName + "'");

        var elements = drv.findElements(
                By.xpath("//label[normalize-space(text())='" + fieldName + "']/following-sibling::span//span[@class='selection']")
        );

        if (elements.isEmpty()) {
            throw new RuntimeException("Dropdown element not found for field: " + fieldName);
        }

        WebElement element = elements.get(0);

        // Open Select2 dropdown
        element.click();

        // Select value (your existing utility)
        clickElementBasedOnName(dropdownValue);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Dropdown value '" + dropdownValue + "' selected.");

        // Send keyboard actions
      //  drv.switchTo().activeElement().sendKeys(Keys.ENTER);
        drv.switchTo().activeElement().sendKeys(Keys.TAB);

        System.out.println("Keyboard actions sent.");
    }

    public void switchToNewTab() {
        WebDriver drv = getDriverInstance();

        // Get all open window handles
        var windowHandles = drv.getWindowHandles();
        var windowsList = new java.util.ArrayList<>(windowHandles);

        // Assuming the new tab is the last opened one
        drv.switchTo().window(windowsList.get(windowsList.size() - 1));

        // Now driver is focused on the new tab
        System.out.println("Current tab title: " + drv.getTitle());
    }

    public void selectCheckBoxByName(String checkBoxLabel, String checkBoxValue) {

        if(Objects.equals(checkBoxValue, "Yes")){
            WebDriver drv = getDriverInstance();
            WebElement checkBox = drv.findElement(
                    By.xpath("//label[normalize-space(text())='" + checkBoxLabel + "']//preceding-sibling::input[@type='checkbox']")
            );
            if (!checkBox.isSelected()) {
                checkBox.click();
            }
        }
    }

    public void readValueFromTextField(String textFieldLabel) {
        WebDriver drv = getDriverInstance();

        WebElement textField = drv.findElement(
                By.xpath("//*[normalize-space()='"+textFieldLabel+"']/following-sibling::td[1]//input")
        );
        String fieldValue = textField.getAttribute("value");
        System.out.println("Value read from field '" + textFieldLabel + "': " + fieldValue);

        // Store the value for future use as needed
        // For example, you could store it in a static variable or a context object
        scenarioContext.set(textFieldLabel, fieldValue);
    }

    public String returnColumnValueByHeader(String headerName) {
        WebDriver drv = getDriverInstance();
        // Step 1: Get the 'data-col-seq' for the given header
        String dataColSeq = getColumnIndexByHeader(headerName, drv);

        System.out.println(headerName + " Column Index: " + dataColSeq);

        // Step 2: Locate the input field in that column and enter the value
        WebElement inputElement = drv.findElement(By.xpath("(//td[@data-col-seq='"+dataColSeq+"'])[2]"));
        return inputElement.getText().trim();
    }

    // New helper to allow other classes to read values stored in the shared ScenarioContext
    public String getScenarioValue(String key) {
        if (key == null) return null;
        return scenarioContext.get(key);
    }
}

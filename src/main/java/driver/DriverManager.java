package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class DriverManager {

    private static WebDriver driver;

    private DriverManager() {
        // Prevent instantiation
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            // 1. Setup ChromeDriver
            WebDriverManager.chromedriver().setup();

            // 2. Create ChromeOptions
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            options.addArguments("--start-maximized");
            // options.addArguments("--headless"); // optional

            // 3. Create ChromeDriver with options
            driver = new ChromeDriver(options);

            // Set implicit wait
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }

        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
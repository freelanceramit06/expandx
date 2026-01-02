package driver;

import com.epam.healenium.SelfHealingDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class DriverManager {

    private static SelfHealingDriver driver;

    private DriverManager() {
        // Prevent instantiation
    }

    public static SelfHealingDriver getDriver() {
        if (driver == null) {
            // 1. Setup ChromeDriver
            WebDriverManager.chromedriver().setup();

            // 2. Create ChromeOptions
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            options.addArguments("--start-maximized");
            // options.addArguments("--headless"); // optional

            // 3. Create original ChromeDriver with options
            WebDriver originalDriver = new ChromeDriver(options);

            // 4. Wrap original driver with Healenium
            driver = SelfHealingDriver.create(originalDriver);

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
package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.BaseClass;

public class Hooks {
    public static WebDriver driver;
    private static final Logger logger = LogManager.getLogger(Hooks.class);

    @Before
    public void setUp() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            BaseClass.driver = driver;  // Sync to utility class
            logger.info("WebDriver initialized successfully.");
        }
    }

    @AfterStep
    public void takeScreenshotOnFailure(Scenario scenario) {
        if (scenario.isFailed() && driver instanceof TakesScreenshot) {
            try {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Failure Screenshot");
                logger.warn("Scenario failed. Screenshot captured and attached to report.");
            } catch (Exception e) {
                logger.error("Could not take screenshot: " + e.getMessage());
            }
        }
    }

    @After
    public void tearDown() {
        // Leave this empty if you want to reuse the session across scenarios
        logger.info("After hook completed. WebDriver still running.");
        // driver.quit(); // Uncomment if you want to quit after every scenario
    }
}

package concepts.browsers.edge.logs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.chromium.ChromiumDriverLogLevel;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Pattern;

public class LogFileFeaturesTest {

    // Declare a WebDriver instance to interact with the web browser
    private WebDriver driver;

    // Declare an EdgeDriverService object to manage the EdgeDriver process
    EdgeDriverService edgeDriverService;

    File logLocation;

    @BeforeMethod
    public void setUp() {
        // Set the system property for the WebDriver to use the JDK HTTP client
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        logLocation = FileUtil.getTempFile("logFileFeatures", ".log");

        System.setProperty(EdgeDriverService.EDGE_DRIVER_LOG_PROPERTY, logLocation.getAbsolutePath());

        System.setProperty(EdgeDriverService.EDGE_DRIVER_LOG_LEVEL_PROPERTY, ChromiumDriverLogLevel.DEBUG.toString());

        edgeDriverService = new EdgeDriverService.Builder().withAppendLog(true).withReadableTimestamp(true).build();

        // Initialize the EdgeDriver with the configured options
        driver = new EdgeDriver(edgeDriverService);

        // Maximize the browser window using WebDriver's manage() method
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void testLogFileFeatures() {
        // Navigate to the Google Home page.
        driver.get("https://www.google.com/");

        // Assert that the page title is "Google".
        Assert.assertEquals(driver.getTitle(), "Google");

        String fileContent;

        try {
            fileContent = new String(Files.readAllBytes(logLocation.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Pattern pattern = Pattern.compile("\\[\\d\\d-\\d\\d-\\d\\d\\d\\d", Pattern.CASE_INSENSITIVE);
        Assert.assertTrue(pattern.matcher(fileContent).find());
    }

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.quit();

            // Close the service after WebDriver usage
            edgeDriverService.stop();
        }
    }

}

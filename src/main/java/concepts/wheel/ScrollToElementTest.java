package concepts.wheel;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import scenarios.DriverConfiguration;

public class ScrollToElementTest {

    // Declare a WebDriver instance to interact with the web browser.
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up the WebDriver instance by calling a method named 'browserSetup' from the 'DriverConfiguration' class
        driver = DriverConfiguration.browserSetup();
    }

    @AfterMethod
    public void tearDown() {
        // Check if the 'driver' variable is not null, indicating that a WebDriver instance exists.
        if (driver != null) {
            // If a WebDriver instance exists, quit/close the browser session.
            driver.quit();
        }
    }

    @Test(priority = 1)
    public void testScrollToElement() {
        // Navigate to the Selenium website
        driver.get("https://www.selenium.dev/");

        // Find the WebElement representing the sponsors section using a CSS selector
        WebElement seleniumSponsors = driver.findElement(By.cssSelector(".selenium.text-center"));

        // Using the Actions class to perform a scroll action to bring the sponsors
        // section into view
        new Actions(driver).scrollToElement(seleniumSponsors).perform();

        // Assert whether the sponsors section is in the viewport after scrolling
        Assert.assertTrue(inViewport(seleniumSponsors));
    }

    public boolean inViewport(WebElement element) {
        String script = """
				for(var e=arguments[0],f=e.offsetTop,t=e.offsetLeft,o=e.offsetWidth,n=e.offsetHeight;\
				 e.offsetParent;)f+=(e=e.offsetParent).offsetTop,t+=e.offsetLeft;\
				return f<window.pageYOffset+window.innerHeight&&t<window.pageXOffset+window.innerWidth&&f+n>\
				window.pageYOffset&&t+o>window.pageXOffset
				""";
        return (boolean) ((JavascriptExecutor) driver).executeScript(script, element);
    }

}

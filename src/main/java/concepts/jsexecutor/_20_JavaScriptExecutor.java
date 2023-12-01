package concepts.jsexecutor;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import junit.framework.Assert;
import scenarios.DriverConfiguration;

public class _20_JavaScriptExecutor {

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

	private JavascriptExecutor jsExecutor;

	@Test(priority = 13, enabled = true)
	private void pageZoomByPercent() {
		String percent = "50%";
		browserSetup();
		driver.get("https://www.selenium.dev/");
		waitForSomeTime();
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("document.body.style.zoom='" + percent + "'");
		WebElement result = driver.findElement(By.xpath("//h2[text()='Getting Started']"));
		Assert.assertTrue(inViewport(result));
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 14, enabled = true)
	private void pageZoomOnFirefox() {
		String percent = "0.75";
		firefoxSetup();
		driver.get("https://www.selenium.dev/");
		waitForSomeTime();
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("document.body.style.MozTransform='scale(" + percent + ")'");
		WebElement result = driver.findElement(By.xpath("//h2[text()='Getting Started']"));
		Assert.assertTrue(inViewport(result));
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 24, enabled = true)
	private void scrollIntoElement() {
		browserSetup();
		driver.get("https://admin-demo.nopcommerce.com/login");
		waitForSomeTime();
		jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		waitForSomeTime();
		jsExecutor.executeScript("document.getElementById('Email').scrollIntoView(true);");
		WebElement email = driver.findElement(By.id("Email"));
		Assert.assertEquals(email.isDisplayed(), true);
		waitForSomeTime();
		driver.close();
	}
		
	private WebDriver browserSetup() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}
	
	private WebDriver firefoxSetup() {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		return driver;
	}

	private void waitForSomeTime() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	private boolean inViewport(WebElement element) {
		String script = """
				for(var e=arguments[0],f=e.offsetTop,t=e.offsetLeft,o=e.offsetWidth,n=e.offsetHeight;\
				 e.offsetParent;)f+=(e=e.offsetParent).offsetTop,t+=e.offsetLeft;\
				return f<window.pageYOffset+window.innerHeight&&t<window.pageXOffset+window.innerWidth&&f+n>\
				window.pageYOffset&&t+o>window.pageXOffset
				""";
		return (boolean) ((JavascriptExecutor) driver).executeScript(script, element);
	}
}

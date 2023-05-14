package com.qa.selenium.concepts;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class _AlertHandle {

	private static WebDriver driver;
	private static ChromeOptions chromeOptions;
	private static WebDriverWait wait;

	@Test(priority = 1, enabled = false)
	private static void verifyAlert() {
		browserSetup();
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		driver.get("https://the-internet.herokuapp.com/javascript_alerts");
		driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		String alertContent = alert.getText();
		System.out.println(alertContent);
		alert.accept();
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 2, enabled = false)
	private static void verifyConfirmAlert() {
		browserSetup();
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		driver.get("https://the-internet.herokuapp.com/javascript_alerts");
		driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		String alertContent = alert.getText();
		System.out.println(alertContent);
		alert.dismiss();
		waitForSomeTime();
		driver.close();
	}

	@Test(priority = 3, enabled = true)
	private static void verifyPromptAlert() {
		browserSetup();
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		driver.get("https://the-internet.herokuapp.com/javascript_alerts");
		driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		String alertContent = alert.getText();
		System.out.println(alertContent);
		alert.sendKeys("Selenium");
		alert.accept();
		waitForSomeTime();
		driver.close();
	}

	private static WebDriver browserSetup() {
		chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();
		return driver;
	}

	private static void waitForSomeTime() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
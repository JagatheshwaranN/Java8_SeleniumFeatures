package selenium4Features;

import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;



/**
 * 
 * @author Jaga
 *
 */
public class DimensonOfElement {

	private static WebDriver driver;
	private static WebElement login;
	private static Rectangle loginButton;

	public static void main(String ar[]) {

		
		driver = new ChromeDriver();
		driver.get("https://opensource-demo.orangehrmlive.com/index.php");
		login = driver.findElement(By.xpath("//input[@id='btnLogin']"));
		loginButton = login.getRect();
		System.out.println(loginButton.getHeight());
		System.out.println(loginButton.getWidth());
		System.out.println(loginButton.getX());
		System.out.println(loginButton.getY());
		driver.close();

	}
}

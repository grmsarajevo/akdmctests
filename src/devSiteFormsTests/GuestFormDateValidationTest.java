package devSiteFormsTests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import shared.MyChromeDriver;
import shared.SharedData;
import shared.TestListener;

@Listeners({TestListener.class})
public class GuestFormDateValidationTest {
WebDriver driver;
private SharedData s;
private String site;
	
	@Parameters("browser")
	
	@BeforeClass(alwaysRun = true)
	public void setUp(String browser) {
		
		String userDir = System.getProperty("user.dir");
		 // If the browser is Firefox, then do this
		 if (browser.equalsIgnoreCase("firefox")) {
		  System.setProperty("webdriver.gecko.driver", userDir + "\\drivers\\geckodriver.exe");
		  driver = new FirefoxDriver();
		  // If browser is IE, then do this
		 } else if (browser.equalsIgnoreCase("ie")) {
		  System.setProperty("webdriver.ie.driver", userDir + "\\drivers\\MicrosoftWebDriver.exe");
		  driver = new InternetExplorerDriver();
		  // If browser is Chrome, then do this
		 } else if (browser.equalsIgnoreCase("chrome")) {
		  System.setProperty("webdriver.chrome.driver", userDir + "\\drivers\\chromedriver.exe");
		  driver = new MyChromeDriver();
		 }		 
		 
		 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	      driver.manage().window().maximize();
	     
		 s = new SharedData();
		site = s.getDevSite();
	
	}
	
	@BeforeMethod
	 
	  public void beforeMethod() {

	      driver.get(site + "booking/");
	 
	  }
	
	@Test (enabled = true)
	public void test1() throws InterruptedException {
		
		driver.findElement(By.id("f2f100ae-1142-432e-8d1f-2790f3798868")).sendKeys("abc");
		driver.findElement(By.id("cd8e4497-dc90-4397-9813-d50e0428ccc4")).sendKeys("abc");
		driver.findElement(By.id("74febdd8-3315-43af-ffb9-afe679927fae")).sendKeys("abc");
		
		driver.findElement(By.name("__next")).click();
		
		try {
			driver.findElement(By.id("f2f100ae-1142-432e-8d1f-2790f3798868-error"));
			driver.findElement(By.id("cd8e4497-dc90-4397-9813-d50e0428ccc4-error"));
			driver.findElement(By.id("74febdd8-3315-43af-ffb9-afe679927fae-error"));
		} catch (Exception e) {
			System.out.println("Guest form date validation fail");
			Reporter.log("Guest form date validation fail");
			Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
			e.printStackTrace();
		}
	}
	
	@Test (enabled = true)
	public void test2() throws InterruptedException {
		driver.findElement(By.id("f2f100ae-1142-432e-8d1f-2790f3798868")).sendKeys("32/1/2017");
		driver.findElement(By.id("cd8e4497-dc90-4397-9813-d50e0428ccc4")).sendKeys("29/2/2017");
		driver.findElement(By.id("74febdd8-3315-43af-ffb9-afe679927fae")).sendKeys("31/4/2017");
		
		driver.findElement(By.name("__next")).click();
		
		try {
			driver.findElement(By.id("f2f100ae-1142-432e-8d1f-2790f3798868-error"));
			driver.findElement(By.id("cd8e4497-dc90-4397-9813-d50e0428ccc4-error"));
			driver.findElement(By.id("74febdd8-3315-43af-ffb9-afe679927fae-error"));
		} catch (Exception e) {
			System.out.println("Guest form date validation fail");
			Reporter.log("Guest form date validation fail");
			Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
			e.printStackTrace();
		}
	}
	
	@Test (enabled = true)
	public void test3() throws InterruptedException {
		driver.findElement(By.id("f2f100ae-1142-432e-8d1f-2790f3798868")).sendKeys("32/1/201");
		driver.findElement(By.id("cd8e4497-dc90-4397-9813-d50e0428ccc4")).sendKeys("29/13/2017");
		driver.findElement(By.id("74febdd8-3315-43af-ffb9-afe679927fae")).sendKeys("31/4/2017");
		
		driver.findElement(By.name("__next")).click();
		
		try {
			driver.findElement(By.id("f2f100ae-1142-432e-8d1f-2790f3798868-error"));
			driver.findElement(By.id("cd8e4497-dc90-4397-9813-d50e0428ccc4-error"));
			driver.findElement(By.id("74febdd8-3315-43af-ffb9-afe679927fae-error"));
		} catch (Exception e) {
			System.out.println("Guest form date validation fail");
			Reporter.log("Guest form date validation fail");
			Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
			e.printStackTrace();
		}
	}
	
	@AfterClass
	  public void afterClass() {
		  driver.quit();
	  }
}

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
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.uncommons.reportng.HTMLReporter;

import shared.MyChromeDriver;
import shared.SharedData;
import shared.TestListener;

@Listeners({TestListener.class , HTMLReporter.class})
public class EmailValidationTests {
private WebDriver driver;
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
  
  @Test (enabled = true)
  public void test1() throws InterruptedException {
	  
	  driver.get(site + "booking/");
	  
	  driver.findElement(By.id("78d313f9-6a46-46bf-db48-69baa21fe2d9")).sendKeys("abc");
	  driver.findElement(By.id("c9ba21b4-eb30-4997-ad0b-10b377be51f0")).sendKeys("abc");
	  driver.findElement(By.id("af240aa3-9de1-495d-ee13-f751d2fe7d14")).sendKeys("abc");
	  
	  Thread.sleep(1000);
	  driver.findElement(By.name("__next")).click();
	  
	  try {
		  driver.findElement(By.id("78d313f9-6a46-46bf-db48-69baa21fe2d9-error"));
		  driver.findElement(By.id("c9ba21b4-eb30-4997-ad0b-10b377be51f0-error"));
		  driver.findElement(By.id("af240aa3-9de1-495d-ee13-f751d2fe7d14-error"));
	  }catch(Exception e) {
		  System.out.println("Guest form email validation fail!");
		  Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
	  }
  }
  
  @Test (enabled = true)
  public void test2() throws InterruptedException {
	  
	  driver.get(site + "booking/");
	  
	  driver.findElement(By.id("78d313f9-6a46-46bf-db48-69baa21fe2d9")).sendKeys("abc@abc");
	  driver.findElement(By.id("c9ba21b4-eb30-4997-ad0b-10b377be51f0")).sendKeys("abc.abc");
	  driver.findElement(By.id("af240aa3-9de1-495d-ee13-f751d2fe7d14")).sendKeys("abc.abc@abc");
	  
	  Thread.sleep(5000);
	  driver.findElement(By.name("__next")).click();
	  Thread.sleep(5000);
	  
	  try {
		  driver.findElement(By.id("78d313f9-6a46-46bf-db48-69baa21fe2d9-error"));
		  driver.findElement(By.id("c9ba21b4-eb30-4997-ad0b-10b377be51f0-error"));
		  driver.findElement(By.id("af240aa3-9de1-495d-ee13-f751d2fe7d14-error"));
	  }catch(Exception e) {
		  System.out.println("Guest form email validation fail!");
		  Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
	  }
  }
  
  @Test (enabled = true)
  public void test3() throws InterruptedException {
	  
	  driver.get(site + "newsletter/");
	  
	  driver.findElement(By.id("23667f54-8254-4f5d-fc25-b83f6a12b57c")).sendKeys("abc");
	  Thread.sleep(5000);
	  driver.findElement(By.name("__next")).click();
	  Thread.sleep(5000);
	  
	  try {
		  driver.findElement(By.id("23667f54-8254-4f5d-fc25-b83f6a12b57c-error"));
	  } catch(Exception e) {
		  System.out.println("Newsletter form email validation fail");
		  Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
	  }
  }
  
  @Test (enabled = true)
  public void test4() throws InterruptedException {
	  driver.get(site + "newsletter/");
	  
	  driver.findElement(By.id("23667f54-8254-4f5d-fc25-b83f6a12b57c")).sendKeys("abc.abc@abc");
	  driver.findElement(By.name("__next")).click();
	  
	  try {
		  driver.findElement(By.id("23667f54-8254-4f5d-fc25-b83f6a12b57c-error"));
	  } catch(Exception e) {
		  System.out.println("Newsletter form email validation fail");
		  Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
	  }
  }
  
  @Test (enabled = true)
  public void test5() throws InterruptedException {
	  driver.get(site + "destinations/south-america/argentina/recommendeditineraries/best-of-buenos-aires/");
	  
	  driver.findElement(By.id("quote_link")).click();
	  
	  driver.findElement(By.id("bf6659ce-35b6-440b-bdc7-6dbeefc7ae19")).sendKeys("abc.abc@abc");
	  driver.findElement(By.name("__next")).click();
	  
	  try {
		  driver.findElement(By.id("bf6659ce-35b6-440b-bdc7-6dbeefc7ae19-error"));
	  } catch(Exception e) {
		  System.out.println("Request a quote form email validation fail");
		  Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
	  }
  }
  
  @Test (enabled = true)
  public void test6() throws InterruptedException {
	  driver.get(site + "destinations/south-america/argentina/recommendeditineraries/best-of-buenos-aires/");
	  
	  driver.findElement(By.id("client_link")).click();
	  
	  driver.findElement(By.id("3efdac17-d555-4a88-c993-ce3b6ac053df")).sendKeys("abc.abc@abc");
	  driver.findElement(By.id("995718a7-e36c-48f6-9a2c-488273b0f4b0")).sendKeys("abc");
	  
	  driver.findElement(By.name("__next")).click();
	  
	  try {
		  driver.findElement(By.id("3efdac17-d555-4a88-c993-ce3b6ac053df-error"));
		  driver.findElement(By.id("995718a7-e36c-48f6-9a2c-488273b0f4b0-error"));
	  } catch(Exception e) {
		  System.out.println("Request a quote form email validation fail");
		  Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
	  }
  }
  
  @AfterClass
  public void afterClass() {
	  driver.quit();
  }
}

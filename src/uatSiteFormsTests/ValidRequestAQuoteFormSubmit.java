package uatSiteFormsTests;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.uncommons.reportng.HTMLReporter;

import shared.RetryAnalyzer;
import shared.RetryCountIfFailed;
import shared.SharedData;
import shared.TestListener;


@Listeners({TestListener.class , HTMLReporter.class})
public class ValidRequestAQuoteFormSubmit {
private WebDriver driver;
private String time;
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
		  driver = new ChromeDriver();
		 }		 
		 
		 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	      driver.manage().window().maximize();
	     
		 s = new SharedData();
		site = s.getSite();
		
		time = String.valueOf(System.currentTimeMillis());
	
	}


  @BeforeMethod
  public void beforeMethod() {
 
      driver.get(site);
 
  }
  
  @RetryCountIfFailed(3)
  @Test (enabled = true , retryAnalyzer = RetryAnalyzer.class)
  public void test1() throws InterruptedException {
	  
	  driver.findElement(By.xpath("//*[@id=\"nav\"]/ul/li[6]/a")).click();
	  driver.findElement(By.xpath("/html/body/div[1]/div[2]/section/div[3]/div/div/div/div/div[1]/div/h3/a")).click();
	  driver.findElement(By.xpath("//*[@id=\"listingGrid\"]/div[1]/a")).click();
	  driver.findElement(By.id("quote_link")).click();
	  
	  driver.findElement(By.id("4091fdde-5532-4911-925a-fce9defbd83e")).sendKeys("test" + time);		// Name
	  driver.findElement(By.id("db986c34-7892-423e-accf-8cf7bba8b737")).sendKeys("test");				// Agency/Company
	  Select country = new Select(driver.findElement(By.id("25975c5c-8195-43b0-c1dc-1134e9390c85")));
	  country.selectByValue("Algeria");																	// Country of Residence
	  driver.findElement(By.id("4535a0c1-a4e1-4239-d7e8-41fe3eec50c3")).sendKeys("test");				// Telephone
	  driver.findElement(By.id("bf6659ce-35b6-440b-bdc7-6dbeefc7ae19")).sendKeys("test@test.test"); 	// Email	
	  
	  driver.findElement(By.id("37de4a01-a426-4e14-f8f4-46ee22a348a9")).sendKeys("test"); 				// Number of adults	
	  driver.findElement(By.id("f01b283d-80de-402d-cda9-e3faf97c898d")).sendKeys("test"); 				// Number of children
	  driver.findElement(By.id("cc6b8904-eca2-40db-a396-14aa590ba290")).sendKeys("test"); 				// Ages of children
	  driver.findElement(By.id("e9e12b84-16aa-478b-a69b-772eb476f13c")).sendKeys("test"); 				// Date of travel
	  driver.findElement(By.id("92d4e056-f65b-44e3-d4fd-5e5d3b9adc57")).sendKeys("test"); 				// Other requirements	
	  
	  driver.findElement(By.name("__next")).click();													// Send
	  
	  Thread.sleep(000);
	  
	  try {
		  assertEquals(driver.findElement(By.xpath("//*[@id=\"request_quote\"]/div/div/div")).getText(), "Thank you. Your request has been sent and you will be contacted by one of our specialists soon.");
		  
	  }catch(Exception e) {
		  Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
		  System.out.println("Request a quote form can't be submitted!");
		  Reporter.log("Request a quote form can't be submitted!");
	  }
	  
	  Thread.sleep(3000);
	  
  }
  
  @Parameters({"username", "password"})
  @RetryCountIfFailed(3)
  @Test (enabled = true , retryAnalyzer = RetryAnalyzer.class)
  public void test2(String username, String password) throws InterruptedException {
	 
	   driver.get(site + "umbraco");
	   
	   try {
		   driver.findElement(By.name("username")).sendKeys(username);
		   driver.findElement(By.name("password")).sendKeys(password);
		   driver.findElement(By.className("btn-success")).click();
	   } catch(Exception e) {
		   System.out.println("Already logged in");
	   }
	   Thread.sleep(2000);
	   driver.findElement(By.className("icon-umb-contour")).click();					// Forms
	   Thread.sleep(1000);
	   driver.findElement(By.xpath("//*[@id=\"tree\"]/ul/li/ul/li[1]/div/ins")).click();				// Forms hierarchy
	   Thread.sleep(1000);
	   driver.findElement(By.xpath("//*[@id=\"tree\"]/ul/li/ul/li[1]/ul/li[7]/div/ins")).click();		// Request a quote hierarchy
	   Thread.sleep(1000);
	   driver.findElement(By.linkText("Entries")).click();
	   Thread.sleep(1000);
	   String name = driver.findElement(By.xpath("//*[@id=\"contentcolumn\"]/div/div/form/div/div[2]/div[2]/div/div[2]/div[2]/div[1]/div[2]/div/div/a")).getText();
	   
	   if(name.contains("test" + time)) {
		   driver.findElement(By.xpath("//*[@id=\"contentcolumn\"]/div/div/form/div/div[2]/div[2]/div/div[2]/div[2]/div[1]/div[2]/div/div/a")).click();
		   Thread.sleep(3000);
	   } else {
		   System.out.println("Request a quote form was not submitted");
		   Reporter.log("Request a quote form was not submitted");
		   System.out.println(name);
		   Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
	   }
	   
	   assertEquals(driver.findElement(By.xpath("//*[@id=\"contentcolumn\"]/div/div/form/div/div[2]/div[2]/div/div[2]/div[1]/div[1]/div/span")).getText(), "test" + time);
  }
  
  @AfterClass
  public void afterClass() {
	  driver.quit();
  }

}

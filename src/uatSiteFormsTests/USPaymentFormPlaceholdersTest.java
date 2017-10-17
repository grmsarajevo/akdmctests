package uatSiteFormsTests;

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
public class USPaymentFormPlaceholdersTest {
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
		  driver = new ChromeDriver();
		 }		 
		 
		 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	      driver.manage().window().maximize();
	     
		 s = new SharedData();
		site = s.getSite();

	}

	
	@BeforeMethod
	  public void beforeMethod() {

	      driver.get(site + "payment/us/");
	 
	  }
	
	@RetryCountIfFailed(3)
	@Test (enabled = true , retryAnalyzer = RetryAnalyzer.class)
	  public void testRequiredFields() throws InterruptedException {
		  Select dmc = new Select(driver.findElement(By.id("dmc")));
		  dmc.selectByValue("Argentina");												// DMC
		  	
		  driver.findElement(By.id("bookingRef")).click();								// Booking reference
		  driver.findElement(By.id("lastname")).click();	 							// Guest last name
		  driver.findElement(By.id("agency")).click();									// Agency name
		  driver.findElement(By.id("iata")).click(); 									// Agency IATA/CLIA
		  driver.findElement(By.id("agent")).click(); 									// Agent Name
		  driver.findElement(By.id("email")).sendKeys("test@test.test") ;				// Agent e-mail
		  driver.findElement(By.id("paymentAmount")).sendKeys("1"); 					// Payment amount
		  Select currency = new Select(driver.findElement(By.id("currency")));
		  currency.selectByValue("USD");												// Currency
		  
		  driver.findElement(By.id("client")).click();
		  driver.findElement(By.id("permission")).click();
		  driver.findElement(By.id("provided")).click();
		  driver.findElement(By.id("TCs")).click();
		  
		  driver.findElement(By.id("next")).click();
		  
		  try{
			  driver.findElement(By.id("next")).click();
			  Reporter.getCurrentTestResult().setStatus(ITestResult.SUCCESS);
		  } catch(Exception e) {
			  Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
			  System.out.println("USPaymentFormPlaceholdersTest - placeholders are sent as values!");
			  Reporter.log("USPaymentFormPlaceholdersTest - placeholders are sent as values!");
		  }
	  }
	  
	  @AfterClass
	  public void afterClass() {
		  driver.quit();
	  }
}

package uatSiteFormsTests;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
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

import shared.PaymentFormGenerator;
import shared.RetryAnalyzer;
import shared.RetryCountIfFailed;
import shared.SharedData;
import shared.TestListener;

@Listeners({TestListener.class , HTMLReporter.class})
public class USPaymentFormLimitsTest {
	private WebDriver driver;
	PaymentFormGenerator g;
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
			g = new PaymentFormGenerator();
		
		}

		
		@BeforeMethod
		  public void beforeMethod() {
		 
		      driver.get(site + "payment/us/");
		 
		  }
		
		@RetryCountIfFailed(3)
		@Test (enabled = true , retryAnalyzer = RetryAnalyzer.class)
		  public void test1() throws InterruptedException {
			  
			  Select dmc = new Select(driver.findElement(By.id("dmc")));
			  dmc.selectByValue("Argentina");												// DMC
			  	
			  driver.findElement(By.id("bookingRef")).sendKeys(g.getBookingRef());			// Booking reference
			  driver.findElement(By.id("lastname")).sendKeys(g.getGuestLastName());	 		// Guest last name
			  driver.findElement(By.id("agency")).sendKeys(g.getAgencyName());				// Agency name
			  driver.findElement(By.id("iata")).sendKeys(g.getIata()); 						// Agency IATA/CLIA
			  driver.findElement(By.id("agent")).sendKeys(g.getAgentName()); 				// Agent Name
			  driver.findElement(By.id("email")).sendKeys(g.getAgentEmail()) ;				// Agent e-mail
			  driver.findElement(By.id("paymentAmount")).clear();
			  driver.findElement(By.id("paymentAmount")).sendKeys("-1"); 					// Payment amount
			  Select currency = new Select(driver.findElement(By.id("currency")));
			  currency.selectByValue("USD");												// Currency
			  
			  Thread.sleep(2000);
			  driver.findElement(By.id("client")).click();
			  driver.findElement(By.id("permission")).click();
			  driver.findElement(By.id("provided")).click();
			  driver.findElement(By.id("TCs")).click();
			  
			  driver.findElement(By.id("next")).click();
			  Thread.sleep(1000);

			  try {
				  driver.findElement(By.xpath("//*[@id=\"CS_CCF_308129_308130\"]/div/form/div[8]/div[2]/label"));
				  driver.findElement(By.id("next"));
				  Reporter.getCurrentTestResult().setStatus(ITestResult.SUCCESS);
			  }catch(Exception e) {
				  Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
				  System.out.println("US payment form - Negative payment can pass as valid!");
				  Reporter.log("US payment form - Negative payment can pass as valid!");
			  }
		  }
		  
		
		@RetryCountIfFailed(3)
		@Test (enabled = true , retryAnalyzer = RetryAnalyzer.class)
		  public void test2() throws InterruptedException {
			  
			  Select dmc = new Select(driver.findElement(By.id("dmc")));
			  dmc.selectByValue("Argentina");												// DMC
			  	
			  driver.findElement(By.id("bookingRef")).sendKeys(g.getBookingRef());			// Booking reference
			  driver.findElement(By.id("lastname")).sendKeys(g.getGuestLastName());	 		// Guest last name
			  driver.findElement(By.id("agency")).sendKeys(g.getAgencyName());				// Agency name
			  driver.findElement(By.id("iata")).sendKeys(g.getIata()); 						// Agency IATA/CLIA
			  driver.findElement(By.id("agent")).sendKeys(g.getAgentName()); 				// Agent Name
			  driver.findElement(By.id("email")).sendKeys(g.getAgentEmail()) ;				// Agent e-mail
			  driver.findElement(By.id("paymentAmount")).sendKeys("100000"); 			    // Payment amount
			  
			  Alert alert = driver.switchTo().alert();
			  
			  assertEquals(alert.getText(), "A maximum amount of $99999.99 can be processed per transaction");
			  alert.accept();
			  
			  driver.switchTo().defaultContent();
			  
			  Select currency = new Select(driver.findElement(By.id("currency")));
			  currency.selectByValue("USD");												// Currency
			  
			  driver.findElement(By.id("client")).click();
			  driver.findElement(By.id("permission")).click();
			  driver.findElement(By.id("provided")).click();
			  driver.findElement(By.id("TCs")).click();
			  
			  driver.findElement(By.id("next")).click();
			  Thread.sleep(1000);

			  try {
				  driver.findElement(By.id("next"));
				  Reporter.getCurrentTestResult().setStatus(ITestResult.SUCCESS);
			  }catch(Exception e) {
				  Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
				  System.out.println("US payment form - Payment is not limited! More than $99999.99 USD still can be paid.");
				  Reporter.log("\"US payment form - Payment is not limited! More than $99999.99 USD still can be paid.");
			  }
		  }
		  
		  @AfterClass
		  public void afterClass() {
			  driver.quit();
		  }
}

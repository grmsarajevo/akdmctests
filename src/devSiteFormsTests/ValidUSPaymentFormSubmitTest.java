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
import shared.PaymentFormGenerator;
import shared.SharedData;
import shared.TestListener;

import org.openqa.selenium.support.ui.Select;


@Listeners({TestListener.class})
public class ValidUSPaymentFormSubmitTest {
	
private WebDriver driver;
private SharedData s;
private String site;
PaymentFormGenerator g;
	
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
		
		g = new PaymentFormGenerator();
	
	}
	
	@BeforeMethod
	  public void beforeMethod() {
		
	      driver.get(site + "payment/us/");
	 
	  }
	  @Test(enabled=true)
	  public void testSubmit() throws InterruptedException {
  
		  Select dmc = new Select(driver.findElement(By.id("dmc")));
		  dmc.selectByValue("Argentina");												//DMC
		  	
		  driver.findElement(By.id("bookingRef")).sendKeys(g.getBookingRef());			// Booking reference
		  driver.findElement(By.id("lastname")).sendKeys(g.getGuestLastName());	 		// Guest last name
		  driver.findElement(By.id("agency")).sendKeys(g.getAgencyName());				// Agency name
		  driver.findElement(By.id("iata")).sendKeys(g.getIata()); 						// Agency IATA/CLIA
		  driver.findElement(By.id("agent")).sendKeys(g.getAgentName()); 				// Agent Name
		  driver.findElement(By.id("email")).sendKeys(g.getAgentEmail());				//Agent e-mail
		  driver.findElement(By.id("paymentAmount")).sendKeys("1"); 					// Payment amount
		  Select currency = new Select(driver.findElement(By.id("currency")));
		  currency.selectByValue("USD");	
		  
		  driver.findElement(By.id("client")).click();
		  driver.findElement(By.id("permission")).click();
		  driver.findElement(By.id("provided")).click();
		  driver.findElement(By.id("TCs")).click();
		  
		  driver.findElement(By.id("next")).click();
		  /*
		  try {
			  driver.findElement(By.id("next")).click();
			  Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
		  }catch(Exception e) {
			  Reporter.getCurrentTestResult().setStatus(ITestResult.SUCCESS);
		  }*/
		  
		  /*
		  // Payment
		  Select cardType = new Select(driver.findElement(By.id("CARDTYPE")));
		  cardType.selectByValue("MASTERCARD");											// Card type
		  driver.findElement(By.name("CARDNUMBER")).sendKeys("5100 2900 2900 2909");	// Card number
		  Select expMonth = new Select(driver.findElement(By.name("CCMONTH")));
		  expMonth.selectByValue("08");													// Expiry month
		  Select expYear = new Select(driver.findElement(By.name("CCYEAR")));
		  expYear.selectByValue("18");													// Expiry year
		  driver.findElement(By.name("CVV")).sendKeys("737");							// CVV
		  driver.findElement(By.name("CARDHOLDERNAME")).sendKeys("John");				// Cardholder name
		  
		  driver.findElement(By.id("submitBtn")).click();								// Pay now
		  */
		  
		  Select cardType = new Select(driver.findElement(By.id("CARDTYPE")));
		  cardType.selectByValue("VISA");												// Card type
		  driver.findElement(By.name("CARDNUMBER")).sendKeys("4111 1111 1111 1111");	// Card number
		  Select expMonth = new Select(driver.findElement(By.name("CCMONTH")));
		  expMonth.selectByValue("08");													// Expiry month
		  Select expYear = new Select(driver.findElement(By.name("CCYEAR")));
		  expYear.selectByValue("18");													// Expiry year
		  driver.findElement(By.name("CVV")).sendKeys("737");							// CVV
		  driver.findElement(By.name("CARDHOLDERNAME")).sendKeys("test");				// Cardholder name
		  
		  driver.findElement(By.id("submitBtn")).click();								// Pay now
		  
		  try {
			  driver.findElement(By.id("cs_control_308130"));
		  } catch(Exception e) {
			  Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
		  }
	  }
	  
	  @AfterClass
	  public void afterClass() {
		  driver.quit();
	  }
}

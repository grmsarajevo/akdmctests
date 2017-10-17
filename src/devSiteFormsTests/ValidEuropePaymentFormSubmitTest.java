package devSiteFormsTests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

import shared.MyChromeDriver;
import shared.PaymentFormGenerator;
import shared.SharedData;
import shared.TestListener;

@Listeners({TestListener.class})
public class ValidEuropePaymentFormSubmitTest {
	
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

	      driver.get(site + "payment/europe/");
	 
	  }
	  @Test (enabled = true)
	  public void test1() throws InterruptedException {
		  
		  
		  	
		  driver.findElement(By.id("bookingRef")).sendKeys(g.getBookingRef());			// Booking reference
		  driver.findElement(By.id("lastname")).sendKeys(g.getGuestLastName());	 		// Guest last name
		  driver.findElement(By.id("email")).sendKeys(g.getAgentEmail()) ;				// Agent e-mail
		  driver.findElement(By.id("paymentAmount")).sendKeys("1"); 					// Payment amount
		  Select currency = new Select(driver.findElement(By.id("currency")));
		  currency.selectByValue("USD");												// Currency
		  
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
		  		  
		  Thread.sleep(5000);
		  
		  WebElement iframe = driver.findElement(By.cssSelector("#barclay_iframe"));
		  
		  driver.switchTo().frame(iframe);
		  
		  driver.findElement(By.id("card.cardNumber")).sendKeys("4111 1111 1111 1111");			// Card number
		  Select expMonth = new Select(driver.findElement(By.id("card.expiryMonth")));
		  expMonth.selectByValue("08");															// Expiry month
		  Select expYear = new Select(driver.findElement(By.id("card.expiryYear")));
		  expYear.selectByValue("2018");														// Expiry year
		  driver.findElement(By.id("card.cvcCode")).sendKeys("737");							// CVV
		  driver.findElement(By.id("card.cardHolderName")).sendKeys("test");					// Cardholder name
		  
		  driver.findElement(By.name("pay")).click();											// Pay now
		  
		  try {
			  driver.findElement(By.id("pmcarddescription"));
			  Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
			  System.out.println("ValidEuropePaymentFormSubmit - Form cannot be submitted!");
		  } catch(Exception e) {
			  driver.switchTo().defaultContent();
		  }  
		  
	  }
	  
	  @AfterClass
	  public void afterClass() {
		  driver.quit();
	  }
}

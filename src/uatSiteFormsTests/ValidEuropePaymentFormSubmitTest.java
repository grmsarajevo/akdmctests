package uatSiteFormsTests;



import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import org.uncommons.reportng.HTMLReporter;

import shared.PaymentFormGenerator;
import shared.RetryAnalyzer;
import shared.RetryCountIfFailed;
import shared.SharedData;
import shared.TestListener;

import org.openqa.selenium.support.ui.Select;


@Listeners({TestListener.class , HTMLReporter.class})
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

	      driver.get(site + "payment/europe/");
	 
	  }
	
	@RetryCountIfFailed(3)
	@Test (enabled = true , retryAnalyzer = RetryAnalyzer.class)
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
		  
		  driver.findElement(By.id("card.cardNumber")).sendKeys("4111 1111 1111 1111");	// Card number
		  Select expMonth = new Select(driver.findElement(By.id("card.expiryMonth")));
		  expMonth.selectByValue("08");													// Expiry month
		  Select expYear = new Select(driver.findElement(By.id("card.expiryYear")));
		  expYear.selectByValue("2018");													// Expiry year
		  driver.findElement(By.id("card.cvcCode")).sendKeys("737");							// CVV
		  driver.findElement(By.id("card.cardHolderName")).sendKeys("test");				// Cardholder name
		  
		  driver.findElement(By.name("pay")).click();								// Pay now
		  
		  try {
			  driver.findElement(By.id("pmcarddescription"));
			  Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
			  System.out.println("Europe payment form can't be submitted!");
			  Reporter.log("Europe payment form can't be submitted!");
		  } catch(Exception e) {
			  driver.switchTo().defaultContent();
		  }  
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
		   
		   Thread.sleep(1000);
		   driver.findElement(By.xpath("//*[@id=\"tree\"]/ul/li/ul/li[1]/div/ins")).click();
		   Thread.sleep(1000);
		   driver.findElement(By.xpath("//*[@id=\"tree\"]/ul/li/ul/li[1]/ul/li[7]/div/ins")).click();
		   Thread.sleep(1000);
		   driver.findElement(By.xpath("//*[@id=\"tree\"]/ul/li/ul/li[1]/ul/li[7]/ul/li[2]/div/ins")).click();
		   Thread.sleep(1000);
		   driver.findElement(By.xpath("//*[@id=\"tree\"]/ul/li/ul/li[1]/ul/li[7]/ul/li[2]/ul/li[5]/div/a[1]")).click();
		   Thread.sleep(1000);
		   driver.findElement(By.xpath("//*[@id=\"tab25\"]/div/div/div/ng-form/div/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div[1]/div/div[3]/a/span")).click();
		   Thread.sleep(1000);
		   driver.findElement(By.xpath("//*[@id=\"tab25\"]/div/div/div/ng-form/div/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div[1]/div/div[3]/a/span")).click();
		   Thread.sleep(2000);
		   
		   String name = driver.findElement(By.xpath("//*[@id=\"tab25\"]/div/div/div/ng-form/div/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div[2]/div[1]/div[2]/a")).getText().toString();
		   Thread.sleep(1000);

		   if(name.contains(g.getBookingRef())) {
			   driver.findElement(By.xpath("//*[@id=\"tab25\"]/div/div/div/ng-form/div/div[2]/div/div/div/div/div/div[2]/div/div/div/div/div/div[2]/div[1]/div[2]/a")).click();
			   driver.findElement(By.id("bookingReference"));
			   Thread.sleep(3000);
		   } else {
			  System.out.println(name);
			  System.out.println("Europe payment form was not submitted");
			  Reporter.log("Europe payment form was not submitted");
			  Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
		   }
		   
		   // Wait for 3 Sec
		   Thread.sleep(3000);

		  }
	  
	  @AfterClass
	  public void afterClass() {
		  driver.quit();
	  }
}

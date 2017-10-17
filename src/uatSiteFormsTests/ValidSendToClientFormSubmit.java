package uatSiteFormsTests;

import static org.testng.Assert.assertEquals;

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
import org.uncommons.reportng.HTMLReporter;

import shared.RetryAnalyzer;
import shared.RetryCountIfFailed;
import shared.SharedData;
import shared.TestListener;


@Listeners({TestListener.class , HTMLReporter.class})
public class ValidSendToClientFormSubmit {
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
	  driver.findElement(By.id("client_link")).click();
	  
	  driver.findElement(By.id("3efdac17-d555-4a88-c993-ce3b6ac053df")).sendKeys("test@test.test");		// Client's email
	  driver.findElement(By.id("995718a7-e36c-48f6-9a2c-488273b0f4b0")).sendKeys("test@test.test");		// Sender's email
	  driver.findElement(By.id("e1bcb9c4-aab3-4d8a-e4d2-24a8460130fd")).sendKeys("test"+ time);			// Subject
	  driver.findElement(By.id("bafc8890-9a4d-46a5-cda3-598b5f2241c5")).sendKeys("test"); 				// Message
	  Thread.sleep(1000);
	  driver.findElement(By.xpath("//*[@id=\"umbraco_form_57df4f399fff499980f54aedcc2d465e\"]/form/div/div[2]/div/input")).click();													// Send
	  
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
	   driver.findElement(By.xpath("//*[@id=\"tree\"]/ul/li/ul/li[1]/div/ins")).click();
	   Thread.sleep(1000);
	   driver.findElement(By.xpath("//*[@id=\"tree\"]/ul/li/ul/li[1]/ul/li[9]/div/ins")).click();
	   Thread.sleep(1000);
	   driver.findElement(By.linkText("Entries")).click();
	   Thread.sleep(1000);
	   
	   String email = driver.findElement(By.xpath("//*[@id=\"contentcolumn\"]/div/div/form/div/div[2]/div[2]/div/div[2]/div[2]/div[1]/div[2]/div/div/a")).getText();
	   
	   if(email.contains("test@test.test")) {
		   driver.findElement(By.xpath("//*[@id=\"contentcolumn\"]/div/div/form/div/div[2]/div[2]/div/div[2]/div[2]/div[1]/div[2]/div/div/a")).click();
		   Thread.sleep(3000);
	   } else {
		   System.out.println("Send to client form was not submitted");
		   Reporter.log("Send to client form was not submitted");
		   System.out.println(email);
		   Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
	   }
	   
	   assertEquals(driver.findElement(By.xpath("//*[@id=\"contentcolumn\"]/div/div/form/div/div[2]/div[2]/div/div[2]/div[1]/div[3]/div/span")).getText(), "test" + time);
  }
  
  @AfterClass
  public void afterClass() {
	  driver.quit();
  }

}

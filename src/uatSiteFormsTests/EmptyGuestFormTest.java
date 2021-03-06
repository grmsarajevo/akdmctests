package uatSiteFormsTests;

import org.testng.annotations.Test;
import org.uncommons.reportng.HTMLReporter;

import shared.RetryAnalyzer;
import shared.RetryCountIfFailed;
import shared.SharedData;
import shared.TestListener;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

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


@Listeners({TestListener.class , HTMLReporter.class})
public class EmptyGuestFormTest {
	
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
		  driver = new ChromeDriver();
		 }		 
		 
		 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	      driver.manage().window().maximize();
	     
		 s = new SharedData();
		site = s.getSite();

	}


  @BeforeMethod
  public void beforeMethod() {
 
      driver.get(site + "booking/");
 
  }
  
  @RetryCountIfFailed(3)
  @Test (enabled = true , retryAnalyzer = RetryAnalyzer.class)
  public void testRequiredFields() throws InterruptedException {
	  driver.findElement(By.name("__next")).click();
	  Thread.sleep(1000);
	  try {
		  driver.findElement(By.name("__next")).click();
		  Reporter.getCurrentTestResult().setStatus(ITestResult.SUCCESS);
	  } catch(Exception e) {	  
		  Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
		  System.out.println("Guest form fields are not required");
		  Reporter.log("Guest form fields are not required");
	  }

  }

  @AfterClass
  public void afterClass() {
	  driver.quit();
  }

  
}

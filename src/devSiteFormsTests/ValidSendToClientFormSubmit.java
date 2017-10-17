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
		  driver = new MyChromeDriver();
		 }		 
		 
		 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	      driver.manage().window().maximize();
	     
		 s = new SharedData();
		site = s.getDevSite();
		
		time = String.valueOf(System.currentTimeMillis());
	
	}

  @BeforeMethod
  public void beforeMethod() {
 
      driver.get(site);
 
  }
  
  @Test (enabled = true)
  public void test1() throws InterruptedException {
	  
	  try {
		  driver.findElement(By.xpath("//*[@id=\"nav\"]/ul/li[6]/a")).click();
		  driver.findElement(By.xpath("/html/body/div[1]/div[2]/section/div[3]/div/div/div/div/div[1]/div/h3/a")).click();
		  driver.findElement(By.xpath("//*[@id=\"listingGrid\"]/div[1]/a")).click();
		  driver.findElement(By.id("client_link")).click();
	  } catch(Exception e) {
		  System.out.println("Couldn't navigate to requested page!");
		  Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
	  }
	  
	  //if(Reporter.getCurrentTestResult().equals(ITestResult.SUCCESS)) {
		  driver.findElement(By.id("3efdac17-d555-4a88-c993-ce3b6ac053df")).sendKeys("test@test.test");		// Client's email
		  driver.findElement(By.id("995718a7-e36c-48f6-9a2c-488273b0f4b0")).sendKeys("test@test.test");		// Sender's email
		  driver.findElement(By.id("e1bcb9c4-aab3-4d8a-e4d2-24a8460130fd")).sendKeys("test"+ time);			// Subject
		  driver.findElement(By.id("bafc8890-9a4d-46a5-cda3-598b5f2241c5")).sendKeys("test"); 				// Message
		  driver.findElement(By.name("__next")).click();													// Send
	  
	  
  }
  
  @AfterClass
  public void afterClass() {
	  driver.quit();
  }

}

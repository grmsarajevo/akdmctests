package devSiteFormsTests;

import org.testng.annotations.Test;

import shared.MyChromeDriver;
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

@Listeners({TestListener.class})
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
		  driver = new MyChromeDriver();
		 }		 
		 
		 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	      driver.manage().window().maximize();
	     
		 s = new SharedData();
		site = s.getDevSite();
	
	}

  @BeforeMethod
  public void beforeMethod() {
 
      driver.get(site + "	booking/");
 
  }
  
  @Test (enabled = true)
  public void testRequiredFields() throws InterruptedException {
	  driver.findElement(By.xpath("//*[@id=\"umbraco_form_445648f506f04881a0e75093a72b38b3\"]/form/div/div[2]/div/input")).click();
	  try {
		  driver.findElement(By.xpath("/html/body/div[1]/div[2]/section/div[3]/div/div/div/h2"));
		  Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
	  } catch(Exception e) {
		  System.out.println("Guest form fields are not required");
		  Reporter.getCurrentTestResult().setStatus(ITestResult.SUCCESS);
	  }

  }

  @AfterClass
  public void afterClass() {
	  driver.quit();
  }

  
}

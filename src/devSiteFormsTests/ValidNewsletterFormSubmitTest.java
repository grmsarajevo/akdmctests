package devSiteFormsTests;

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

import shared.MyChromeDriver;
import shared.NewsletterFormGenerator;
import shared.SharedData;
import shared.TestListener;

@Listeners({TestListener.class})
public class ValidNewsletterFormSubmitTest {
	
private WebDriver driver;
private SharedData s;
private String site;
NewsletterFormGenerator g;
	
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
		
		g = new NewsletterFormGenerator();
	
	}
	
	@BeforeMethod
	  public void beforeMethod() {

	      driver.get(site + "newsletter/");
	}
	
	  @Test (enabled = true)
	  public void testFormWithData() throws InterruptedException {
		  		  	
		  driver.findElement(By.id("4091fdde-5532-4911-925a-fce9defbd83e")).sendKeys(g.getFirstName());			// First name
		  driver.findElement(By.id("db986c34-7892-423e-accf-8cf7bba8b737")).sendKeys(g.getLastName());			// Last name
		  driver.findElement(By.id("23667f54-8254-4f5d-fc25-b83f6a12b57c")).sendKeys(g.getEmail());				// e-mail
		  driver.findElement(By.id("4535a0c1-a4e1-4239-d7e8-41fe3eec50c3")).sendKeys(g.getAgency());			// Agency/Company
		  driver.findElement(By.id("bf6659ce-35b6-440b-bdc7-6dbeefc7ae19")).sendKeys(g.getConsortia());			// Consortia
		  	
		  Select residence = new Select(driver.findElement(By.id("25975c5c-8195-43b0-c1dc-1134e9390c85")));	
		  residence.selectByValue("Algeria");															// Country of residence
		  
		  driver.findElement(By.id("27e870c0-154c-4f3a-9956-78536c670481_0")).click();					// Newsletter region
		  
		  driver.findElement(By.name("__next")).click();												// Subscribe
		  
		  try {
			  driver.findElement(By.name("__next"));
			  Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
			  System.out.println("Newsletter form can't be submitted!");
		  } catch(Exception ex) {
			  Reporter.getCurrentTestResult().setStatus(ITestResult.SUCCESS);
		  }
	  }
	  
	  @AfterClass
	  public void afterClass() {
		  driver.quit();
	  }
}

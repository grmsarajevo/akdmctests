package uatSiteFormsTests;

import static org.testng.Assert.assertEquals;

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

import shared.NewsletterFormGenerator;
import shared.RetryAnalyzer;
import shared.RetryCountIfFailed;
import shared.SharedData;
import shared.TestListener;


@Listeners({TestListener.class , HTMLReporter.class})
public class ValidNewsletterFormSubmitTest {
	
private WebDriver driver;
NewsletterFormGenerator g;
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
		
		g = new NewsletterFormGenerator();
	}

	
	@BeforeMethod
	  public void beforeMethod() {

	      driver.get(site + "newsletter/");
	}
	
	@RetryCountIfFailed(3)
	@Test (enabled = true , retryAnalyzer = RetryAnalyzer.class)
	  public void test1() throws InterruptedException {
		 		  	
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
			  Reporter.log("Newsletter form can't be submitted!");
		  } catch(Exception ex) {
			  Reporter.getCurrentTestResult().setStatus(ITestResult.SUCCESS);
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
		   driver.findElement(By.className("icon-umb-contour")).click();
		   Thread.sleep(1000);
		   driver.findElement(By.xpath("//*[@id=\"tree\"]/ul/li/ul/li[1]/div/ins")).click();
		   Thread.sleep(1000);
		   driver.findElement(By.xpath("//*[@id=\"tree\"]/ul/li/ul/li[1]/ul/li[4]/div/ins")).click();
		   Thread.sleep(1000);
		   driver.findElement(By.linkText("Entries")).click();
		   Thread.sleep(1000);
		   
		   
		   String countryString = driver.findElement(By.xpath("//*[@id=\"contentcolumn\"]/div/div/form/div/div[2]/div[2]/div/div[2]/div[2]/div[1]/div[2]/div/div/a")).getText().toString();
		   Thread.sleep(1000);

		   if(countryString.contains(g.getFirstName())) {
		    driver.findElement(By.xpath("//*[@id=\"contentcolumn\"]/div/div/form/div/div[2]/div[2]/div/div[2]/div[2]/div[1]/div[2]/div/div/a")).click();
		    Thread.sleep(3000);
		   } else {
			   System.out.println("Newsletter form was not submitted!");
			   Reporter.log("Newsletter form was not submitted!");
			   System.out.println(countryString);
			   Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
		   }
		   Thread.sleep(2000);
		   
		   assertEquals(driver.findElement(By.xpath("//*[@id=\"contentcolumn\"]/div/div/form/div/div[2]/div[2]/div/div[2]/div[1]/div[1]/div/span")).getText(), g.getFirstName());

		   // Wait for 3 Sec
		   Thread.sleep(3000);

		  }
	  
	  @AfterClass
	  public void afterClass() {
		  driver.quit();
	  }
}

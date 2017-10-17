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

import java.util.ArrayList;
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
import org.openqa.selenium.support.ui.Select;


/* This test case tests if there are right number of guest forms for
 * each element of the dropdown list
 */
@Listeners({TestListener.class , HTMLReporter.class})
public class NumberOfGuestsTest {
	
	WebDriver driver;
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
	  public void testNumberOfGuests() throws InterruptedException {
		  
		  ArrayList<String> numbers = new ArrayList<String>();
		  
		  numbers.add("bc4728d8-4673-4ee4-b4b1-b984fad1ce8a"); // Guest two
		  numbers.add("77cb2777-ea8e-49e1-9fb6-712dc9494222"); // Guest three
		  numbers.add("fb5d64e2-4dbe-490f-9a30-561d16a66c15"); // Guest four
		  numbers.add("0febb20d-00ef-42a9-f20e-cfbeee2eb73d"); // Guest five
		  numbers.add("e4595c1d-9fce-47b7-a39b-f24658b2ee9b"); // Guest six
		  numbers.add("56e0e998-c66e-427a-e22d-7bbb9b064488"); // Guest seven
		  numbers.add("7add3a49-7868-4c46-d9cf-3f258ef1116f"); // Guest eight
		  numbers.add("a16ea91a-1777-4dbd-f602-15e3986e1227"); // Guest nine
		  numbers.add("e14f069d-822d-4286-faed-b0a2c627134b"); // Guest ten
		  
		  Select droplist = new Select(driver.findElement(By.id("7c8984a5-8b35-437e-e03a-d40de906acb8")));
		  for(int i = 2; i <= 10; i++) {
			  
			  // change value in dropdown list
			  droplist.selectByVisibleText(String.valueOf(i));
			  
			  for(int j = 2; j <= i; j++) {
				  // check if form for each guest is present
				  String style = driver.findElement(By.id(numbers.get(j-2))).getAttribute("style");
				  
				  if(style.contains("none")) {
					  //if at least on of the forms doesn't show up, test will fail 
					  System.out.println("Guest "+ String.valueOf(j) + " form invalid!");
					  Reporter.log("Guest "+ String.valueOf(j) + " form invalid!");
					  Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
				  }
			  }
		  }
		  
	  }
	
	
	  @AfterClass
	  public void afteClass() {
		  driver.quit();
	  }

}

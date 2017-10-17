package uatSiteFormsTests;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
public class SearchTest {
	private WebDriver driver;
	private String site;
	private SharedData s;
	
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
	 
	      driver.get(site);
	 
	  }
	
	@RetryCountIfFailed(2)
	@Test (enabled = true , retryAnalyzer = RetryAnalyzer.class)
	public void test() throws InterruptedException {
		Thread.sleep(1000);
		
		try {
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		
		executor.executeScript("document.getElementById('searchTerm').value='Argentina';");
		
		Thread.sleep(1000);
		executor.executeScript("document.getElementById('btn_search2').click();");
		
		Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(driver.findElement(By.xpath("//*[@id=\"form0\"]/div/div/div[1]/div/h2")).getText(), "Search Results");
		
		String text = driver.findElement(By.xpath("//*[@id=\"search-results\"]/div/div[1]/div/table/tbody/tr[1]/td[2]/div/span/span/p/span")).getText();
		
		if(text.contains("Argentina")) {
			Reporter.getCurrentTestResult().setStatus(ITestResult.SUCCESS);
		} else {
			Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
			System.out.println("Search failed");
			Reporter.log("Search failed");
		}
		
		
	}
	
	@AfterClass
	  public void afterClass() {
		  driver.quit();
	  }
}

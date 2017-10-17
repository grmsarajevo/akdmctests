package uatSiteFormsTests;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
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

import shared.GuestFormGenerator;
import shared.RetryAnalyzer;
import shared.RetryCountIfFailed;
import shared.SharedData;
import shared.TestListener;


@Listeners({TestListener.class , HTMLReporter.class})
public class ValidGuestFormSubmitTest {
	WebDriver driver;
	GuestFormGenerator c;
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
		
		c = new GuestFormGenerator();

	}

	
	@BeforeMethod
	 
	  public void beforeMethod() {

	      driver.get(site + "booking/");	      

	  }
	
	@RetryCountIfFailed(3)
	@Test (enabled = true , retryAnalyzer = RetryAnalyzer.class)
	  public void test1() throws InterruptedException {
		  	  
		  Select dmc = new Select(driver.findElement(By.id("ee8b701b-5356-45c1-acfe-442ee1764410")));
		  dmc.selectByValue("Jordan");																				// DMC
		  
		  driver.findElement(By.id("ab121366-6ba3-49fb-e6f4-d8d1e67906b1")).sendKeys(c.getBrn());					// Booking reference number
	
		  // Agency details
		  driver.findElement(By.id("44f668ac-6b37-4709-ef46-9a0141fff374")).sendKeys(c.getAgency());				// Agency name
		  driver.findElement(By.id("d140dee6-b1e3-4d56-bc14-e2d2929bec73")).sendKeys(c.getAgent());					// Agent name
		  driver.findElement(By.id("78d313f9-6a46-46bf-db48-69baa21fe2d9")).sendKeys(c.getAgentEmail());			// Agent e-mail
		  driver.findElement(By.id("965a5a05-eade-4320-979f-983d1f261571")).sendKeys(c.getIata());					// IATA number
		  
		  // Lead passenger details
		  driver.findElement(By.id("db51fe53-5b47-4ab0-cfc3-cb7864de8273")).sendKeys(c.getFirstName());				// First name
		  driver.findElement(By.id("7e42b1dc-0233-4bce-a581-999478fc5685")).sendKeys(c.getLastName());				// Last name
		  driver.findElement(By.id("30d8a75b-cd6c-452e-f028-e12eaf3155f2")).sendKeys(c.getAdress());				// Adress
		  driver.findElement(By.id("c9ba21b4-eb30-4997-ad0b-10b377be51f0")).sendKeys(c.getPassEmail());				// e-mail
		  driver.findElement(By.id("1585fdf7-2c2b-4526-c30e-512d36434754")).sendKeys(c.getTelephone());				// Telephone
		  driver.findElement(By.id("d7296b1b-3741-4859-cac4-522537981b23")).sendKeys(c.getMobile());				// Mobile
		  driver.findElement(By.id("4d27014f-ebe6-4ed1-fa99-effd40214e85")).sendKeys(c.getNationality());			// Nationality
		  driver.findElement(By.id("f2f100ae-1142-432e-8d1f-2790f3798868")).sendKeys(c.getDateOfBirth());			// Date of birth
		  driver.findElement(By.id("6c3d2ac2-4173-479f-dc1c-071ec082b3cc")).sendKeys(c.getPassNum());				// Passport number
		  driver.findElement(By.id("cd8e4497-dc90-4397-9813-d50e0428ccc4")).sendKeys(c.getDateOfIssue()); 			// Passport date of issue
		  driver.findElement(By.id("74febdd8-3315-43af-ffb9-afe679927fae")).sendKeys(c.getPassExpiry());			// Passport expiry
		  driver.findElement(By.id("556d2eed-0136-4de4-b27b-08aadfdaa9be")).sendKeys(c.getPassPlace());				// Passport place of issue
		  
		  // Emergency Contact Details
		  driver.findElement(By.id("5d1df71b-09d2-4a51-859d-e8ed7ab7ca90")).sendKeys(c.getName());					// Name
		  driver.findElement(By.id("ac541199-3e00-44e1-dd13-3c9608df0542")).sendKeys(c.getRelationship()); 			// Relationship
		  driver.findElement(By.id("af240aa3-9de1-495d-ee13-f751d2fe7d14")).sendKeys(c.getContactEmail());			// e-mail
		  driver.findElement(By.id("85088650-4ead-4ba5-cdd7-b89453dee25a")).sendKeys(c.getContactPhone()); 			// Telephone
		  driver.findElement(By.id("59f91d16-1306-48f1-feed-ba953a5ecb0d")).sendKeys(c.getContactMobile()); 		// Mobile
		  
		  //Number of guests
		  Select droplist = new Select(driver.findElement(By.id("7c8984a5-8b35-437e-e03a-d40de906acb8")));
		  droplist.selectByVisibleText("10");
		  // Guest forms
		  ArrayList<String> guests = new ArrayList<String>();  
		  guests.add("bc4728d8-4673-4ee4-b4b1-b984fad1ce8a"); // Guest two
		  guests.add("77cb2777-ea8e-49e1-9fb6-712dc9494222"); // Guest three
		  guests.add("fb5d64e2-4dbe-490f-9a30-561d16a66c15"); // Guest four
		  guests.add("0febb20d-00ef-42a9-f20e-cfbeee2eb73d"); // Guest five
		  guests.add("e4595c1d-9fce-47b7-a39b-f24658b2ee9b"); // Guest six
		  guests.add("56e0e998-c66e-427a-e22d-7bbb9b064488"); // Guest seven
		  guests.add("7add3a49-7868-4c46-d9cf-3f258ef1116f"); // Guest eight
		  guests.add("a16ea91a-1777-4dbd-f602-15e3986e1227"); // Guest nine
		  guests.add("e14f069d-822d-4286-faed-b0a2c627134b"); // Guest ten
		  
		  // First names
		  ArrayList<String> firstNames = new ArrayList<String>();
		  firstNames.add("56c5ee9c-cbfd-4267-ba63-e7503f1c755d");	// Guest two
		  firstNames.add("15a0fc18-aa74-4c4f-8391-0a6753f764cf");	// Guest three
		  firstNames.add("0f6c8be8-2853-4062-df22-e1b55997e4d8");	// Guest four
		  firstNames.add("31137f0f-a570-4bce-8700-f0e5c887f5a9");	// Guest five
		  firstNames.add("d14b7bbb-0674-4962-90d6-f52a2da240dc");	// Guest six
		  firstNames.add("356b8193-9046-418d-c9e8-0f37f3a1de05");	// Guest seven
		  firstNames.add("79b2b1ef-4418-4f5e-e294-78275f1a6924"); 	// Guest eight
		  firstNames.add("42e6e73a-53f7-4bbe-e360-aec9205732ed"); 	// Guest nine
		  firstNames.add("c01d63f3-7865-4cf1-cf58-f03ddc87cb31"); 	// Guest ten
		  
		  // Last names
		  ArrayList<String> lastNames = new ArrayList<String>();
		  lastNames.add("522a5605-abd8-4e6f-f057-0bbdc096095b");	// Guest two
		  lastNames.add("4c787490-2b6e-49d0-e1fc-c2636cb9daaf"); 	// Guest three
		  lastNames.add("76e326d6-13b4-4d25-b8a1-b0137f9a61bd"); 	// Guest four
		  lastNames.add("65b279a2-46ad-4a2b-d2a6-cd97dd3776d3");	// Guest five
		  lastNames.add("13a89e7c-87c2-4fbe-8469-99f63cd7c410"); 	// Guest six
		  lastNames.add("5cd6f5f0-7802-4b05-c7f0-e5b6dffd6812"); 	// Guest seven
		  lastNames.add("2781ba5d-f98d-4fac-cf98-e31ae009a04a");	// Guest eight
		  lastNames.add("ba0933ef-a2bb-413a-83f7-3111cc9432dd"); 	// Guest nine
		  lastNames.add("7ea5bd0f-f52f-4e6d-aeee-0c021a44bd62"); 	// Guest ten
		  
		  for(int i = 0; i <= 8; i++) {
			  try {
				  driver.findElement(By.id(firstNames.get(i))).sendKeys("Guest "+ String.valueOf(i+2) + " first name");
				  driver.findElement(By.id(lastNames.get(i))).sendKeys("Guest " + String.valueOf(i+2) + " last name");
			  } catch(Exception ex) {
				  System.out.println("Guest " + String.valueOf(i+2) + " form invalid");
				  Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
			  }
		  }
		  
		  driver.findElement(By.id("8e49b365-c1f2-49a0-a284-4e379e74bed1")).sendKeys(c.getFlight()); 		// International flight details: (arrival and departure)
		  driver.findElement(By.id("7360b4f5-2408-4760-9ec2-3e67c91b0b00")).sendKeys(c.getReq());			// Dietary Requirements and Allergies
		  driver.findElement(By.id("b11eca31-d2af-42e3-c8c3-48cdcb59b990")).sendKeys(c.getSpec());			// Special requirements
		  driver.findElement(By.id("3e570d9a-6a4a-4c1d-9301-cb452116af28")).sendKeys(c.getOccasions());		// Special Occasions or Requests
		  driver.findElement(By.id("34dc27ab-5be3-4858-d4b9-d1e104f0432e")).sendKeys(c.getLists());			// Rooming Lists and Bedding Requirements
		  
		  driver.findElement(By.name("__next")).click(); 	//Submit
	
		  try {
			  driver.findElement(By.xpath("/html/body/div[1]/div[2]/section/div[3]/div/div/div/h2"));
		  } catch (Exception ex) {
			  System.out.println("Guest form cannot be submitted");
			  Reporter.log("Guest form cannot be submitted");
			  Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
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
		   driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/div[1]/div[2]/ul/li/ul/li[1]/div/ins")).click();
		   Thread.sleep(1000);
		   driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/div[1]/div[2]/ul/li/ul/li[1]/ul/li[1]/div/ins")).click();
		   Thread.sleep(1000);
		   driver.findElement(By.linkText("Entries")).click();
		   Thread.sleep(1000);
		   
		   
		   String countryString = driver.findElement(By.xpath("/html/body/div/section/div/div/div/form/div/div[2]/div[2]/div/div[2]/div[2]/div[1]/div[2]/div/div/a")).getText().toString();
		   Thread.sleep(1000);
	
		   if(countryString.contains("Jordan")) {
			   driver.findElement(By.xpath("/html/body/div/section/div/div/div/form/div/div[2]/div[2]/div/div[2]/div[2]/div[1]/div[2]/div/div/a")).click();
			   Thread.sleep(3000);
		   } else {
			   System.out.println("Guest form was not submitted!");
			   Reporter.log("Guest form was not submitted!");
			   System.out.println(countryString);
			   Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
		   }
		   Thread.sleep(2000);

		   assertEquals(driver.findElement(By.xpath("//*[@id=\"contentcolumn\"]/div/div/form/div/div[2]/div[2]/div/div[2]/div[1]/div[2]/div/span")).getText(), c.getBrn());
	
		   // Wait for 3 Sec
		   Thread.sleep(3000);
	
		  }
	  
	  @AfterClass
	  public void afterClass() {
		  driver.quit();
	  }
}

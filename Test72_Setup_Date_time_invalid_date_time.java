package SBA_GW_Tests;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import SBA_GW_Tests.Log;

/**
* ----------------
* This test tests an entry of invalid dates and times
* -----------------
* Tests:
*       
*   1. Check headers of the menu
*   2. Try to set an invalid date format
*   3. Try to set an invalid time format
*   4. Try to save an empty timezone
*
* Results:
*    1. Menu should looks ok
*    2. The system should change the date automaticaly to valid date long in the futre - therfore invalid
*    3. The system should change the time automaticaly to valid time
*    4. Empty timezone is not allowed
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test72_Setup_Date_time_invalid_date_time {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test72_Setup_Date_time_invalid_date_time(String browser) {
	  
	  this.usedBrowser = browser;
	  Log.info("Browser - "  + browser);
  }
  
  //Define each browser as a parameter
  @SuppressWarnings("rawtypes")
  @Parameters(name="{0}")
  public static Collection data() {
	  
	  SBAGlobalVars testVars2  = new SBAGlobalVars();
	  return Arrays.asList(testVars2.getBrowsers());
  }
  
  @BeforeClass public static void setting_SystemProperties() {
	  
      Log.info("System Properties seting Key value.");
  }  
  
  @Before
  public void setUp() throws Exception {
	  
	testVars  = new SBAGlobalVars();
	testFuncs = new SBAGlobalFuncs(); 
    System.setProperty("webdriver.chrome.driver", testVars.getChromeDrvPath());
	System.setProperty("webdriver.ie.driver"    , testVars.getIEDrvPath());
	testFuncs.myDebugPrinting("Enter setUp(); browser - " + this.usedBrowser + "\n", testVars.logerVars.MAJOR);
	driver = testFuncs.defineUsedBrowser(this.usedBrowser);
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }
    
  @Test  
  public void Setup_date_time_invalid_date() throws Exception {
	  
	  Log.startTestCase(this.getClass().getName());
  	  testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
  	  testFuncs.enterMenu(driver, "Setup_Date_time", "Date & Time");
  	
  	  /**
  	  * Step 1: Check some headers of menu
  	  */
  	  testFuncs.myDebugPrinting("Step 1: Check some headers of menu", testVars.logerVars.MAJOR);
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[1]/h3"), "Date & Time");
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyform']/div[1]/div[1]/label")			   , "Date:");
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyform']/div[1]/div[2]/div/label")		   , "Time:");
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyTzform']/div[1]/div[3]/label")		   , "Time zone");

	  /**
   	  * Step 2: Set an invalid date format
   	  */
   	  testFuncs.myDebugPrinting("Step 2: Set an invalid date format", testVars.logerVars.MAJOR);
   	  testFuncs.mySendKeys(driver, By.xpath("//*[@id='reservation']"), "99-03-2017", 3000);   	  
   	  driver.findElement(By.xpath("//*[@id='reservation']")).sendKeys(Keys.ENTER);
   	  testFuncs.myWait(2000);
   	  testFuncs.myClick(driver, By.xpath("//*[@id='parsleyform']/div[2]/button"), 7000);
   	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='errorIDDate']"), "Failed to set local time"); 	  
   	  
	  /**
      * Step 3: Try to set an invalid time format
      */
   	  testFuncs.myDebugPrinting("Step 3: Try to set an invalid time format", testVars.logerVars.MAJOR);
   	  
   	  driver.findElement(By.xpath("//*[@id='reservation']")).clear();;
   	  testFuncs.myWait(4000); 
   	  testFuncs.mySendKeys(driver, By.xpath("//*[@id='reservation']"), "11-03-", 3000);   	  
  	  driver.findElement(By.xpath("//*[@id='reservation']")).sendKeys(Keys.ENTER);
   	  testFuncs.myWait(4000);  
   	  testFuncs.mySendKeys(driver, By.xpath("//*[@id='parsleyform']/div[1]/div[2]/div/div/input"), "99:99 AM", 3000);
   	  driver.findElement(By.xpath("//*[@id='parsleyform']/div[1]/div[2]/div/div/input")).sendKeys(Keys.ENTER);
   	  testFuncs.myWait(4000);
   	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[2]/div/div/div/div[1]/h4"), "Date & Time successfully changed!");	
   	  
	  /**
	  * Step 4: Try to save an empty timezone
      */
   	  testFuncs.myDebugPrinting("Step 4: Try to save an empty timezone", testVars.logerVars.MAJOR);  
   	  Select timeZones = new Select(driver.findElement(By.xpath("//*[@id='parsleyTzform']/div[1]/div[3]/select")));
   	  timeZones.selectByIndex(0);
   	  testFuncs.myClick(driver, By.xpath("//*[@id='parsleyTzform']/div[2]/button"), 2000);
   	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsley-id-10']/li"), "This value is required.");	  
  }

  @After
  public void tearDown() throws Exception {
	  
    driver.quit();
    System.clearProperty("webdriver.chrome.driver");
	System.clearProperty("webdriver.ie.driver");
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
    	
      testFuncs.myFail(verificationErrorString);
    }
  }
}

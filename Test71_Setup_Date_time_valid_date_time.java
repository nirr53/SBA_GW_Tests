package SBA_GW_Tests;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Random;
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
* This test tests an entry of valid dates and times
* -----------------
* Tests:
*       
*   1. Check headers of the menu
*   2. Try to set an valid date format
*   3. Try to set an valid time format
*   4. Try to save a random timezone
*
* Results:
*    1. Menu should looks ok
*    2. The new date should be saved successfully
*    3. The new time should be saved successfully
*    4. The new timezone should be saved successfully
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test71_Setup_Date_time_valid_date_time {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test71_Setup_Date_time_valid_date_time(String browser) {
	  
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
  public void Setup_date_time_valid_date() throws Exception {
	  
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
   	  * Step 2: Set a valid date format
   	  */
   	  testFuncs.myDebugPrinting("Step 2: Set a valid date format", testVars.logerVars.MAJOR);
   	  testFuncs.myDebugPrinting(getDate());
   	  testFuncs.myDebugPrinting(getTime());   	  
  	  testFuncs.mySendKeys(driver, By.xpath("//*[@id='reservation']"), getDate(), 3000);   	  
  	  driver.findElement(By.xpath("//*[@id='reservation']")).sendKeys(Keys.ENTER);
   	  testFuncs.myWait(2000);
   	  testFuncs.myClick(driver, By.xpath("//*[@id='parsleyform']/div[2]/button"), 2000);
   	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[2]/div/div/div/div[1]/h4"), "Date & Time successfully changed!"); 	  
   	  
	  /**
      * Step 3: Try to set a valid time format
      */
   	  testFuncs.myDebugPrinting("Step 3: Try to set a valid time format", testVars.logerVars.MAJOR);

   	  testFuncs.mySendKeys(driver, By.xpath("//*[@id='parsleyform']/div[1]/div[2]/div/div/input"), this.getTime(), 7000);
   	  driver.findElement(By.xpath("//*[@id='parsleyform']/div[1]/div[2]/div/div/input")).sendKeys(Keys.ENTER);
   	  testFuncs.myWait(10000);
   	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[2]/div/div/div/div[1]/h4"), "Date & Time successfully changed!"); 	  
   	  
	  /**
	  * Step 4: Try to save a new random timezone
      */
   	  testFuncs.myDebugPrinting("Step 4: Try to save a new random timezone", testVars.logerVars.MAJOR);  
   	  Select timeZones = new Select(driver.findElement(By.xpath("//*[@id='parsleyTzform']/div[1]/div[3]/select")));
   	  Random rand = new Random();
   	  timeZones.selectByIndex(rand.nextInt(timeZones.getOptions().size()) + 1);
  	  testFuncs.myClick(driver, By.xpath("//*[@id='parsleyTzform']/div[2]/button"), 7000);  
   	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyTzform']/div[1]/div[1]/h4"), "Time Zone successfully changed!"); 	  
  }
  
  /**
  *  Return the current date in dd-MM-YYYY format
  *  @return currnet date in dd-MM-YYYY format
  */
  private String getDate() {
	  
	  DateFormat dateFormat = new SimpleDateFormat("dd-MM-");
	  Date date  = new Date();
	  testFuncs.myDebugPrinting("Curr data - " + dateFormat.format(date), testVars.logerVars.MINOR);
	  return dateFormat.format(date);
  }
  
  /**
  *  Return the current time in dd-MM-YYYY format
  *  @return currnet time in dd-MM-YYYY format
  */
  private String getTime() {
	  
	  DateFormat timeFormat = new SimpleDateFormat("HH:mm a");
	  Date date  = new Date();  	
	  testFuncs.myDebugPrinting("Curr time - " + timeFormat.format(date), testVars.logerVars.MINOR);
	  return timeFormat.format(date);
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

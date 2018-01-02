package SBA_GW_Tests;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.*;

import SBA_GW_Tests.Log;

/**
* ----------------
* This test tests the alarms which displayed on the SBA web
* -----------------
* Tests:
*   - Enter the SBC-gateway-web page,
*    	* Configuration    -> VoIP 			   -> VoIP network  ->
*    	* proxy sets table -> Lync 			   -> Edit          ->
*     	* Keep alive 	   -> Proxy keep alive -> using OPTIONS -> Save 
*    1. Check that Alarm is displayed on main page
*    2. Check Alarm info
* 
* Results:
*    1+2. Alarm info is displayed on the main page
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test53_Active_alarms {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test53_Active_alarms(String browser) {
	  
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
  public void Active_alarms() throws Exception {
	  
	Log.startTestCase(this.getClass().getName());
	
	/**
	* Step 1: Check that Alarm is displayed on main page
	*/
	testFuncs.myDebugPrinting("Step 1: Check that Alarm is displayed on main page", testVars.logerVars.MAJOR);
	testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[9]/div/table/tbody/tr/td[3]/table/tbody/tr/td/div/label")					   , "SBC Alarms");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[9]/div/table/tbody/tr/td[3]/table/tbody/tr/td/div/table/thead/tr/th[1]")	   , "Description");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[9]/div/table/tbody/tr/td[3]/table/tbody/tr/td/div/table/thead/tr/th[2]")	   , "ID");
	testFuncs.myWait(10000);
	
	/**
	* Step 2: Check Alarm info
	*/
	testFuncs.myDebugPrinting("Step 2: Check Alarm info", testVars.logerVars.MAJOR);
	testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div[9]/div/table/tbody/tr/td[3]/table/tbody/tr/td/div/table/tbody/tr/td[3]/div/i"), 5000);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[9]/div/div/div/div/div[1]/h4/span[1]"), "Alarm");
	String bodyText = driver.findElement(By.xpath("//*[@id='contentwrapper']/section/div[9]/div/div/div/div/div[2]/label")).getText();
	testFuncs.myDebugPrinting("bodyText - " + bodyText, testVars.logerVars.MAJOR);
	testFuncs.myAssertTrue("Field was ID not detected !!"		  , bodyText.contains("ID"));
	testFuncs.myAssertTrue("Alarm Description was not detected !!", bodyText.contains("DESCRIPTION = Proxy Set Alarm Proxy Set 2: Proxy lost. looking for another proxy"));
	testFuncs.myAssertTrue("Alarm Source was not detected !!"  	  , bodyText.contains("SOURCE = Board#1/ProxyConnection#2"));
	testFuncs.myAssertTrue("Alarm Severity was not detected !!"	  , bodyText.contains("SEVERITY = Major"));
	testFuncs.myAssertTrue("Field was DATE not detected !!"	   	  , bodyText.contains("DATE"));
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

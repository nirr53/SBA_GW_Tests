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
* This test tests the process of add a new SNMP
* -----------------
* Tests:
*       
*   1. Check the headers of the menu
*   2. Insert SNMP information and submit for every host
*   3. Insert SNMP information and submit for specefic hosts
*
* Results:
*    1.   Menu should looks clear.
*    2-3. System should save the choise.
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test73_Setup_Add_SNMP {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test73_Setup_Add_SNMP(String browser) {
	  
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
    // driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }
    
  @Test  
  public void Setup_add_snmp() throws Exception {
	  
	  Log.startTestCase(this.getClass().getName());
  	  testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
  	  testFuncs.enterMenu(driver, "Setup_SNMP", "SNMP Information");
  	
  	  /**
  	  * Step 1: Check the headers of the menu
  	  */
  	  testFuncs.myDebugPrinting("Step 1: Check the headers of the menu", testVars.logerVars.MAJOR);
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyform']/div[1]/section[1]/div/div[1]/h3  ") 				, "SNMP Information");
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyform']/div[1]/section[1]/div/div[2]/label")				, "Contact:");
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyform']/div[1]/section[1]/div/div[3]/label")				, "Location:");
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyform']/div[1]/section[2]/div/div[1]/h3")   				, "Trap Community Name");
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyform']/div[1]/section[2]/div/div[2]/label")				, "Trap Community Name:");
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyform']/div[1]/section[2]/div/div[4]/label")				, "Traps Destinations:");
   	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyform']/div[1]/section[3]/div/div[1]/h3")					, "Accepted community names");
   	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyform']/div[1]/section[3]/div/div[2]/label")				, "Accepted community names:");
   	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyform']/div[1]/section[3]/div/div[2]/table/thead/tr/th[1]"), "Rights");
   	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyform']/div[1]/section[3]/div/div[2]/table/thead/tr/th[2]"), "Community");
   	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyform']/div[1]/section[3]/div/div[4]/div[1]/h3")			, "SNMP Trusted Manager");
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyform']/div[1]/section[3]/div/div[4]/div[2]/div[1]/label")	, "Accept SNMP packets from any host");
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyform']/div[1]/section[3]/div/div[4]/div[2]/div[2]/label")	, "Accept SNMP packets from these hosts");

	  /**
   	  * Step 2: Insert SNMP information and submit for every host
   	  */
   	  testFuncs.myDebugPrinting("Step 2: Insert SNMP information", testVars.logerVars.MAJOR);
   	  String contact  = "snmpContact"  + testFuncs.GenerateId();
   	  String loaction = "snmpLocation" + testFuncs.GenerateId();
	  testFuncs.mySendKeys(driver, By.xpath("//*[@id='contact']") 		   , contact ,  1000);   	  
	  testFuncs.mySendKeys(driver, By.xpath("//*[@id='location']")		   , loaction,  1000);   	  
	  testFuncs.mySendKeys(driver, By.xpath("//*[@id='TrapCommunityName']"), "pucblic", 1000);   	  
	  testFuncs.myClick(driver, By.xpath("//*[@id='parsleyform']/div[2]/div/button"), 5000);
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyform']/div[2]/div/span"), "* Submit will restart SNMP service");
  
	  /**
      * Step 3: Insert SNMP information and submit for specific hosts
      */
   	  testFuncs.myDebugPrinting("Step 3: Insert SNMP information", testVars.logerVars.MAJOR);
	  testFuncs.myClick(driver, By.xpath("//*[@id='optionsRadios2']"), 2000);
	  testFuncs.myClick(driver, By.xpath("//*[@id='parsleyform']/div[2]/div/button"), 5000);
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyform']/div[2]/div/span"), "* Submit will restart SNMP service"); 
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

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
* This test tests the network screen in Setup section via the SBA web
* -----------------
* Tests:
*    1. Check headers at the Network page
*    2. Enable the GE1 and verify that network connection is still active
*    3. Enable the GE2 and verify that network connection is still active
*    4. Try to Disable the connection of Main network adapter
*
* Results:
*    1.   All Headers should be detected
*    2-3. Newtork connection should stay active when GE1 or GE2 is up
*    4.   Try to disable the main Network connection, verify alert and Cancel the try
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test65_Setup_different_network_connections {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test65_Setup_different_network_connections(String browser) {
	  
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
  public void Setup_different_connections() throws Exception {
	    
	Log.startTestCase(this.getClass().getName());
	testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
	testFuncs.enterMenu(driver, "Setup_Network", "Network");
  
	// Step 1 - Check menu headers
	testFuncs.myDebugPrinting("Step 1 - Check menu headers", testVars.logerVars.MAJOR);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[1]/h3"), "Network");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[2]/div[1]/div/div/div[2]/div/div/span[1]")						  , "MICROSOFT NETWORK ADAPTER MULTIPLEXOR DRIVER #3");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[2]/div[1]/div/div/div[2]/div/div/span[3]")						  , "External team");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[2]/div[1]/div/div/div[2]/div/div/span[4]/table/tbody/tr/td[1]/span"), "External");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[2]/div[1]/div/div/div[3]/div/div/span[1]")						  , "MICROSOFT NETWORK ADAPTER MULTIPLEXOR DRIVER");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[2]/div[1]/div/div/div[3]/div/div/span[3]")						  , "Internal network");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[2]/div[1]/div/div/div[3]/div/div/span[4]/table/tbody/tr/td[1]/span"), "Internal");

//	// Step 2 - Enable the GE1 and verify that network connection is still active		
//	testFuncs.myDebugPrinting("Step 2 - Enable the GE1 and verify that network connection is still active", testVars.logerVars.MAJOR);
//	testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[2]/div[1]/div/div/div[2]/div/div/span[4]/table/tbody/tr/td[2]/i"), 5000);
//	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[2]/div[2]/div/div/div[1]/h4"), "Disable GE1");
//	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='en_dis_body']")													   , "Are you shure you want to disable the Intel(R) 82574L Gigabit Network Connection");
//	testFuncs.myClick(driver, By.xpath("//*[@id='en_dis_btn']"), 5000);
//	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[2]/div[1]/div/div/div[2]/div/div/span[4]/table/tbody/tr/td[2]/i"), "Disable");
//	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[2]/div[1]/div/div/div[4]/div/div/span[4]/table/tbody/tr/td[2]/i"), "Disable");
//	testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[2]/div[1]/div/div/div[2]/div/div/span[4]/table/tbody/tr/td[2]/i"), 10000);
//	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[2]/div[1]/div/div/div[2]/div/div/span[4]/table/tbody/tr/td[2]/i"), "Enable");
//	
//	// Step 3 - Enable the GE2 and verify that network connection is still active		
//	testFuncs.myDebugPrinting("Step 3 - Enable the GE2 and verify that network connection is still active", testVars.logerVars.MAJOR);
//	testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[2]/div[1]/div/div/div[3]/div/div/span[4]/table/tbody/tr/td[2]/i"), 10000);
//	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[2]/div[1]/div/div/div[3]/div/div/span[4]/table/tbody/tr/td[2]/i"), "Disable");
//	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[2]/div[1]/div/div/div[4]/div/div/span[4]/table/tbody/tr/td[2]/i"), "Disable");
//	testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[2]/div[1]/div/div/div[3]/div/div/span[4]/table/tbody/tr/td[2]/i"), 10000);
//	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[2]/div[1]/div/div/div[3]/div/div/span[4]/table/tbody/tr/td[2]/i"), "Enable");
//	
	// Step 4 - Try to disable the main Network connection, verify alert and Cancel the try
//	testFuncs.myDebugPrinting("Step 4 - Try to disable the main Network connection, verify alert and Cancel the try", testVars.logerVars.MAJOR);
//	testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[2]/div[1]/div/div/div[4]/div/div/span[4]/table/tbody/tr/td[2]/i"), 2000);
//	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[2]/div[2]/div/div/div[1]/h4"), "Disable Internal network");
//	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='en_dis_body']")													   , "Are you shure you want to disable the Microsoft Network Adapter Multiplexor Driver");
//	testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[2]/div[2]/div/div/div[3]/button[1]"), 2000);
//	testFuncs.myClick(driver, By.xpath("/html/body/div[1]/div[2]/div/div/div[3]/button")									  , 2000);
//	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[2]/div[1]/div/div/div[4]/div/div/span[4]/table/tbody/tr/td[2]/i"), "Disable");
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

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
* This test tests the timeout mechanism to the SBA web
* -----------------
* Tests:
*    1. Open unchanged data web page (Setup -> Network) for 4 minutes
*    2. Open another unchanged data web page (Tools -> SBA softwere) for 3 minutes
*    3. Open another unchanged data web page (Tools -> SBA services) for 6-7 minutes
* 
* Results:
*    1-2. User should not get disconnected from the system.
*      3. User should get disconnected from the system. 
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test66_Timeout {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test66_Timeout(String browser) {
	  
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
  public void timeout() throws Exception {
	  
	Log.startTestCase(this.getClass().getName());
	
	/**
	* Step 1: Open unchanged data web page (Setup -> Network) for 4 minutes
	*/
	testFuncs.myDebugPrinting("Step 1: Open unchanged data web page (Setup -> Network) for 4 minutes", testVars.logerVars.MAJOR);
	testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
	testFuncs.enterMenu(driver, "Setup_Network", "Network");
	for (int i = 0; i < getSeconds(6); ++i) {
		
		testFuncs.myWait(1000);
		testFuncs.myDebugPrinting("Waiting for " + i + " seconds", testVars.logerVars.MINOR);
	}
	testFuncs.searchStr(driver, "Network");
	
	/**
	* Step 2: Open another unchanged data web page (Tools -> SBA softwere) for 3 minutes
	*/
	testFuncs.myDebugPrinting("Step 2: Open another unchanged data web page (Tools -> SBA softwere) for 3 minutes", testVars.logerVars.MAJOR);
	testFuncs.enterMenu(driver, "Tools_SBA_softwere_upgrade", "SBA Upgrade");
	for (int i = 0; i < getSeconds(3); ++i) {
		
		testFuncs.myWait(1000);
		testFuncs.myDebugPrinting("Waiting for " + i + " seconds", testVars.logerVars.MINOR);
	}
	testFuncs.searchStr(driver, "SBA Upgrade");
	
	/**
	* Step 3: Open another unchanged data web page (Tools -> SBA services) for 6-7 minutes
	*/
	testFuncs.myDebugPrinting("Step 3: Open another unchanged data web page (Tools -> SBA services) for 6-7 minutes", testVars.logerVars.MAJOR);
	testFuncs.enterMenu(driver, "Tools_SBA_services", "SERVICES");	
	testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div[5]/div/div/div[2]/table/tbody/tr/td/button/span"), 2000);
	Alert alert = driver.switchTo().alert();
	testFuncs.myAssertTrue("Message was not detected (" + alert.getText() + ")", alert.getText().contains("Are you sure you want to restart the SBA?")); 
	alert.accept();
	testFuncs.myWait(2000);
	testFuncs.searchStr(driver, "Restarting please wait...");
	testFuncs.waitForString(driver, "Login to SBA Web Administration", 400000);
	testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
	testFuncs.enterMenu(driver, "Tools_SBA_services", "SERVICES");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[5]/div/div/div[2]/table/tbody/tr/td/button/span"), "Restart SBA");
  
  
  
  }
  
  // Get number of seconds
  private int getSeconds(int minutes) {
	  
	  int returnValue =  (minutes * 60) + 1;  
	  testFuncs.myDebugPrinting("return  - " + returnValue, testVars.logerVars.MINOR);
	  return returnValue;	
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

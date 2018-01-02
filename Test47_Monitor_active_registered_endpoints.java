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
* This test tests the active registered endpoints menu of the the SBA web
* -----------------
* Tests:
*    1. Verify headers.
*    2. Verify number of registered endpoints at More info menu.
* Results:
*    1. Headers should be detected successfully.
*    2. Number of registered endpoints should match.
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test47_Monitor_active_registered_endpoints {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test47_Monitor_active_registered_endpoints(String browser) {
	  
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
  public void Monitor_dashboard_active_registered_endpoints() throws Exception {
	   
	Log.startTestCase(this.getClass().getName());

	// Set vars and login
	String regisEndpointsNumber = "0"; 
	testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	  
	testFuncs.myWait(10000);
	
	/**
	* Step 1: Verify headers
	*/
	testFuncs.myDebugPrinting("Step 1: Verify headers", testVars.logerVars.MAJOR);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[3]/div[2]/div/div/span[1]"), "ACTIVE REGISTERED ENDPOINTS");
	String tempRegUsers = driver.findElement(By.xpath("//*[@id='contentwrapper']/section/div[3]/div[2]/div/div/span[2]")).getText();
	testFuncs.myAssertTrue("Number of registered endpoints dismatch !! (" + tempRegUsers + ")" , tempRegUsers.contains(regisEndpointsNumber));
	
	/**
	* Step 2: Verify number of registered endpoints at More info menu
	*/
	testFuncs.myDebugPrinting("Step 2: Verify number of registered endpoints at More info menu", testVars.logerVars.MAJOR);
	testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div[3]/div[2]/div/div/span[3]/a"), 2000);
	testFuncs.searchStr(driver, "Active Registered Endpoints");
	String tempRegEndPoints = driver.findElement(By.xpath("//*[@id='active_endpoints']/div[2]")).getText();
	testFuncs.myAssertTrue("Number of registered endpoints dismatch !! (" + tempRegEndPoints + ")", tempRegEndPoints.contains(regisEndpointsNumber));
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

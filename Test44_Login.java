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
* This test tests the login mechanism to the SBA web
* -----------------
* Tests:
*    1. Login to web with user in 'User' permit
*    2. Login to web with local user that belongs to RTCUniversalSBATechnicians group
* 
* Results:
*    1. Login should succeed
*    2. Login should not succeed
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test44_Login {
	
  public static int 	testIdx     = 0;
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test44_Login(String browser) {
	  
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
  public void Valid_login() throws Exception {
	  
	Log.startTestCase(String.valueOf(testIdx) + ". " + this.getClass().getName() + "  -  Valid_login");
	
	/**
	* Step 1: Login to web with user in 'User' permit
	*/
	testFuncs.myDebugPrinting("Step 1: Login to web with user in 'User' permit", testVars.logerVars.MAJOR);
	testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
	testIdx++;  
  }
  
  @Ignore
  @Test
  public void Invalid_login() throws Exception {
	  
	Log.startTestCase(String.valueOf(testIdx) + ". " + this.getClass().getName() + "  -  Invalid login");
	String invalidHeader = "The user name or password is incorrect";

	/**
	* Step 2: Login to web with local user that belongs to RTCUniversalSBATechnicians group
	*/
	testFuncs.myDebugPrinting("Step 2: Login to web with local user that belongs to RTCUniversalSBATechnicians group", testVars.logerVars.MAJOR);
	testFuncs.login(driver, testVars.getSysRTCUniUsername(), testVars.getSysRTCUniPassword(), testVars.getSbaInitStr(), invalidHeader);	
	testIdx++;
  }

  @After
  public void tearDown() throws Exception {
	  
//    driver.quit();
    System.clearProperty("webdriver.chrome.driver");
	System.clearProperty("webdriver.ie.driver");
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
    	
      testFuncs.myFail(verificationErrorString);
    }
  }
}

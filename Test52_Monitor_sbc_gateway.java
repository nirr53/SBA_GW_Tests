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
* This test tests the headers of the SBC -gateway page
* -----------------
* Tests:
*    1. Press the SBC button and verify that SBC - gateway is opened successfully
*    2. Verify headers
* 
* Results:
*    1. SBC page should be opened successfully
*    2. Hedares should be detected
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test52_Monitor_sbc_gateway {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test52_Monitor_sbc_gateway(String browser) {
	  
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
  public void Monitor_sbc_gateway() throws Exception {
	  
	Log.startTestCase(this.getClass().getName() + " - Headers + info links");
	testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
	testFuncs.myWait(2000);
	
	/**
	* Step 1: Press the SBC button and verify that SBC - gateway is opened successfully
	*/
	testFuncs.myDebugPrinting("Step 1: Press the SBC button and verify that SBC - gateway is opened successfully", testVars.logerVars.MAJOR);
	testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div[5]/div/h2/a"), 5000);
    for(String winHandle : driver.getWindowHandles()) {
    	
        driver.switchTo().window(winHandle);
    }
        
    /**
	* Step 2: Verify headers
	*/
	testFuncs.myDebugPrinting("Step 2: Verify headers", testVars.logerVars.MAJOR);
    testFuncs.searchStr(driver, "Basic");
    testFuncs.searchStr(driver, "Advanced");
    testFuncs.searchStr(driver, "System");
    testFuncs.searchStr(driver, "VoIP");
    String sbcIp = driver.getCurrentUrl();
    testFuncs.myAssertTrue("SBC ip is nor recognized !! <" + sbcIp + ">", sbcIp.contains(testVars.getSbcIp()));  
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

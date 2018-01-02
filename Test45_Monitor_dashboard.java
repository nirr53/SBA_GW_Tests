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
* This test tests the headers of the Monitor-Dashboard on the the SBA web
* -----------------
* Tests:
*    1. Verify headers
*    2. Check More-info links
*    3. Test Fax server link
*    4. Test Auto attendant IVR link
*    5. Test Read-more link
* 
* Results:
*    1-5. All headers should be detected
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test45_Monitor_dashboard {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test45_Monitor_dashboard(String browser) {
	  
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
  public void Monitor_dashboard_headers_info_links() throws Exception {
	  
	Log.startTestCase(this.getClass().getName() + " - Headers + info links");
	testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
	
	/**
	* Step 1: Verify headers of Monitors section
	*/
	testFuncs.myDebugPrinting("Step 1: Verify headers of Monitors section", testVars.logerVars.MAJOR);
	testFuncs.myClick(driver, By.xpath("//*[@id='navbar-collapse']/ul[1]/li[2]/a"), 2000);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='left-nav']/ul/li[1]/a")      			  , "DASHBOARD");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='left-nav']/ul/li[1]/ul/li/a")			  , "Dashboard");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='left-nav']/ul/li[2]/a/span") 			  , "PERFORMANCE");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[3]/div[1]/div[2]/div/span[1]"), "ACTIVE REGISTERED USERS");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[3]/div[2]/div/div/span[1]")   , "ACTIVE REGISTERED ENDPOINTS");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[4]/div[1]/div/div[1]/p")		, "Outbound Calls");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[4]/div[2]/div/div[1]/p")		, "Inbound Calls");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[4]/div[3]/div/div[1]/p")		, "CPU Utilization");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[4]/div[4]/div/div[1]/p")		, "Available MBytes");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[2]/div[4]/address/strong")	, testVars.getVersions()[0]);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[2]/div[5]/address/strong")	, testVars.getVersions()[1]);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[2]/div[6]/address/strong")	, testVars.getVersions()[2] + " / " + testVars.getVersions()[3]);
	
	/**
	* Step 2: Check More-info links
	*/
	testFuncs.myDebugPrinting("Step 2: Check More-info links", testVars.logerVars.MAJOR);
	
	// Active registered-users info
	testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div[3]/div[1]/div[2]/div/span[3]/a"), 2000);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[3]/div[2]/div/div[1]/div/div[1]/h3"), "Active Registered Users");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[3]/div[2]/div/div[2]/div/div[1]/h3"), "Active Registered Endpoints");
	testFuncs.myClick(driver, By.xpath("/html/body/div/header/a/img"), 2000);

	// Active registered-users-endpoints info
	testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div[3]/div[2]/div/div/span[3]/a"), 2000);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[3]/div[2]/div/div[1]/div/div[1]/h3"), "Active Registered Users");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[3]/div[2]/div/div[2]/div/div[1]/h3"), "Active Registered Endpoints");
	testFuncs.myClick(driver, By.xpath("/html/body/div/header/a/img"), 2000);
	
	// Active outbound-calls info
	testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div[4]/div[1]/div/a"), 2000);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[3]/div[1]/h3")					   , "Current Calls");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[3]/div[2]/div/div[2]/div/div[1]/h3") , "Inbound Calls - current");
	testFuncs.myClick(driver, By.xpath("/html/body/div/header/a/img"), 2000);

	// Active inbound-calls info
	testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div[4]/div[2]/div/a"), 2000);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[3]/div[1]/h3")					   , "Current Calls");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[3]/div[2]/div/div[2]/div/div[1]/h3") , "Inbound Calls - current");
	testFuncs.myClick(driver, By.xpath("/html/body/div/header/a/img"), 2000);

	// Active CPU-utilization info
	testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div[4]/div[3]/div/a"), 2000);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[3]/div[1]/h3")					  , "General Server Health: Monitor on all Servers - Processor, Disk, Memory and Network");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[3]/div[2]/div/div[1]/div/div[1]/h3"), "Available Mbytes");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[3]/div[2]/div/div[2]/div/div[1]/h3"), "CPU Utilization");
	testFuncs.myClick(driver, By.xpath("/html/body/div/header/a/img"), 2000);

	// Active Avaliblae-MegaBytes info
	testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div[4]/div[4]/div/a"), 2000);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[3]/div[1]/h3")					  , "General Server Health: Monitor on all Servers - Processor, Disk, Memory and Network");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[3]/div[2]/div/div[1]/div/div[1]/h3"), "Available Mbytes");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[3]/div[2]/div/div[2]/div/div[1]/h3"), "CPU Utilization");
	testFuncs.myClick(driver, By.xpath("/html/body/div/header/a/img"), 2000);
  }
  
  @Test
  public void Monitor_dashboard_headers_fax_server_link() throws Exception {
	  
	Log.startTestCase(this.getClass().getName() + " - Fax server link");
	testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
	
	/**
	* Step 3: Test Fax server link
	*/
	testFuncs.myDebugPrinting("Step 3: Test Fax server link", testVars.logerVars.MAJOR);
	testFuncs.myClick(driver, By.xpath("//*[@id='thesidebar']/aside/section[2]/div/h5[1]/boldy/a"), 7000);
    for(String winHandle : driver.getWindowHandles()) {
    	
        driver.switchTo().window(winHandle);
    }
    testFuncs.searchStr(driver, "Voice Applications");
    testFuncs.searchStr(driver, "AudioCodes Fax Server");
  }
  
  @Test
  public void Monitor_dashboard_headers_auto_attendant_ivr_link() throws Exception {
	  
	Log.startTestCase(this.getClass().getName() + " - Auto attendant IVR link");
	testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
	
	/**
	* Step 4: Test Auto attendant IVR link
	*/
	testFuncs.myDebugPrinting("Step 4: Test Auto attendant IVR link", testVars.logerVars.MAJOR);
	testFuncs.myClick(driver, By.xpath("//*[@id='thesidebar']/aside/section[2]/div/h5[2]/boldy/a"), 7000);
    for(String winHandle : driver.getWindowHandles()) {
    	
        driver.switchTo().window(winHandle);
    }
    testFuncs.searchStr(driver, "AudioCodes Products");
  }
  
  @Test
  public void Monitor_dashboard_headers_auto_attendant_read_more_link() throws Exception {
	  
	Log.startTestCase(this.getClass().getName() + " - Read more link");
	testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
	
	/**
	* Step 5: Test Read-more link
	*/
	testFuncs.myDebugPrinting("Test Read-more link", testVars.logerVars.MAJOR);
	testFuncs.myClick(driver, By.xpath("//*[@id='thesidebar']/aside/section[2]/div/h5[3]/a"), 7000);
    for(String winHandle : driver.getWindowHandles()) {
    	
        driver.switchTo().window(winHandle);
    }
    testFuncs.searchStr(driver, "AudioCodes Global Services");
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

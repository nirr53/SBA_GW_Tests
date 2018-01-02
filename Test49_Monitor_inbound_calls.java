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
* This test tests the inbound calls data of the the SBA web
* -----------------
* Tests:
*    1. Verify headers.
*    2. Verify data of inbound calls at More info menu.
* Results:
*    1. Headers should be detected successfully.
*    2. Number of outbound calls should match.
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test49_Monitor_inbound_calls {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test49_Monitor_inbound_calls(String browser) {
	  
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
  public void Monitor_dashboard_inbound_calls() throws Exception {
	   
	Log.startTestCase(this.getClass().getName());
	
	// Set vars and login
	String inboundCalls  = "0"; 
	testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	  

	/**
	* Step 1: Verify headers
	*/
	testFuncs.myDebugPrinting("Step 1: Verify headers", testVars.logerVars.MAJOR);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[4]/div[2]/div/div[1]/p"), "Inbound Calls");
	testFuncs.myWait(5000);
	String tempInboundCalls = driver.findElement(By.xpath("//*[@id='contentwrapper']/section/div[4]/div[2]/div/div[1]/h3")).getText();
	testFuncs.myAssertTrue("Number of outbound calls dismatch !! (" + tempInboundCalls + ")", tempInboundCalls.contains(inboundCalls));
	
	/**
	* Step 2: Verify number of outbound calls at More info menu
	*/
	testFuncs.myDebugPrinting("Step 2: Verify number of outbound calls at More info menu", testVars.logerVars.MAJOR);
	testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div[4]/div[2]/div/a"), 7000);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[3]/div[1]/h3"), "Current Calls");
	testFuncs.searchStr(driver, "Inbound Calls - current");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[5]/div[1]/h3"), "Inbound Calls Counters");	
	testFuncs.searchStr(driver, "Inbound Established Calls");
	testFuncs.searchStr(driver, "Inbound Attempts Calls");
	testFuncs.searchStr(driver, "Inbound Rejected Calls");
	testFuncs.searchStr(driver, "Inbound Invites Calls");
	testFuncs.searchStr(driver, "Active Inbound Priority Calls");
	testFuncs.searchStr(driver, "Active Inbound Media Bypass Calls");
	testFuncs.searchStr(driver, "Inbound Media Bypass Calls");
	
	// Test Current Calls graphs
	testFuncs.myDebugPrinting("Test Current Calls graphs", testVars.logerVars.NORMAL);
	tempInboundCalls = driver.findElement(By.xpath("//*[@id='current_inbound_calls']/div[2]")).getText();
	testFuncs.myAssertTrue("Number of calls dismatch !! (" + tempInboundCalls + ")", tempInboundCalls.contains(inboundCalls));
	tempInboundCalls = driver.findElement(By.xpath("//*[@id='current_outbound_calls']/div[2]")).getText();
	testFuncs.myAssertTrue("Number of calls dismatch !! (" + tempInboundCalls + ")", tempInboundCalls.contains(inboundCalls));
    
	// Test Inbound Calls Counters
	testFuncs.myDebugPrinting("Test Inbound Calls Counters", testVars.logerVars.NORMAL);
	tempInboundCalls = driver.findElement(By.xpath("//*[@id='total_inbound_established_calls']/div[2]")).getText();
	testFuncs.myAssertTrue("Number of calls dismatch !! (" + tempInboundCalls + ")", tempInboundCalls.contains(inboundCalls));
	tempInboundCalls = driver.findElement(By.xpath("//*[@id='total_inbound_attempts_calls']/div[2]")).getText();
	testFuncs.myAssertTrue("Number of calls dismatch !! (" + tempInboundCalls + ")", tempInboundCalls.contains(inboundCalls));
	tempInboundCalls = driver.findElement(By.xpath("//*[@id='total_inbound_rejected_calls']/div[2]")).getText();
	testFuncs.myAssertTrue("Number of calls dismatch !! (" + tempInboundCalls + ")", tempInboundCalls.contains(inboundCalls));
	tempInboundCalls = driver.findElement(By.xpath("//*[@id='total_inbound_invites_calls']/div[2]")).getText();
	testFuncs.myAssertTrue("Number of calls dismatch !! (" + tempInboundCalls + ")", tempInboundCalls.contains("..."));
	tempInboundCalls = driver.findElement(By.xpath("//*[@id='active_inbound_priority_calls']/div[2]")).getText();
	testFuncs.myAssertTrue("Number of calls dismatch !! (" + tempInboundCalls + ")", tempInboundCalls.contains(inboundCalls));
	tempInboundCalls = driver.findElement(By.xpath("//*[@id='active_inbound_media_bypass_calls']/div[2]")).getText();
	testFuncs.myAssertTrue("Number of calls dismatch !! (" + tempInboundCalls + ")", tempInboundCalls.contains(inboundCalls));
	tempInboundCalls = driver.findElement(By.xpath("//*[@id='total_inbound_media_bypass_calls']/div[2]")).getText();
	testFuncs.myAssertTrue("Number of calls dismatch !! (" + tempInboundCalls + ")", tempInboundCalls.contains(inboundCalls));
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

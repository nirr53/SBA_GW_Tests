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
* This test tests the headers & graphs of the Key health indicators menu
* -----------------
* Tests:
*    1. Check headers
*    2. Press the Off button
*    3. Press the On Button
* 
* Results:
*    1.   Key health indicators menu should be opened successfully and all headers should looks ok
*    2-3. The bahavior of the data should be according to the state of the button
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test61_Monitor_key_health_indicators {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test61_Monitor_key_health_indicators(String browser) {
	  
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
  public void Monitor_key_health_indicators() throws Exception {
	  
	Log.startTestCase(this.getClass().getName());
	testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
	testFuncs.myWait(2000);
	testFuncs.enterMenu(driver, "Monitor_Key_health_indicators", "General Server Health");
	
	/**
	* Step 1: Check headers
	*/
	testFuncs.myDebugPrinting("Step 1: Check headers", testVars.logerVars.MAJOR);

	// Section 1
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[3]/div[1]/h3"), "General Server Health: Monitor on all Servers - Processor, Disk, Memory and Network");
	testFuncs.searchStr(driver, "Available Mbytes");
	testFuncs.searchStr(driver, "CPU Utilization");
	testFuncs.searchStr(driver, "Disk sec/Read");
	testFuncs.searchStr(driver, "Disk sec/Write");
	
	// Section 2
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[4]/div[1]/h3"), "SQL Server: Monitor on all Back-end and Front-end Servers");
	testFuncs.searchStr(driver, "MSSQL RTC Page life expectancy");
	testFuncs.searchStr(driver, "MSSQL LYNC Page life expectancy");
	
	// Section 3
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[5]/div[1]/h3"), "Front-end Server: Monitor on all Front-end Servers");
	testFuncs.searchStr(driver, "Database - Queue Latency (msec)");
	testFuncs.searchStr(driver, "Database - Sproc Latency (msec)");
	testFuncs.searchStr(driver, "Database - Throttled requests/sec");
	testFuncs.searchStr(driver, "REG Database - Queue Latency (msec)");
	testFuncs.searchStr(driver, "REG Database - Sproc Latency (msec)");
	testFuncs.searchStr(driver, "REG Database - Throttled requests/sec");
	testFuncs.searchStr(driver, "Shared Database - Queue Latency (msec)");
	testFuncs.searchStr(driver, "Shared Database - Sproc Latency (msec)");
	testFuncs.searchStr(driver, "Shared Database - Throttled requests/sec");
	        
    /**
	* Step 2: Press the Off button
	*/
	testFuncs.myDebugPrinting("Step 2: Press the Off button", testVars.logerVars.MAJOR);
	testFuncs.myClick(driver, By.xpath("//*[@id='realtime']/button[2]"), 5000);
	String    initValue = driver.findElement(By.xpath("//*[@id='avg_disk_sec_write']/div[2]")).getText();
	for (int i = 0 ; i < 50; ++ i) {
	
		String currValue = driver.findElement(By.xpath("//*[@id='avg_disk_sec_write']/div[2]")).getText();
		testFuncs.myDebugPrinting(i + ". currValue -  " + currValue + "   initValue -  " + initValue, testVars.logerVars.MINOR);
		testFuncs.myAssertTrue(i + ". Values not match. Off button not works\ninitValue -" + initValue + "\ncurrValue - " + currValue, currValue.equals(initValue));
		testFuncs.myWait(1000);
	}
	
    /**
	* Step 3: Press the On button
	*/
	testFuncs.myDebugPrinting("Step 3: Press the On button", testVars.logerVars.MAJOR);
	testFuncs.myClick(driver, By.xpath("//*[@id='realtime']/button[1]"), 5000);
	initValue = driver.findElement(By.xpath("//*[@id='avg_disk_sec_write']/div[2]")).getText();
	int i = 0;
	for (i = 0 ; i < 50; ++ i) {
	
		String currValue = driver.findElement(By.xpath("//*[@id='avg_disk_sec_write']/div[2]")).getText();
		testFuncs.myDebugPrinting(i + ". currValue -  " + currValue + "   initValue -  " + initValue, testVars.logerVars.MINOR);
		if (!currValue.equals(initValue)) {
			
			testFuncs.myDebugPrinting(i + ". Dismatch was found. On button do works.", testVars.logerVars.MINOR);
			break;	
		}
		testFuncs.myWait(1000);
	}
	testFuncs.myAssertTrue("Dismatch was NOT found. On button do not works.", i < 100);
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

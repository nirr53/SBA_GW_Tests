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
* This test tests the memory graphs of the SBA system
* -----------------
* Tests:
*    1. Check that memory-usage is displayed on main screen
*    2. Check more info tables and data
* 
* Results:
*    1. Memory should displayed at main menu
*    2. All memory usgae tables should be displayed properly
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test51_Monitor_memory {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test51_Monitor_memory(String browser) {
	  
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
  public void Monitor_memory() throws Exception {
	  
	Log.startTestCase(this.getClass().getName());
	testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
	testFuncs.myWait(30000);
	
	/**
	* Step 1: Check memory-usage at main screen
	*/
	testFuncs.myDebugPrinting("Step 1: Check memory-usage at main screen", testVars.logerVars.MAJOR);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[4]/div[4]/div/div[1]/p"), "Available MBytes");
	int mbs = Integer.valueOf(driver.findElement(By.xpath("//*[@id='contentwrapper']/section/div[4]/div[4]/div/div[1]/h3")).getText());
	testFuncs.myAssertTrue("Avalible Mbs are not displayed !\nmbs - " + mbs, mbs > 0);
		        
    /**
	* Step 2: Check more info tables and data
	*/
	testFuncs.myDebugPrinting("Step 2: Check more info tables and data", testVars.logerVars.MAJOR);
	testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div[4]/div[4]/div/a"), 5000);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[3]/div[1]/h3"), "General Server Health: Monitor on all Servers - Processor, Disk, Memory and Network");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[4]/div[1]/h3"), "SQL Server: Monitor on all Back-end and Front-end Servers");	
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[5]/div[1]/h3"), "Front-end Server: Monitor on all Front-end Servers");
	testFuncs.searchStr(driver, "Available Mbytes");
	testFuncs.searchStr(driver, "CPU Utilization");
	testFuncs.searchStr(driver, "Avg. Disk sec/Read");
	testFuncs.searchStr(driver, "Avg. Disk sec/Write");
	testFuncs.searchStr(driver, "MSSQL RTC Page life expectancy");
	testFuncs.searchStr(driver, "MSSQL LYNC Page life expectancy");
	testFuncs.searchStr(driver, "Database - Queue Latency (msec)");
	testFuncs.searchStr(driver, "Database - Sproc Latency (msec)");
	testFuncs.searchStr(driver, "Database - Throttled requests/sec");
	testFuncs.searchStr(driver, "REG Database - Queue Latency (msec)");
	testFuncs.searchStr(driver, "REG Database - Sproc Latency (msec)");
	testFuncs.searchStr(driver, "REG Database - Throttled requests/sec");
	testFuncs.searchStr(driver, "Shared Database - Queue Latency (msec)");
	testFuncs.searchStr(driver, "Shared Database - Sproc Latency (msec)");
	testFuncs.searchStr(driver, "Shared Database - Throttled requests/sec"); 
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

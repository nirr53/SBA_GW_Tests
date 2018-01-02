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
* This test tests the CPU graphs of the SBA system
* -----------------
* Tests:
*    1. Check that cpu is displayed on main screen
*    2. Check more info tables and data
* 
* Results:
*    1. CPU should displayed at main menu
*    2. All CPU should be displayed properly
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test50_Monitor_cpu {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test50_Monitor_cpu(String browser) {
	  
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
  public void Monitor_memory() throws Exception {
	  
	Log.startTestCase(this.getClass().getName());
	testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
	testFuncs.myWait(10000);
	
	/**
	* Step 1: Check CPU at main screen
	*/
	testFuncs.myDebugPrinting("Step 1: Check CPU at main screen", testVars.logerVars.MAJOR);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[4]/div[3]/div/div[1]/p"), "CPU Utilization");	
	String cpuStr = driver.findElement(By.xpath("//*[@id='contentwrapper']/section/div[4]/div[3]/div/div[1]/h3")).getText();
	cpuStr = cpuStr.substring(0, cpuStr.length()-1);	
	int cpuInt = Integer.valueOf(cpuStr);
	testFuncs.myAssertTrue("Avalible CPU is not displayed !\ncpuInt - " + cpuInt, cpuInt < 100);
		        
    /**
	* Step 2: Check more info tables and data
	*/
	testFuncs.myDebugPrinting("Step 2: Check more info tables and data", testVars.logerVars.MAJOR);
	testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div[4]/div[3]/div/a"), 5000);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[3]/div[1]/h3"), "General Server Health: Monitor on all Servers - Processor, Disk, Memory and Network");
	testFuncs.searchStr(driver, "Available Mbytes");
	testFuncs.searchStr(driver, "CPU Utilization");
	testFuncs.searchStr(driver, "Disk sec/Read");
	testFuncs.searchStr(driver, "Disk sec/Write");
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

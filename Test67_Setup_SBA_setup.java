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
* This test tests the SBA Setup page via the SBA web
* -----------------
* Tests:
*    1. Check headers of the page & success icons
*    2. Test repair of different components
*    3. Test Logs of different components
*
* Results:
*    1. All Headers + icons should be detected
*    2. All Repair actions should end successfully
*    3. All Log menus should be opend successfully
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test67_Setup_SBA_setup {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test67_Setup_SBA_setup(String browser) {
	  
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

  @Ignore
  @Test
  public void Setup_SBA_setup_headers_check() throws Exception {
	    
	Log.startTestCase(this.getClass().getName());
	testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
	testFuncs.enterMenu(driver, "Setup_SBA_setup", "SBA Setup");
	testFuncs.myWait(20000);
  
	// Step 1 - Check menu headers
	testFuncs.myDebugPrinting("Step 1 - Check menu headers", testVars.logerVars.MAJOR);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[1]/div/div/div[1]/h3")						  ,	"SBA Setup");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[1]/tr/td[1]/span"), "RTC Local Database (MSSQL$RTCLOCAL)");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[2]/tr/td[1]/span"), "Local Database (MSSQL$LYNCLOCAL)");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[3]/tr/td[1]/span"), "Skype for Business Server 2015, Core Components");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[4]/tr/td[1]/span"), "Skype for Business Server 2015, Front End Server");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[5]/tr/td[1]/span"), "Skype for Business Server 2015, Mediation Server");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[2]/div/div/div[1]/h3")						  , "Activation");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[2]/div/div/div[2]/table/tbody[1]/tr/td[3]/span"), "Install lyss database");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[2]/div/div/div[2]/table/tbody[2]/tr/td[3]/span"), "Install registrar database");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[2]/div/div/div[2]/table/tbody[3]/tr/td[3]/span"), "Initialize local configuration store");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[2]/div/div/div[2]/table/tbody[4]/tr/td[3]/span"), "Add the SBA to the replication path");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[2]/div/div/div[2]/table/tbody[5]/tr/td[3]/span"), "Enable SBA services and server roles");

	// Check success icon
	for (int i = 1; i <= 5; ++ i) {
		
		testFuncs.myDebugPrinting(i + ". Check success icon", testVars.logerVars.MINOR);
		WebElement elem = driver.findElement(By.xpath("//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[" +  String.valueOf(i) + "]/tr/td[1]/i"));
		testFuncs.myAssertTrue("Icon success was not detected !!", elem.getAttribute("class").contains("successColor"));
		testFuncs.myWait(15000);
	}
	for (int i = 1; i <= 5; ++ i) {
		
		testFuncs.myDebugPrinting(i + ". Check success icon", testVars.logerVars.MINOR);
		WebElement elem = driver.findElement(By.xpath("//*[@id='contentwrapper']/section/div[2]/div/div/div[2]/table/tbody[" + String.valueOf(i) + "]/tr/td[1]/i"));
		testFuncs.myAssertTrue("Icon success was not detected !! " + elem.getAttribute("class"), elem.getAttribute("class").contains("successColor"));
		testFuncs.myWait(15000);
	}	
  }
    
  @Test  
  public void Setup_SBA_setup_logs_mechnism_check() throws Exception {
	  
	  Log.startTestCase(this.getClass().getName());
  	  testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
  	  testFuncs.enterMenu(driver, "Setup_SBA_setup", "SBA Setup");
  	  testFuncs.myWait(120000);
      String winHandleBefore = driver.getWindowHandle();
      testFuncs.myDebugPrinting("Step 3 - Check Log menus", testVars.logerVars.MAJOR);
      
      // Open Logs of RTC Local Database (MSSQL$RTCLOCAL)
      testFuncs.myDebugPrinting("Check Logs of RTC Local Database (MSSQL$RTCLOCAL)", testVars.logerVars.MINOR);
      testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[1]/tr/td[4]/button[2]"), 10000);
  	  
      // Open Logs of Local Database (MSSQL$LYNCLOCAL)
      testFuncs.myDebugPrinting("Open Logs of Local Database (MSSQL$LYNCLOCAL)", testVars.logerVars.MINOR);
      testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[2]/tr/td[4]/button[2]"), 10000);
  
      // Open Logs of Skype for Business Server 2015, Core Components 
      testFuncs.myDebugPrinting("Open Logs of Skype for Business Server 2015, Core Components", testVars.logerVars.MINOR);
      testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[3]/tr/td[4]/button[2]"), 10000);
  	  
      // Open Logs of Skype for Business Server 2015, Front End Server
      testFuncs.myDebugPrinting("Open Logs of Skype for Business Server 2015, Front End Server", testVars.logerVars.MINOR);
      testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[4]/tr/td[4]/button[2]"), 10000);
      
      // Open Logs of Skype for Business Server 2015, Mediation Server
      testFuncs.myDebugPrinting("Open Logs of Skype for Business Server 2015, Mediation Server", testVars.logerVars.MINOR);
      testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[5]/tr/td[4]/button[2]"), 10000);
      
      // Check the opened windows
      String[] logNames = {"mediationServer", "server", "ocsCore", "LyncLocalDb", "RtcLocalDb"};
      java.util.Set<java.lang.String> windowHandles = driver.getWindowHandles();
      int i = 0;
      if ((windowHandles.size() - 1) == logNames.length) {
    	  
	      for (String window : windowHandles) {
	    	  
	          if(!window.equals(winHandleBefore)) {
	        	  
	              driver.switchTo().window(window);
	              testFuncs.searchStr(driver, logNames[i++]);
	          }
	          driver.switchTo().window(winHandleBefore); 
	      }
      } else {
    	  
          testFuncs.myDebugPrinting("windowHandles.size() - " + (windowHandles.size() - 1), testVars.logerVars.MINOR);
          testFuncs.myDebugPrinting("logNames.length - " 	  + logNames.length     , testVars.logerVars.MINOR);
    	  testFuncs.myFail("windowHandles.size() and logNames.length are not match !!");
      }
  }  
  
  @Test
  public void Setup_SBA_setup_repair_mechnism_check() throws Exception {
	  
	  Log.startTestCase(this.getClass().getName());
	  testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
	  testFuncs.enterMenu(driver, "Setup_SBA_setup", "SBA Setup");
  	  testFuncs.myWait(120000);
	  
	  // Test repair of RTC Local Database (MSSQL$RTCLOCAL)
	  testFuncs.myDebugPrinting("Test repair of RTC Local Database (MSSQL$RTCLOCAL)", testVars.logerVars.MINOR);
	  testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[1]/tr/td[4]/button[1]/span"), 200000);
	  testFuncs.myAssertTrue("Error occured durong a repair", !driver.findElement(By.xpath("//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[1]/tr/td[5]/span")).getText().contains("error"));
	  
	  // Test repair of Local Database (MSSQL$LYNCLOCAL)
	  testFuncs.myDebugPrinting("Test repair of Local Database (MSSQL$LYNCLOCAL)", testVars.logerVars.MINOR);
	  testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[2]/tr/td[4]/button[1]/span"), 200000);
	  testFuncs.myAssertTrue("Error occured durong a repair", !driver.findElement(By.xpath("//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[2]/tr/td[5]/span")).getText().contains("error"));
	  
	  // Test repair of Skype for Business Server 2015, Core Components	
	  testFuncs.myDebugPrinting("Test repair of Skype for Business Server 2015, Core Components", testVars.logerVars.MINOR);
	  testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[3]/tr/td[4]/button[1]/span"), 200000);
	  testFuncs.myAssertTrue("Error occured durong a repair", !driver.findElement(By.xpath("//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[3]/tr/td[5]/span")).getText().contains("error"));
	  
	  // Test repair of Skype for Business Server 2015, Front End Server
	  testFuncs.myDebugPrinting("Test repair of Skype for Business Server 2015, Front End Server", testVars.logerVars.MINOR);
	  testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[4]/tr/td[4]/button[1]/span"), 200000);
	  testFuncs.myAssertTrue("Error occured durong a repair", !driver.findElement(By.xpath("//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[4]/tr/td[5]/span")).getText().contains("error"));
	  
	  // Test repair of Skype for Business Server 2015, Mediation Server
	  testFuncs.myDebugPrinting("Test repair of Skype for Business Server 2015, Mediation Server", testVars.logerVars.MINOR);
	  testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[5]/tr/td[4]/button[1]/span"), 200000);
	  testFuncs.myAssertTrue("Error occured durong a repair", !driver.findElement(By.xpath("//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[5]/tr/td[5]/span")).getText().contains("error")); 
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

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
* This test tests the toolbar of the the SBA web
* -----------------
* Tests:
*    1.   Verify headers of the toolbar
*    2.   Check Info section
*    3.   Check SBA Info button
*    4.   Check that Help section is opened
*    5.   Verify headers of Setup section
*    6.   Verify headers of Tools section
*    7.   Verify headers of Monitors section
*    8.   Test Collapse and Expend button
*    9.   Test Skype 2015 button
*    10.  Test Logout method
* 
* Results:
*    1-10. All headers should be detected and work
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test110_Toolbar {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test110_Toolbar(String browser) {
	  
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
  public void Toolbar_headers_info() throws Exception {
	  
	Log.startTestCase(this.getClass().getName() + " - Headers + info");
	testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
	
	/**
	* Step 1: Verify headers of the toolbar
	*/
	testFuncs.myDebugPrinting("Step 1: Verify headers of the toolbar", testVars.logerVars.MAJOR);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='navbar-collapse']/ul[1]/li[1]/a")		  , "SETUP");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='navbar-collapse']/ul[1]/li[2]/a")		  , "MONITOR");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='navbar-collapse']/ul[1]/li[3]/a")		  , "TOOLS");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='navbar-collapse']/ul[2]/li/a")   		  , "SKYPE FOR BUSINESS 2015");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='navbar-collapse']/ul[3]/li[2]/a")		  , testVars.getSbaHostName());
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='left-nav']/ul/li[1]/a")      			  , "DASHBOARD");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='left-nav']/ul/li[1]/ul/li/a")			  , "Dashboard");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='left-nav']/ul/li[2]/a/span") 			  , "PERFORMANCE");
	
	// Check main headers
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[2]/div[1]") , "Server Name");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[2]/div[2]") , "Domain");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[2]/div[3]") , "Last Boot UpTime");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[2]/div[4]") , "OS Version");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[2]/div[5]") , "SBA Version");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[2]/div[6]") , "Web/Config Versions\n" + testVars.getVersions()[2] + " / " + testVars.getVersions()[3]);
	
	// Check SBC/Gateway headers
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[5]/div/h2") , "SBC/Gateway");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[8]/div[1]") , "Product Name");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[8]/div[2]") , "Product Version");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[8]/div[3]") , "IP Address");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[8]/div[4]") , "Serial Number");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[8]/div[5]") , "MAC Address");

	// Check alarms
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[9]/div/table/tbody/tr/td[3]/table/tbody/tr/td/div/label") , "SBC Alarms");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[9]/div/table/tbody/tr[1]/td[3]/table/tbody/tr/td/div/table/thead/tr/th[1]"), "Description");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[9]/div/table/tbody/tr[1]/td[3]/table/tbody/tr/td/div/table/thead/tr/th[2]"), "ID");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[9]/div/table/tbody/tr[2]/td/table/tbody/tr/td/div/label") 				 , "SBA Alarms");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[9]/div/table/tbody/tr[2]/td/table/tbody/tr/td/div/table/thead/tr/th[1]")   , "Name");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[9]/div/table/tbody/tr[2]/td/table/tbody/tr/td/div/table/thead/tr/th[2]")   , "Severity");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[9]/div/table/tbody/tr[2]/td/table/tbody/tr/td/div/table/thead/tr/th[3]")   , "Description");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[9]/div/table/tbody/tr[2]/td/table/tbody/tr/td/div/table/thead/tr/th[4]")   , "Source");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[9]/div/table/tbody/tr[2]/td/table/tbody/tr/td/div/table/thead/tr/th[5]")   , "Time");
	
	/**
	* Step 2: Check Info section
	*/
	testFuncs.myDebugPrinting("Step 2: Check Info section", testVars.logerVars.MAJOR);
	testFuncs.myClick(driver, By.xpath("//*[@id='navbar-collapse']/ul[3]/li[4]/a")		   , 3000); 
	testFuncs.myClick(driver, By.xpath("//*[@id='navbar-collapse']/ul[3]/li[4]/ul/li[1]/a"), 3000); 	
	testFuncs.verifyStrBy(driver, By.xpath("/html/body/div[1]/div[2]/div/div/div[1]/h4/span") , "System Info");
	String text = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div/div[2]/textarea")).getText();
	testFuncs.myAssertTrue("Header was not detected !! <" + "Domain: cloudbond365B.com" 					   + ">", text.contains("Domain: cloudbond365B.com"));
	testFuncs.myAssertTrue("Header was not detected !! <" + "Last Boot UpTime:" 							   + ">", text.contains("Last Boot UpTime:"));
	testFuncs.myAssertTrue("Header was not detected !! <" + "BuildNumber:" 									   + ">", text.contains("BuildNumber:"));
	testFuncs.myAssertTrue("Header was not detected !! <" + "Name: "               + testVars.getSysUsername() + ">", text.contains("Name: "               + testVars.getSysUsername()));
	testFuncs.myAssertTrue("Header was not detected !! <" + "DNS Host Name: "      + testVars.getSbaHostName() + ">", text.contains("DNS Host Name: "      + testVars.getSbaHostName()));
	testFuncs.myAssertTrue("Header was not detected !! <" + "Version: "            + testVars.getVersions()[0] + ">", text.contains("Version: "            + testVars.getVersions()[0]));
	testFuncs.myAssertTrue("Header was not detected !! <" + "SBA Version: "        + testVars.getVersions()[1] + ">", text.contains("SBA Version: "        + testVars.getVersions()[1]));
	testFuncs.myAssertTrue("Header was not detected !! <" + "Web Admin Version: "  + testVars.getVersions()[2] + ">", text.contains("Web Admin Version: "  + testVars.getVersions()[2]));
	testFuncs.myAssertTrue("Header was not detected !! <" + "SBA Config Version: " + testVars.getVersions()[3] + ">", text.contains("SBA Config Version: " + testVars.getVersions()[3]));
	testFuncs.myClick(driver, By.xpath("/html/body/div[1]/div[2]/div/div/div[3]/button"), 3000);
	
	/**
	* Step 3: Check SBA Info button
	*/
	testFuncs.myDebugPrinting("Step 3: Check SBA Info button", testVars.logerVars.MAJOR);
	testFuncs.myClick(driver, By.xpath("//*[@id='navbar-collapse']/ul[3]/li[2]/a")		   , 3000); 
	testFuncs.verifyStrBy(driver, By.xpath("/html/body/div[1]/div[2]/div/div/div[1]/h4/span") , "System Info");
	testFuncs.myClick(driver, By.xpath("/html/body/div[1]/div[2]/div/div/div[3]/button"), 3000); 	
	
	/**
	* Step 4: Check that Help section is opened
	*/
	testFuncs.myDebugPrinting("Step 4: Check that Help section is opened", testVars.logerVars.MAJOR);
	testFuncs.myClick(driver, By.xpath("//*[@id='navbar-collapse']/ul[3]/li[5]/a/i"), 2000);
    String winHandleBefore = driver.getWindowHandle();
	testFuncs.myDebugPrinting("winHandleBefore - " + winHandleBefore, testVars.logerVars.MAJOR);
    for(String winHandle : driver.getWindowHandles()) {
    	
        driver.switchTo().window(winHandle);
        winHandleBefore = driver.getWindowHandle();
    	testFuncs.myDebugPrinting("winHandleBefore - " + winHandleBefore, testVars.logerVars.MAJOR);
    }
    testFuncs.searchStr(driver, "Using the SBA Management Interface");
    testFuncs.searchStr(driver, "SBA Management Interface- Main Dashboard Components");
  }
  
  @Test
  public void Toolbar_buttons() throws Exception {
	  
	Log.startTestCase(this.getClass().getName() + " - Buttons check");
	testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
	
	/**
	* Step 5: Verify headers of Setup section
	*/
	testFuncs.myDebugPrinting("Step 5 Verify headers of Setup section", testVars.logerVars.MAJOR);
	testFuncs.myClick(driver, By.xpath("//*[@id='navbar-collapse']/ul[1]/li[1]/a"), 2000);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='left-nav']/ul/li/ul/li[1]/a/span[1]"), "Network");	
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='left-nav']/ul/li/ul/li[2]/a/span[1]"), "SBA Setup");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='left-nav']/ul/li/ul/li[3]/a/span[1]"), "Change Admin Password");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='left-nav']/ul/li/ul/li[4]/a/span[1]"), "Date & Time");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='left-nav']/ul/li/ul/li[5]/a/span[1]"), "SNMP");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='left-nav']/ul/li/ul/li[6]/a/span[1]"), "SBA Certificate");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='left-nav']/ul/li/ul/li[7]/a/span[1]"), "SBC GW Certificate");
	testFuncs.myClick(driver, By.xpath("/html/body/div/header/a/img"), 2000);
	testFuncs.searchStr(driver, testVars.getSbaAfLoginStr());
	
	/**
	* Step 6: Verify headers of Tools section
	*/
	testFuncs.myDebugPrinting("Step 6: Verify headers of Tools section", testVars.logerVars.MAJOR);
	testFuncs.myClick(driver, By.xpath("//*[@id='navbar-collapse']/ul[1]/li[3]/a"), 2000);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[1]/div/h3"), "SBA Upgrade");	
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='left-nav']/ul/li/ul/li[1]/a/span[1]") 	 , "SBA Software Upgrade");	
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='left-nav']/ul/li/ul/li[2]/a/span[1]")   , "SBA Services");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='left-nav']/ul/li/ul/li[3]/a/span[1]")   , "SBA Syslog");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='left-nav']/ul/li/ul/li[4]/a/span[1]")   , "Web Admin Logs");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='left-nav']/ul/li/ul/li[5]/a/span[1]")   , "SBA Configuration Logs");
	testFuncs.myClick(driver, By.xpath("/html/body/div/header/a/img"), 2000);
	testFuncs.searchStr(driver, testVars.getSbaAfLoginStr());
	
	/**
	* Step 7: Verify headers of Monitors section
	*/
	testFuncs.myDebugPrinting("Step 7: Verify headers of Monitors section", testVars.logerVars.MAJOR);
	testFuncs.myClick(driver, By.xpath("//*[@id='navbar-collapse']/ul[1]/li[2]/a"), 2000);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='left-nav']/ul/li[1]/a")      			  , "DASHBOARD");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='left-nav']/ul/li[1]/ul/li/a")			  , "Dashboard");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='left-nav']/ul/li[2]/a/span") 			  , "PERFORMANCE");
  
	/**
	* Step 8: Test Collapse and Expend button
	*/
	testFuncs.myDebugPrinting("Step 8: Test Collapse and Expend button", testVars.logerVars.MAJOR);
	testFuncs.myClick(driver, By.xpath("/html/body/div/header/nav/a"), 2000);
	testFuncs.myClick(driver, By.xpath("/html/body/div/header/nav/a"), 2000);
	
	/**
	* Step 9: Test Skype 2015 button
	*/
	testFuncs.myDebugPrinting("Step 9: Test Skype 2015 button", testVars.logerVars.MAJOR);
	testFuncs.myClick(driver, By.xpath("//*[@id='navbar-collapse']/ul[2]/li/a"), 5000);	
    String winHandleBefore = driver.getWindowHandle();
	testFuncs.myDebugPrinting("winHandleBefore - " + winHandleBefore, testVars.logerVars.MAJOR);
    for(String winHandle : driver.getWindowHandles()) {
    	
        driver.switchTo().window(winHandle);
        winHandleBefore = driver.getWindowHandle();
    	testFuncs.myDebugPrinting("winHandleBefore - " + winHandleBefore, testVars.logerVars.MAJOR);
    }
    testFuncs.searchStr(driver, "Products for Microsoft");
    testFuncs.searchStr(driver, "Applications for Microsoft Skype for Business");
  }
  
  @Test
  public void Toolbar_logout() throws Exception {
	  
	/**
	* Step 10: Test Logout method
	*/
	testFuncs.myDebugPrinting("Step 10: Test Logout method", testVars.logerVars.MAJOR);  
	Log.startTestCase(this.getClass().getName() + " - Logout check");	
	testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
	testFuncs.logout(driver);
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

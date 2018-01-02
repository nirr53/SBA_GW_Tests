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
* This test tests the SBA activation actions the SBA web
* -----------------
* Tests:
*       
*   1. Install lyss database and check its log
*   2. Install Install registrar database and check its log
*   3. Initialize local configuration store and check its log
*   4. Add the SBA to the replication path and check its log
*   5. Enable SBA services and server roles and check its log
*
* Results:
*    1-5. All Install actions should end successfully
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test68_Setup_SBA_setup_activation {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test68_Setup_SBA_setup_activation(String browser) {
	  
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
  public void Setup_SBA_setup_logs_mechnism_check() throws Exception {
	  
	  Log.startTestCase(this.getClass().getName());
  	  testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
  	  testFuncs.enterMenu(driver, "Setup_SBA_setup", "SBA Setup");
      testFuncs.myWait(60000);
      
      // Step 1 - Install lyss database and check its log
      testFuncs.myDebugPrinting("Step 1 - Install lyss database and check its log", testVars.logerVars.MAJOR);
      installAndCheckInLog(driver, "1", "activation_database_lyss_install", "\"Install-CsDatabase\" processing has completed successfully.");
       
      // Step 2 - Install Install registrar database and check its log
      testFuncs.myDebugPrinting("Step 2 - Install Install registrar database and check its log", testVars.logerVars.MAJOR);
      installAndCheckInLog(driver, "2", "activation_database_registrar_install", "\"Install-CsDatabase\" processing has completed successfully.");

      // Step 3 - Initialize local configuration store and check its log
      testFuncs.myDebugPrinting("Step 3 - Initialize local configuration store and check its log", testVars.logerVars.MAJOR);
      installAndCheckInLog(driver, "3", "activation_topology", "Successfully exported topology configuration\nSuccessfully imported topology to local computer");

      // Step 4 - Add the SBA to the replication path and check its log
      testFuncs.myDebugPrinting("Step 4 - Add the SBA to the replication path and check its log", testVars.logerVars.MAJOR);
      installAndCheckInLog(driver, "4", "activation_replica_enable", "\"Enable-CsReplica\" processing has completed successfully.");

      // Step 5 - Enable SBA services and server roles and check its log
      testFuncs.myDebugPrinting("Step 5 - Enable SBA services and server roles and check its log", testVars.logerVars.MAJOR);
      installAndCheckInLog(driver, "5", "activation_computer_enable", "\"Enable-CsComputer\" processing has completed successfully.");
  }  

  // Install and verify install at logs
  // driver 		- a given driver
  // idx   	 		- index of the given action
  // actionName     - Name of action as it appears at Logs menu
  // logsActionName - Name of action as it appears at Logs menu 
  private void installAndCheckInLog(WebDriver driver, String idx, String actionName, String logsActionName) {
	  
      String winHandleBefore = driver.getWindowHandle();
	  testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div[2]/div/div/div[2]/table/tbody[" + idx + "]/tr/td[4]/button[1]"), 30000); 
	  testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div[2]/div/div/div[2]/table/tbody[" + idx + "]/tr/td[4]/button[2]"), 30000);     
	  java.util.Set<java.lang.String> windowHandles = driver.getWindowHandles(); 	     
	  for (String window : windowHandles) {
		    	    
		  if(!window.equals(winHandleBefore)) {
		        	   		  
			  driver.switchTo().window(window);  
			  testFuncs.searchStr(driver, actionName);  	      
			  testFuncs.myClick(driver, By.xpath("//*[@id='accordion']/div[1]/div[1]/h4/a"), 1000);
			  testFuncs.searchStr(driver, logsActionName);      		    
			  driver.close();  
		  }
		  driver.switchTo().window(winHandleBefore);  
	  }
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

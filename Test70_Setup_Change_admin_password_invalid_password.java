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
* This test tests an entry of new invalid password
* -----------------
* Tests:
*   1. Check headers of the menu
*   2. Try to submit an empty data
*   3. Try to submit a dismatch data
*   4. Try to submit a password with only numbers
*
* Results:
*    1.   Menu should looks ok
*    2-4. Password should not be set and error prompt should appear*    
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test70_Setup_Change_admin_password_invalid_password {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test70_Setup_Change_admin_password_invalid_password(String browser) {
	  
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
  public void Setup_change_admin_password_invalid_password() throws Exception {
	  
	  Log.startTestCase(this.getClass().getName());
  	  testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
  	  testFuncs.enterMenu(driver, "Setup_Change_admin_password", "Change Admin Password");
  	
  	  /**
  	  * Step 1: Check some headers of menu
  	  */
  	  testFuncs.myDebugPrinting("Step 1: Check some headers of menu", testVars.logerVars.MAJOR);
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[1]/h3"), "Change Admin Password");
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyform']/div[1]/div[1]/label")			   , "Current Password");
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyform']/div[1]/div[3]/label")			   , "New Password");
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyform']/div[1]/div[4]/label")			   , "Confirm Password");
  	  
	  /**
  	  * Step 2: Try to submit an empty data
   	  */
   	  testFuncs.myDebugPrinting("Step 2: Try to submit an empty data", testVars.logerVars.MAJOR);
   	  testFuncs.myClick(driver, By.xpath("//*[@id='parsleyform']/div[2]/button"), 2000);
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsley-id-4']/li"), "This value is required.");
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsley-id-6']/li"), "This value is required.");
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsley-id-8']/li"), "This value is required.");
  	  
	  /**
   	  * Step 3: Try to submit a dismatch data
   	  */
      testFuncs.myDebugPrinting("Step 3: Try to submit a dismatch data", testVars.logerVars.MAJOR);
      String newPassword = "newPassword";
      testFuncs.mySendKeys(driver, By.xpath("//*[@id='newpass']")						 , newPassword 			   , 2000);
      testFuncs.mySendKeys(driver, By.xpath("//*[@id='parsleyform']/div[1]/div[4]/input"), newPassword.substring(1), 2000);
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsley-id-8']/li"), "This value should be the same."); 
      
  	  /**
  	  * Step 4: Try to submit a password with only numbers
  	  */
      testFuncs.myDebugPrinting("Step 4: Try to submit a password with only numbers", testVars.logerVars.MAJOR);
      String numPassword = "1234";
      testFuncs.mySendKeys(driver, By.xpath("//*[@id='newpass']")						 , numPassword 			   , 2000);
   	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsley-id-6']/li"), "This value is too short. It should have 6 characters or more.");
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

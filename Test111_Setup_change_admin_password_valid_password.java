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
* This test tests an entry of new valid password
* -----------------
* Tests:
*   1. Change the password, logout and verify good relogin with new password
*   2. Change the password again to inital password and verify good login
*
* Results:
*    1-2. In both cases login with the modifed password should work
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test111_Setup_change_admin_password_valid_password {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test111_Setup_change_admin_password_valid_password(String browser) {
	  
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
  public void Setup_change_admin_password_valid_password() throws Exception {
	  
	  Log.startTestCase(this.getClass().getName());
	  
	  // Set vars and login
	  String newPassword = "1q2w3e4r";
  	  testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
  	  testFuncs.enterMenu(driver, "Setup_Change_admin_password", "Change Admin Password");
  	  
	  /**
   	  * Step 1: Change the system password
   	  */
      testFuncs.myDebugPrinting("Step 1: Change the system password", testVars.logerVars.MAJOR);
      changePassword(driver, testVars.getSysPassword(), newPassword);
      
  	  /**
  	  * Step 2: Verify that password was changed and reset the password again
  	  */
      testFuncs.myDebugPrinting("Step 2: Verify that password was changed and reset the password again", testVars.logerVars.MAJOR);
      testFuncs.logout(driver);
  	  testFuncs.login(driver, testVars.getSysUsername(), newPassword, testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
  	  testFuncs.enterMenu(driver, "Setup_Change_admin_password", "Change Admin Password");
      changePassword(driver, newPassword, testVars.getSysPassword());
  }
  
  // Change the password 
  private void changePassword(WebDriver driver, String oldPassword, String newPassword) {
	  
      testFuncs.myDebugPrinting("oldPassword - " + oldPassword, testVars.logerVars.MINOR);
      testFuncs.myDebugPrinting("newPassword - " + newPassword, testVars.logerVars.MINOR);
      testFuncs.mySendKeys(driver, By.xpath("//*[@id='parsleyform']/div[1]/div[1]/input"), oldPassword, 2000);
      testFuncs.mySendKeys(driver, By.xpath("//*[@id='newpass']")						 , newPassword, 2000);
      testFuncs.mySendKeys(driver, By.xpath("//*[@id='parsleyform']/div[1]/div[4]/input"), newPassword, 2000);
      testFuncs.myClick(driver, By.xpath("//*[@id='parsleyform']/div[2]/button"), 3000);
      testFuncs.myAssertTrue("Fail error was detected !!", !driver.findElement(By.tagName("body")).getText().contains("Fail to update password."));
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

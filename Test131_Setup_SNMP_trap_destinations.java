package SBA_GW_Tests;

import java.net.InetAddress;
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
* This test tests the trap detsinations of the SNMP menu
* -----------------
* Tests:
*       
*   1. Check the headers of the menu
*   2. Insert same string for read and write community and press the Submit button
*
* Results:
*   1. Menu should looks clear.
*   2. Appropriate error message should appear that it prevented
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test131_Setup_SNMP_trap_destinations {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test131_Setup_SNMP_trap_destinations(String browser) {
	  
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
  public void Setup_snmp_invalid_string() throws Exception {
	  
	  Log.startTestCase(this.getClass().getName());
	  
	  	  testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
  	  testFuncs.enterMenu(driver, "Setup_SNMP", "SNMP Information");
  	
  	  
//  	Change the Trap Community Name
//  	- Add Trap Destinations:
//  	1. to EMS
//  	2. Your PC
//  	- Submit changes - SNMP service should restart
//  	- Run WireShark on your PC (SNMP filter)
//  	- Cause an alarm (stop one of Skype services in the SBA server > Services)
//  	- Verify that EMS receive the alarm
  	  
  	  
  	  /**
  	  * Step 1: Change the Trap Community Name by add Trap Destinations
  	  */
  	  testFuncs.myDebugPrinting("Step 1: Change the Trap Community Name by add Trap Destinations", testVars.logerVars.MAJOR);
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyform']/div[1]/section[1]/div/div[1]/h3"), "SNMP Information");
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyform']/div[1]/section[2]/div/div[1]/h3"), "Trap Community Name");
  		
  	  
  	  
  	  
  	  // Add EMS as trap destibation
  	  addNewTrap(driver, InetAddress.getLocalHost().toString());	
  		

  }

  // Add new Trap
  private void addNewTrap(WebDriver 	driver, String trapDest) {
	  
  	  testFuncs.myDebugPrinting("trapDest - " + trapDest, testVars.logerVars.MINOR);
  	  testFuncs.myClick(driver, By.xpath("//*[@id='parsleyform']/div[1]/section[2]/div/div[5]/div/div/a[1]"), 3000);
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='trapModel']/div/div/div[1]/h4")		 , "SNMP Service Configuration");
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='trapModel']/div/div/div[2]/div/label"), "Host name, IP or IPX address"); 	 
  	  String press = Keys.chord(Keys.SHIFT,Keys.ENTER); 
	  driver.findElement(By.xpath("//*[@id='trapModel']/div/div/div[3]/button[1]")).sendKeys(press);
	  
	  testFuncs.myWait(3000);
	  

//	  testFuncs.myWait(3000);	  
//  	  testFuncs.myClick(driver, By.xpath("//*[@id='trapModel']/div/div/div[3]/button[2]"), 3000);
//  	  testFuncs.searchStr(driver, trapDest);
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

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
* This test tests an insert of invalid string to the SNMP menu
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
public class Test205_Setup_SNMP_invalid_string {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test205_Setup_SNMP_invalid_string(String browser) {
	  
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
	  
	  // Set vars
	  String error = "Read and write community names cannot use the same values.";
	  
  	  testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
  	  testFuncs.enterMenu(driver, "Setup_SNMP", "SNMP Information");
  	
  	  /**
  	  * Step 1: Check the headers of the menu
  	  */
  	  testFuncs.myDebugPrinting("Step 1: Check the headers of the menu", testVars.logerVars.MAJOR);
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyform']/div[1]/section[1]/div/div[1]/h3  ") 				, "SNMP Information");
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyform']/div[1]/section[1]/div/div[2]/label")				, "Contact:");
  		
  	  /**
   	  * Step 2: Insert same string for read and write community and press the Submit button
   	  */
   	  testFuncs.myDebugPrinting("Step 2: Insert same string for read and write community and press the Submit button", testVars.logerVars.MAJOR);
   	  testFuncs.myClick(driver, By.xpath("//*[@id='parsleyform']/div[1]/section[3]/div/div[2]/table/tbody/tr[1]/td[1]"), 3000);
   	  testFuncs.myClick(driver, By.xpath("//*[@id='parsleyform']/div[1]/section[3]/div/div[3]/div/div/a[2]")		   , 3000);
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='communityModal']/div/div/div[1]/h4")			 , "SNMP Service Configuration");
  	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='communityModal']/div/div/div[2]/div[2]/label"), "Community name:");
  	  testFuncs.mySendKeys(driver, By.xpath("//*[@id='community']"), "public", 2000);
   	  testFuncs.myClick(driver, By.xpath("//*[@id='communityModal']/div/div/div[3]/button[2]")		   				   , 3000);
   	  Alert alert = driver.switchTo().alert();
	  String warningMsg = alert.getText();
	  if (!warningMsg.contains(error)) {
		  
			testFuncs.myFail("Error message was not detected !! ("  + warningMsg + ")");
	  } else {
		  
	   	  testFuncs.myDebugPrinting("Error message was detected !!", testVars.logerVars.MINOR);  
	  }
	  alert.accept();
	  testFuncs.myWait(3000); 
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

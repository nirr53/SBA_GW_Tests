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
* This test tests the automatic-certificate mechnism
* -----------------
* Tests:
*   1. Check menu headers
*   2. Press the Request key and create a certificate
*
* Results:
*    1. Menu should be displayed properly
*    2. Certificate wizard details page should appear and certificate should be created successfully
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test27_Setup_SBA_certificate_automatic {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test27_Setup_SBA_certificate_automatic(String browser) {
	  
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
  public void Setup_certificate_automatic() throws Exception {
	  
	  Log.startTestCase(this.getClass().getName());
  	  testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
  	  testFuncs.enterMenu(driver, "Setup_SBA_certificate", "Request, Install or Assign SBA Certificates");
  	  
	  /**
   	  * Step 1: Check menu headers
   	  */
      testFuncs.myDebugPrinting("Step 1: Check menu headers", testVars.logerVars.MAJOR); 
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[1]/h3")	 				    	 , "Request, Install or Assign SBA Certificates");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[1]/span")				  		 , "This step starts the Certificate Wizard. Create certificate request for local system. Install, and assign Certificate for this system based on the topology definition.");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table[1]/thead/tr/th")		 				  		 , "Select a Skype for Business Server Certificate Type and then select a task. Expand the Certificate Type to perform advanced certificate usage tasks.");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table[1]/tbody/tr[1]/td/table/thead/tr/th[1]")		 , "Certificate");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table[1]/tbody/tr[1]/td/table/thead/tr/th[2]")		 , "Assign");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table[1]/tbody/tr[1]/td/table/thead/tr/th[3]")		 , "Friendly Name");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table[1]/tbody/tr[1]/td/table/thead/tr/th[4]")		 , "Expiration Date");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table[1]/tbody/tr[1]/td/table/thead/tr/th[5]")		 , "Location");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='btnr']")												  		 , "Request");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table[1]/tbody/tr[3]/td/button")			  		 , "Assign");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table[1]/tbody/tr[4]/td/button")			  		 , "Remove");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table[1]/tbody/tr[5]/td/button")			  		 , "View");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table[1]/tbody/tr[7]/td/table/tbody/tr/td[1]/button"), "Get Certificate Log");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table[1]/tbody/tr[7]/td/table/tbody/tr/td[2]/button"), "Remove Certificate Log");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table[1]/tbody/tr[7]/td/table/tbody/tr/td[4]/button"), "Refresh");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table[1]/tbody/tr[7]/td/table/tbody/tr/td[5]/button"), "Import Certificate");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='btnp']")														 , "Process Pending Certificates");
      
	  /**
	  * Step 2: Press the Request key and create a certificate
      */
      testFuncs.myDebugPrinting("Step 2: Press the Request key and create a certificate", testVars.logerVars.MAJOR); 
      testFuncs.myClick(driver, By.xpath("//*[@id='btnr']"), 10000);
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-2']/table[1]/tbody/tr[1]/td[1]/div/label"), "Select a CA from the list detected in your environment");
      
      // Set data
      String id = testFuncs.GenerateId();
      String cerName = "certificate" + id;
      testFuncs.mySendKeys(driver, By.xpath("//*[@id='FriendlyName']")								 , cerName					, 2000);
      testFuncs.mySendKeys(driver, By.xpath("//*[@id='tabs-2']/table[1]/tbody/tr[6]/td[1]/div/input"), "Administrator"			, 2000);
      testFuncs.mySendKeys(driver, By.xpath("//*[@id='tabs-2']/table[1]/tbody/tr[6]/td[3]/div/input"), testVars.getSysPassword(), 2000);
      testFuncs.myClick(driver, By.xpath("//*[@id='cb1']"), 2000);
      testFuncs.myClick(driver, By.xpath("//*[@id='tabs-2']/table[2]/tbody/tr[2]/td[2]/button[3]"), 20000);
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table[1]/thead/tr/th")		 				  		 , "Select a Skype for Business Server Certificate Type and then select a task. Expand the Certificate Type to perform advanced certificate usage tasks.");
      testFuncs.myClick(driver, By.xpath("//*[@id='tabs-1']/table[1]/tbody/tr[7]/td/table/tbody/tr/td[4]/button"), 5000);
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table[1]/tbody/tr[1]/td/table/tbody/tr[1]/td[3]")	 , cerName);  
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

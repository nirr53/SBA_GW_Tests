package SBA_GW_Tests;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import SBA_GW_Tests.Log;

/**
* ----------------
* This test tests the immediatly-certificate create process
* -----------------
* Tests:
*   1. Create a certificate manually using the immediatly rule
*
* Results:
*   1. Certficate should be cerated and downloaded successfully.
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test34_Setup_SBA_certificate_immediatly {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test34_Setup_SBA_certificate_immediatly(String browser) {
	  
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
  public void Setup_certificate_manual_immediatly() throws Exception {
	  
	  String username 	  = "Administrator";
	  String password 	  = "1q2w3e$r";
      String cerName      = "certificate" + testFuncs.GenerateId();
	  Log.startTestCase(this.getClass().getName());
  	  testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
  	  testFuncs.enterMenu(driver, "Setup_SBA_certificate", "Request, Install or Assign SBA Certificates"); 
  	  testFuncs.myWait(10000);
  	  
	  /**
	  * Step 1: Press the Request key and start create manual certificate immediatly
      */
      testFuncs.myDebugPrinting("Step 1: Press the Request key and start create manual certificate", testVars.logerVars.MAJOR); 
      testFuncs.myClick(driver, By.xpath("//*[@id='btnr']"), 10000);
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-2']/table[1]/tbody/tr[1]/td[1]/div/label"), "Select a CA from the list detected in your environment");
      
      // 1.1 Press 'Advanced' and select 'Send the request immediately'
      testFuncs.myDebugPrinting("1.1 Press 'Advanced' and select 'Send the request immediately'", testVars.logerVars.NORMAL); 
      testFuncs.myClick(driver, By.xpath("//*[@id='tabs-2']/table[2]/tbody/tr[2]/td[1]/button"), 3000); 
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-3']/table/thead/tr/th"), "Delayed or Immediate Request.");        
      testFuncs.myClick(driver, By.xpath("//*[@id='tabs-3']/div/button[2]")					   , 3000); 
      
      // 1.2 Select a CA from the list of detected environment and specify alternate credentials & set username and password
      testFuncs.myDebugPrinting("1.2 Select a CA from the list of detected environment and specify alternate credentials & set username and password", testVars.logerVars.NORMAL); 
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-10']/table/thead/tr/th"), "Choose a Certification Authority (CA)");
      testFuncs.myClick(driver, By.xpath("//*[@id='tabs-10']/div/button[2]"), 3000);
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-11']/table/thead/tr/th"), "Specify Choose a Certification Authority (CA)");
      testFuncs.myClick(driver, By.xpath("//*[@id='credential']"), 1000);      
      testFuncs.mySendKeys(driver, By.xpath("//*[@id='CaAccount']") , username, 2000);
      testFuncs.mySendKeys(driver, By.xpath("//*[@id='CaPassword']"), password, 2000);
      testFuncs.myClick(driver, By.xpath("//*[@id='tabs-11']/div/button[2]"), 3000);
      
      // 1.3 Prepare the Request
      testFuncs.myDebugPrinting("1.3 Prepare the Request", testVars.logerVars.NORMAL); 
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-12']/table/thead/tr/th"), "Prepare the Request");  
      Select offSet = new Select(driver.findElement(By.xpath("//*[@id='tabs-12']/table/tbody/tr[7]/td/div/select")));
      offSet.selectByVisibleText("4096");
      testFuncs.myClick(driver, By.xpath("//*[@id='mark']"), 1000);
      testFuncs.mySendKeys(driver, By.xpath("//*[@id='tabs-12']/table/tbody/tr[13]/td/table/thead/tr/th[1]/div/input"), cerName, 2000);
      testFuncs.myClick(driver, By.xpath("//*[@id='tabs-12']/div/button[2]") , 7000); 

      // 1.4 Set valid values for certificate details page and produce a certificate
      testFuncs.myDebugPrinting("1.4 Set valid values for certificate details page and produce a certificate", testVars.logerVars.NORMAL);  
      testFuncs.mySendKeys(driver, By.xpath("//*[@id='Organization']"), "Audiocodes"			   , 2000);
      testFuncs.mySendKeys(driver, By.xpath("//*[@id='OU']")		  , "Audiocodes-developement"  , 2000);
      Select country = new Select(driver.findElement(By.xpath("//*[@id='Country']")));
      country.selectByVisibleText("ISRAEL");
      testFuncs.mySendKeys(driver, By.xpath("//*[@id='State']")		  , "State"					   , 2000);
      testFuncs.mySendKeys(driver, By.xpath("//*[@id='City']")		  , "Beer-Sheva"			   , 2000);
      testFuncs.myClick(driver, By.xpath("//*[@id='cb1']") 										   , 1000);   
      testFuncs.myClick(driver, By.xpath("//*[@id='tabs-2']/table[2]/tbody/tr[2]/td[2]/button[3]") , 9000);
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

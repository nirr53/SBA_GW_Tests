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
* This test tests the manual-certificate create process
* -----------------
* Tests:
*   1. Create a certificate manually
*   2. Check certificate download
*
* Results:
*    1-2. Certficate should be cerated and downloaded successfully.
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test35_Setup_SBA_certificate_manual {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test35_Setup_SBA_certificate_manual(String browser) {
	  
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
  public void Setup_certificate_manual() throws Exception {
	  
	  String exportPrefix = "certificate";
	  Log.startTestCase(this.getClass().getName());
  	  testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
  	  testFuncs.enterMenu(driver, "Setup_SBA_certificate", "Request, Install or Assign SBA Certificates");  
  	  testFuncs.myWait(10000);
  	  
	  /**
	  * Step 1: Press the Request key and start create manual certificate
      */
      testFuncs.myDebugPrinting("Step 1: Press the Request key and start create manual certificate", testVars.logerVars.MAJOR); 
      testFuncs.myClick(driver, By.xpath("//*[@id='btnr']"), 15000);
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-2']/table[1]/tbody/tr[1]/td[1]/div/label"), "Select a CA from the list detected in your environment");
      
      // Press 'Advanced' and select 'Delayed Request'
      testFuncs.myClick(driver, By.xpath("//*[@id='tabs-2']/table[2]/tbody/tr[2]/td[1]/button"), 10000);
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-3']/table/thead/tr/th"), "Delayed or Immediate Request.");
      testFuncs.myClick(driver, By.xpath("//*[@id='group1_1']"), 2000);
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-3']/table/tbody/tr[4]/td/div/label[3]"), "File location: C:\\audiocodes\\upload\\certificate.req");
      
      // Set certificta-name, bit-offset
      testFuncs.myClick(driver, By.xpath("//*[@id='tabs-3']/div/button[2]"), 5000); 
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-12']/table/thead/tr/th")		, "Prepare the Request");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-12']/table/tbody/tr[2]/td/span"), "Name and Security Settings");
      Select bit = new Select(driver.findElement(By.xpath("//*[@id='tabs-12']/table/tbody/tr[7]/td/div/select")));
      bit.selectByIndex(2);
      String cerName = "certificate" + testFuncs.GenerateId();
      testFuncs.mySendKeys(driver, By.xpath("//*[@id='tabs-12']/table/tbody/tr[13]/td/table/thead/tr/th[1]/div/input"), cerName, 2000);
      testFuncs.myClick(driver, By.xpath("//*[@id='tabs-12']/div/button[2]"), 15000); 

      // Set valid values for certificate details page and produce a certificate
      testFuncs.mySendKeys(driver, By.xpath("//*[@id='Organization']"), "Audiocodes"			 , 2000);
      testFuncs.mySendKeys(driver, By.xpath("//*[@id='OU']")		  , "Audiocodes-developement", 2000);
      Select country = new Select(driver.findElement(By.xpath("//*[@id='Country']")));
      country.selectByVisibleText("ISRAEL");
      testFuncs.mySendKeys(driver, By.xpath("//*[@id='State']")		  , "State"					 , 2000);
      testFuncs.mySendKeys(driver, By.xpath("//*[@id='City']")		  , "Beer-Sheva"			 , 2000);
      testFuncs.myClick(driver, By.xpath("//*[@id='cb1']") 										 , 1000);   
      
      /**
 	  * Step 2: Check certificate download
      */
      testFuncs.myDebugPrinting("Step 2: Check certificate download", testVars.logerVars.MAJOR); 
      testFuncs.deleteFilesByPrefix(testVars.getDownloadsDir(), exportPrefix);	
      testFuncs.myClick(driver, By.xpath("//*[@id='tabs-2']/table[2]/tbody/tr[2]/td[2]/button[3]"), 15000); 
      String certName = testFuncs.findFilesByGivenPrefix(testVars.getDownloadsDir(), exportPrefix);
      testFuncs.myAssertTrue("Certificate was not downloaded successfully !!", certName != null);
      testFuncs.deleteFilesByPrefix(testVars.getDownloadsDir(), exportPrefix);	
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

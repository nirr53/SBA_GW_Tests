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
* This test tests the requset-certificate for sbc-gw
* -----------------
* Tests:
*   1. Check menu headers
*   2. Requset certificate
*
* Results:
*    1. Menu should be displayed properly
*    2. Requset certificate should be ended successfully
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class XXX_Test109_Setup_SBC_gw_request_certificate {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public XXX_Test109_Setup_SBC_gw_request_certificate(String browser) {
	  
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
  public void Setup_sbc_request_certificate() throws Exception {
	  
	  Log.startTestCase(this.getClass().getName());
  	  testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
  	  testFuncs.enterMenu(driver, "Setup_SBC_gw_certificate", "SBC GW Certificates");
  	  
	  /**
   	  * Step 1: Check menu headers
   	  */
      testFuncs.myDebugPrinting("Step 1: Check menu headers", testVars.logerVars.MAJOR); 
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[1]/h3")	    , "SBC GW Certificates");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[1]/span")   , "This step starts the SBC GW Certificate Wizard. Create, request, export or import certificate for local system. Export, import or remove trusted certificates");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table[2]/tbody/tr/td[2]/button"), "Create CSR");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table[2]/tbody/tr/td[3]/button"), "Request Certificate");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table[2]/tbody/tr/td[4]/button"), "Import");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/h4[2]")							, "Trusted Certificates");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table[3]/thead/tr/th[1]")		, "Common Name");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table[3]/thead/tr/th[2]")		, "Issuer");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table[3]/thead/tr/th[3]")		, "Version");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table[3]/thead/tr/th[4]")		, "Expires");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table[3]/thead/tr/th[5]")		, "Common Alternative Name");
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table[4]/tbody/tr/td[2]/button"), "Import");
      testFuncs.myWait(60000);

	  /**
      * Step 2: Requset certificate
      */ 
      testFuncs.myDebugPrinting("Step 2: Requset certificate", testVars.logerVars.MAJOR);  
      testFuncs.myClick(driver, By.xpath("//*[@id='tabs-1']/table[2]/tbody/tr/td[3]/button"), 7000);  
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='cacombo']/td[1]/div/label"), "Select a CA from the list detected in your environment  *");
      testFuncs.mySendKeys(driver, By.xpath("//*[@id='subjectName']"), "SBA-M800-MA24-GW.cloudbond365B.comþ", 3000);
      testFuncs.myClick(driver, By.xpath("//*[@id='tabs-2']/table[2]/tbody/tr/td[3]/button"), 7000);  
      testFuncs.myAssertTrue("Error was detected !!", !driver.findElement(By.tagName("body")).getText().contains("Failed to post request."));      
      testFuncs.myWait(60000);
      
      
    testFuncs.searchStr(driver, "gdgsd");
//      testFuncs.searchStr(driver, "Select a Skype for Business Server Certificate Type and then select a task. Expand the Certificate Type to perform advanced certificate usage tasks.");
//      testFuncs.searchStr(driver, "Certificate");
//      testFuncs.searchStr(driver, "Assign");
//      testFuncs.searchStr(driver, "Friendly Name");
//      testFuncs.searchStr(driver, "Expiration Date");
//      testFuncs.searchStr(driver, "Location");
//      testFuncs.searchStr(driver, "Request");
//      testFuncs.searchStr(driver, "Assign");
//      testFuncs.searchStr(driver, "Remove");
//      testFuncs.searchStr(driver, "View");
//      testFuncs.searchStr(driver, "Get Certificate Log");
//      testFuncs.searchStr(driver, "Remove Certificate Log");
//      testFuncs.searchStr(driver, "Refresh");
//      testFuncs.searchStr(driver, "Import Certificate");
//      testFuncs.searchStr(driver, "Process Pending Certificates");
     
	  /**
	  * Step 2: Press the Request key and create a certificate
      */
      testFuncs.myDebugPrinting("Step 2: Press the Request key and create a certificate", testVars.logerVars.MAJOR); 
      testFuncs.myClick(driver, By.xpath("//*[@id='btnr']"), 10000);
      testFuncs.searchStr(driver, "Select a CA from the list detected in your environment");
      
      // Set data
      String id = testFuncs.GenerateId();
      String cerName = "certificate" + id;
      testFuncs.mySendKeys(driver, By.xpath("//*[@id='FriendlyName']")								 , cerName					, 2000);
//      testFuncs.mySendKeys(driver, By.xpath("//*[@id='tabs-2']/table[1]/tbody/tr[6]/td[1]/div/input"), "Administrator"			, 2000);
//      testFuncs.mySendKeys(driver, By.xpath("//*[@id='tabs-2']/table[1]/tbody/tr[6]/td[3]/div/input"), testVars.getSysPassword(), 2000);
//      testFuncs.myClick(driver, By.xpath("//*[@id='cb1']"), 2000);
//      testFuncs.myClick(driver, By.xpath("//*[@id='tabs-2']/table[2]/tbody/tr[2]/td[2]/button[3]"), 20000);
//      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table[1]/thead/tr/th")		 				  		 , "Select a Skype for Business Server Certificate Type and then select a task. Expand the Certificate Type to perform advanced certificate usage tasks.");
//      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table[1]/tbody/tr[1]/td/table/tbody/tr[1]/td[3]")	 , cerName);  
  }
  
  @After
  public void tearDown() throws Exception {
	  
    //driver.quit();
    System.clearProperty("webdriver.chrome.driver");
	System.clearProperty("webdriver.ie.driver");
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
    	
      testFuncs.myFail(verificationErrorString);
    }
  }
}

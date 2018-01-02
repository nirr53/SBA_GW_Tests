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
* This test tests the Add-certificate for SBC-gw
* -----------------
* Tests:
*   1. Check menu headers
*   2. Add a certificate
*
* Results:
*    1. Menu should be displayed properly
*    2. Certificate should be added successfully
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class XXX_Test108_Setup_SBC_gw_add_certificate {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public XXX_Test108_Setup_SBC_gw_add_certificate(String browser) {
	  
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
  public void Setup_sbc_gw_add_certificate() throws Exception {
	  
	  Log.startTestCase(this.getClass().getName());
	  
	  // Set vars
	  String id = testFuncs.GenerateId();
	  String cerName = "certificate" + id;
	  
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
      testFuncs.waitForString(driver, "Failed to get certificates", 30000); 
      
	  /**
      * Step 2: Enter the Create CSR menu and check headers
      */ 
      testFuncs.myDebugPrinting("Step 2: Enter the Create CSR menu and check headers", testVars.logerVars.MAJOR);  
      testFuncs.myClick(driver, By.xpath("//*[@id='tabs-1']/table[2]/tbody/tr/td[2]/button"), 12000);  
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-5']/table[1]/tbody/tr[1]/td[1]/div/label"), "Common Name [CN]  *");
      testFuncs.searchStr(driver, "Company name [O] (optional)");
      testFuncs.searchStr(driver, "Organizational Unit [OU] (optional)");   
      testFuncs.searchStr(driver, "Locality or city name [L] (optional)");
      testFuncs.searchStr(driver, "State [ST] (optional)");     
      testFuncs.searchStr(driver, "Country code [C] (optional)");
      testFuncs.searchStr(driver, "Signature Algorithm");     
      
	  /**
	  * Step 3: Press the Request key and verify errors
      */
      testFuncs.myDebugPrinting("Step 3: Press the Request key and verify errors", testVars.logerVars.MAJOR); 
      testFuncs.myClick(driver, By.xpath("//*[@id='tabs-5']/table[2]/tbody/tr/td[2]/button"), 7000);
      testFuncs.verifyStrBy(driver, By.xpath("//*[@id='errorID']"), "Please fill required property: Common Name [CN]  *");
      testFuncs.myDebugPrinting("cerName - " + cerName, testVars.logerVars.MINOR); 
//      testFuncs.mySendKeys(driver, By.id("subjectName"), cerName, 2000);
      
      
    //*[@id="subjectName"]
//      testFuncs.mySendKeys(driver, By.xpath("//*[@id='subjectName']"), cerName, 2000);

      
      testFuncs.myWait(2000);
 
      WebElement compnayName = driver.findElement(By.xpath("//*[@id='state']"));
      testFuncs.myWait(2000);
      compnayName.sendKeys("ff");
//      testFuncs.mySendKeys(driver, , cerName, 2000);

    //*[@id="state"]

      
    //*[@id="subjectName"]
      
      
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

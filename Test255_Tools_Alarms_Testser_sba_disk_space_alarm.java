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
* This test tests sending of differnt disk space alarms.
* -----------------
* 
* Tests:
*    1. Check that page is opened clear and correct
*    2. Test disk space alarm with warning severity	
*    3. Test disk space alarm with minor severity
*    4. Test disk space alarm with major severity
*    5. Test disk space alarm with critical severity
*    6. Test disk space alarm with cleared severity
*    7. Test disk space alarm with indeterminate severity
*    
*    Results:
*    1.   Page should look clear and correct.
*    2-7. Alarms should be send successfully.
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test255_Tools_Alarms_Testser_sba_disk_space_alarm {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test255_Tools_Alarms_Testser_sba_disk_space_alarm(String browser) {
	  
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
  public void Tools_Alarms_Testser_sba_disk_space_alarm() throws Exception {
	    
	Log.startTestCase(this.getClass().getName() + " - Headers + info");
	
	// Set vars
	String ipSource    = "10.21.8.72";
	String description = "descripton" + testFuncs.GenerateId();
	String alarmType   = "SBA Disk Space Alarm";
		
	// login
	testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
	testFuncs.enterMenu(driver, "Tools_SBA_alarms_tester", "Send Test Alarm To The EMS");
  
	// Step 1 - check that page is opened clear and correct
	testFuncs.myDebugPrinting("Step 1 - Step 1 - check that page is opened clear and correct", testVars.logerVars.MAJOR);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div[1]/h3")							  , "Send Test Alarm To The EMS");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div[2]/div/div/div[2]/div/div[1]/label"), "Name");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div[2]/div/div/div[2]/div/div[2]/label"), "Severity Name");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div[2]/div/div/div[2]/div/div[3]/label"), "Source");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div[2]/div/div/div[2]/div/div[4]/label"), "Description");

	// Step 2 - test disk space alarm with warning severity
	testFuncs.myDebugPrinting("Step 2 - test disk space alarm with warning severity", testVars.logerVars.MAJOR);
	sendAlarmViaTester(driver, alarmType, "Warning", ipSource, description);
	
	// Step 3 - test disk space alarm with minor severity
	testFuncs.myDebugPrinting("Step 3 - test disk space alarm with minor severity", testVars.logerVars.MAJOR);
	sendAlarmViaTester(driver, alarmType, "Minor", ipSource, description);
            	
	// Step 4 - test disk space alarm with major severity
	testFuncs.myDebugPrinting("Step 4 - test disk space alarm with major severity", testVars.logerVars.MAJOR);
	sendAlarmViaTester(driver, alarmType, "Major", ipSource, description);
	
	// Step 5 - test disk space alarm with critical severity
	testFuncs.myDebugPrinting("Step 5 - test disk space alarm with critical severity", testVars.logerVars.MAJOR);
	sendAlarmViaTester(driver, alarmType, "Critical", ipSource, description);

	// Step 6 - test disk space alarm with cleared severity
	testFuncs.myDebugPrinting("Step 6 - test disk space alarm with cleared severity", testVars.logerVars.MAJOR);
	sendAlarmViaTester(driver, alarmType, "Cleared", ipSource, description);
	
	// Step 7 - test disk space alarm with indeterminate severity
	testFuncs.myDebugPrinting("Step 7 - test disk space alarm with indeterminate severity", testVars.logerVars.MAJOR);
	sendAlarmViaTester(driver, alarmType, "Indeterminate", ipSource, description);
  }
  
  // Send an alarm using a given parametsrs
  private void sendAlarmViaTester(WebDriver driver, String alName, String alSevrity, String alSource, String alDescription) {
	  
	  // Set data
	  testFuncs.myDebugPrinting("Set data", testVars.logerVars.MINOR);
	  new Select(driver.findElement(By.xpath("//*[@id='Name']"))).selectByVisibleText(alName);
	  new Select(driver.findElement(By.xpath("//*[@id='Severity']"))).selectByVisibleText(alSevrity);
	  testFuncs.mySendKeys(driver, By.xpath("//*[@id='Source']"), alSource	   , 2000);
	  testFuncs.mySendKeys(driver, By.xpath("//*[@id='Text']")  , alDescription, 2000);
	  
	  // Send the alarm
	  testFuncs.myDebugPrinting("Send the alarm", testVars.logerVars.MINOR);
	  testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div/div[3]/button"), 3000);
	  testFuncs.verifyStrBy(driver, By.xpath("/html/body/div[1]/div[5]/div/div/div[1]/h4/span")	   , "Test");
	  testFuncs.verifyStrBy(driver, By.xpath("/html/body/div[1]/div[5]/div/div/div[2]/div/h4/span"), "Failed to send test alarm.");
	  testFuncs.myClick(driver, By.xpath("/html/body/div[1]/div[5]/div/div/div[3]/button"), 3000);
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

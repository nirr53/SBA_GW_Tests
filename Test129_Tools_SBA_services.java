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
* This test tests the activate and reactivate of SBA services via the the SBA web
* -----------------
* Tests:
*    1. Stop and restart Windows Fabric Host Service
*    2. Stop and restart SNMP Agent for SBA Service
*    3. Stop and restart Skype for Business Server Replica Replicator Agent service
*    4. Stop and restart Skype for Business Server Centralized Logging Service Agent service
*    5. Stop and restart Skype for Business Server Mediation service
*    6. Restart SBA
* 
* Results:
*    1-5. All Stop & Start operation should succeed.
*    6. Restart the SBA should be end successfully.
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test129_Tools_SBA_services {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test129_Tools_SBA_services(String browser) {
	  
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
  public void Tools_SBA_services() throws Exception {
	    
	String currState = "";
	Log.startTestCase(this.getClass().getName() + " - Headers + info");
	testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
	testFuncs.enterMenu(driver, "Tools_SBA_services", "SERVICES");
  
	// Step 1 - Stop and restart Windows Fabric Host Service
	testFuncs.myDebugPrinting("Step 1 - Stop and restart Windows Fabric Host Service", testVars.logerVars.MAJOR);
	currState = driver.findElement(By.xpath("//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[1]/tr/td[2]/span/span")).getText();
	if (currState.contains("Running")) {
		
		changeServiceState(driver, "//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[1]/tr/td[3]/button/span", "//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[1]/tr/td[2]/span/span", "Stopped");
		changeServiceState(driver, "//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[1]/tr/td[3]/button/span", "//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[1]/tr/td[2]/span/span", "Running");
	} else {
		
		testFuncs.myFail("Windows Fabric Host Service is not running (" + currState + ")");
	}	
	
	// Step 2 - Stop and restart SNMP Agent for SBA Service
	testFuncs.myDebugPrinting("Step 2 - Stop and restart SNMP Agent for SBA Service", testVars.logerVars.MAJOR);
	currState = driver.findElement(By.xpath("//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[2]/tr/td[2]/span/span")).getText();
	if (currState.contains("Running")) {
		
		changeServiceState(driver, "//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[2]/tr/td[3]/button/span", "//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[2]/tr/td[2]/span/span", "Stopped");
		changeServiceState(driver, "//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[2]/tr/td[3]/button/span", "//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[2]/tr/td[2]/span/span", "Running");
	} else {
		
		testFuncs.myFail("Windows Fabric Host Service is not running (" + currState + ")");
	}
	
	 // Step 3 - Stop and restart Skype for Business Server Replica Replicator Agent service
	testFuncs.myDebugPrinting("Step 3 - Stop and restart Skype for Business Server Replica Replicator Agent service", testVars.logerVars.MAJOR);
	currState = driver.findElement(By.xpath("//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[3]/tr/td[2]/span/span")).getText();
	if (currState.contains("Running")) {
		
		changeServiceState(driver, "//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[3]/tr/td[3]/button/span", "//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[3]/tr/td[2]/span/span", "Stopped");
		changeServiceState(driver, "//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[3]/tr/td[3]/button/span", "//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[3]/tr/td[2]/span/span", "Running");
	} else {
		
		testFuncs.myFail("Windows Fabric Host Service is not running (" + currState + ")");
	}
	
	// Step 4 - Stop and restart Skype for Business Server Centralized Logging Service Agent service
	testFuncs.myDebugPrinting("Step 4 - Stop and restart Skype for Business Server Centralized Logging Service Agent service", testVars.logerVars.MAJOR);
	currState = driver.findElement(By.xpath("//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[4]/tr/td[2]/span/span")).getText();
	if (currState.contains("Running")) {
		
		changeServiceState(driver, "//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[4]/tr/td[3]/button/span", "//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[4]/tr/td[2]/span/span", "Stopped");
		changeServiceState(driver, "//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[4]/tr/td[3]/button/span", "//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[4]/tr/td[2]/span/span", "Running");
	} else {
		
		testFuncs.myFail("Windows Fabric Host Service is not running (" + currState + ")");
	}
	
//	// Step 5 - Stop and restart Skype for Business Server Mediation service
//	testFuncs.myDebugPrinting("Step 5 - Stop and restart Skype for Business Server Mediation service", testVars.logerVars.MAJOR);	
//	currState = driver.findElement(By.xpath("//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[5]/tr/td[2]/span/span")).getText();
//	if (currState.contains("Running")) {
//		
//		changeServiceState(driver, "//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[5]/tr/td[3]/button/span", "//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[5]/tr/td[2]/span/span", "Stopped");
//		changeServiceState(driver, "//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[5]/tr/td[3]/button/span", "//*[@id='contentwrapper']/section/div[1]/div/div/div[2]/table/tbody[5]/tr/td[2]/span/span", "Running");
//	} else {
//		
//		testFuncs.myFail("Windows Fabric Host Service is not running (" + currState + ")");
//	}
	
	// Step 6 - Restart SBA
	testFuncs.myDebugPrinting("Step 6 - Restart SBA", testVars.logerVars.MAJOR);
	testFuncs.myClick(driver, By.xpath("//*[@id='contentwrapper']/section/div[5]/div/div/div[2]/table/tbody/tr/td/button/span"), 2000);
	Alert alert = driver.switchTo().alert();
	testFuncs.myAssertTrue("Message was not detected (" + alert.getText() + ")", alert.getText().contains("Are you sure you want to restart the SBA?")); 
	alert.accept();
	testFuncs.myWait(2000);
	testFuncs.searchStr(driver, "Restarting please wait...");
	testFuncs.waitForString(driver, "Login to SBA Web Administration", 500000);
	testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
	testFuncs.enterMenu(driver, "Tools_SBA_services", "SERVICES");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[5]/div/div/div[2]/table/tbody/tr/td/button/span"), "Restart SBA");
  }
  
  
  // Change the state of button accorfing to given parametsr
  // driver     - given driver
  // butXpath   - xpath of the button that change service state
  // stateXpath - xpath of the state of the service
  // stateStr   - the searched state string
  private void changeServiceState(WebDriver driver, String butXpath, String stateXpath, String stateStr) {
	  
	  testFuncs.myDebugPrinting("Needed state - "  + stateStr, testVars.logerVars.MINOR);	
	  testFuncs.myClick(driver, By.xpath(butXpath), 25000);
	  testFuncs.verifyStrBy(driver, By.xpath(stateXpath), stateStr);
	  testFuncs.myDebugPrinting("Needed state was acheived - "  + stateStr, testVars.logerVars.MINOR);
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

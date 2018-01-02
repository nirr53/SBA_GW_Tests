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
* This test tests a download of logs at Configuration-Logs menu.
* -----------------
* 
* Tests:
*    1. Check that page is opened clear and correct
*    2. Try to download the sba-config-log.txt file
*    3. Try to download all the other log files
*    
*    Results:
*    1.   Page should look clear and correct.
*    2-3. File should be downloaded properly.
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test298_Tools_Configuration_Logs_download {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test298_Tools_Configuration_Logs_download(String browser) {
	  
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
  public void Tools_Configuration_Logs_download() throws Exception {
	    
	Log.startTestCase(this.getClass().getName() + " - Headers + info");
	
	// Set vars
	String curLogsName = "sba-config-log.txt";
	String hisLogsName = "-sba-config-log.txt";
	
	// login
	testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
	testFuncs.enterMenu(driver, "Tools_SBA_configuration_logs", "SBA Configuration Logs");
  
	// Step 1 - check that page is opened clear and correct
	testFuncs.myDebugPrinting("Step 1 - Step 1 - check that page is opened clear and correct", testVars.logerVars.MAJOR);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[1]/h3"), "SBA Configuration Logs");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='logfile']/thead/tr/th")		 	 , "Last Log");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='logfile']/tbody/tr/td[1]/label")	 , "log.txt");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table/tbody/tr[3]/td") 	 , "Archive");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='archive']/thead/tr/th[1]")		 	 , "Date");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='archive']/thead/tr/th[2]")		 	 , "Name");

	// Step 2 - download sba-config-log.txt
	testFuncs.myDebugPrinting("Step 2 - download sba-config-log.txt", testVars.logerVars.MAJOR);
	testFuncs.deleteFilesByPrefix(testVars.getDownloadsDir(), curLogsName);
	testFuncs.myClick(driver, By.xpath("//*[@id='logfile']/tbody/tr/td[3]/button"), 7000);
  	String fileName = testFuncs.getLatestFilefromDir(testVars.getDownloadsDir()).getName();
    testFuncs.myDebugPrinting("fileName - " + fileName, testVars.logerVars.MINOR);
    testFuncs.myAssertTrue("Downloaded failed !!\nfileName -" + fileName, fileName.equals(curLogsName));
	testFuncs.deleteFilesByPrefix(testVars.getDownloadsDir(), curLogsName);
	
	// Step 3 - download all log files one by one
	testFuncs.myDebugPrinting("Step 3 - download all log files one by one", testVars.logerVars.MAJOR);
	String bodyText     = driver.findElement(By.tagName("body")).getText();
	int filesNum = (bodyText.split(hisLogsName, -1).length-1);
	testFuncs.myDebugPrinting("filesNum - " + filesNum, testVars.logerVars.MINOR);

	// Loop on all files
	for (int i = 1; i <= filesNum; ++i) {
		
		testFuncs.myDebugPrinting(i + ". Download file:", testVars.logerVars.MINOR);
		testFuncs.deleteFilesByPrefix(testVars.getDownloadsDir(), hisLogsName);
		testFuncs.myClick(driver, By.xpath("//*[@id='archive']/tbody/tr[" + i + "]/td[4]/button"), 7000);
	  	fileName = testFuncs.getLatestFilefromDir(testVars.getDownloadsDir()).getName();
	    testFuncs.myDebugPrinting("fileName - " + fileName, testVars.logerVars.MINOR);
	    testFuncs.myAssertTrue("Downloaded failed !!\nfileName -" + fileName, fileName.contains(hisLogsName));
		testFuncs.deleteFilesByPrefix(testVars.getDownloadsDir(), hisLogsName);
	}
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

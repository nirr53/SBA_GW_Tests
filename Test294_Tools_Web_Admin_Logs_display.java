package SBA_GW_Tests;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.*;
import SBA_GW_Tests.Log;

/**
* ----------------
* This test tests a display of logs at Web-Admin-Logs menu.
* -----------------
* 
* Tests:
*    1. Check that page is opened clear and correct
*    2. Try to display the log.txt file
*    3. Try to display all the other log files
*    
*    Results:
*    1.   Page should look clear and correct.
*    2-3. File should be displayed properly.
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test294_Tools_Web_Admin_Logs_display {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test294_Tools_Web_Admin_Logs_display(String browser) {
	  
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
  public void Tools_Web_Admin_Logs_display() throws Exception {
	    
	Log.startTestCase(this.getClass().getName() + " - Headers + info");
	
	// login
	testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
	testFuncs.enterMenu(driver, "Tools_SBA_web_admin_logs", "SBA Web Admin Logs");
  
	// Step 1 - check that page is opened clear and correct
	testFuncs.myDebugPrinting("Step 1 - Step 1 - check that page is opened clear and correct", testVars.logerVars.MAJOR);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[1]/h3"), "SBA Web Admin Logs");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='logfile']/thead/tr/th")		 	 , "Last Log");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='logfile']/tbody/tr/td[1]/label")	 , "log.txt");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='tabs-1']/table/tbody/tr[3]/td") 	 , "Archive");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='archive']/thead/tr/th[1]")		 	 , "Date");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='archive']/thead/tr/th[2]")		 	 , "Name");

	// Step 2 - display log.txt
	testFuncs.myDebugPrinting("Step 2 - display log.txt", testVars.logerVars.MAJOR);
	String times[] = getTimes();
	testFuncs.myClick(driver, By.xpath("//*[@id='logfile']/tbody/tr/td[2]/button"), 7000);
	testFuncs.myClick(driver, By.xpath("//*[@id='tabs-0']/div[3]/button")		  , 7000);
	testFuncs.myClick(driver, By.xpath("//*[@id='logfile']/tbody/tr/td[2]/button"), 7000);
	String textValue = driver.findElement(By.xpath("//*[@id='tabs-2']/table/tbody/tr[2]/td/textarea")).getAttribute("value");
	testFuncs.myAssertTrue("Current times were not detected !! \ntextValue - " + textValue, textValue.contains(times[0]) ||
																							textValue.contains(times[1]) ||
																							textValue.contains(times[2]));
	testFuncs.myClick(driver, By.xpath("//*[@id='tabs-0']/div[3]/button")		  , 7000);

	
	// Step 3 - display all log files one by one
	testFuncs.myDebugPrinting("Step 3 - display all log files one by one", testVars.logerVars.MAJOR);
	String bodyText     = driver.findElement(By.tagName("body")).getText();
	int filesNum = (bodyText.split("logger_", -1).length-1);
	testFuncs.myDebugPrinting("filesNum - " + filesNum, testVars.logerVars.MINOR);

	// Loop on all files
	for (int i = 1; i <= filesNum; ++i) {
		
		testFuncs.myDebugPrinting(i + ". Open file:", testVars.logerVars.MINOR);
		testFuncs.myClick(driver, By.xpath("//*[@id='archive']/tbody/tr[" + i + "]/td[3]/button"), 7000);
		String textValue2 = driver.findElement(By.xpath("//*[@id='tabs-2']/table/tbody/tr[2]/td/textarea")).getAttribute("value");
		testFuncs.myAssertTrue("Current log was not opened !! \ntextValue - " + textValue2, textValue2.contains("WARN") || textValue2.contains("INFO"));
		testFuncs.myClick(driver, By.xpath("//*[@id='tabs-0']/div[3]/button")		  , 7000);	
	}
  }
 
  // Get local times
  private String[] getTimes() {
	  
	  Calendar c = Calendar.getInstance();
	  c.setTime(new Date());
	  String time0 = new SimpleDateFormat("HH:mm").format(c.getTime());
	  c.add(Calendar.MINUTE, 1); 
	  String time1 = new SimpleDateFormat("HH:mm").format(c.getTime());
	  c.add(Calendar.MINUTE, 1); 
	  String time2 = new SimpleDateFormat("HH:mm").format(c.getTime());
	  testFuncs.myDebugPrinting("Current searched times - " + time0 + "-" + time1 + "-" + time2, testVars.logerVars.MINOR);	
	  String times[] = {time0, time1, time2};
	  return times;
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

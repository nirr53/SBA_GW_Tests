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
* This test tests the different display oprions of logs at Configuration-Logs menu.
* -----------------
* 
* Tests:
*    1. Check that page is opened clear and correct
*    2. Select 'Display last 10 rows'  and show log.txt + history logs
*    3. Select 'Display last 20 rows'  and show log.txt + history logs
*    4. Select 'Display last 30 rows'  and show log.txt + history logs
*    5. Select 'Display last 50 rows'  and show log.txt + history logs
*    6. Select 'Display last 100 rows' and show log.txt + history logs
*    7. Select 'Display all rows'      and show log.txt + history logs
*    
*    Results:
*    1.   Page should look clear and correct.
*    2-7. Files should be displayed according to the chosen value.
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test299_Tools_Configuration_Logs_display {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test299_Tools_Configuration_Logs_display(String browser) {
	  
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
  public void Tools_Web_Admin_Logs_download() throws Exception {
	    
	Log.startTestCase(this.getClass().getName() + " - Headers + info");
	
	// Set vars
	String displayOptions[] = {"10", "20", "30", "50", "100", "All"};
	
	// login
	testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
	testFuncs.enterMenu(driver, "Tools_SBA_configuration_logs", "SBA Configuration Logs");
	String bodyText     = driver.findElement(By.tagName("body")).getText();
	int filesNum = (bodyText.split("logger_", -1).length-1);
	testFuncs.myDebugPrinting("filesNum - " + filesNum, testVars.logerVars.MINOR);
  
	// Select a display-values one by one
	testFuncs.myDebugPrinting("Select a display-values one by one", testVars.logerVars.MAJOR);
	for (int i = 0; i < displayOptions.length; ++i) {
		
		// Select value
		testFuncs.myDebugPrinting("1. Select value", testVars.logerVars.NORMAL);
		testFuncs.myDebugPrinting("The selected value is - " + displayOptions[i], testVars.logerVars.MINOR);
		new Select(driver.findElement(By.xpath("//*[@id='rows']"))).selectByVisibleText(displayOptions[i]);
		testFuncs.myWait(2000);
		
		// Show log.txt
		testFuncs.myDebugPrinting("2. Show log.txt", testVars.logerVars.NORMAL);	
		testFuncs.myClick(driver, By.xpath("//*[@id='logfile']/tbody/tr/td[2]/button"), 7000);
		testFuncs.myClick(driver, By.xpath("//*[@id='tabs-0']/div[3]/button")		  , 7000);
		testFuncs.myClick(driver, By.xpath("//*[@id='logfile']/tbody/tr/td[2]/button"), 7000);
		String textValue = driver.findElement(By.xpath("//*[@id='tabs-2']/table/tbody/tr[2]/td/textarea")).getAttribute("value");	
		testFuncs.myClick(driver, By.xpath("//*[@id='tabs-0']/div[3]/button")		  , 7000);
		int lines = (textValue.split("\n", -1).length-1);
		testFuncs.myDebugPrinting("lines - "  + lines, testVars.logerVars.MINOR);
		if (!displayOptions[i].equals("All")) {
			
			testFuncs.myAssertTrue("Lines number do not match the selected value !!", displayOptions[i].equals(String.valueOf(lines)));
		}

		// Show history logs
		testFuncs.myDebugPrinting("3. Show history logs", testVars.logerVars.NORMAL);
		for (int j = 1; j <= filesNum; ++j) {
			
			testFuncs.myDebugPrinting(j + ". Open file:", testVars.logerVars.MINOR);
			testFuncs.myClick(driver, By.xpath("//*[@id='archive']/tbody/tr[" + j + "]/td[3]/button"), 7000);
			String textValue2 = driver.findElement(By.xpath("//*[@id='tabs-2']/table/tbody/tr[2]/td/textarea")).getAttribute("value");
			int lines2 = (textValue2.split("\n", -1).length-1);
			testFuncs.myDebugPrinting("lines2 - "  + lines2, testVars.logerVars.MINOR);
			if (!displayOptions[i].equals("All")) {
				
				testFuncs.myAssertTrue("Current lines number do not match !!"
						+ "\nlines2 - "    		  + lines2
						+ "\ndisplayOptions[i] -" + displayOptions[i], displayOptions[i].equals(String.valueOf(lines2)));	
			}
			testFuncs.myClick(driver, By.xpath("//*[@id='tabs-0']/div[3]/button")		  , 7000);	
		}
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

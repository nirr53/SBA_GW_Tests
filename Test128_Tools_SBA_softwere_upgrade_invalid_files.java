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
* This test tests an invalid uploads of softwere upgrade
* -----------------
* Tests:
*    1. Enter the Softwere upgrade menu and check headers the Upload button when no-setup-file file is uploaded
*    2. Select Skype Cumulative Update and Press the Upload button when no-cu-file file is uploaded
*    3. Upload very old full-package
*
* Results:
*    1.2 An error message shpuld appear
*    3.  Upload very old full-package should fail - version numbers should not be changed
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test128_Tools_SBA_softwere_upgrade_invalid_files {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test128_Tools_SBA_softwere_upgrade_invalid_files(String browser) {
	  
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
  public void Tools_SBA_softwere_upgrade_invalid_files() throws Exception {
	    
	Log.startTestCase(this.getClass().getName() + " - Softwere upgrade invalid  files");
    String filePath   = testVars.getInpFilesDir() + "\\";
	testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
	testFuncs.enterMenu(driver, "Tools_SBA_softwere_upgrade", "SBA Upgrade");
  
	// Step 1 - Press the Upload button when no-setup-file file is uploaded
	testFuncs.myDebugPrinting("Step 1 - Press the Upload button when no-setup-file file is uploaded", testVars.logerVars.MAJOR);
	WebElement fileInput = driver.findElement(By.id("file_upload"));
	testFuncs.myDebugPrinting("fileName - " + filePath + testVars.getImportFile("128_1"), testVars.logerVars.MINOR);
	fileInput.sendKeys(filePath + testVars.getImportFile("128_1"));
	testFuncs.myClick(driver, By.xpath("//*[@id='updloadform']/div[4]/input"), 3000);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapperupload']/div/div/h4"), "Invalid file to upload");
	
	
	// Step 2 - Select Skype Cumulative Update and Press the Upload button when no-cu-file file is uploaded
	testFuncs.myDebugPrinting("Step 2 - Select Skype Cumulative Update and Press the Upload button when no-cu-file file is uploaded", testVars.logerVars.MAJOR);
	testFuncs.myClick(driver, By.xpath("//*[@id='updloadform']/div[1]/input[2]"), 1000);
	testFuncs.myClick(driver, By.xpath("//*[@id='updloadform']/div[4]/input"), 3000);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapperupload']/div/div/h4"), "Invalid file to upload");
  
	// Step 3 - Upload very old full-package
	testFuncs.myDebugPrinting("Step 3 - Upload very old full-package", testVars.logerVars.MAJOR);	
	testFuncs.myClick(driver, By.xpath("//*[@id='updloadform']/div[1]/input[1]"), 1000);
	String filePrefix = "126_2";
	testFuncs.myDebugPrinting("fileName - " + filePath + testVars.getImportFile(filePrefix), testVars.logerVars.MINOR);
	fileInput.sendKeys(filePath + testVars.getImportFile(filePrefix));	
	testFuncs.myClick(driver, By.xpath("//*[@id='updloadform']/div[4]/input"), 20000);
	
	// Verify that upload failed - numbers did not changed
	testFuncs.myDebugPrinting("Verify that upload failed - numbers did not changed", testVars.logerVars.MINOR);
	testFuncs.myClick(driver, By.xpath("//*[@id='navbar-collapse']/ul[3]/li[4]/a")		   , 2000);
	testFuncs.myClick(driver, By.xpath("//*[@id='navbar-collapse']/ul[3]/li[4]/ul/li[1]/a"), 2000);
	String text = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div/div[2]/textarea")).getText();
	testFuncs.myAssertTrue("Header was not detected !! <" + "Version: "            + testVars.getVersions()[0] + ">", text.contains("Version: "            + testVars.getVersions()[0]));
	testFuncs.myAssertTrue("Header was not detected !! <" + "SBA Version: "        + testVars.getVersions()[1] + ">", text.contains("SBA Version: "        + testVars.getVersions()[1]));
	testFuncs.myAssertTrue("Header was not detected !! <" + "Web Admin Version: "  + testVars.getVersions()[2] + ">", text.contains("Web Admin Version: "  + testVars.getVersions()[2]));
	testFuncs.myAssertTrue("Header was not detected !! <" + "SBA Config Version: " + testVars.getVersions()[3] + ">", text.contains("SBA Config Version: " + testVars.getVersions()[3]));
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

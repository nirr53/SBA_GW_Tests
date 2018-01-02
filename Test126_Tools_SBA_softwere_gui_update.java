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
* This test tests 
* -----------------
* Tests:
*    1. Enter the Softwere upgrade menu and check headers
*    2. Upload the current full-package
*    3. Verify that version numbers do not changed
* 
* Results:
*    1. Headers should be detected successfully
*    2. Upload full package should end succuessfully
*    3. Version numbers should not be changed
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Test126_Tools_SBA_softwere_gui_update {
	
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Test126_Tools_SBA_softwere_gui_update(String browser) {
	  
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
  public void Tools_SBA_softwere_upgrade() throws Exception {
	    
	Log.startTestCase(this.getClass().getName() + " - Softwere upgrade");
	testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
	testFuncs.enterMenu(driver, "Tools_SBA_softwere_upgrade", "SBA Upgrade");
    String filePath   = testVars.getInpFilesDir() + "\\";
    String filePrefix = "";
  
	// Step 1 - Enter the Softwere upgrade menu and check headers
	testFuncs.myDebugPrinting("Step 1 - Enter the Softwere upgrade menu and check headers", testVars.logerVars.MAJOR);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div[1]/div/h3"), "SBA Upgrade");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='updloadform']/div[1]")					 , "SBA GUI update");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='updloadform']/div[1]")					 , "Skype Cumulative Update and the default the CU");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='updloadform']/div[2]/label")			 , "Select file to upload:");
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='updloadform']/div[3]/label")			 , "Arguments:");
	
	// Step 2 - Upload the current full-package
	testFuncs.myDebugPrinting("Step 2 - Upload the current full-package", testVars.logerVars.MAJOR);
	filePrefix = "126_1";
	testFuncs.myAssertTrue("Version not match!" 										+
							"\n  Current package - " + testVars.getImportFile(filePrefix) +
							"\n  Current version - " + testVars.getVersions()[0],
							testVars.getImportFile("126_1").contains(testVars.getVersions()[1]));
	WebElement fileInput = driver.findElement(By.id("file_upload"));
	testFuncs.myDebugPrinting("fileName - " + filePath + testVars.getImportFile(filePrefix), testVars.logerVars.MINOR);
	fileInput.sendKeys(filePath + testVars.getImportFile(filePrefix));
	testFuncs.myClick(driver, By.xpath("//*[@id='updloadform']/div[4]/input"), 20000);
	testFuncs.verifyStrBy(driver, By.xpath("//*[@id='exetable']/tbody/tr/td[5]/span"), "Success");
	testFuncs.myAssertTrue("Icon was not detected !!", driver.findElement(By.xpath("//*[@id='exetable']/tbody/tr/td[1]/i")).getAttribute("class").contains("successColor"));
  
	// Step 3 - Verify that version numbers do not changed
	testFuncs.myDebugPrinting("Step 3 - Verify that version numbers do not changed", testVars.logerVars.MAJOR);
	testFuncs.myClick(driver, By.xpath("//*[@id='navbar-collapse']/ul[3]/li[4]/a")		   , 2000);
	testFuncs.myClick(driver, By.xpath("//*[@id='navbar-collapse']/ul[3]/li[4]/ul/li[1]/a"), 2000);
	String text = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div/div[2]/textarea")).getText();
	testFuncs.myAssertTrue("Header was not detected !! <" + "Domain: cloudbond365B.com" 					   + ">", text.contains("Domain: cloudbond365B.com"));
	testFuncs.myAssertTrue("Header was not detected !! <" + "Last Boot UpTime:" 							   + ">", text.contains("Last Boot UpTime:"));
	testFuncs.myAssertTrue("Header was not detected !! <" + "BuildNumber:" 									   + ">", text.contains("BuildNumber:"));
	testFuncs.myAssertTrue("Header was not detected !! <" + "Name: "               + testVars.getSysUsername() + ">", text.contains("Name: "               + testVars.getSysUsername()));
	testFuncs.myAssertTrue("Header was not detected !! <" + "DNS Host Name: "      + testVars.getSbaHostName() + ">", text.contains("DNS Host Name: "      + testVars.getSbaHostName()));
	testFuncs.myAssertTrue("Header was not detected !! <" + "Version: "            + testVars.getVersions()[0] + ">", text.contains("Version: "            + testVars.getVersions()[0]));
	testFuncs.myAssertTrue("Header was not detected !! <" + "SBA Version: "        + testVars.getVersions()[1] + ">", text.contains("SBA Version: "        + testVars.getVersions()[1]));
	testFuncs.myAssertTrue("Header was not detected !! <" + "Web Admin Version: "  + testVars.getVersions()[2] + ">", text.contains("Web Admin Version: "  + testVars.getVersions()[2]));
	testFuncs.myAssertTrue("Header was not detected !! <" + "SBA Config Version: " + testVars.getVersions()[3] + ">", text.contains("SBA Config Version: " + testVars.getVersions()[3]));
	testFuncs.myClick(driver, By.xpath("/html/body/div[1]/div[2]/div/div/div[3]/button"), 3000);
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

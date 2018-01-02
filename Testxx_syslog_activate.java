package SBA_GW_Tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.Inet4Address;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.*;
import SBA_GW_Tests.Log;

/**
* ----------------
* This test tests the activate/deactivate of the Syslog mechnism
* -----------------
* Tests:
*    1.
* 
* Results:
*    1. 
* 
* @author Nir Klieman
* @version 1.00
*/

@RunWith(Parameterized.class)
public class Testxx_syslog_activate {
	
  public static int 	testIdx     = 0;
  public String         usedBrowser = "";
  private WebDriver 	driver;
  private StringBuffer  verificationErrors = new StringBuffer();
  SBAGlobalVars 		testVars;
  SBAGlobalFuncs		testFuncs;
  
  // Default constructor for print the name of the used browser 
  public Testxx_syslog_activate(String browser) {
	  
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
  public void Syslog_enable_disable() throws Exception {
	  
	  
	  Log.startTestCase(this.getClass().getName() + " - Headers + info");
	  
	  // Set vars
	  String currIp      = Inet4Address.getLocalHost().getHostAddress();
	  String currPort    = "514";
//	  String captureName = System.getProperty("user.dir") + "\\" + "sysTest_" + testFuncs.GenerateId() + ".pcap";
	  String captureName = "sysTest_" + testFuncs.GenerateId() + ".pcap";
//	  String captureName = "sysTest_" + "153042" + ".txt";


	
	  // Login
	  testFuncs.myDebugPrinting("Step 1: Login and enter the Syslog menu", testVars.logerVars.MAJOR);
	  testFuncs.login(driver, testVars.getSysUsername(), testVars.getSysPassword(), testVars.getSbaInitStr(), testVars.getSbaAfLoginStr());	
	  testFuncs.enterMenu(driver, "Tools_SBA_syslog", "Syslog Settings");
	  
	  // Step 1 - check that page is opened clear and correct
	  testFuncs.myDebugPrinting("Step 1 - Step 1 - check that page is opened clear and correct", testVars.logerVars.MAJOR);
	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[1]/h3"), "Syslog Settings");
	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyform']/div[1]/div[1]/label")			   , "Enable");
	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyform']/div[1]/div[2]/label")			   , "Server");
	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='parsleyform']/div[1]/div[4]/label")			   , "Port");
	  
	  
	  // Check if Wireshark enviroment installed
	  testFuncs.myDebugPrinting("Check if Wireshark enviroment installed", testVars.logerVars.MAJOR);
	  File f = new File(testVars.getTsharkPath());
	  testFuncs.myAssertTrue("Wireshark is not installed !!", f.exists() && !f.isDirectory());
 
	  // Step 2 - enable the syslog mechnism
	  testFuncs.myDebugPrinting("Step 2 - enable the syslog mechnism", testVars.logerVars.MAJOR);	
	  if (driver.findElement(By.xpath("//*[@id='enablesyslog']")).isSelected()) {
		  
		  testFuncs.myDebugPrinting("checkbox is checked !!", testVars.logerVars.MINOR);		
	  }	else {
		  
		  testFuncs.myDebugPrinting("checkbox is not checked !!", testVars.logerVars.MINOR);
		  testFuncs.myClick(driver, By.xpath("//*[@id='enablesyslog']")				, 2000);
	  }
	  testFuncs.mySendKeys(driver, By.xpath("//*[@id='parsleyform']/div[1]/div[2]/input"), currIp  , 2000);
	  testFuncs.mySendKeys(driver, By.xpath("//*[@id='parsleyform']/div[1]/div[4]/input"), currPort, 2000);
	  testFuncs.myClick(driver, By.xpath("//*[@id='parsleyform']/div[2]/button"), 2000);
	  testFuncs.verifyStrBy(driver, By.xpath("//*[@id='contentwrapper']/section/div/div/div/div[2]/div/div/div/div[1]/h4"), "Syslog Settings successfully changed!");  

//	  // Activate the Tshark
//	  testFuncs.myDebugPrinting("Activate the Tshark", testVars.logerVars.NORMAL);	
//	  new ProcessBuilder(testVars.getTsharkPath(), "-w " + captureName, "-c 10000").start();
//	  testFuncs.myDebugPrinting("Capture ended !! (capturename - " + captureName + ")", testVars.logerVars.MINOR);
//	  testFuncs.myWait(30000);
//  	File[] dirFiles = new File(System.getProperty("user.dir") + "\\").listFiles();
//  	int filesNum = dirFiles.length;
//  	for (int i = 0; i < filesNum; i++) {
//	
//		testFuncs.myDebugPrinting("-->" + dirFiles[i].getName() + "<--",  testVars.logerVars.MINOR);
//  	    if (dirFiles[i].getName().startsWith(" sysTest_", 0)) {
//  	    	
//  			testFuncs.myDebugPrinting(testFuncs.readFile(System.getProperty("user.dir") + "\\" + dirFiles[i].getName()), testVars.logerVars.MINOR);
//  			break;
//  	    }
//  	}
	  
	  Runnable r1 = new Runnable() {
		  public void run() {
		    while (true) {
		        System.out.println("Hello, world!");
		        testFuncs.myWait(1000);
		      }
		  }
		};
		
		Runnable r2 = new Runnable() {
		  public void run() {
		    while (true) {
		        System.out.println("Goodbye, " +
				"cruel world!");
		        testFuncs.myWait(1000);
		      }
		  }
		};
		
		
		class SomeTask implements Runnable
		{
			int gg;
		  public SomeTask(int i) {
			  	this.gg = i;
			  	}

		@Override
		  public void run()
		  {
			  System.out.println(gg);
			  try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		}
		
		Thread thr1 = new Thread(r1);
		Thread thr2 = new Thread(r2);
		
		new Thread(new Runnable() {
		    public void run() {
		        System.out.println("Look ma, no hands");
		    }
		}).start();

		new Thread(new Runnable() {
		    public void run() {
		        System.out.println("Look at me, look at me...");
		    }
		}).start();
		
		
		  ExecutorService executor = Executors.newCachedThreadPool();
		  
		  ExecutorService service = Executors.newFixedThreadPool(3);

		  
		  for (int i = 0; i < 3; i++) {
			  service.execute(new SomeTask(i));
		  }

		
		  testFuncs.myWait(20000);



	 
	  
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

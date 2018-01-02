package SBA_GW_Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.DesiredCapabilities;
import static org.junit.Assert.*;

/**
* This class holds all the functions which been used by the tests
* @author Nir Klieman
* @version 1.00
*/

public class SBAGlobalFuncs {
	
	  /**
	  *  webUrl  	  - default url for the used funcs
	  *  StringBuffer - default string for errors buffering
	  */
	  SBAGlobalVars 	   testVars;
	  SBAMenuPaths         testMenuPaths;
	  private String	   webUrl;
	  
	  @SuppressWarnings("unused")
	  private StringBuffer verificationErrors = new StringBuffer();

	  /**
	  *  Default constructor
	  */
	  public SBAGlobalFuncs() {
		  
		  testVars 		= new SBAGlobalVars();
		  testMenuPaths = new SBAMenuPaths();
		  webUrl        = testVars.getUrl();
	  }
	   
	  /**
	  * Set a driver according to a given browser name
	  * @param  usedBrowser - given browser name (I.e. Chrome, FireFox or IE)
	  * @return driver      - driver object
	  */
	  public WebDriver defineUsedBrowser(String usedBrowser) {
			
		// Set a driver according to given browser-name
		if 		  (usedBrowser.equals(testVars.CHROME)) {
			
			return new ChromeDriver();
			
		} else if (usedBrowser.equals(testVars.FF))     {
			
			System.setProperty("webdriver.gecko.driver", testVars.getGeckoPath());
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("browser.download.folderList", 2);
			profile.setPreference("browser.download.dir", testVars.getDownloadsDir());
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream");
			profile.setPreference("pdfjs.disabled", true);  
			return new FirefoxDriver(profile);
			
		} else if (usedBrowser.equals(testVars.IE))     {  
			
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability("requireWindowFocus", true);   	
			return new InternetExplorerDriver(capabilities);
		}  else                                        {
			
			myFail ("The browser type is invalid - <" + usedBrowser +">");
		}
		
		return null;
	  }
	  
	  /**
	  *  Click on given element by given xpath and waits a given timeout
	  *  @param driver  - given element
	  *  @param byType  - given By element (By xpath, name or id)
	  *  @param timeout - given timeout value (Integer)
	  */
	  public void myClick(WebDriver driver, By byType, int timeout) {
		  
	      driver.findElement(byType).click();
		  myWait(timeout);
	  }
	  
	  /**
	  *  Generate a random ID based on current time
	  *  @return id - random id sting
	  */
	  public String GenerateId() {
		  
		DateFormat dateFormat = new SimpleDateFormat("HH_mm_ss");
	  	Date date  = new Date();
	  	String id  = dateFormat.format(date);
	  	id         = id.replaceAll("_", "");
		myDebugPrinting("id - " + id, testVars.logerVars.MAJOR);
		
		return id;
	  }
	  
	  /**
	  *  read file method
	  *  @param  path    - given path for file to read
	  *  @return content - string of readed file
	  */  
	  String readFile(String path) {
			  		    
		  String content = null;		    
		  File file = new File(path);		    
		  FileReader reader = null;	    
		  try {
			    	      
			  reader = new FileReader(file);        
			  char[] chars = new char[(int) file.length()];
			  reader.read(chars);
			  content = new String(chars);	        
			  reader.close(); 
		  } catch (IOException e) {
			  
			  myDebugPrinting("e - " + e, testVars.logerVars.DEBUG);
		  } finally {
			    	       
			  if(reader != null) {
     	
				  try {
			        				        	
					  reader.close();
				  } catch (IOException e) {}
			  } else {
				  
				  myDebugPrinting("reader == null", testVars.logerVars.DEBUG); 
			  }
		  }
		  myWait(3000);
		  return content; 
	  }
	  
	  /**
	  *  Wrap assertTrue with logger
	  *  @param errorStr  - error message for display at the logger
	  *  @param condition - condition for mark if the assert succeeded or not
	  */
	  public void myAssertTrue(String errorStr, Boolean condition) {
		  
		  if (!condition) {
			  
			  myFail(errorStr);  
		  }
	  }
	  
	  /**
	  *  login method
	  *  @param driver   	  - given driver for make all tasks
	  *  @param currUsr       - username for login
	  *  @param currPswd      - password for login
	  *  @param sbaInitStr    - given string for accsses the sba site (before login) 
	  *  @param sbaAfLoginStr - given string for verify good login (after login) 
	  */
	  public void login(WebDriver 	driver, String currUsr, String currPswd, String sbaInitStr, String sbaAfLoginStr) {
		  	  
		  myDebugPrinting("Login details:", testVars.logerVars.MAJOR);
		  myDebugPrinting("webUrl   - " + webUrl 	   , testVars.logerVars.NORMAL);
		  myDebugPrinting("email    - " + currUsr	   , testVars.logerVars.NORMAL);
		  myDebugPrinting("password - " + currPswd     , testVars.logerVars.NORMAL);
		  myDebugPrinting("mainStr  - " + sbaInitStr   , testVars.logerVars.NORMAL);
		  myDebugPrinting("mainStr  - " + sbaAfLoginStr, testVars.logerVars.NORMAL);
		  
		  // Get the main page
		  driver.get(webUrl);
		  searchStr(driver, sbaInitStr);
		  myWait(3000);
		  
		  // Login and verify
		  mySendKeys(driver, By.name("username")		  , currUsr , 2000);
		  mySendKeys(driver, By.name("password")		  , currPswd, 2000);
		  myClick(driver, By.xpath("//*[@id='submitbtn']"), 1000); 
		  searchStr(driver, sbaAfLoginStr);  
	  }
	  
	  /**
	  *  Send a string to a given elemnent using given parameters
	  *  @param driver  - given driver
	  *  @param byType  - given By element (By xpath, name or id)
	  *  @param currUsr - given string to send
	  *  @param timeout - given timeout (Integer)
	  */
	  public void mySendKeys(WebDriver driver, By byType, String currUsr, int timeOut) {
		  
		  driver.findElement(byType).clear();
		  driver.findElement(byType).sendKeys(currUsr);
		  myWait(timeOut);
	  }
	  
	  /**
	  *  Logout from the system
	  *  @param driver - given driver
	  */
	  public void logout(WebDriver driver) {
		  
		  myClick(driver, By.xpath("//*[@id='navbar-collapse']/ul[3]/li[4]/a"), 2000); 
		  myClick(driver, By.xpath("//*[@id='navbar-collapse']/ul[3]/li[4]/ul/li[3]/a"), 1000); 
		  searchStr(driver, testVars.getSbaInitStr());
	  }
	  
	  /**
	  *  Verify string in page based on read the whole page
	  *  @param driver  - given driver
	  *  @param strName - given string for detect
	  */
	  public void searchStr(WebDriver 	driver, String strName) {
		  
		  String bodyText     = driver.findElement(By.tagName("body")).getText();
		  if (bodyText.contains(strName)) {
			  myDebugPrinting(strName + " was detected !!",  testVars.logerVars.MINOR);
		  } else {
			  myFail(strName + " was not detected !! \nbodyText - " + bodyText);
		  }
	  }
	  
	  /**
	  *  Delete all files in directory by given prefix
	  *  @param dir    - given directory path
	  *  @param prefix - given prefix
	  */
	  public void deleteFilesByPrefix(String dir, String prefix) {
    	
		myDebugPrinting("dir    - " + dir   ,  testVars.logerVars.MINOR);
		myDebugPrinting("prefix - " + prefix,  testVars.logerVars.MINOR);
    	File[] dirFiles = new File(dir).listFiles();
    	int filesNum = dirFiles.length;
    	for (int i = 0; i < filesNum; i++) {
    		
    	    if (dirFiles[i].getName().startsWith(prefix, 0)) {
    	    	
    			myDebugPrinting("Delete file - " + dirFiles[i].getName(),  testVars.logerVars.MINOR);
    	        new File(dir + "\\" + dirFiles[i].getName()).delete();
    	    }
    	}	
	  }
	  
	  /**
	  *  Find files in a given directory by a given prefix
	  *  @param dir    - given directory path
	  *  @param prefix - a given prefix
	  *  @return       - TRUE if files were found (NULL otherwise)
	  */
	  public String findFilesByGivenPrefix(String dir, String prefix) {
    	
		myDebugPrinting("dir    - " + dir   ,  testVars.logerVars.MINOR);
		myDebugPrinting("prefix - " + prefix,  testVars.logerVars.MINOR);
    	File[] dirFiles = new File(dir).listFiles();
    	int filesNum = dirFiles.length;
    	for (int i = 0; i < filesNum; i++) {
    		
    	    if (dirFiles[i].getName().startsWith(prefix, 0)) {
    	    	
    			myDebugPrinting("Find a file ! (" + dirFiles[i].getName() + ")",  testVars.logerVars.MINOR);
    	        return dirFiles[i].getName();
    	    }
    	}
    	
    	return null;
	  }
	
	  /**
	  *  Highlight given element
	  *  @param driver  - given driver
	  *  @param element - given element
	  */
	  public void markElemet(WebDriver 	driver, WebElement element) {	
		  
	    try {
	    	((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid yellow'", element);
			TimeUnit.MILLISECONDS.sleep(200);
		
	    } catch (InterruptedException e1) {}
	   ((JavascriptExecutor)driver).executeScript("arguments[0].style.border=''", element);
	  }
	
	  /**
	  *  Sleep for a given time
	  *  @param sleepValue - given sleep factor
	  */
	  public void myWait(int sleepValue) {
			
	    try 							{ Thread.sleep(sleepValue);  }
	    catch (InterruptedException e1) {					   }
	  }
	  
	  /**
	  *  Print a given string to the console
	  *  @param str   - given string to print
	  *  @param level - given print level (MAJOR, NORMAL, MINOR, DEBUG)
	  */
	  public void myDebugPrinting(String str, int level) {
			
		String spaces = testVars.logerVars.debug[level];
		Log.info(spaces + str);
	  }
	 
	  /**
	  *  Print a given string to the console with default level of MAJOR
	  *  @param str - given string to print
	  */
	  public void myDebugPrinting(String str) {
			
		String spaces = testVars.logerVars.debug[testVars.logerVars.MAJOR];
		Log.info(spaces + str);	
	  }
	
	  /**
	  *  Global error function
	  *  @param errorStr - given error message
	  */
	  public void myFail(String errorStr) {
		
		Log.error(errorStr);
		fail(errorStr);
	  }
	  
	  /**
	  *  Search a string according to given parameters
	  *  @param driver  - given driver
	  *  @param element - given element path (By xpath, id or name)
	  *  @param strName - given string for search
	  */
	  public void verifyStrBy(WebDriver driver, By element, String strName) {
		  
		  if (driver.findElements(element).size() > 0) {
			  markElemet(driver, driver.findElement(element));
			  String elemStr = driver.findElement(element).getText();
			  if (!elemStr.contains(strName)) {
				  myFail("Xpath element not contains given string !!\nCurrent string - " + elemStr + "\nSearched string - " + strName);
			  }
			  
		  } else {
			  myFail("The given path <" + element + "> was not detected !!");
			  
		  }
	  }

	  /**
	  *  Enter a menu
	  *  @param driver     - given driver for make all tasks
	  *  @param menuName   - given menu name
	  *  @param menuHeader - given menu header
	  */
	  public void enterMenu(WebDriver 	driver, String menuName, String menuHeader) {
		  		  
		  String paths[] = testMenuPaths.getPaths(menuName);
		  int length = paths.length;
		  for (int i = 0; i < length; ++i) {
			  
			  if (paths[i].isEmpty()) {
				  
				  break;
			  }
			  myDebugPrinting("curr path - " + paths[i],  testVars.logerVars.MINOR);
			  myClick(driver, By.xpath(paths[i]), 5000);
		  }
		  searchStr(driver, menuHeader);
      }
	  
	  /**
	  *  Get last file save on given path
	  *  @param  dirPath - directory path
	  *  @return  		 - last modified file-name or NULL
	  */
	  public File getLatestFilefromDir(String dirPath) {
		
	    File dir = new File(dirPath);
	    File[] files = dir.listFiles();
	    if (files == null || files.length == 0) {
	    	
	        return null;
	    }
	    File lastModifiedFile = files[0];
	    for (int i = 1; i < files.length; i++) {
	    	
	       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
	    	   
	           lastModifiedFile = files[i];
	       }
	    }
	    
	    return lastModifiedFile;
	  }
	  
	  /**
	  *  Wait till a given string appears on the screen
	  *  @param driver     - given driver for make all tasks
	  *  @param wantedStr  - string we wait for appear on the screen
	  *  @param maxTimeout - maximum timeout for wait (in ms)
	  */
	  public void waitForString(WebDriver driver, String wantedStr, int maxTimeout) {
		
		String bodyText  = "";
		int idx = 0;
		while (true) {
			
			bodyText = driver.findElement(By.tagName("body")).getText();  
			if (bodyText.contains(wantedStr)) {
				
				myDebugPrinting("Wanted string (" + wantedStr + ") was detected. Loop is stopped.", testVars.logerVars.MAJOR);
				break;
			} else {
				
				myDebugPrinting("Wanted string (" + wantedStr + ") was not detected. Keep waiting. (" + (idx / 1000) + ")", testVars.logerVars.MAJOR);
				myWait(1000);
				idx += 1000;
				if (idx > maxTimeout) {
					
					myFail("Timeout (" + (maxTimeout / 1000) + ") reached, and string not detected !!\n bodyText - " + bodyText);
				}	
			}
		}
	  }
}
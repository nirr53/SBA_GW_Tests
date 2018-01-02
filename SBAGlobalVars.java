package SBA_GW_Tests;


/**
* This class holds all the functions which been used by the SBA tests
* @author Nir Klieman
* @version 1.00
*/

public class SBAGlobalVars {
	
	/**
	*  Login details:
	*  	    url  		 	  - Default initial url for access the system
	*  		sbaUsername   	  - Default username for access the SBA via a Permit user
	*  		sbaPassword   	  - Default password for access the SBA via a Permit user
	*  		sbaRTCUniUsername  - Default username that belongs to RTCUniversalSBATechnicians group
	*  		sbaRTCUniPassword  - Default password that belongs to RTCUniversalSBATechnicians group
	*  		serUsername   	  - Default username for access the system via Service
	*  		sbaHostName		  - SBA host name
	*  		sbaInitStr 		  - Default string for search when access the SBA page (before login)
	*  		sbaAfLoginStr	  - Default string for search when login the SBA system (after login)
	*  
	*  Versions:
	*  		version		  	 - Current version number
	*  		sbaVersion		 - Current SBA version number
	*  		webAdminVersion	 - Current Web Admin version number
	*  		sbaConfigVersion - Current SBA config version number
	*  
	*  Browsers-List  	  - List of used browsers.
	*  		CHROME   	  	  -	Default Chrome browser name
	*  		FF   	  	  	  -	Default Firefox browser name
	*  		IE   	  	  	  -	Default Internet Explorer browser name
	*  
	*  General data
	*  downloadsDir  	  - Default location for the Download-files directory
	*  tsharkPath		  - Default location for the Tshark application
	*/
    public   LogVars 	logerVars;
	private  String		sbcIp				= "10.21.8.72";
    
    // Login variables
    private  String   	url  		   		= "http://" + sbcIp + "/login.php";
    private  String   	sbaUsername    		= "sba";
	private  String   	sbaPassword    		= "1q2w3e$r";
    private  String   	sbaRTCUniUsername   = "SBA_M1K_MA121.1";
	private  String   	sbaRTCUniPassword   = "1q2w3e$r";
	private  String 	sbaInitStr 			= "Login to SBA Web Administration";
	private  String 	sbaAfLoginStr		= "SBA for       SKYPE FOR BUSINESS 2015";
	public   String 	sbaHostName			= "QA-SFB-SBA-872";
	
	// Version variables
	private  String 	version			    = "6.3.9600";
	private  String 	sbaVersion			= "7.2.101";
	private  String 	webAdminVersion		= "7.2.101.42545";
	private  String 	sbaConfigVersion	= "7.2.27.42590";

	// Browser paths and variables
	private  String   	chromeDrvPath  		= "C:\\Users\\nirk\\Desktop\\Selenium\\chromedriver_win32\\chromedriver.exe";
	private  String     ieDrvPath      		= "C:\\Users\\nirk\\Desktop\\Selenium\\IEDriverServer_x64_2.53.1\\IEDriverServer.exe";
	private  String     geckoPath      	    = "C:\\Users\\nirk\\Desktop\\Selenium\\geckodriver-v0.11.1-win64\\geckodriver.exe";
	public   String 	CHROME  	   		= "Chrome";
	public   String 	FF 			   		= "Firefox";
	public 	 String 	IE			   		= "IE";
	private  Object[][] browsersList   	    = {{CHROME}};
	
	// Paths
	private  String     downloadsDir;
	private  String     inputFilesDir		= "C:\\Users\\nirk\\Desktop\\myEclipseProjects\\SBA_Tests_0\\inputFiles"; 
	private  String 	srcBadSBASftUpgrd   = "sba_update_bad_import_file.txt";
	private  String 	srcCurrFullPackage  = "SBA Setup 7.2.24.exe";
	private  String 	srcOldFullPackage   = "SBA Setup 7.2.20.exe";
	private  String 	tsharkPath 			= "C:\\Program Files\\Wireshark\\tshark.exe";
	
    /**
    *  Non-default constructor for provide another data
    *  @param _url  	Non-default url for access the system
	*  @param _username Non-default username for access the system
	*  @param _password Non-default password for access the system
	*  @param _mainStr  Non-default string for verify good access
    */
	public SBAGlobalVars(String _url, String _username, String _password, String _mainStr) {
		
		Log.info("GlobalVars constructor is called");
    	this.logerVars     = new LogVars();
		this.url 		   = _url;
		this.sbaUsername   = _username;
		this.sbaPassword   = _password;
		this.downloadsDir  = "C:\\Users\\" + System.getProperty("user.name") + "\\Downloads";
	}
	
    /**
    *  Default constructor for provide interface
    */
    public SBAGlobalVars() {
    	
    	this.logerVars    = new LogVars();
		this.downloadsDir  = "C:\\Users\\" + System.getProperty("user.name") + "\\Downloads";
	}
    
	/**
    *  Default method for return the url variable
    *  @return url of the system
    */
	public String getUrl() {
		
		return this.url;
	}	

    /**
    *  Default method for return the permit username variable
    *  @return sbaUsername
    */
	public String getSysUsername() {
		
		return this.sbaUsername;
	}
	
    /**
    *  Default method for return the permit password variable
    *  @return sbaPassword
    */
	public String getSysPassword() {
		
		return this.sbaPassword;
	}
	
    /**
    *  Default method for return the username variable that belongs to RTCUniversalSBATechnicians group
    *  @return sbaUsername
    */
	public String getSysRTCUniUsername() {
		
		return this.sbaRTCUniUsername;
	}
	
    /**
    *  Default method for return the permit password variable that belongs to RTCUniversalSBATechnicians group
    *  @return sbaPassword
    */
	public String getSysRTCUniPassword() {
		
		return this.sbaRTCUniPassword;
	}
	
    /**
    *  Default method for return the gecko driver path (the external driver for FF)
    *  @return ieDrvPath of the system
    */
	public String getGeckoPath() {
		
		return this.geckoPath;
	}
	
    /**
    *  Default method for return the default Downloads directory path
    *  @return downloadsDir of the system
    */
	public String getDownloadsDir() {
		
		return this.downloadsDir;
	}
	
    /**
    *  Default method for return the default Tshark path
    *  @return tsharkPath of the system
    */
	public String getTsharkPath() {
		
		return this.tsharkPath;
	}
	
    /**
    *  Default method for return the ip of the connected SBC
    *  @return sbcIp of the system
    */
	public String getSbcIp() {
		
		return this.sbcIp;
	}
	
    /**
    *  Default method for return the Chrome driver path
    *  @return chromeDrvPath of the system
    */
	public String getChromeDrvPath() {
		
		return this.chromeDrvPath;
	}
	
    /**
    *  Default method for return the IE driver path
    *  @return ieDrvPath of the system
    */
	public String getIEDrvPath() {
		
		return this.ieDrvPath;
	}
	
    /**
    *  Default method for return the Initial string for search when access the SBA system
    *  @return sbaInitStr
    */
	public String getSbaInitStr() {
		
		return this.sbaInitStr;
	}
	
    /**
    *  Default method for return the Initial string for search after login the SBA system
    *  @return sbaAfLoginStr
    */
	public String getSbaAfLoginStr() {
		
		return this.sbaAfLoginStr;
	}
	
    /**
    *  Default method for return the used browsers in the current test
    *  @return browsersList
    */
	public Object[][] getBrowsers() {
		
		return this.browsersList;
	}
	
    /**
    *  Default method for return the SBA-Host-name
    *  @return sbaAfLoginStr
    */
	public String getSbaHostName() {
		
		return this.sbaHostName;
	}
	
    /**
    *  Default method for return Version numbers
    *  The returned array is:
    *   	[Version-number],
    *   	[SBA Version number],
    *   	[Web Admin config version],
    *   	[SBA config version]
    *  I.e. version[0] will get the Version number etc.
    *  @return versions
    */
	public String[] getVersions() {
		
		String[] versions = {version, sbaVersion, webAdminVersion, sbaConfigVersion};
		return versions;
	}
	
    /**
    *  Default method for get the path of the input-files directory
    *  @return sbaAfLoginStr
    */

	public String getInpFilesDir() {
		
		return this.inputFilesDir;
	}
	
	/**
	*  Default method for return a name of source file by given integer
	*  @param  idx Index of the current test in the format of <test>.<sub-test>
	*  @return String that represent name of the used file
	*/
	public String getImportFile(String idx) {
			
		String usedSrcFile = "";
		SBAGlobalFuncs testFuncs = new SBAGlobalFuncs();
		switch (idx) {
		
			case "126_1":
				usedSrcFile = this.srcCurrFullPackage;
				break;
			case "126_2":
				usedSrcFile = this.srcOldFullPackage;
				break;
			case "128_1":
				 usedSrcFile = this.srcBadSBASftUpgrd;
				 break;
				  
			default:
				testFuncs.myFail("The given idx (" + idx + ") is not recognized !!");
		}
		testFuncs.myDebugPrinting("The usedSrcFile is - " + usedSrcFile, logerVars.MINOR);
		return usedSrcFile;
	}


}
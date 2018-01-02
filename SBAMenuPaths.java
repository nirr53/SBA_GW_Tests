package SBA_GW_Tests;

public class SBAMenuPaths {
	
	public String[] getPaths(String menuName) {
		
		// Monitor section headers
		String MONITOR_PERFORMANCE_SECTION      = "//*[@id='left-nav']/ul/li[2]/a/span";
		String MONITOR_PERFORMANCE_KEY_HEALTH  = "//*[@id='left-nav']/ul/li[2]/ul/li[3]/a/span[1]";	
		
		// Setup section headers
		String SETUP_SECTION               		= "//*[@id='navbar-collapse']/ul[1]/li[1]/a";
		String SETUP_NETWORK					= "//*[@id='left-nav']/ul/li/ul/li[1]/a/span[1]";
		String SETUP_SBA_SETUP					= "//*[@id='left-nav']/ul/li/ul/li[2]/a/span[1]";	
		String SETUP_CHANGE_ADMIN_PASSWORD		= "//*[@id='left-nav']/ul/li/ul/li[3]/a/span[1]";
		String SETUP_DATE_TIME					= "//*[@id='left-nav']/ul/li/ul/li[4]/a/span[1]";	
		String SETUP_SNMP						= "//*[@id='left-nav']/ul/li/ul/li[5]/a/span[1]";	
		String SETUP_SBA_CERTIFICATE			= "//*[@id='left-nav']/ul/li/ul/li[6]/a/span[1]";	
		String SETUP_SBC_GW_CERTIFICATE			= "//*[@id='left-nav']/ul/li/ul/li[7]/a/span[1]";	
		
		// Tools section headers
		String TOOLS_SECTION               		= "//*[@id='navbar-collapse']/ul[1]/li[3]/a";
		String TOOLS_SBA_SOFTWERE_UPGRADE		= "//*[@id='left-nav']/ul/li/ul/li[1]/a/span[1]";
		String TOOLS_SBA_SERVICES				= "//*[@id='left-nav']/ul/li/ul/li[2]/a/span[1]";
		
		String TOOLS_SYSLOG						= "//*[@id='left-nav']/ul/li/ul/li[3]/a/span[1]";

		
		
		String TOOLS_WEB_ADMIN_LOGS				= "//*[@id='left-nav']/ul/li/ul/li[4]/a/span[1]";
		String TOOLS_CONFIGURATION_LOGS			= "//*[@id='left-nav']/ul/li/ul/li[5]/a/span[1]";
		String TOOLS_SBA_ALARMS_TESTER			= "//*[@id='left-nav']/ul/li/ul/li[7]/a/span[1]";	
		
		String[] paths = {"", "", "", ""};
		SBAGlobalFuncs testFuncs = new SBAGlobalFuncs(); 
    	testFuncs.myDebugPrinting("menuName - " + menuName);
		switch (menuName) {
		
			// Setup menus
        	case "Setup_Network":
        		paths[0] = SETUP_SECTION;
        		paths[1] = SETUP_NETWORK;
            	break;
        	case "Setup_SBA_setup":
        		paths[0] = SETUP_SECTION;
        		paths[1] = SETUP_SBA_SETUP;
            	break;
        	case "Setup_Date_time":
        		paths[0] = SETUP_SECTION;
        		paths[1] = SETUP_DATE_TIME;
            	break;       	
        	case "Setup_SNMP":
        		paths[0] = SETUP_SECTION;
        		paths[1] = SETUP_SNMP;
            	break; 	
           	case "Setup_Change_admin_password":
        		paths[0] = SETUP_SECTION;
        		paths[1] = SETUP_CHANGE_ADMIN_PASSWORD;
            	break; 	
           	case "Setup_SBA_certificate":
        		paths[0] = SETUP_SECTION;
        		paths[1] = SETUP_SBA_CERTIFICATE;
            	break; 
           	case "Setup_SBC_gw_certificate":
        		paths[0] = SETUP_SECTION;
        		paths[1] = SETUP_SBC_GW_CERTIFICATE;
            	break; 
            	
            // Monitor menus
         	case "Monitor_Key_health_indicators":
        		paths[0] = MONITOR_PERFORMANCE_SECTION;
        		paths[1] = MONITOR_PERFORMANCE_KEY_HEALTH;
            	break;         	
            	
            // Tools menus
	        case "Tools_SBA_softwere_upgrade":
	            paths[0] = TOOLS_SECTION;
	            paths[1] = TOOLS_SBA_SOFTWERE_UPGRADE;
	            break;
	        case "Tools_SBA_services":
	            paths[0] = TOOLS_SECTION;
	            paths[1] = TOOLS_SBA_SERVICES;
	            break; 
	        case "Tools_SBA_configuration_logs":   	
	            paths[0] = TOOLS_SECTION;
	            paths[1] = TOOLS_CONFIGURATION_LOGS;
	            break;   
	        case "Tools_SBA_syslog":   	
	            paths[0] = TOOLS_SECTION;
	            paths[1] = TOOLS_SYSLOG;
	            break;      
	        case "Tools_SBA_web_admin_logs":   	
	            paths[0] = TOOLS_SECTION;
	            paths[1] = TOOLS_WEB_ADMIN_LOGS;
	            break;  
	        case "Tools_SBA_alarms_tester":   	
	            paths[0] = TOOLS_SECTION;
	            paths[1] = TOOLS_SBA_ALARMS_TESTER;
	            break;
				
			default:
				testFuncs.myFail("Menu name <" + menuName + "> is not recognized !!");    
		}		
		return paths;      
     }		
}

package SBA_GW_Tests;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	
	Test110_Toolbar.class,
	Test111_Setup_change_admin_password_valid_password.class		,
	Test126_Tools_SBA_softwere_gui_update.class						,
	Test128_Tools_SBA_softwere_upgrade_invalid_files.class			,
	Test129_Tools_SBA_services.class								,
	Test131_Setup_SNMP_trap_destinations.class						,
	Test205_Setup_SNMP_invalid_string.class							,
	Test216_Tools_Alarms_Testser_sba_services_status_alarm.class	,
	Test217_Tools_Alarms_Testser_sba_cpu_status_alarm.class			,
	Test218_Tools_Alarms_Testser_sba_memory_status_alarm.class		,
	Test255_Tools_Alarms_Testser_sba_disk_space_alarm.class			,
	Test256_Tools_Alarms_Testser_certificate_expired_alarm.class	,
	Test257_Tools_Alarms_Testser_perf_counter_alarm.class			,
	Test27_Setup_SBA_certificate_automatic.class					,
	Test294_Tools_Web_Admin_Logs_display.class						,
	Test295_Tools_Web_Admin_Logs_download.class						,
	Test296_Tools_Web_Admin_Rows_display.class						,
	Test297_Tools_Configuration_Logs_display.class					,
	Test298_Tools_Configuration_Logs_download.class					,
	Test299_Tools_Configuration_Logs_display.class					,
	Test34_Setup_SBA_certificate_immediatly.class					,
	Test35_Setup_SBA_certificate_manual.class						,
	Test44_Login.class												,
	Test45_Monitor_dashboard.class									,
	Test46_Monitor_active_registered_users.class					,
	Test47_Monitor_active_registered_endpoints.class				,
	Test48_Monitor_outbound_calls.class								,
	Test49_Monitor_inbound_calls.class								,
	Test50_Monitor_cpu.class										,
	Test51_Monitor_memory.class										,
	Test52_Monitor_sbc_gateway.class								,
	Test53_Active_alarms.class										,
	Test61_Monitor_key_health_indicators.class						,
	Test65_Setup_different_network_connections.class				,
	Test66_Timeout.class											,
	Test67_Setup_SBA_setup.class									,
	Test68_Setup_SBA_setup_activation.class							,
	Test70_Setup_Change_admin_password_invalid_password.class		,
	Test71_Setup_Date_time_valid_date_time.class					,
	Test72_Setup_Date_time_invalid_date_time.class					,
	Test73_Setup_Add_SNMP.class										,
	TestSuite.class													,
	Testxx_syslog_activate.class									,
	XXX_Test108_Setup_SBC_gw_add_certificate.class					,
	XXX_Test109_Setup_SBC_gw_request_certificate.class
	
})

public class TestSuite {

}

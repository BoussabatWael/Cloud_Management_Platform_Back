package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import com.gcs.cmp.entity.Core_Logs;


public interface Core_Logs_Service {

	public Core_Logs addCore_Logs(Core_Logs core_Logs) ;
	public List<Core_Logs> getCore_LogsList(Long account_id);
	public List<Core_Logs> getLogsListByUserID(Long account_id, Long user_id);
	public List<Core_Logs> getUserLogsList(Long account_id, Long user_id);
	public List<Core_Logs> getProviderLogsList(Long account_id, Long provider_id);
	public List<Core_Logs> getServerLogsList(Long account_id, Long server_id);
	public List<Core_Logs> getDomainNamesLogsList(Long account_id, Long domain_id);
	public List<Core_Logs> getApplicationLogsList(Long account_id, Long application_id);
	public List<Core_Logs> getBackupLogsList(Long account_id, Long backup_id);
	public Optional<Core_Logs> findCore_LogsById(Long id) ;
	public String deleteCore_Logs(Long id) ;
}

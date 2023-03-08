package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;
import com.gcs.cmp.entity.Core_Api_Logs;

public interface Core_Api_Logs_Service {

	public Core_Api_Logs addCore_Api_Logs(Core_Api_Logs core_Api_Logs) ;
	public List<Core_Api_Logs> getCore_Api_LogsList(Long account_id);
	public Optional<Core_Api_Logs> findCore_Api_LogsById(Long id) ;
	public String deleteCore_Api_Logs(Long id) ;
	
}

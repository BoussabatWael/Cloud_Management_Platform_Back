package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gcs.cmp.entity.Core_Api_Logs;

public interface Core_Api_Logs_Repo extends JpaRepository<Core_Api_Logs, Long>{

	@Query(value="SELECT core_api_logs.* FROM core_api_logs INNER JOIN api_keys on core_api_logs.apikey_id = api_keys.id WHERE api_keys.account_id=?1 AND api_keys.status IN (1,2,3)",nativeQuery=true)
	public List<Core_Api_Logs> getCore_Api_LogsList(Long account_id);
	
}

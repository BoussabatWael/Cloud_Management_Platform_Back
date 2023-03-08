package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gcs.cmp.entity.Core_Logs;


public interface Core_Logs_Repo extends JpaRepository<Core_Logs, Long> {

	@Query(value="SELECT core_logs.* FROM core_logs INNER JOIN core_users ON core_logs.user_id = core_users.id WHERE core_users.account_id=?1 AND core_users.status IN (1,2,3)",nativeQuery=true)
	public List<Core_Logs> getCore_LogsList(Long account_id);
	
	@Query(value="SELECT a.* FROM core_logs a INNER JOIN core_users b ON a.user_id = b.id WHERE b.account_id=?1 AND b.status IN (1,2,3) AND a.user_id=?2",nativeQuery=true)
	public List<Core_Logs> getLogsListByUserID(Long account_id, Long user_id);
	
	@Query(value="SELECT a.* FROM core_logs a LEFT JOIN core_users b ON b.id = a.element_id AND a.element = 1 LEFT JOIN core_users_security c ON c.id = a.element_id AND a.element= 2 LEFT JOIN core_users_permissions d ON d.id = a.element_id AND a.element = 18 LEFT JOIN core_users_instances e ON e.id = a.element_id AND a.element = 19 LEFT JOIN core_users_tokens f ON f.id = a.element_id AND a.element = 21 WHERE b.account_id=?1 AND (a.element IN (1,2,18,19,21) AND a.element_id =?2 AND a.source IN(2,3)) OR c.user_id =?2 OR d.user_id =?2 OR e.user_id =?2 OR f.user_id =?2",nativeQuery=true)
	public List<Core_Logs> getUserLogsList(Long account_id, Long user_id);
	
	@Query(value="SELECT a.* FROM core_logs a LEFT JOIN providers b ON b.id = a.element_id AND a.element = 7 LEFT JOIN inventory_instances c ON c.id = a.element_id AND a.element= 3 LEFT JOIN cloud_providers_accounts k ON k.provider_id= b.id OR (k.id=c.cloud_provider_id AND a.source IN(4,5)) LEFT JOIN core_users d ON d.id = a.user_id WHERE d.account_id =?1 AND d.status IN (1,2,3) AND (a.element IN (3,7) AND a.element_id =?2 AND a.source IN(4,5)) OR b.id =?2 OR (c.cloud_provider_id=k.id AND k.provider_id=?2)",nativeQuery=true)
	public List<Core_Logs> getProviderLogsList(Long account_id, Long provider_id);
	
	@Query(value="SELECT a.* FROM core_logs a LEFT JOIN inventory_servers b ON b.id = a.element_id AND a.element = 20 LEFT JOIN inventory_applications_instances c ON c.id = a.element_id AND a.element= 14 LEFT JOIN core_users d ON d.id = a.user_id LEFT JOIN backup_instances e ON e.id = a.element_id AND a.element= 17 LEFT JOIN core_access_credentials f ON f.id = a.element_id AND a.element= 6 AND f.element=1 LEFT JOIN inventory_servers z ON z.id = f.element_id WHERE d.account_id=?1 AND d.status IN (1,2,3) AND ((a.element=3 AND a.element_id=?2) OR (a.element=20 AND b.instance_id=?2 AND a.source IN (7,8)) OR (c.instance_id=?2 AND a.element= 14 AND a.source IN (7,8)) OR (e.instance_id=?2 AND a.element=17) OR (a.element=6 AND z.instance_id=?2 AND f.element_id=z.id))",nativeQuery=true)
	public List<Core_Logs> getServerLogsList(Long account_id, Long instance_id);
	
	@Query(value="SELECT a.* FROM core_logs a LEFT JOIN networks_domain_names b ON b.id = a.element_id AND a.element = 8 AND a.element = 10 LEFT JOIN networks_hosts k ON k.id = a.element_id AND a.element = 9 LEFT JOIN networks_ssl_certificates c ON c.id = a.element_id AND a.element= 11 LEFT JOIN core_users d ON d.id = a.user_id WHERE d.account_id =?1 AND d.status IN (1,2,3) AND (a.element IN (8,9,10,11) AND a.element_id=?2 AND a.source IN(9,10)) OR b.id=?2 OR c.domain_id =?2 OR k.domain_id =?2 OR a.element = 10",nativeQuery=true)
	public List<Core_Logs> getDomainNamesLogsList(Long account_id, Long domain_id);
	
	@Query(value="SELECT a.* FROM core_logs a LEFT JOIN inventory_applications b ON b.id = a.element_id AND a.element = 12 LEFT JOIN inventory_applications_dependencies k ON k.id = a.element_id AND a.element = 13 LEFT JOIN inventory_applications_instances c ON c.id = a.element_id AND a.element= 14 LEFT JOIN core_users d ON d.id = a.user_id LEFT JOIN inventory_applications_sources f ON f.id = a.element_id AND a.element= 15 LEFT JOIN inventory_applications_versions j ON j.id = a.element_id AND a.element= 4 WHERE d.account_id =?1 AND d.status IN (1,2,3) AND (a.element IN (12,13,14,15) AND a.element_id =?2 AND a.source IN(11,12)) OR (c.application_id=?2 AND a.source = 12) OR f.application_id=?2 OR k.application_id =?2 OR j.application_id =?2",nativeQuery=true)
	public List<Core_Logs> getApplicationLogsList(Long account_id, Long application_id);
	
	@Query(value="SELECT a.* FROM core_logs a LEFT JOIN backup_operations b ON b.id = a.element_id AND a.element = 16 LEFT JOIN backup_executions c ON c.id = a.element_id AND a.element = 5 AND a.source=13 LEFT JOIN core_users d ON d.id = a.user_id LEFT JOIN backup_instances e ON e.id = a.element_id AND a.element = 17 AND a.source=13 LEFT JOIN backup_executions z ON z.id = e.backup_id WHERE d.account_id =?1 AND d.status IN (1,2,3) AND (a.element IN(5,16,17) AND a.element_id =?2 AND a.source IN(13,14)) OR c.operation_id=?2 OR (e.backup_id=z.id AND z.operation_id=?2)",nativeQuery=true)
	public List<Core_Logs> getBackupLogsList(Long account_id, Long backup_op_id);
	
}

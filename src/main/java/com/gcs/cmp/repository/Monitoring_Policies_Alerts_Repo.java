package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gcs.cmp.entity.Monitoring_Policies_Alerts;

public interface Monitoring_Policies_Alerts_Repo extends JpaRepository<Monitoring_Policies_Alerts, Long>{

	@Query(value="SELECT monitoring_policies_alerts.* FROM monitoring_policies_alerts INNER JOIN monitoring_policies ON monitoring_policies_alerts.policy_id = monitoring_policies.id WHERE monitoring_policies.account_id=?1 AND monitoring_policies.status IN (1,2,3) AND monitoring_policies_alerts.status IN (1,2,3)",nativeQuery=true)
	public List<Monitoring_Policies_Alerts> getMonitoring_Policies_AlertsList(Long account_id);
	
}

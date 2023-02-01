package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gcs.cmp.entity.Monitoring_Automations;

public interface Monitoring_Automations_Repo extends JpaRepository<Monitoring_Automations, Long>{
	
	@Query(value="SELECT * FROM monitoring_automations WHERE account_id=?1 AND status IN (1,2,3)",nativeQuery=true)
	public List<Monitoring_Automations> getMonitoring_AutomationsList(Long account_id);
}

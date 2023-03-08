package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gcs.cmp.entity.Monitoring_Policies;

public interface Monitoring_Policies_Repo extends JpaRepository<Monitoring_Policies, Long>{

	@Query(value="SELECT * FROM monitoring_policies WHERE account_id=?1 AND status IN (1,2,3)",nativeQuery=true)
	public List<Monitoring_Policies> getMonitoring_PoliciesList(Long account_id);
	
}

package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gcs.cmp.entity.Monitoring_Metrics;

public interface Monitoring_Metrics_Repo extends JpaRepository<Monitoring_Metrics, Long>{

	@Query(value="SELECT * FROM monitoring_metrics WHERE account_id=?1 AND status IN (1,2,3)",nativeQuery=true)
	public List<Monitoring_Metrics> getMonitoring_MetricsList(Long account_id);
	
}

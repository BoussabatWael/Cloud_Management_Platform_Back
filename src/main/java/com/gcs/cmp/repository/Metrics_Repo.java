package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gcs.cmp.entity.Metrics;

public interface Metrics_Repo extends JpaRepository<Metrics, Long>{

	@Query(value="SELECT * FROM metrics WHERE status IN (1,2,3)",nativeQuery=true)
	public List<Metrics> getMetricsList();
}

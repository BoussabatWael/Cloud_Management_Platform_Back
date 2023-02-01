package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gcs.cmp.entity.Core_Users_Metrics;

public interface Core_Users_Metrics_Repo extends JpaRepository<Core_Users_Metrics, Long>{

	@Query(value="SELECT core_users_metrics.* FROM core_users_metrics INNER JOIN core_users ON core_users_metrics.user_id = core_users.id WHERE core_users.account_id=?1 AND core_users.status IN (1,2,3) AND core_users_metrics.status IN (1,2,3)",nativeQuery=true)
	public List<Core_Users_Metrics> getCore_Users_MetricsList(Long account_id);
	
}

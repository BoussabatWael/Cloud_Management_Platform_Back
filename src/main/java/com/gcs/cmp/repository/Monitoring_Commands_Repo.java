package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gcs.cmp.entity.Monitoring_Commands;

public interface Monitoring_Commands_Repo extends JpaRepository<Monitoring_Commands, Long>{

	@Query(value="SELECT * FROM monitoring_commands WHERE account_id=?1 AND status IN (1,2,3)",nativeQuery=true)
	public List<Monitoring_Commands> getMonitoring_CommandsList(Long account_id);
}

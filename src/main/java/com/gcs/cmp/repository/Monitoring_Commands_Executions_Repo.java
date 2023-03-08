package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gcs.cmp.entity.Monitoring_Commands_Executions;

public interface Monitoring_Commands_Executions_Repo extends JpaRepository<Monitoring_Commands_Executions, Long>{

	@Query(value="SELECT a.* FROM monitoring_commands_executions a INNER JOIN monitoring_commands b ON a.command_id=b.id INNER JOIN inventory_instances c ON c.id = a.instance_id WHERE b.account_id=?1 AND c.account_id=?1 AND a.status IN (1,2,3) AND b.status IN (1,2,3) AND c.status IN (1,2,3)",nativeQuery=true)
	public List<Monitoring_Commands_Executions> getMonitoring_Commands_ExecutionsList(Long account_id);
	
}

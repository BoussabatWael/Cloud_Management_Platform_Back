package com.gcs.cmp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gcs.cmp.entity.Backup_Executions;

public interface Backup_Executions_Repo extends JpaRepository<Backup_Executions, Long>{

	@Query(value="SELECT backup_executions.* FROM backup_executions INNER JOIN backup_operations on backup_executions.operation_id = backup_operations.id WHERE backup_operations.account_id=?1 AND backup_operations.status IN (1,2,3) AND backup_executions.status IN (1,2,3)",nativeQuery=true)
	public List<Backup_Executions> findBackup_ExecutionsList(Long account_id);
	
	@Query(value="SELECT a.* FROM backup_executions a INNER JOIN backup_operations b ON a.operation_id = b.id INNER JOIN backup_instances c ON a.id = c.backup_id INNER JOIN inventory_instances d ON d.id = c.instance_id WHERE b.account_id=?1 AND b.status IN (1,2,3) AND a.operation_id=?2 AND a.status IN (1,2,3) AND c.status IN (1,2,3) AND d.account_id=?1 AND d.status IN (1,2,3)",nativeQuery=true)
	public Optional<Backup_Executions> getBackup_ExecutionsByOperationID(Long account_id, Long operation_id);
}

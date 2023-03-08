package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gcs.cmp.entity.Backup_Operations;

public interface Backup_Operations_Repo extends JpaRepository<Backup_Operations, Long>{

	@Query(value="select * from backup_operations where status IN (1,2,3) AND account_id=?1",nativeQuery=true)
	public List<Backup_Operations> findBackup_OperationsList(Long account_id);
	
	@Query(value="SELECT a.* FROM backup_operations a INNER JOIN backup_executions b ON b.operation_id = a.id INNER JOIN backup_instances c ON b.id = c.backup_id INNER JOIN inventory_instances d ON d.id = c.instance_id WHERE a.account_id =?1 AND a.status IN (1,2,3) AND b.status IN (1,2,3) AND c.status IN (1,2,3) AND c.instance_id =?2 AND d.account_id =?1 AND d.status IN (1,2,3)",nativeQuery=true)
	public List<Backup_Operations> getBackup_OperationsByInstanceID(Long account_id, Long instance_id);
	
}

package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gcs.cmp.entity.Backup_Instances;

public interface Backup_Instances_Repo extends JpaRepository<Backup_Instances, Long>{

	@Query(value="SELECT b_ins.* FROM backup_instances b_ins INNER JOIN inventory_instances inv_ins ON b_ins.instance_id = inv_ins.id WHERE b_ins.status IN (1,2,3) AND inv_ins.status IN (1,2,3) AND inv_ins.account_id=?1",nativeQuery=true)
	public List<Backup_Instances> getBackup_InstancesList(Long account_id);
}

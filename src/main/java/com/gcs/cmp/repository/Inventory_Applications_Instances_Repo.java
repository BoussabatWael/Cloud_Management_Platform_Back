package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gcs.cmp.entity.Inventory_Applications_Instances;


public interface Inventory_Applications_Instances_Repo extends JpaRepository<Inventory_Applications_Instances, Long>{

	@Query(value="SELECT a.* FROM inventory_applications_instances a INNER JOIN inventory_applications b  ON a.application_id = b.id INNER JOIN inventory_instances c ON a.instance_id = c.id WHERE b.account_id=?1 AND c.account_id=?1 AND a.status IN (1,2,3) AND b.status IN (1,2,3) AND c.status IN (1,2,3)",nativeQuery=true)
	public List<Inventory_Applications_Instances> getInventory_Applications_InstancesList(Long account_id);
	
	@Query(value="SELECT a.* FROM inventory_applications_instances a INNER JOIN inventory_instances b ON a.instance_id = b.id INNER JOIN inventory_applications c ON a.application_id = c.id WHERE a.status IN (1,2,3) AND a.application_id =?2 AND b.account_id =?1 AND b.status IN (1,2,3) AND c.account_id =?1 AND c.status IN (1,2,3)",nativeQuery=true)
	public List<Inventory_Applications_Instances> getInventoryApplicationsByApplicationID(Long account_id, Long application_id);
	
	@Query(value="SELECT a.* FROM inventory_applications_instances a INNER JOIN inventory_applications b ON a.application_id=b.id WHERE b.account_id=?1 AND b.status IN (1,2,3) AND a.status IN (1,2,3) AND a.instance_id=?2",nativeQuery=true)
	public List<Inventory_Applications_Instances> getInventoryApplicationsByInstanceID(Long account_id, Long instance_id);
	
}

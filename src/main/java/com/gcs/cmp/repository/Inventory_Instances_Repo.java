package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gcs.cmp.entity.Inventory_Instances;


public interface Inventory_Instances_Repo extends JpaRepository<Inventory_Instances, Long>{

	@Query(value="SELECT a.* FROM inventory_instances a INNER JOIN cloud_providers_accounts b ON a.cloud_provider_id = b.id WHERE b.status IN (1,2,3) AND a.account_id=?1 AND a.status IN (1,2,3)",nativeQuery=true)
	public List<Inventory_Instances> getInventory_InstancesList(Long account_id);
	
	@Query(value="SELECT a.* FROM inventory_instances a INNER JOIN cloud_providers_accounts b ON a.cloud_provider_id = b.id WHERE a.status IN (1,2,3) AND a.account_id=?1 AND b.provider_id=?2",nativeQuery=true)
	public List<Inventory_Instances> getInstancesNumber(Long account_id, Long provider_id);
	
	@Query(value="SELECT COUNT(*) FROM inventory_instances WHERE status IN (1,2,3) AND account_id=?1",nativeQuery=true)
	public Object getAllInstancesNumber(Long account_id);
}

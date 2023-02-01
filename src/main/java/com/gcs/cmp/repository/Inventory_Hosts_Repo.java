package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gcs.cmp.entity.Inventory_Hosts;


public interface Inventory_Hosts_Repo extends JpaRepository<Inventory_Hosts, Long>{

	@Query(value="SELECT a.* FROM inventory_hosts a INNER JOIN cloud_providers_accounts b ON a.cloud_provider_id = b.id WHERE b.status IN (1,2,3) AND a.account_id=?1 AND a.status IN (1,2,3)",nativeQuery=true)
	public List<Inventory_Hosts> getInventory_HostsList(Long account_id);
}

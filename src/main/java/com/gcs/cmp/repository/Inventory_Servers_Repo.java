package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gcs.cmp.entity.Inventory_Servers;


public interface Inventory_Servers_Repo extends JpaRepository<Inventory_Servers, Long>{

	@Query(value="SELECT inventory_servers.* FROM inventory_servers INNER JOIN inventory_instances ON inventory_servers.instance_id = inventory_instances.id WHERE inventory_instances.account_id=?1 AND inventory_instances.status IN (1,2,3)",nativeQuery=true)
	public List<Inventory_Servers> getInventory_ServersList(Long account_id);
	
}

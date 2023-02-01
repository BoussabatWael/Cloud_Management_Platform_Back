package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gcs.cmp.entity.Inventory_Servers_Versions;


public interface Inventory_Servers_Versions_Repo extends JpaRepository<Inventory_Servers_Versions, Long>{

	@Query(value="SELECT inventory_servers_versions.* FROM inventory_servers_versions INNER JOIN inventory_servers ON inventory_servers_versions.server_id = inventory_servers.id INNER JOIN inventory_instances ON inventory_servers.instance_id = inventory_instances.id WHERE inventory_instances.account_id=?1 AND inventory_instances.status IN (1,2,3) AND inventory_servers_versions.status IN (1,2,3)",nativeQuery=true)
	public List<Inventory_Servers_Versions> getInventory_Servers_VersionsList(Long account_id);
}

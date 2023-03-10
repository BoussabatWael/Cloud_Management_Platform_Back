package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gcs.cmp.entity.Inventory_Applications_Sources;


public interface Inventory_Applications_Sources_Repo extends JpaRepository<Inventory_Applications_Sources, Long>{

	@Query(value="SELECT inventory_applications_sources.* FROM inventory_applications_sources INNER JOIN inventory_applications ON inventory_applications_sources.application_id = inventory_applications.id WHERE inventory_applications.account_id=?1 AND inventory_applications.status IN (1,2,3) AND inventory_applications_sources.status IN (1,2,3)",nativeQuery=true)
	public List<Inventory_Applications_Sources> getInventory_Applications_SourcesList(Long account_id);
	
	@Query(value="SELECT a.* FROM inventory_applications_sources a INNER JOIN inventory_applications b ON a.application_id = b.id WHERE a.application_id=?2 AND a.status IN (1,2,3) AND b.account_id=?1 AND b.status IN (1,2,3)",nativeQuery=true)
	public List<Inventory_Applications_Sources> getApplications_SourcesListByApplicationID(Long account_id, Long application_id);
	
}

package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gcs.cmp.entity.Inventory_Applications_Versions;


public interface Inventory_Applications_Versions_Repo extends JpaRepository<Inventory_Applications_Versions, Long>{

	@Query(value="SELECT inventory_applications_versions.* FROM inventory_applications_versions INNER JOIN inventory_applications ON inventory_applications_versions.application_id = inventory_applications.id WHERE inventory_applications.account_id=?1 AND inventory_applications.status IN (1,2,3) AND inventory_applications_versions.status IN (1,2,3)",nativeQuery=true)
	public List<Inventory_Applications_Versions> getInventory_Applications_VersionsList(Long account_id);
	
	@Query(value="SELECT a.* FROM inventory_applications_versions a INNER JOIN inventory_applications b ON a.application_id = b.id WHERE b.account_id=?1 AND b.status IN (1,2,3) AND a.application_id=?2 AND a.status IN (1,2,3)",nativeQuery=true)
	public List<Inventory_Applications_Versions> getApplications_VersionsListByApplicationID(Long account_id, Long application_id);
}

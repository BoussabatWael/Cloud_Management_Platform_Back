package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import com.gcs.cmp.entity.Inventory_Applications_Versions;

public interface Inventory_Applications_Versions_Service {

	public Inventory_Applications_Versions addInventory_Applications_Versions(Inventory_Applications_Versions inventory_Applications_Versions) ;
	public List<Inventory_Applications_Versions> getInventory_Applications_VersionsList(Long account_id);
	public List<Inventory_Applications_Versions> getApplications_VersionsListByApplicationID(Long account_id, Long application_id);
	public Inventory_Applications_Versions updateInventory_Applications_Versions(Inventory_Applications_Versions inventory_Applications_Versions);
	public Optional<Inventory_Applications_Versions> findInventory_Applications_VersionsById(Long id) ;
	public String deleteInventory_Applications_Versions(Long id) ;
}

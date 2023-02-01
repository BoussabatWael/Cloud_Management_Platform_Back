package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import com.gcs.cmp.entity.Inventory_Applications_Sources;

public interface Inventory_Applications_Sources_Service {

	public Inventory_Applications_Sources addInventory_Applications_Sources(Inventory_Applications_Sources inventory_Applications_Sources) ;
	public List<Inventory_Applications_Sources> getInventory_Applications_SourcesList(Long account_id);
	public List<Inventory_Applications_Sources> getApplications_SourcesListByApplicationID(Long account_id, Long application_id);
	public Inventory_Applications_Sources updateInventory_Applications_Sources(Inventory_Applications_Sources inventory_Applications_Sources);
	public Optional<Inventory_Applications_Sources> findInventory_Applications_SourcesId(Long id) ;
	public String deleteInventory_Applications_Sources(Long id) ;
}

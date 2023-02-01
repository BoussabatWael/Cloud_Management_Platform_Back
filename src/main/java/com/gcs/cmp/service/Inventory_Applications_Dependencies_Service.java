package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;
import com.gcs.cmp.entity.Inventory_Applications_Dependencies;

public interface Inventory_Applications_Dependencies_Service {

	public Inventory_Applications_Dependencies addInventory_Applications_Dependencies(Inventory_Applications_Dependencies inventory_Applications_Dependencies) ;
	public List<Inventory_Applications_Dependencies> getInventory_Applications_DependenciesList(Long account_id);
	public List<Inventory_Applications_Dependencies> getApplications_DependenciesListByApplicationID(Long account_id, Long application_id);
	public Inventory_Applications_Dependencies updateInventory_Applications_Dependencies(Inventory_Applications_Dependencies inventory_Applications_Dependencies);
	public Optional<Inventory_Applications_Dependencies> findInventory_Applications_DependenciesById(Long id) ;
	public String deleteInventory_Applications_Dependencies(Long id) ;
}
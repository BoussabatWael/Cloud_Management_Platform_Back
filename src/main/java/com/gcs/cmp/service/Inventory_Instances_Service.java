package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import com.gcs.cmp.entity.Inventory_Instances;

public interface Inventory_Instances_Service {

	public Inventory_Instances addInventory_Instances(Inventory_Instances inventory_Instances) ;
	public List<Inventory_Instances> getInventory_InstancesList(Long account_id);
	public List<Inventory_Instances> getInstancesNumber(Long account_id, Long provider_id);
	public Object getAllInstancesNumber(Long account_id);
	public Inventory_Instances updateInventory_Instances(Inventory_Instances inventory_Instances);
	public Optional<Inventory_Instances> findInventory_InstancesById(Long id) ;
	public String deleteInventory_Instances(Long id) ;
	
}

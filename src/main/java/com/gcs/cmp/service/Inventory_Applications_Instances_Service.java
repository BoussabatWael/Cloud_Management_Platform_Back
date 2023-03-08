package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import com.gcs.cmp.entity.Inventory_Applications_Instances;

public interface Inventory_Applications_Instances_Service {

	public Inventory_Applications_Instances addInventory_Applications_Instances(Inventory_Applications_Instances inventory_Applications_Instances) ;
	public List<Inventory_Applications_Instances> getInventory_Applications_InstancesList(Long account_id);
	public List<Inventory_Applications_Instances> getInventoryApplicationsByApplicationID(Long account_id, Long application_id);
	public List<Inventory_Applications_Instances> getInventoryApplicationsByInstanceID(Long account_id, Long instance_id);
	public Inventory_Applications_Instances updateInventory_Applications_Instances(Inventory_Applications_Instances inventory_Applications_Instances);
	public Optional<Inventory_Applications_Instances> findInventory_Applications_InstancesById(Long id) ;
	public String deleteInventory_Applications_Instances(Long id) ;
	
}

package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import com.gcs.cmp.entity.Inventory_Hosts;

public interface Inventory_Hosts_Service {

	public Inventory_Hosts addInventory_Hosts(Inventory_Hosts inventory_Hosts) ;
	public List<Inventory_Hosts> getInventory_HostsList(Long account_id);
	public Inventory_Hosts updateInventory_Hosts(Inventory_Hosts inventory_Hosts);
	public Optional<Inventory_Hosts> findInventory_HostsById(Long id) ;
	public String deleteInventory_Hosts(Long id) ;
}

package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import com.gcs.cmp.entity.Inventory_Servers_Versions;

public interface Inventory_Servers_Versions_Service {

	public Inventory_Servers_Versions addInventory_Servers_Versions(Inventory_Servers_Versions inventory_Servers_Versions) ;
	public List<Inventory_Servers_Versions> getInventory_Servers_VersionsList(Long account_id);
	public Inventory_Servers_Versions updateInventory_Servers_Versions(Inventory_Servers_Versions inventory_Servers_Versions);
	public Optional<Inventory_Servers_Versions> findInventory_Servers_VersionsById(Long id) ;
	public String deleteInventory_Servers_Versions(Long id) ;
}

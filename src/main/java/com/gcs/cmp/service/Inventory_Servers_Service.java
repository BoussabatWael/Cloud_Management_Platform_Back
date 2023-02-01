package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import com.gcs.cmp.entity.Inventory_Servers;

public interface Inventory_Servers_Service {

	public Inventory_Servers addInventory_Servers(Inventory_Servers inventory_Servers) ;
	public List<Inventory_Servers> getInventory_ServersList(Long account_id);
	public Inventory_Servers updateInventory_Servers(Inventory_Servers inventory_Servers);
	public Optional<Inventory_Servers> findInventory_ServersById(Long id) ;
	public String deleteInventory_Servers(Long id) ;
}

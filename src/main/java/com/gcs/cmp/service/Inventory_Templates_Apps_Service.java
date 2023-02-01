package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;
import com.gcs.cmp.entity.Inventory_Templates_Apps;

public interface Inventory_Templates_Apps_Service {

	public Inventory_Templates_Apps addInventory_Templates_Apps(Inventory_Templates_Apps inventory_Templates_Apps) ;
	public List<Inventory_Templates_Apps> getInventory_Templates_AppsList(Long account_id);
	public Inventory_Templates_Apps updateInventory_Templates_Apps(Inventory_Templates_Apps inventory_Templates_Apps);
	public Optional<Inventory_Templates_Apps> findInventory_Templates_AppsById(Long id) ;
	public String deleteInventory_Templates_Apps(Long id) ;
}

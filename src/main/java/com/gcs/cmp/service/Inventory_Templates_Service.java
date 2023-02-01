package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;
import com.gcs.cmp.entity.Inventory_Templates;

public interface Inventory_Templates_Service {

	public Inventory_Templates addInventory_Templates(Inventory_Templates inventory_Templates) ;
	public List<Inventory_Templates> getInventory_TemplatesList(Long account_id);
	public Inventory_Templates updateInventory_Templatess(Inventory_Templates inventory_Templates);
	public Optional<Inventory_Templates> findInventory_TemplatesById(Long id) ;
	public String deleteInventory_Templates(Long id) ;
}

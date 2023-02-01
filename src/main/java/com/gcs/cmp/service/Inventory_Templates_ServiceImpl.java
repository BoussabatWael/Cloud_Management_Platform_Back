package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Inventory_Templates;
import com.gcs.cmp.repository.Inventory_Templates_Repo;

@Service
public class Inventory_Templates_ServiceImpl implements Inventory_Templates_Service{

	@Autowired
	private Inventory_Templates_Repo inventory_Templates_Repo;
	
	@Override
	public Inventory_Templates addInventory_Templates(Inventory_Templates inventory_Templates) {
		// TODO Auto-generated method stub
		return inventory_Templates_Repo.save(inventory_Templates);
	}

	@Override
	public List<Inventory_Templates> getInventory_TemplatesList(Long account_id) {
		// TODO Auto-generated method stub
		return inventory_Templates_Repo.getInventory_TemplatesList(account_id);
	}

	@Override
	public Inventory_Templates updateInventory_Templatess(Inventory_Templates inventory_Templates) {
		// TODO Auto-generated method stub
		return inventory_Templates_Repo.save(inventory_Templates);
	}

	@Override
	public Optional<Inventory_Templates> findInventory_TemplatesById(Long id) {
		// TODO Auto-generated method stub
		return inventory_Templates_Repo.findById(id);
	}

	@Override
	public String deleteInventory_Templates(Long id) {
		try {
			// TODO Auto-generated method stub
			inventory_Templates_Repo.deleteById(id);
			return "Template "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
		
	}

}

package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Inventory_Templates_Apps;
import com.gcs.cmp.repository.Inventory_Templates_Apps_Repo;

@Service
public class Inventory_Templates_Apps_ServiceImpl implements Inventory_Templates_Apps_Service{

	@Autowired
	private Inventory_Templates_Apps_Repo inventory_Templates_Apps_Repo;
	
	@Override
	public Inventory_Templates_Apps addInventory_Templates_Apps(Inventory_Templates_Apps inventory_Templates_Apps) {
		// TODO Auto-generated method stub
		return inventory_Templates_Apps_Repo.save(inventory_Templates_Apps);
	}

	@Override
	public List<Inventory_Templates_Apps> getInventory_Templates_AppsList(Long _account_id) {
		// TODO Auto-generated method stub
		return inventory_Templates_Apps_Repo.getInventory_Templates_AppsList(_account_id);
	}

	@Override
	public Inventory_Templates_Apps updateInventory_Templates_Apps(Inventory_Templates_Apps inventory_Templates_Apps) {
		// TODO Auto-generated method stub
		return inventory_Templates_Apps_Repo.save(inventory_Templates_Apps);
	}

	@Override
	public Optional<Inventory_Templates_Apps> findInventory_Templates_AppsById(Long id) {
		// TODO Auto-generated method stub
		return inventory_Templates_Apps_Repo.findById(id);
	}

	@Override
	public String deleteInventory_Templates_Apps(Long id) {
		try {
			// TODO Auto-generated method stub
			inventory_Templates_Apps_Repo.deleteById(id);
			return "Template app "+id+" has been deleted";
		}catch(Exception e) {
			return null;
		}		
	}
	
}

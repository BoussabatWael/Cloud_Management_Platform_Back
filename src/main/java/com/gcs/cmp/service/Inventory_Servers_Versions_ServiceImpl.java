package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Inventory_Servers_Versions;
import com.gcs.cmp.repository.Inventory_Servers_Versions_Repo;

@Service
public class Inventory_Servers_Versions_ServiceImpl implements Inventory_Servers_Versions_Service{

	@Autowired
	private Inventory_Servers_Versions_Repo inventory_Servers_Versions_Repo;
	
	@Override
	public Inventory_Servers_Versions addInventory_Servers_Versions(
			Inventory_Servers_Versions inventory_Servers_Versions) {
		// TODO Auto-generated method stub
		return inventory_Servers_Versions_Repo.save(inventory_Servers_Versions);
	}

	@Override
	public List<Inventory_Servers_Versions> getInventory_Servers_VersionsList(Long account_id) {
		// TODO Auto-generated method stub
		return inventory_Servers_Versions_Repo.getInventory_Servers_VersionsList(account_id);
	}

	@Override
	public Inventory_Servers_Versions updateInventory_Servers_Versions(
			Inventory_Servers_Versions inventory_Servers_Versions) {
		// TODO Auto-generated method stub
		return inventory_Servers_Versions_Repo.save(inventory_Servers_Versions);
	}

	@Override
	public Optional<Inventory_Servers_Versions> findInventory_Servers_VersionsById(Long id) {
		// TODO Auto-generated method stub
		return inventory_Servers_Versions_Repo.findById(id);
	}

	@Override
	public String deleteInventory_Servers_Versions(Long id) {
		try {
			// TODO Auto-generated method stub
			inventory_Servers_Versions_Repo.deleteById(id);
			return "Server version "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
	}
	
}

package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Inventory_Applications_Versions;
import com.gcs.cmp.repository.Inventory_Applications_Versions_Repo;

@Service
public class Inventory_Applications_Versions_ServiceImpl implements Inventory_Applications_Versions_Service{

	@Autowired
	private Inventory_Applications_Versions_Repo inventory_Applications_Versions_Repo;
	
	@Override
	public Inventory_Applications_Versions addInventory_Applications_Versions(
			Inventory_Applications_Versions inventory_Applications_Versions) {
		// TODO Auto-generated method stub
		return inventory_Applications_Versions_Repo.save(inventory_Applications_Versions);
	}

	@Override
	public List<Inventory_Applications_Versions> getInventory_Applications_VersionsList(Long account_id) {
		// TODO Auto-generated method stub
		return inventory_Applications_Versions_Repo.getInventory_Applications_VersionsList(account_id);
	}

	@Override
	public Inventory_Applications_Versions updateInventory_Applications_Versions(
			Inventory_Applications_Versions inventory_Applications_Versions) {
		// TODO Auto-generated method stub
		return inventory_Applications_Versions_Repo.save(inventory_Applications_Versions);
	}

	@Override
	public Optional<Inventory_Applications_Versions> findInventory_Applications_VersionsById(Long id) {
		// TODO Auto-generated method stub
		return inventory_Applications_Versions_Repo.findById(id);
	}

	@Override
	public String deleteInventory_Applications_Versions(Long id) {
		try {
			// TODO Auto-generated method stub
			inventory_Applications_Versions_Repo.deleteById(id);
			return "Application version "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}		
	}

	@Override
	public List<Inventory_Applications_Versions> getApplications_VersionsListByApplicationID(Long account_id,
			Long application_id) {
		// TODO Auto-generated method stub
		return inventory_Applications_Versions_Repo.getApplications_VersionsListByApplicationID(account_id, application_id);
	}
	
}

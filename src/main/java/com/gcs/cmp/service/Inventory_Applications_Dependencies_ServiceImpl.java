package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Inventory_Applications_Dependencies;
import com.gcs.cmp.repository.Inventory_Applications_Dependencies_Repo;

@Service
public class Inventory_Applications_Dependencies_ServiceImpl implements Inventory_Applications_Dependencies_Service{

	@Autowired
	private Inventory_Applications_Dependencies_Repo inventory_Applications_Dependencies_Repo;
	
	@Override
	public Inventory_Applications_Dependencies addInventory_Applications_Dependencies(
			Inventory_Applications_Dependencies inventory_Applications_Dependencies) {
		// TODO Auto-generated method stub
		return inventory_Applications_Dependencies_Repo.save(inventory_Applications_Dependencies);
	}

	@Override
	public List<Inventory_Applications_Dependencies> getInventory_Applications_DependenciesList(Long account_id) {
		// TODO Auto-generated method stub
		return inventory_Applications_Dependencies_Repo.getInventory_Applications_DependenciesList(account_id);
	}

	@Override
	public Inventory_Applications_Dependencies updateInventory_Applications_Dependencies(
			Inventory_Applications_Dependencies inventory_Applications_Dependencies) {
		// TODO Auto-generated method stub
		return inventory_Applications_Dependencies_Repo.save(inventory_Applications_Dependencies);
	}

	@Override
	public Optional<Inventory_Applications_Dependencies> findInventory_Applications_DependenciesById(Long id) {
		// TODO Auto-generated method stub
		return inventory_Applications_Dependencies_Repo.findById(id);
	}

	@Override
	public String deleteInventory_Applications_Dependencies(Long id) {
		try {
			// TODO Auto-generated method stub
			inventory_Applications_Dependencies_Repo.deleteById(id);
			return "Application dependencies "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
		
	}

	@Override
	public List<Inventory_Applications_Dependencies> getApplications_DependenciesListByApplicationID(Long account_id,
			Long application_id) {
		// TODO Auto-generated method stub
		return inventory_Applications_Dependencies_Repo.getApplications_DependenciesListByApplicationID(account_id, application_id);
	}

}

package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Inventory_Applications_Sources;
import com.gcs.cmp.repository.Inventory_Applications_Sources_Repo;

@Service
public class Inventory_Applications_Sources_ServiceImpl implements Inventory_Applications_Sources_Service{

	@Autowired
	private Inventory_Applications_Sources_Repo inventory_Applications_Sources_Repo;
	
	@Override
	public Inventory_Applications_Sources addInventory_Applications_Sources(
			Inventory_Applications_Sources inventory_Applications_Sources) {
		// TODO Auto-generated method stub
		return inventory_Applications_Sources_Repo.save(inventory_Applications_Sources);
	}

	@Override
	public List<Inventory_Applications_Sources> getInventory_Applications_SourcesList(Long account_id) {
		// TODO Auto-generated method stub
		return inventory_Applications_Sources_Repo.getInventory_Applications_SourcesList(account_id);
	}

	@Override
	public Inventory_Applications_Sources updateInventory_Applications_Sources(
			Inventory_Applications_Sources inventory_Applications_Sources) {
		// TODO Auto-generated method stub
		return inventory_Applications_Sources_Repo.save(inventory_Applications_Sources);
	}

	@Override
	public Optional<Inventory_Applications_Sources> findInventory_Applications_SourcesId(Long id) {
		// TODO Auto-generated method stub
		return inventory_Applications_Sources_Repo.findById(id);
	}

	@Override
	public String deleteInventory_Applications_Sources(Long id) {
		try {
			// TODO Auto-generated method stub
			inventory_Applications_Sources_Repo.deleteById(id);
			return "Application source "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
	}

	@Override
	public List<Inventory_Applications_Sources> getApplications_SourcesListByApplicationID(Long account_id,
			Long application_id) {
		// TODO Auto-generated method stub
		return inventory_Applications_Sources_Repo.getApplications_SourcesListByApplicationID(account_id, application_id);
	}
	
}

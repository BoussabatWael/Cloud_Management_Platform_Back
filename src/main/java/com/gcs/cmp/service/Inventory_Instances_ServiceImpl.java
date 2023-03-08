package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Inventory_Instances;
import com.gcs.cmp.repository.Inventory_Instances_Repo;

@Service
public class Inventory_Instances_ServiceImpl implements Inventory_Instances_Service{

	@Autowired
	private Inventory_Instances_Repo inventory_Instances_Repo;
	
	@Override
	public Inventory_Instances addInventory_Instances(Inventory_Instances inventory_Instances) {
		// TODO Auto-generated method stub
		return inventory_Instances_Repo.save(inventory_Instances);
	}

	@Override
	public List<Inventory_Instances> getInventory_InstancesList(Long account_id) {
		// TODO Auto-generated method stub
		return inventory_Instances_Repo.getInventory_InstancesList(account_id);
	}

	@Override
	public Inventory_Instances updateInventory_Instances(Inventory_Instances inventory_Instances) {
		// TODO Auto-generated method stub
		return inventory_Instances_Repo.save(inventory_Instances);
	}

	@Override
	public Optional<Inventory_Instances> findInventory_InstancesById(Long id) {
		// TODO Auto-generated method stub
		return inventory_Instances_Repo.findById(id);
	}

	@Override
	public String deleteInventory_Instances(Long id) {
		try {
			// TODO Auto-generated method stub
			inventory_Instances_Repo.deleteById(id);
			return "Instance "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
	}

	@Override
	public List<Inventory_Instances> getInstancesNumber(Long account_id, Long provider_id) {
		// TODO Auto-generated method stub
		return inventory_Instances_Repo.getInstancesNumber(account_id, provider_id);
	}

	@Override
	public Object getAllInstancesNumber(Long account_id) {
		// TODO Auto-generated method stub
		return inventory_Instances_Repo.getAllInstancesNumber(account_id);
	}
	
}

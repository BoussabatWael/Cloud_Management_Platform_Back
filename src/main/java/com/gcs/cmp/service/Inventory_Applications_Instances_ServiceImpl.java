package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Inventory_Applications_Instances;
import com.gcs.cmp.repository.Inventory_Applications_Instances_Repo;

@Service
public class Inventory_Applications_Instances_ServiceImpl implements Inventory_Applications_Instances_Service{

	@Autowired
	private Inventory_Applications_Instances_Repo inventory_Applications_Instances_Repo;
	
	@Override
	public Inventory_Applications_Instances addInventory_Applications_Instances(
			Inventory_Applications_Instances inventory_Applications_Instances) {
		// TODO Auto-generated method stub
		return inventory_Applications_Instances_Repo.save(inventory_Applications_Instances);

	}

	@Override
	public List<Inventory_Applications_Instances> getInventory_Applications_InstancesList(Long account_id) {
		// TODO Auto-generated method stub
		return inventory_Applications_Instances_Repo.getInventory_Applications_InstancesList(account_id);
	}

	@Override
	public Inventory_Applications_Instances updateInventory_Applications_Instances(
			Inventory_Applications_Instances inventory_Applications_Instances) {
		// TODO Auto-generated method stub
		return inventory_Applications_Instances_Repo.save(inventory_Applications_Instances);
	}

	@Override
	public Optional<Inventory_Applications_Instances> findInventory_Applications_InstancesById(Long id) {
		// TODO Auto-generated method stub
		return inventory_Applications_Instances_Repo.findById(id);
	}

	@Override
	public String deleteInventory_Applications_Instances(Long id) {
		try {
			// TODO Auto-generated method stub
			inventory_Applications_Instances_Repo.deleteById(id);
			return "Application instance "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
		

	}

	@Override
	public List<Inventory_Applications_Instances> getInventoryApplicationsByApplicationID(Long account_id, Long application_id) {
		// TODO Auto-generated method stub
		return inventory_Applications_Instances_Repo.getInventoryApplicationsByApplicationID(account_id, application_id);
	}

	@Override
	public List<Inventory_Applications_Instances> getInventoryApplicationsByInstanceID(Long account_id,
			Long instance_id) {
		// TODO Auto-generated method stub
		return inventory_Applications_Instances_Repo.getInventoryApplicationsByInstanceID(account_id, instance_id);
	}

}

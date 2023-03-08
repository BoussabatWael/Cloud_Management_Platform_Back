package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Inventory_Hosts;
import com.gcs.cmp.repository.Inventory_Hosts_Repo;

@Service
public class Inventory_Hosts_ServiceImpl implements Inventory_Hosts_Service{

	@Autowired
	private Inventory_Hosts_Repo inventory_Hosts_Repo;
	
	@Override
	public Inventory_Hosts addInventory_Hosts(Inventory_Hosts inventory_Hosts) {
		// TODO Auto-generated method stub
		return inventory_Hosts_Repo.save(inventory_Hosts);
	}

	@Override
	public List<Inventory_Hosts> getInventory_HostsList(Long account_id) {
		// TODO Auto-generated method stub
		return inventory_Hosts_Repo.getInventory_HostsList(account_id);
	}

	@Override
	public Inventory_Hosts updateInventory_Hosts(Inventory_Hosts inventory_Hosts) {
		// TODO Auto-generated method stub
		return inventory_Hosts_Repo.save(inventory_Hosts);
	}

	@Override
	public Optional<Inventory_Hosts> findInventory_HostsById(Long id) {
		// TODO Auto-generated method stub
		return inventory_Hosts_Repo.findById(id);
	}

	@Override
	public String deleteInventory_Hosts(Long id) {
		try {
			// TODO Auto-generated method stub
			inventory_Hosts_Repo.deleteById(id);
			return "Host "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
	}
	
}

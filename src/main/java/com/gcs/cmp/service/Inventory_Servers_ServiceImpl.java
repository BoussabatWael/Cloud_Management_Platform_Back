package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Inventory_Servers;
import com.gcs.cmp.repository.Inventory_Servers_Repo;

@Service
public class Inventory_Servers_ServiceImpl implements Inventory_Servers_Service{

	@Autowired
	private Inventory_Servers_Repo inventory_Servers_Repo;
	
	@Override
	public Inventory_Servers addInventory_Servers(Inventory_Servers inventory_Servers) {
		// TODO Auto-generated method stub
		return inventory_Servers_Repo.save(inventory_Servers);
	}

	@Override
	public List<Inventory_Servers> getInventory_ServersList(Long account_id) {
		// TODO Auto-generated method stub
		return inventory_Servers_Repo.getInventory_ServersList(account_id);
	}

	@Override
	public Inventory_Servers updateInventory_Servers(Inventory_Servers inventory_Servers) {
		// TODO Auto-generated method stub
		return inventory_Servers_Repo.save(inventory_Servers);
	}

	@Override
	public Optional<Inventory_Servers> findInventory_ServersById(Long id) {
		// TODO Auto-generated method stub
		return inventory_Servers_Repo.findById(id);
	}

	@Override
	public String deleteInventory_Servers(Long id) {
		try {
			// TODO Auto-generated method stub
			inventory_Servers_Repo.deleteById(id);
			return "Server "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
	}


}

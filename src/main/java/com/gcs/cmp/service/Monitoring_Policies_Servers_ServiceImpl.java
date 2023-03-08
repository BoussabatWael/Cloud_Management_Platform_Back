package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Monitoring_Policies_Servers;
import com.gcs.cmp.repository.Monitoring_Policies_Servers_Repo;

@Service
public class Monitoring_Policies_Servers_ServiceImpl implements Monitoring_Policies_Servers_Service{

	@Autowired
	private Monitoring_Policies_Servers_Repo monitoring_Policies_Servers_Repo;
	
	@Override
	public Monitoring_Policies_Servers addMonitoring_Policies_Servers(
			Monitoring_Policies_Servers monitoring_Policies_Servers) {
		// TODO Auto-generated method stub
		return monitoring_Policies_Servers_Repo.save(monitoring_Policies_Servers);
	}

	@Override
	public List<Monitoring_Policies_Servers> getMonitoring_Policies_ServersList(Long account_id) {
		// TODO Auto-generated method stub
		return monitoring_Policies_Servers_Repo.getMonitoring_Policies_ServersList(account_id);
	}

	@Override
	public Monitoring_Policies_Servers updateMonitoring_Policies_Servers(
			Monitoring_Policies_Servers monitoring_Policies_Servers) {
		// TODO Auto-generated method stub
		return monitoring_Policies_Servers_Repo.save(monitoring_Policies_Servers);
	}

	@Override
	public Optional<Monitoring_Policies_Servers> findMonitoring_Policies_ServersById(Long id) {
		// TODO Auto-generated method stub
		return monitoring_Policies_Servers_Repo.findById(id);
	}

	@Override
	public String deleteMonitoring_Policies_Servers(Long id) {
		try {
			// TODO Auto-generated method stub
			monitoring_Policies_Servers_Repo.deleteById(id);
			return "Monitoring policies server "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}		
	}
	
}

package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Monitoring_Commands;
import com.gcs.cmp.repository.Monitoring_Commands_Repo;

@Service
public class Monitoring_Commands_ServiceImpl implements Monitoring_Commands_Service{

	@Autowired
	private Monitoring_Commands_Repo monitoring_Commands_Repo;
	
	@Override
	public Monitoring_Commands addMonitoring_Commands(Monitoring_Commands monitoring_Commands) {
		// TODO Auto-generated method stub
		return monitoring_Commands_Repo.save(monitoring_Commands);
	}

	@Override
	public List<Monitoring_Commands> getMonitoring_CommandsList(Long account_id) {
		// TODO Auto-generated method stub
		return monitoring_Commands_Repo.getMonitoring_CommandsList(account_id);
	}

	@Override
	public Monitoring_Commands updateMonitoring_Commands(Monitoring_Commands monitoring_Commands) {
		// TODO Auto-generated method stub
		return monitoring_Commands_Repo.save(monitoring_Commands);
	}

	@Override
	public Optional<Monitoring_Commands> findMonitoring_CommandsById(Long id) {
		// TODO Auto-generated method stub
		return monitoring_Commands_Repo.findById(id);
	}

	@Override
	public String deleteMonitoring_Commands(Long id) {
		try {
			// TODO Auto-generated method stub
			monitoring_Commands_Repo.deleteById(id);
			return "Monitoring command "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
		
	}

}

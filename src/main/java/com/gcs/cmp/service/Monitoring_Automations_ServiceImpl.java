package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Monitoring_Automations;
import com.gcs.cmp.repository.Monitoring_Automations_Repo;

@Service
public class Monitoring_Automations_ServiceImpl implements Monitoring_Automations_Service{

	@Autowired
	private Monitoring_Automations_Repo monitoring_Automations_Repo;
	
	@Override
	public Monitoring_Automations addMonitoring_Automations(Monitoring_Automations monitoring_Automations) {
		// TODO Auto-generated method stub
		return monitoring_Automations_Repo.save(monitoring_Automations);
	}

	@Override
	public List<Monitoring_Automations> getMonitoring_AutomationsList(Long account_id) {
		// TODO Auto-generated method stub
		return monitoring_Automations_Repo.getMonitoring_AutomationsList(account_id);
	}

	@Override
	public Monitoring_Automations updateMonitoring_Automations(Monitoring_Automations monitoring_Automations) {
		// TODO Auto-generated method stub
		return monitoring_Automations_Repo.save(monitoring_Automations);
	}

	@Override
	public Optional<Monitoring_Automations> findMonitoring_AutomationsById(Long id) {
		// TODO Auto-generated method stub
		return monitoring_Automations_Repo.findById(id);
	}

	@Override
	public String deleteMonitoring_Automations(Long id) {
		try {
			// TODO Auto-generated method stub
			monitoring_Automations_Repo.deleteById(id);
			return "Monitoring automation "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
		
	}

}

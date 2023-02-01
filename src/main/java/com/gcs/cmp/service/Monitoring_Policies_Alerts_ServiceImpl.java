package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Monitoring_Policies_Alerts;
import com.gcs.cmp.repository.Monitoring_Policies_Alerts_Repo;

@Service
public class Monitoring_Policies_Alerts_ServiceImpl implements Monitoring_Policies_Alerts_Service{

	@Autowired
	private Monitoring_Policies_Alerts_Repo monitoring_Policies_Alerts_Repo;
	
	@Override
	public Monitoring_Policies_Alerts addMonitoring_Policies_Alerts(
			Monitoring_Policies_Alerts monitoring_Policies_Alerts) {
		// TODO Auto-generated method stub
		return monitoring_Policies_Alerts_Repo.save(monitoring_Policies_Alerts);
	}

	@Override
	public List<Monitoring_Policies_Alerts> getMonitoring_Policies_AlertsList(Long account_id) {
		// TODO Auto-generated method stub
		return monitoring_Policies_Alerts_Repo.getMonitoring_Policies_AlertsList(account_id);
	}

	@Override
	public Monitoring_Policies_Alerts updateMonitoring_Policies_Alerts(
			Monitoring_Policies_Alerts monitoring_Policies_Alerts) {
		// TODO Auto-generated method stub
		return monitoring_Policies_Alerts_Repo.save(monitoring_Policies_Alerts);
	}

	@Override
	public Optional<Monitoring_Policies_Alerts> findMonitoring_Policies_AlertsById(Long id) {
		// TODO Auto-generated method stub
		return monitoring_Policies_Alerts_Repo.findById(id);
	}

	@Override
	public String deleteMonitoring_Policies_Alerts(Long id) {
		try {
			// TODO Auto-generated method stub
			monitoring_Policies_Alerts_Repo.deleteById(id);
			return "Monitoring policies alert "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
		
	}

}

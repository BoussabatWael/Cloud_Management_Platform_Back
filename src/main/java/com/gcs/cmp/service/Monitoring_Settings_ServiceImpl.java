package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Monitoring_Settings;
import com.gcs.cmp.repository.Monitoring_Settings_Repo;

@Service
public class Monitoring_Settings_ServiceImpl implements Monitoring_Settings_Service{

	@Autowired
	private Monitoring_Settings_Repo monitoring_Settings_Repo;
	
	@Override
	public Monitoring_Settings addMonitoring_Settings(Monitoring_Settings monitoring_Settings) {
		// TODO Auto-generated method stub
		return monitoring_Settings_Repo.save(monitoring_Settings);
	}

	@Override
	public List<Monitoring_Settings> getMonitoring_SettingsList(Long account_id) {
		// TODO Auto-generated method stub
		return monitoring_Settings_Repo.getMonitoring_SettingsList(account_id);
	}

	@Override
	public Monitoring_Settings updateMonitoring_Settings(Monitoring_Settings monitoring_Settings) {
		// TODO Auto-generated method stub
		return monitoring_Settings_Repo.save(monitoring_Settings);
	}

	@Override
	public Optional<Monitoring_Settings> findMonitoring_SettingsById(Long id) {
		// TODO Auto-generated method stub
		return monitoring_Settings_Repo.findById(id);
	}

	@Override
	public String deleteMonitoring_Settings(Long id) {
		try {
			// TODO Auto-generated method stub
			monitoring_Settings_Repo.deleteById(id);
			return "Monitoring settings "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
		
	}

}

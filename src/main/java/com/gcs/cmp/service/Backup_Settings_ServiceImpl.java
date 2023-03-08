package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Backup_Settings;
import com.gcs.cmp.repository.Backup_Settings_Repo;

@Service
public class Backup_Settings_ServiceImpl implements Backup_Settings_Service{

	@Autowired
	private Backup_Settings_Repo backup_Settings_Repo;
	
	@Override
	public Backup_Settings addBackup_Settings(Backup_Settings backup_Settings) {
		// TODO Auto-generated method stub
		return backup_Settings_Repo.save(backup_Settings);
	}

	@Override
	public List<Backup_Settings> findBackup_SettingsList(Long account_id) {
		// TODO Auto-generated method stub
		return backup_Settings_Repo.findBackup_SettingsList(account_id);
	}

	@Override
	public Backup_Settings updateBackup_Settings(Backup_Settings backup_Settings) {
		// TODO Auto-generated method stub
		return backup_Settings_Repo.save(backup_Settings);	
	}

	@Override
	public Optional<Backup_Settings> findBackup_SettingsById(Long id) {
		// TODO Auto-generated method stub
		return backup_Settings_Repo.findById(id);
	}

	@Override
	public String deleteBackup_Settings(Long id) {
		try {
			// TODO Auto-generated method stub
			backup_Settings_Repo.deleteById(id);
			return "Backup settings "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}		
	}
	
}

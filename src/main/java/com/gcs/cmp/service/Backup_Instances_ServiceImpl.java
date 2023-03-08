package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Backup_Instances;
import com.gcs.cmp.repository.Backup_Instances_Repo;

@Service
public class Backup_Instances_ServiceImpl implements Backup_Instances_Service{

	@Autowired
	private Backup_Instances_Repo backup_Instances_Repo;
	
	@Override
	public Backup_Instances addBackup_Instances(Backup_Instances backup_Instances) {
		// TODO Auto-generated method stub
		return backup_Instances_Repo.save(backup_Instances);
	}

	@Override
	public List<Backup_Instances> getBackup_InstancesList(Long account_id) {
		// TODO Auto-generated method stub
		return backup_Instances_Repo.getBackup_InstancesList(account_id);
	}
	
	@Override
	public Backup_Instances updateBackup_Instances(Backup_Instances backup_Instances) {
		// TODO Auto-generated method stub
		return backup_Instances_Repo.save(backup_Instances);
	}

	@Override
	public Optional<Backup_Instances> findBackup_InstancesById(Long id) {
		// TODO Auto-generated method stub
		return backup_Instances_Repo.findById(id);
	}

	@Override
	public String deleteBackup_Instances(Long id) {
		// TODO Auto-generated method stub
		try {
			backup_Instances_Repo.deleteById(id);
			return "Backup instance "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
	}
	
}

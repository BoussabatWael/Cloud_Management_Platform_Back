package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Backup_Operations;
import com.gcs.cmp.repository.Backup_Operations_Repo;

@Service
public class Backup_Operations_ServiceImpl implements Backup_Operations_Service{

	@Autowired
	private Backup_Operations_Repo backup_Operations_Repo;
	
	@Override
	public Backup_Operations addBackup_Operations(Backup_Operations backup_Operations) {
		return backup_Operations_Repo.save(backup_Operations);
	}

	@Override
	public List<Backup_Operations> findBackup_OperationsList(Long account_id) {
		// TODO Auto-generated method stub
		return backup_Operations_Repo.findBackup_OperationsList(account_id);
	}
	
	@Override
	public Backup_Operations updateBackup_Operations(Backup_Operations backup_Operations) {
		// TODO Auto-generated method stub
		return backup_Operations_Repo.save(backup_Operations);	
	}

	@Override
	public Optional<Backup_Operations> findBackup_OperationsById(Long id) {
		// TODO Auto-generated method stub
		return backup_Operations_Repo.findById(id);
	}

	@Override
	public String deleteBackup_Operations(Long id) {
		try {
			// TODO Auto-generated method stub
			backup_Operations_Repo.deleteById(id);
			return "Backup operation "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
	
	}

	@Override
	public List<Backup_Operations> getBackup_OperationsByInstanceID(Long account_id, Long instance_id) {
		// TODO Auto-generated method stub
		return backup_Operations_Repo.getBackup_OperationsByInstanceID(account_id, instance_id);
	}

	
}

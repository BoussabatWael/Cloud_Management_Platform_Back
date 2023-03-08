package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Backup_Executions;
import com.gcs.cmp.repository.Backup_Executions_Repo;
import com.gcs.cmp.validators.Inputs_Validations;

@Service
public class Backup_Executions_ServiceImpl extends Inputs_Validations implements Backup_Executions_Service{

	@Autowired
	private Backup_Executions_Repo backup_Executions_Repo;
	
 	@Override
	public Backup_Executions addBackup_Executions(Backup_Executions backup_Executions) {
		// TODO Auto-generated method stub
 	 	return backup_Executions_Repo.save(backup_Executions);
	}

 	@Override
	public List<Backup_Executions> getBackup_ExecutionsList(Long account_id) {
		// TODO Auto-generated method stub
		return backup_Executions_Repo.findBackup_ExecutionsList(account_id);
	}
	
	@Override
	public Backup_Executions updateBackup_Executions(Backup_Executions backup_Executions) {
		// TODO Auto-generated method stub
 	 	return backup_Executions_Repo.save(backup_Executions);
 	}

	@Override
	public Optional<Backup_Executions> findBackup_ExecutionsById(Long id) {
		// TODO Auto-generated method stub
		return backup_Executions_Repo.findById(id);
	}

	@Override
	public String deleteBackup_Executions(Long id) {
		try {
			// TODO Auto-generated method stub
			backup_Executions_Repo.deleteById(id);
			return "Backup execution "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
	}

	@Override
	public Optional<Backup_Executions> getBackup_ExecutionsByOperationID(Long account_id, Long operation_id) {
		// TODO Auto-generated method stub
		return backup_Executions_Repo.getBackup_ExecutionsByOperationID(account_id, operation_id);
	}
	
}

package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Monitoring_Commands_Executions;
import com.gcs.cmp.repository.Monitoring_Commands_Executions_Repo;

@Service
public class Monitoring_Commands_Executions_ServiceImpl implements Monitoring_Commands_Executions_Service{

	@Autowired
	private Monitoring_Commands_Executions_Repo monitoring_Commands_Executions_Repo;
	
	@Override
	public Monitoring_Commands_Executions addMonitoring_Commands_Executions(
			Monitoring_Commands_Executions monitoring_Commands_Executions) {
		// TODO Auto-generated method stub
		return monitoring_Commands_Executions_Repo.save(monitoring_Commands_Executions);
	}

	@Override
	public List<Monitoring_Commands_Executions> getMonitoring_Commands_ExecutionsList(Long account_id) {
		// TODO Auto-generated method stub
		return monitoring_Commands_Executions_Repo.getMonitoring_Commands_ExecutionsList(account_id);
	}

	@Override
	public Monitoring_Commands_Executions updateMonitoring_Commands_Executions(
			Monitoring_Commands_Executions monitoring_Commands_Executions) {
		// TODO Auto-generated method stub
		return monitoring_Commands_Executions_Repo.save(monitoring_Commands_Executions);
	}

	@Override
	public Optional<Monitoring_Commands_Executions> findMonitoring_Commands_ExecutionsById(Long id) {
		// TODO Auto-generated method stub
		return monitoring_Commands_Executions_Repo.findById(id);
	}

	@Override
	public String deleteMonitoring_Commands_Executions(Long id) {
		try {
			// TODO Auto-generated method stub
			monitoring_Commands_Executions_Repo.deleteById(id);
			return "Monitoring command execution "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}		
	}
	
}

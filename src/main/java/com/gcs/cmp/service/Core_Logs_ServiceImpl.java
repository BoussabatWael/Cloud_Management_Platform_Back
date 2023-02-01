package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Core_Logs;
import com.gcs.cmp.repository.Core_Logs_Repo;

@Service
public class Core_Logs_ServiceImpl implements Core_Logs_Service{

	@Autowired
	private Core_Logs_Repo core_Logs_Repo;
	
	@Override
	public Core_Logs addCore_Logs(Core_Logs core_Logs) {
		// TODO Auto-generated method stub
		return core_Logs_Repo.save(core_Logs);
	}

	@Override
	public List<Core_Logs> getCore_LogsList(Long account_id) {
		// TODO Auto-generated method stub
		return core_Logs_Repo.getCore_LogsList(account_id);
	}

	@Override
	public Optional<Core_Logs> findCore_LogsById(Long id) {
		// TODO Auto-generated method stub
		return core_Logs_Repo.findById(id);
	}

	@Override
	public String deleteCore_Logs(Long id) {
		// TODO Auto-generated method stub
		core_Logs_Repo.deleteById(id);
		return "Logs "+id+" has been deleted!";
	}

	@Override
	public List<Core_Logs> getLogsListByUserID(Long account_id, Long user_id) {
		// TODO Auto-generated method stub
		return core_Logs_Repo.getLogsListByUserID(account_id, user_id);
	}

	@Override
	public List<Core_Logs> getUserLogsList(Long account_id, Long user_id) {
		// TODO Auto-generated method stub
		return core_Logs_Repo.getUserLogsList(account_id, user_id);
	}

	@Override
	public List<Core_Logs> getProviderLogsList(Long account_id, Long provider_id) {
		// TODO Auto-generated method stub
		return core_Logs_Repo.getProviderLogsList(account_id, provider_id);
	}

	@Override
	public List<Core_Logs> getServerLogsList(Long account_id, Long server_id) {
		// TODO Auto-generated method stub
		return core_Logs_Repo.getServerLogsList(account_id, server_id);
	}
	
	
	@Override
	public List<Core_Logs> getDomainNamesLogsList(Long account_id, Long domain_id) {
		// TODO Auto-generated method stub
		return core_Logs_Repo.getDomainNamesLogsList(account_id, domain_id);
	}

	@Override
	public List<Core_Logs> getApplicationLogsList(Long account_id, Long application_id) {
		// TODO Auto-generated method stub
		return core_Logs_Repo.getApplicationLogsList(account_id, application_id);
	}

	@Override
	public List<Core_Logs> getBackupLogsList(Long account_id, Long backup_id) {
		// TODO Auto-generated method stub
		return core_Logs_Repo.getBackupLogsList(account_id, backup_id);
	}
}

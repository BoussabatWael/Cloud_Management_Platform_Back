package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Core_Api_Logs;
import com.gcs.cmp.repository.Core_Api_Logs_Repo;

@Service
public class Core_Api_Logs_ServiceImpl implements Core_Api_Logs_Service{

	@Autowired
	private Core_Api_Logs_Repo core_Api_Logs_Repo;
	
	@Override
	public Core_Api_Logs addCore_Api_Logs(Core_Api_Logs core_Api_Logs) {
		// TODO Auto-generated method stub
		return core_Api_Logs_Repo.save(core_Api_Logs);
	}

	@Override
	public List<Core_Api_Logs> getCore_Api_LogsList(Long account_id) {
		// TODO Auto-generated method stub
		return core_Api_Logs_Repo.getCore_Api_LogsList(account_id);
	}

	@Override
	public Optional<Core_Api_Logs> findCore_Api_LogsById(Long id) {
		// TODO Auto-generated method stub
		return core_Api_Logs_Repo.findById(id);
	}

	@Override
	public String deleteCore_Api_Logs(Long id) {
		// TODO Auto-generated method stub
		core_Api_Logs_Repo.deleteById(id);
		return "Api logs "+id+" has been deleted!";
	}
	
}

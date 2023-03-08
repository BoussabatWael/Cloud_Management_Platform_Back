package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Core_Users_Metrics;
import com.gcs.cmp.repository.Core_Users_Metrics_Repo;

@Service
public class Core_Users_Metrics_ServiceImpl implements Core_Users_Metrics_Service{

	@Autowired
	private Core_Users_Metrics_Repo core_Users_Metrics_Repo;

	@Override
	public Core_Users_Metrics addCore_Users_Metrics(Core_Users_Metrics core_Users_Metrics) {
		// TODO Auto-generated method stub
		return core_Users_Metrics_Repo.save(core_Users_Metrics);
	}

	@Override
	public List<Core_Users_Metrics> getCore_Users_MetricsList(Long account_id) {
		// TODO Auto-generated method stub
		return core_Users_Metrics_Repo.getCore_Users_MetricsList(account_id);
	}

	@Override
	public Core_Users_Metrics updateCore_Users_Metrics(Core_Users_Metrics core_Users_Metrics) {
		// TODO Auto-generated method stub
		return core_Users_Metrics_Repo.save(core_Users_Metrics);
	}

	@Override
	public Optional<Core_Users_Metrics> findCore_Users_MetricsById(Long id) {
		// TODO Auto-generated method stub
		return core_Users_Metrics_Repo.findById(id);
	}

	@Override
	public String deleteCore_Users_Metrics(Long id) {
		try {
			// TODO Auto-generated method stub
			core_Users_Metrics_Repo.deleteById(id);
			return "User metric "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}		
	}
	
}

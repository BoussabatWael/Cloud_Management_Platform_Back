package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Monitoring_Metrics;
import com.gcs.cmp.repository.Monitoring_Metrics_Repo;

@Service
public class Monitoring_Metrics_ServiceImpl implements Monitoring_Metrics_Service{

	@Autowired
	private Monitoring_Metrics_Repo monitoring_Metrics_Repo;
	
	@Override
	public Monitoring_Metrics addMonitoring_Metrics(Monitoring_Metrics monitoring_Metrics) {
		// TODO Auto-generated method stub
		return monitoring_Metrics_Repo.save(monitoring_Metrics);
	}

	@Override
	public List<Monitoring_Metrics> getMonitoring_MetricsList(Long account_id) {
		// TODO Auto-generated method stub
		return monitoring_Metrics_Repo.getMonitoring_MetricsList(account_id);
	}

	@Override
	public Monitoring_Metrics updateMonitoring_Metrics(Monitoring_Metrics monitoring_Metrics) {
		// TODO Auto-generated method stub
		return monitoring_Metrics_Repo.save(monitoring_Metrics);
	}

	@Override
	public Optional<Monitoring_Metrics> findMonitoring_MetricsById(Long id) {
		// TODO Auto-generated method stub
		return monitoring_Metrics_Repo.findById(id);
	}

	@Override
	public String deleteMonitoring_Metrics(Long id) {
		try {
			// TODO Auto-generated method stub
			monitoring_Metrics_Repo.deleteById(id);
			return "Monitoring metric "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
		
	}

}

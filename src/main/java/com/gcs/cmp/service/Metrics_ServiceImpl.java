package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Metrics;
import com.gcs.cmp.repository.Metrics_Repo;

@Service
public class Metrics_ServiceImpl implements Metrics_Service{

	@Autowired
	private Metrics_Repo metrics_Repo;
	@Override
	public Metrics addMetrics(Metrics metrics) {
		// TODO Auto-generated method stub
		return metrics_Repo.save(metrics);
	}

	@Override
	public List<Metrics> getMetricsList() {
		// TODO Auto-generated method stub
		return metrics_Repo.getMetricsList();
	}

	@Override
	public Metrics updateMetrics(Metrics metrics) {
		// TODO Auto-generated method stub
		return metrics_Repo.save(metrics);
	}

	@Override
	public Optional<Metrics> findMetricsById(Long id) {
		// TODO Auto-generated method stub
		return metrics_Repo.findById(id);
	}

	@Override
	public String deleteMetrics(Long id) {
		try {
			// TODO Auto-generated method stub
			metrics_Repo.deleteById(id);
			return "Metric "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
		
	}

}

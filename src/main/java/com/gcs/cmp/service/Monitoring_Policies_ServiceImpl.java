package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Monitoring_Policies;
import com.gcs.cmp.repository.Monitoring_Policies_Repo;

@Service
public class Monitoring_Policies_ServiceImpl implements Monitoring_Policies_Service{

	@Autowired
	private Monitoring_Policies_Repo monitoring_Policies_Repo;
	
	@Override
	public Monitoring_Policies addMonitoring_Policies(Monitoring_Policies monitoring_Policies) {
		// TODO Auto-generated method stub
		return monitoring_Policies_Repo.save(monitoring_Policies);
	}

	@Override
	public List<Monitoring_Policies> getMonitoring_PoliciesList(Long account_id) {
		// TODO Auto-generated method stub
		return monitoring_Policies_Repo.getMonitoring_PoliciesList(account_id);
	}

	@Override
	public Monitoring_Policies updateMonitoring_Policies(Monitoring_Policies monitoring_Policies) {
		// TODO Auto-generated method stub
		return monitoring_Policies_Repo.save(monitoring_Policies);
	}

	@Override
	public Optional<Monitoring_Policies> findMonitoring_PoliciesById(Long id) {
		// TODO Auto-generated method stub
		return monitoring_Policies_Repo.findById(id);
	}

	@Override
	public String deleteMonitoring_Policies(Long id) {
		try {
			// TODO Auto-generated method stub
			monitoring_Policies_Repo.deleteById(id);
			return "Monitoring policies "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
	}
	
}

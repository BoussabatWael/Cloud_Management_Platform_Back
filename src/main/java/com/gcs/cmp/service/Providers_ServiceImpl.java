package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gcs.cmp.entity.Providers;
import com.gcs.cmp.repository.Providers_Repo;


@Service
public class Providers_ServiceImpl implements Providers_Service {
	
	@Autowired
	private Providers_Repo cloud_Providers_Repo;
	
	@Override
	public Providers addCloud_Providers(Providers Cloud_Providers) {
		// TODO Auto-generated method stub
		return cloud_Providers_Repo.save(Cloud_Providers);
	}

	@Override
	public List<Providers> getProvidersList() {
		// TODO Auto-generated method stub
		return cloud_Providers_Repo.getProvidersList();
	}

	@Override
	public Providers updateCloud_Providers(Providers Cloud_Providers) {
		// TODO Auto-generated method stub
		return cloud_Providers_Repo.save(Cloud_Providers);
	}

	@Override
	public Optional<Providers> findCloud_ProvidersById(Long id) {
		// TODO Auto-generated method stub
		return cloud_Providers_Repo.findById(id);
	}

	@Override
	public String deleteCloud_Providers(Long id) {
		try {
			// TODO Auto-generated method stub
			cloud_Providers_Repo.deleteById(id);
			return "Provider "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}	
	}
	
}

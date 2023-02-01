package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Cloud_Providers_Accounts;
import com.gcs.cmp.repository.Cloud_Providers_Accounts_Repo;


@Service
public class Cloud_Providers_Accounts_ServiceImpl implements Cloud_Providers_Accounts_Service{
	
	@Autowired
	private Cloud_Providers_Accounts_Repo cloud_Accounts_Repo;
	
	@Override
	public Cloud_Providers_Accounts addCloudAccount(Cloud_Providers_Accounts cloud_Accounts) {
		// TODO Auto-generated method stub
		return cloud_Accounts_Repo.save(cloud_Accounts);
	}

	@Override
	public List<Cloud_Providers_Accounts> findCloud_Providers_AccountsList(Long account_id) {
		// TODO Auto-generated method stub
		return cloud_Accounts_Repo.findCloud_Providers_AccountsList(account_id);
	}

	@Override
	public Cloud_Providers_Accounts updateCloudAccount(Cloud_Providers_Accounts cloud_Accounts) {
		// TODO Auto-generated method stub
		return cloud_Accounts_Repo.save(cloud_Accounts);
	}

	@Override
	public Optional<Cloud_Providers_Accounts> findCloudAccountById(Long id) {
		// TODO Auto-generated method stub
		return cloud_Accounts_Repo.findById(id);
	}

	@Override
	public String deleteCloudAccount(Long id) {
		try {
			// TODO Auto-generated method stub
			cloud_Accounts_Repo.deleteById(id);
			return "Provider account "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
	}

	@Override
	public List<Cloud_Providers_Accounts> findCloudProvidersAccountsByProviderID(Long provider_id) {
		// TODO Auto-generated method stub
		return cloud_Accounts_Repo.findCloudProvidersAccountsByProviderID(provider_id);
	}
}

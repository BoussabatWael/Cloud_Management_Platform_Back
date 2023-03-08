package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import com.gcs.cmp.entity.Cloud_Providers_Accounts;


public interface Cloud_Providers_Accounts_Service {

	public Cloud_Providers_Accounts addCloudAccount(Cloud_Providers_Accounts cloud_Accounts) ;
	public List<Cloud_Providers_Accounts> findCloud_Providers_AccountsList(Long account_id);
	public List<Cloud_Providers_Accounts> findCloudProvidersAccountsByProviderID(Long provider_id);
	public Cloud_Providers_Accounts updateCloudAccount(Cloud_Providers_Accounts cloud_Accounts);
	public Optional<Cloud_Providers_Accounts> findCloudAccountById(Long id) ;
	public String deleteCloudAccount(Long id) ;
	
}

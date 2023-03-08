package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gcs.cmp.entity.Cloud_Providers_Accounts;


public interface Cloud_Providers_Accounts_Repo extends JpaRepository<Cloud_Providers_Accounts, Long>{

	@Query(value="select * from cloud_providers_accounts where status IN (1,2,3) AND account_id=?1",nativeQuery=true)
	public List<Cloud_Providers_Accounts> findCloud_Providers_AccountsList(Long account_id);
	
	@Query(value="SELECT a.* FROM cloud_providers_accounts a INNER JOIN providers b ON a.provider_id=b.id WHERE a.status IN (1,2,3) AND a.provider_id=?1 AND b.status IN (1,2,3)",nativeQuery=true)
	public List<Cloud_Providers_Accounts> findCloudProvidersAccountsByProviderID(Long provider_id);
	
}

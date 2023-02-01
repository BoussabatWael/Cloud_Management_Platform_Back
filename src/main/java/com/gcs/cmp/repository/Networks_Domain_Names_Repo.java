package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gcs.cmp.entity.Networks_Domain_Names;

public interface Networks_Domain_Names_Repo extends JpaRepository<Networks_Domain_Names, Long>{

	@Query(value="SELECT a.* FROM networks_domain_names a INNER JOIN cloud_providers_accounts b ON a.cloud_provider_id = b.id WHERE b.status IN (1,2,3) AND a.account_id=?1 AND a.status IN (1,2,3)",nativeQuery=true)
	public List<Networks_Domain_Names> getNetworks_Domain_NamesList(Long account_id);
	
	@Query(value="SELECT * FROM networks_domain_names WHERE account_id=?1 AND parent_id=?2 AND status IN (1,2,3)",nativeQuery=true)
	public List<Networks_Domain_Names> getNetworks_Sub_Domains_List(Long account_id, Long parent_id);
}

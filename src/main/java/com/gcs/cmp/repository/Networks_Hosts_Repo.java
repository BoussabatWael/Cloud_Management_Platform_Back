package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gcs.cmp.entity.Networks_Hosts;

public interface Networks_Hosts_Repo extends JpaRepository<Networks_Hosts, Long>{

	@Query(value="SELECT a.* FROM networks_hosts a INNER JOIN networks_domain_names b ON a.domain_id = b.id INNER JOIN inventory_hosts c ON a.hosting_id = c.id INNER JOIN inventory_instances d ON a.instance_id = d.id INNER JOIN cloud_providers_accounts e ON a.cloud_provider_id = e.id WHERE b.account_id=?1 AND c.account_id=?1 AND d.account_id=?1 AND a.status IN (1,2,3) AND b.status IN (1,2,3) AND c.status IN (1,2,3) AND d.status IN (1,2,3) AND e.status IN (1,2,3)",nativeQuery=true)
	public List<Networks_Hosts> getNetworks_HostsList(Long account_id);
	
	@Query(value="SELECT a.* FROM networks_hosts a INNER JOIN networks_domain_names b ON a.domain_id=b.id INNER JOIN inventory_instances c on a.instance_id = c.id INNER JOIN inventory_hosts d ON a.hosting_id=d.id INNER JOIN cloud_providers_accounts e ON a.cloud_provider_id=e.id WHERE a.status IN (1,2,3) AND a.domain_id=?2 AND b.status IN (1,2,3) AND b.account_id=?1 AND c.account_id=?1 AND c.status IN (1,2,3) AND d.status IN (1,2,3) AND d.account_id=?1 AND e.status IN (1,2,3)",nativeQuery=true)
	public List<Networks_Hosts> getDomainNetworks_HostsList(Long account_id,Long domain_name_id);	
	
}

package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gcs.cmp.entity.Monitoring_Policies_Servers;

public interface Monitoring_Policies_Servers_Repo extends JpaRepository<Monitoring_Policies_Servers, Long>{

	@Query(value="SELECT a.* FROM monitoring_policies_servers a INNER JOIN monitoring_policies b ON a.policy_id = b.id INNER JOIN inventory_instances c ON a.instance_id = c.id WHERE b.account_id=?1 AND c.account_id=?1 AND a.status IN (1,2,3) AND b.status IN (1,2,3) AND c.status IN (1,2,3)",nativeQuery=true)
	public List<Monitoring_Policies_Servers> getMonitoring_Policies_ServersList(Long account_id);
}

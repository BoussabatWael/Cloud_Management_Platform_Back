package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gcs.cmp.entity.Core_Users_Instances;


public interface Core_Users_Instances_Repo extends JpaRepository<Core_Users_Instances, Long>{

	@Query(value="SELECT core_users_instances.* FROM core_users_instances INNER JOIN core_users ON core_users_instances.user_id = core_users.id WHERE core_users.account_id=?1 AND core_users.status IN (1,2,3) AND core_users_instances.status IN (1,2,3)",nativeQuery=true)
	public List<Core_Users_Instances> getCore_Users_InstancesList(Long account_id);
	
	@Query(value="SELECT a.* FROM core_users_instances a INNER JOIN core_users b ON a.user_id = b.id WHERE a.user_id =?2 AND a.status IN (1,2,3) AND b.account_id =?1 AND b.status IN (1,2,3)",nativeQuery=true)
	public List<Core_Users_Instances> getUsers_InstancesByUserID(Long account_id, Long user_id);
}

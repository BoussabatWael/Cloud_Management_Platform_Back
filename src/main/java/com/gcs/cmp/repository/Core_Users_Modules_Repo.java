package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gcs.cmp.entity.Core_Users_Modules;


public interface Core_Users_Modules_Repo extends JpaRepository<Core_Users_Modules, Long>{

	@Query(value="SELECT core_users_modules.* FROM core_users_modules INNER JOIN core_users ON core_users_modules.user_id = core_users.id WHERE core_users.account_id=?1 AND core_users.status IN (1,2,3) AND core_users_modules.status IN (1,2,3)",nativeQuery=true)
	public List<Core_Users_Modules> getCore_Users_ModulesList(Long account_id);
	
	@Query(value="SELECT a.* FROM core_users_modules a INNER JOIN core_users b ON a.user_id = b.id INNER JOIN modules c ON a.module_id = c.id WHERE a.status IN (1,2,3) AND a.user_id=?2 AND b.status IN (1,2,3) AND b.account_id =?1 AND c.status IN (1,2,3)",nativeQuery=true)
	public List<Core_Users_Modules> getUsers_ModulesByUserID(Long account_id, Long user_id);
	
}

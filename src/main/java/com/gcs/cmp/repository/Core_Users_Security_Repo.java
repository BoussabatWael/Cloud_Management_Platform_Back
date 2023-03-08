package com.gcs.cmp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gcs.cmp.entity.Core_Users_Security;

public interface Core_Users_Security_Repo extends JpaRepository<Core_Users_Security, Long>{

	@Query(value="SELECT core_users_security.* FROM core_users_security INNER JOIN core_users ON core_users_security.user_id = core_users.id WHERE core_users.account_id=?1 AND core_users.status IN (1,2,3) and core_users_security.status IN (1,2,3)",nativeQuery=true)
	public List<Core_Users_Security> getCore_Users_SecurityList(Long account_id);
	
	@Query(value="SELECT a.* FROM core_users_security a INNER JOIN core_users b ON a.user_id = b.id WHERE a.user_id =?2 AND a.status IN (1,2,3) AND b.account_id =?1 AND b.status IN (1,2,3)",nativeQuery=true)
	public Optional<Core_Users_Security> getUsers_SecurityByUserID(Long account_id, Long user_id);
	
	@Query(value="SELECT a.* FROM core_users_security a INNER JOIN core_users b ON a.user_id = b.id WHERE a.status IN (1,2,3) AND a.login=?1 AND b.status IN (1,2,3)",nativeQuery=true)
	public Optional<Core_Users_Security> getUsersSecurityByLogin(String login);
	
}

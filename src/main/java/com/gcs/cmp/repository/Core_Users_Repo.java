package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gcs.cmp.entity.Core_Users;


public interface Core_Users_Repo extends JpaRepository<Core_Users, Long>{

	@Query(value="select * from core_users where status IN (1,2,3) AND account_id=?1",nativeQuery=true)
	public List<Core_Users> findCore_UsersList(Long account_id);
}

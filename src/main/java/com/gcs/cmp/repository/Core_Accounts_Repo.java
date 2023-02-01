package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gcs.cmp.entity.Core_Accounts;


public interface Core_Accounts_Repo extends JpaRepository<Core_Accounts, Long>{

	@Query(value="SELECT * FROM core_accounts WHERE status IN (1,2,3)",nativeQuery=true)
	public List<Core_Accounts> findCore_AccountsList();
}

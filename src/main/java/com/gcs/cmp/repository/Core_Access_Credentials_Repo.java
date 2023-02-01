package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gcs.cmp.entity.Core_Access_Credentials;


public interface Core_Access_Credentials_Repo extends JpaRepository<Core_Access_Credentials, Long>{

	@Query(value="select * from core_access_credentials where status IN (1,2,3) AND account_id=?1",nativeQuery=true)
	public List<Core_Access_Credentials> findCore_Access_CredentialsList(Long account_id);
	
	@Query(value="SELECT * FROM core_access_credentials WHERE account_id=?1 AND element=1 AND element_id=?2 AND status IN (1,2,3)",nativeQuery=true)
	public List<Core_Access_Credentials> getServersAccess_CredentialsByElementID(Long account_id, Long element_id);
}

package com.gcs.cmp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gcs.cmp.entity.Api_Keys;

public interface Api_Keys_Repo extends JpaRepository<Api_Keys, Long>{

	@Query(value="select * from api_keys where status IN (1,2,3) AND account_id=?1",nativeQuery=true)
	public List<Api_Keys> findApi_KeysList(Long account_id);
	
	@Query(value="SELECT DISTINCT * FROM api_keys WHERE account_id =?1 AND type=1 AND status IN (1,2,3)",nativeQuery=true)
	public Optional<Api_Keys> getOneApiKey(Long account_id);
	
}

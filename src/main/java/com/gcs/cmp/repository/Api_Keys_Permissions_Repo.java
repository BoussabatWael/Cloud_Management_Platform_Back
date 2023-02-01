package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gcs.cmp.entity.Api_Keys_Permissions;

public interface Api_Keys_Permissions_Repo extends JpaRepository<Api_Keys_Permissions, Long>{

	@Query(value="SELECT api_keys_permissions.* FROM api_keys_permissions INNER JOIN api_keys on api_keys_permissions.apikey_id = api_keys.id WHERE api_keys.account_id=?1 AND api_keys.status IN (1,2,3) AND api_keys_permissions.status IN (1,2,3)",nativeQuery=true)
	public List<Api_Keys_Permissions> findApi_Keys_PermissionsList(Long account_id);
	
}

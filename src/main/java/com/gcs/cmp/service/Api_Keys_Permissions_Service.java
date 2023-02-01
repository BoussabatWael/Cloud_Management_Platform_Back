package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import com.gcs.cmp.entity.Api_Keys_Permissions;

public interface Api_Keys_Permissions_Service {

	public Api_Keys_Permissions addApi_Keys_Permissions(Api_Keys_Permissions api_Keys_Permissions) ;
	public List<Api_Keys_Permissions> getApi_Keys_PermissionsList(Long account_id);
	public Api_Keys_Permissions updateApi_Keys_Permissions(Api_Keys_Permissions api_Keys_Permissions);
	public Optional<Api_Keys_Permissions> findApi_Keys_PermissionsId(Long id) ;
	public String deleteApi_Keys_Permissions(Long id) ;
}

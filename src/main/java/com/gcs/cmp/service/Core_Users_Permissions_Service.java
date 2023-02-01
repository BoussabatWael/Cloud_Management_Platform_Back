package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import com.gcs.cmp.entity.Core_Users_Permissions;


public interface Core_Users_Permissions_Service {

	public Core_Users_Permissions addCore_Users_Permissions(Core_Users_Permissions core_Users_Permissions) ;
	public List<Core_Users_Permissions> getCore_Users_PermissionsList(Long account_id);
	public Optional<Core_Users_Permissions> getUsers_PermissionsByUserID(Long account_id, Long user_id);
	public Optional<Core_Users_Permissions> getActiveUsers_PermissionsByUserID(Long user_id);
	public Core_Users_Permissions updateCore_Users_Permissions(Core_Users_Permissions core_Users_Permissions);
	public Optional<Core_Users_Permissions> findCore_Users_PermissionsById(Long id) ;
	public String deleteCore_Users_Permissions(Long id) ;
}

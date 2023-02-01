package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Core_Users_Permissions;
import com.gcs.cmp.repository.Core_Users_Permissions_Repo;

@Service
public class Core_Users_Permissions_ServiceImpl implements Core_Users_Permissions_Service{

	@Autowired
	private Core_Users_Permissions_Repo core_Users_Permissions_Repo;
	
	@Override
	public Core_Users_Permissions addCore_Users_Permissions(Core_Users_Permissions core_Users_Permissions) {
		// TODO Auto-generated method stub
		return core_Users_Permissions_Repo.save(core_Users_Permissions);
	}

	@Override
	public List<Core_Users_Permissions> getCore_Users_PermissionsList(Long account_id) {
		// TODO Auto-generated method stub
		return core_Users_Permissions_Repo.getCore_Users_PermissionsList(account_id);
	}

	@Override
	public Core_Users_Permissions updateCore_Users_Permissions(Core_Users_Permissions core_Users_Permissions) {
		// TODO Auto-generated method stub
		return core_Users_Permissions_Repo.save(core_Users_Permissions);
	}

	@Override
	public Optional<Core_Users_Permissions> findCore_Users_PermissionsById(Long id) {
		// TODO Auto-generated method stub
		return core_Users_Permissions_Repo.findById(id);
	}

	@Override
	public String deleteCore_Users_Permissions(Long id) {
		try {
			// TODO Auto-generated method stub
			core_Users_Permissions_Repo.deleteById(id);
			return "User permission "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
		

	}

	@Override
	public Optional<Core_Users_Permissions> getUsers_PermissionsByUserID(Long account_id, Long user_id) {
		// TODO Auto-generated method stub
		return core_Users_Permissions_Repo.getUsers_PermissionsByUserID(account_id, user_id);
	}

	@Override
	public Optional<Core_Users_Permissions> getActiveUsers_PermissionsByUserID(Long user_id) {
		// TODO Auto-generated method stub
		return core_Users_Permissions_Repo.getActiveUsers_PermissionsByUserID(user_id);
	}

}

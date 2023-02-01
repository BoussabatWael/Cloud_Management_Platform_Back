package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Api_Keys_Permissions;
import com.gcs.cmp.repository.Api_Keys_Permissions_Repo;

@Service
public class Api_Keys_Permissions_ServiceImpl implements Api_Keys_Permissions_Service{

	@Autowired
	private Api_Keys_Permissions_Repo api_Keys_Permissions_Repo;
	
	@Override
	public Api_Keys_Permissions addApi_Keys_Permissions(Api_Keys_Permissions api_Keys_Permissions) {
		return api_Keys_Permissions_Repo.save(api_Keys_Permissions);
	}


	@Override
	public List<Api_Keys_Permissions> getApi_Keys_PermissionsList(Long account_id) {
		// TODO Auto-generated method stub
		return api_Keys_Permissions_Repo.findApi_Keys_PermissionsList(account_id);
	}
	
	@Override
	public Api_Keys_Permissions updateApi_Keys_Permissions(Api_Keys_Permissions api_Keys_Permissions) {
		return api_Keys_Permissions_Repo.save(api_Keys_Permissions);
	}

	@Override
	public Optional<Api_Keys_Permissions> findApi_Keys_PermissionsId(Long id) {
		// TODO Auto-generated method stub
		return api_Keys_Permissions_Repo.findById(id);
	}

	@Override
	public String deleteApi_Keys_Permissions(Long id) {
		// TODO Auto-generated method stub
		try {
			api_Keys_Permissions_Repo.deleteById(id);
			return "Key permission "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
	}

}

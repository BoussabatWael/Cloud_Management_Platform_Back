package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Core_Users_Instances;
import com.gcs.cmp.repository.Core_Users_Instances_Repo;

@Service
public class Core_Users_Instances_ServiceImpl implements Core_Users_Instances_Service{

	@Autowired
	private Core_Users_Instances_Repo core_Users_Instances_Repo;
	
	@Override
	public Core_Users_Instances addCore_Users_Instances(Core_Users_Instances core_Users_Instances) {
		// TODO Auto-generated method stub
		return core_Users_Instances_Repo.save(core_Users_Instances);
	}

	@Override
	public List<Core_Users_Instances> getCore_Users_InstancesList(Long account_id) {
		// TODO Auto-generated method stub
		return core_Users_Instances_Repo.getCore_Users_InstancesList(account_id);
	}

	@Override
	public Core_Users_Instances updateCore_Users_Instances(Core_Users_Instances core_Users_Instances) {
		// TODO Auto-generated method stub
		return core_Users_Instances_Repo.save(core_Users_Instances);
	}

	@Override
	public Optional<Core_Users_Instances> findCore_Users_InstancesById(Long id) {
		// TODO Auto-generated method stub
		return core_Users_Instances_Repo.findById(id);
	}

	@Override
	public String deleteCore_Users_Instances(Long id) {
		try {
			// TODO Auto-generated method stub
			core_Users_Instances_Repo.deleteById(id);
			return "User instance "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
	}

	@Override
	public List<Core_Users_Instances> getUsers_InstancesByUserID(Long account_id, Long user_id) {
		// TODO Auto-generated method stub
		return core_Users_Instances_Repo.getUsers_InstancesByUserID(account_id, user_id);
	}
	
}

package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Core_Users_Modules;
import com.gcs.cmp.repository.Core_Users_Modules_Repo;

@Service
public class Core_Users_Modules_ServiceImpl implements Core_Users_Modules_Service{

	@Autowired
	private Core_Users_Modules_Repo core_Users_Modules_Repo;

	@Override
	public Core_Users_Modules addCore_Users_Modules(Core_Users_Modules core_Users_Modules) {
		// TODO Auto-generated method stub
		return core_Users_Modules_Repo.save(core_Users_Modules);
	}

	@Override
	public List<Core_Users_Modules> getCore_Users_ModulesList(Long account_id) {
		// TODO Auto-generated method stub
		return core_Users_Modules_Repo.getCore_Users_ModulesList(account_id);
	}

	@Override
	public Core_Users_Modules updateCore_Users_Modules(Core_Users_Modules core_Users_Modules) {
		// TODO Auto-generated method stub
		return core_Users_Modules_Repo.save(core_Users_Modules);
	}

	@Override
	public Optional<Core_Users_Modules> findCore_Users_ModulesById(Long id) {
		// TODO Auto-generated method stub
		return core_Users_Modules_Repo.findById(id);
	}

	@Override
	public String deleteCore_Users_Modules(Long id) {
		try {
			// TODO Auto-generated method stub
			core_Users_Modules_Repo.deleteById(id);
			return "User module "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
		

	}

	@Override
	public List<Core_Users_Modules> getUsers_ModulesByUserID(Long account_id, Long user_id) {
		// TODO Auto-generated method stub
		return core_Users_Modules_Repo.getUsers_ModulesByUserID(account_id, user_id);
	}

}

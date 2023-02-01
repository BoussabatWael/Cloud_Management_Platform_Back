package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Core_Accounts_Modules;
import com.gcs.cmp.repository.Core_Accounts_Modules_Repo;

@Service
public class Core_Accounts_Modules_ServiceImpl implements Core_Accounts_Modules_Service{

		
	@Autowired
	private Core_Accounts_Modules_Repo core_Accounts_Modules_Repo;
	
	@Override
	public Core_Accounts_Modules addCore_Accounts_Modules(Core_Accounts_Modules Core_Accounts_Modules) {
		// TODO Auto-generated method stub
		return core_Accounts_Modules_Repo.save(Core_Accounts_Modules);
	}

	@Override
	public List<Core_Accounts_Modules> findCore_Accounts_ModulesList(Long account_id) {
		// TODO Auto-generated method stub
		return core_Accounts_Modules_Repo.findCore_Accounts_ModulesList(account_id);
	}

	@Override
	public Core_Accounts_Modules updateCore_Accounts_Modules(Core_Accounts_Modules Core_Accounts_Modules) {
		// TODO Auto-generated method stub
		return core_Accounts_Modules_Repo.save(Core_Accounts_Modules);	
	}

	@Override
	public Optional<Core_Accounts_Modules> findCore_Accounts_ModulesById(Long id) {
		// TODO Auto-generated method stub
		return core_Accounts_Modules_Repo.findById(id);
	}

	@Override
	public String deleteCore_Accounts_Modules(Long id) {
		try {
			// TODO Auto-generated method stub
			core_Accounts_Modules_Repo.deleteById(id);
			return "Account module "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
		
	}

}

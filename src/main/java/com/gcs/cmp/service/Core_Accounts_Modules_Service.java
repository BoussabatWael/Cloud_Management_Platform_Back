package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import com.gcs.cmp.entity.Core_Accounts_Modules;


public interface Core_Accounts_Modules_Service {
	
	public Core_Accounts_Modules addCore_Accounts_Modules(Core_Accounts_Modules Core_Accounts_Modules) ;
	public List<Core_Accounts_Modules> findCore_Accounts_ModulesList(Long account_id);
	public Core_Accounts_Modules updateCore_Accounts_Modules(Core_Accounts_Modules Core_Accounts_Modules);
	public Optional<Core_Accounts_Modules> findCore_Accounts_ModulesById(Long id) ;
	public String deleteCore_Accounts_Modules(Long id) ;
	
}

package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import com.gcs.cmp.entity.Core_Users_Modules;

public interface Core_Users_Modules_Service {

	public Core_Users_Modules addCore_Users_Modules(Core_Users_Modules core_Users_Modules) ;
	public List<Core_Users_Modules> getCore_Users_ModulesList(Long account_id);
	public List<Core_Users_Modules> getUsers_ModulesByUserID(Long account_id, Long user_id);
	public Core_Users_Modules updateCore_Users_Modules(Core_Users_Modules core_Users_Modules);
	public Optional<Core_Users_Modules> findCore_Users_ModulesById(Long id) ;
	public String deleteCore_Users_Modules(Long id) ;
}

package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import com.gcs.cmp.entity.Core_Users_Security;


public interface Core_Users_Security_Service {

	public Core_Users_Security addCore_Users_Security(Core_Users_Security core_Users_Security) ;
	public List<Core_Users_Security> getCore_Users_SecurityList(Long account_id);
	public Core_Users_Security updateCore_Users_Security(Core_Users_Security core_Users_Security);
	public Optional<Core_Users_Security> getUsersSecurityByLogin(String login);
	public Optional<Core_Users_Security> getUsers_SecurityByUserID(Long account_id, Long user_id);
	public Optional<Core_Users_Security> findCore_Users_SecurityById(Long id) ;
	public String deleteCore_Users_Security(Long id) ;
	
}

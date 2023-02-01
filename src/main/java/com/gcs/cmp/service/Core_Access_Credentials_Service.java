package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import com.gcs.cmp.entity.Core_Access_Credentials;


public interface Core_Access_Credentials_Service {
	
	public Core_Access_Credentials addCore_Credentials(Core_Access_Credentials core_Credentials) ;
	public List<Core_Access_Credentials> findCore_Access_CredentialsList(Long account_id);
	public Core_Access_Credentials updateCore_Credentials(Core_Access_Credentials core_Credentials);
	public List<Core_Access_Credentials> 	getServersAccess_CredentialsByElementID(Long account_id,Long element_id) ;
	public Optional<Core_Access_Credentials> findCore_CredentialsById(Long id) ;
	public String deleteCore_Credentials(Long id) ;
}

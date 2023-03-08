package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Core_Access_Credentials;
import com.gcs.cmp.repository.Core_Access_Credentials_Repo;

@Service
public class Core_Access_Credentials_ServiceImpl implements Core_Access_Credentials_Service{

	@Autowired
	private Core_Access_Credentials_Repo core_Credentials_Repo;
	
	@Override
	public Core_Access_Credentials addCore_Credentials(Core_Access_Credentials core_Credentials) {
		// TODO Auto-generated method stub
		return core_Credentials_Repo.save(core_Credentials);
	}

	@Override
	public List<Core_Access_Credentials> findCore_Access_CredentialsList(Long account_id) {
		// TODO Auto-generated method stub
		return core_Credentials_Repo.findCore_Access_CredentialsList(account_id);
	}

	@Override
	public Core_Access_Credentials updateCore_Credentials(Core_Access_Credentials core_Credentials) {
		// TODO Auto-generated method stub
		return core_Credentials_Repo.save(core_Credentials);
	}

	@Override
	public Optional<Core_Access_Credentials> findCore_CredentialsById(Long id) {
		// TODO Auto-generated method stub
		return core_Credentials_Repo.findById(id);
	}

	@Override
	public String deleteCore_Credentials(Long id) {
		try {
			// TODO Auto-generated method stub
			core_Credentials_Repo.deleteById(id);
			return "Credential "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
	}

	@Override
	public List<Core_Access_Credentials> getServersAccess_CredentialsByElementID(Long account_id, Long element_id) {
		// TODO Auto-generated method stub
		return core_Credentials_Repo.getServersAccess_CredentialsByElementID(account_id, element_id);
	}
	
}

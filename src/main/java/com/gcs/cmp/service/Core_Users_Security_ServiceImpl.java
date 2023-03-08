package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Core_Users_Security;
import com.gcs.cmp.repository.Core_Users_Security_Repo;

@Service
public class Core_Users_Security_ServiceImpl implements Core_Users_Security_Service{

	@Autowired
	private Core_Users_Security_Repo core_Users_Security_Repo;
	
	@Override
	public Core_Users_Security addCore_Users_Security(Core_Users_Security core_Users_Security) {
		// TODO Auto-generated method stub
		return core_Users_Security_Repo.save(core_Users_Security);	
	}

	@Override
	public List<Core_Users_Security> getCore_Users_SecurityList(Long ccount_id) {
		// TODO Auto-generated method stub
		return core_Users_Security_Repo.getCore_Users_SecurityList(ccount_id);
	}

	@Override
	public Core_Users_Security updateCore_Users_Security(Core_Users_Security core_Users_Security) {
		// TODO Auto-generated method stub
		return core_Users_Security_Repo.save(core_Users_Security);
	}

	@Override
	public Optional<Core_Users_Security> findCore_Users_SecurityById(Long id) {
		// TODO Auto-generated method stub
		return core_Users_Security_Repo.findById(id);
	}

	@Override
	public String deleteCore_Users_Security(Long id) {
		try {
			// TODO Auto-generated method stub
			core_Users_Security_Repo.deleteById(id);
			return "User security"+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
	}

	@Override
	public Optional<Core_Users_Security> getUsers_SecurityByUserID(Long account_id, Long user_id) {
		// TODO Auto-generated method stub
		return core_Users_Security_Repo.getUsers_SecurityByUserID(account_id, user_id);
	}

	@Override
	public Optional<Core_Users_Security> getUsersSecurityByLogin(String login) {
		// TODO Auto-generated method stub
		return core_Users_Security_Repo.getUsersSecurityByLogin(login);
	}
	
}

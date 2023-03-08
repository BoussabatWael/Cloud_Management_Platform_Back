package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Core_Users_Tokens;
import com.gcs.cmp.repository.Core_Users_Tokens_Repo;

@Service
public class Core_Users_Tokens_ServiceImpl implements Core_Users_Tokens_Service{

	@Autowired
	private Core_Users_Tokens_Repo core_Users_Tokens_Repo;
	
	@Override
	public Core_Users_Tokens addCore_Users_Tokens(Core_Users_Tokens core_Users_Tokens) {
		// TODO Auto-generated method stub
		return core_Users_Tokens_Repo.save(core_Users_Tokens);
	}

	@Override
	public List<Core_Users_Tokens> getCore_Users_TokensList(Long account_id) {
		// TODO Auto-generated method stub
		return core_Users_Tokens_Repo.getCore_Users_TokensList(account_id);
	}

	@Override
	public Core_Users_Tokens updateCore_Users_Tokens(Core_Users_Tokens core_Users_Tokens) {
		// TODO Auto-generated method stub
		return core_Users_Tokens_Repo.save(core_Users_Tokens);
	}

	@Override
	public Optional<Core_Users_Tokens> findCore_Users_TokensById(Long id) {
		// TODO Auto-generated method stub
		return core_Users_Tokens_Repo.findById(id);
	}

	@Override
	public String deleteCore_Users_Tokens(Long id) {
		try {
			// TODO Auto-generated method stub
			core_Users_Tokens_Repo.deleteById(id);
			return "User token "+id+" has been deleted";
		}catch(Exception e) {
			return null;
		}		
	}

	@Override
	public Optional<Core_Users_Tokens> getUsersTokenByUserID(Long user_id) {
		// TODO Auto-generated method stub
		return core_Users_Tokens_Repo.getUsersTokenByUserID(user_id);
	}
	
}

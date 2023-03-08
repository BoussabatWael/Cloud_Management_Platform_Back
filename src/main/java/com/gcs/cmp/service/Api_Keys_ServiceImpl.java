package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Api_Keys;
import com.gcs.cmp.repository.Api_Keys_Repo;

@Service
public class Api_Keys_ServiceImpl implements Api_Keys_Service{

	@Autowired
	private Api_Keys_Repo api_Keys_Repo;
	
	@Override
	public Api_Keys addApi_Keys(Api_Keys api_Keys) {
		return api_Keys_Repo.save(api_Keys);
	}
	
	@Override
	public List<Api_Keys> getApi_KeysList(Long account_id) {
		// TODO Auto-generated method stub
		return api_Keys_Repo.findApi_KeysList(account_id);
	}

	@Override
	public Api_Keys updateApi_Keys(Api_Keys api_Keys) {
		
		return api_Keys_Repo.save(api_Keys);
	}

	@Override
	public Optional<Api_Keys> findApi_KeysById(Long id) {
		// TODO Auto-generated method stub
		return api_Keys_Repo.findById(id);
	}

	@Override
	public String deleteApi_Keys(Long id) {
		// TODO Auto-generated method stub
		try {
			api_Keys_Repo.deleteById(id);
			return "Key "+id+" has been deleted!";	
		}catch(Exception e) {
			return null;
		}
	}

	@Override
	public Optional<Api_Keys> getOneApiKey(Long account_id) {
		// TODO Auto-generated method stub
		return api_Keys_Repo.getOneApiKey(account_id);
	}
	
}

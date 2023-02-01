package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Core_Urls;
import com.gcs.cmp.repository.Core_Urls_Repo;

@Service
public class Core_Urls_ServiceImpl implements Core_Urls_Service{

	@Autowired
	private Core_Urls_Repo core_Urls_Repo;
	
	@Override
	public Core_Urls addCore_Urls(Core_Urls core_Urls) {
		// TODO Auto-generated method stub
		return core_Urls_Repo.save(core_Urls);
	}

	@Override
	public List<Core_Urls> getAllCore_Urls() {
		// TODO Auto-generated method stub
		return core_Urls_Repo.findAll();
	}

	@Override
	public Core_Urls updateCore_Urls(Core_Urls core_Urls) {
		// TODO Auto-generated method stub
		return core_Urls_Repo.save(core_Urls);
	}

	@Override
	public Optional<Core_Urls> findCore_UrlsById(Long id) {
		// TODO Auto-generated method stub
		return core_Urls_Repo.findById(id);
	}

	@Override
	public String deleteCore_Urls(Long id) {
		try {
			// TODO Auto-generated method stub
			core_Urls_Repo.deleteById(id);
			return "URL "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
		

	}

}

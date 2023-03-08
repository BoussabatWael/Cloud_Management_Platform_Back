package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Modules;
import com.gcs.cmp.repository.Modules_Repo;

@Service
public class Modules_ServiceImpl implements Modules_Service{

	@Autowired
	private Modules_Repo core_Modules_Repo;
	
	@Override
	public Modules addCore_Modules(Modules core_Modules) {
		// TODO Auto-generated method stub
		return core_Modules_Repo.save(core_Modules);
	}

	@Override
	public List<Modules> getModulesList() {
		// TODO Auto-generated method stub
		return core_Modules_Repo.getModulesList();
	}

	@Override
	public Modules updateCore_Modules(Modules core_Modules) {
		// TODO Auto-generated method stub
		return core_Modules_Repo.save(core_Modules);
	}

	@Override
	public Optional<Modules> findCore_ModulesById(Long id) {
		// TODO Auto-generated method stub
		return core_Modules_Repo.findById(id);
	}

	@Override
	public String deleteCore_Modules(Long id) {
		try {
			// TODO Auto-generated method stub
			core_Modules_Repo.deleteById(id);
			return "Module "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}		
	}
	
}

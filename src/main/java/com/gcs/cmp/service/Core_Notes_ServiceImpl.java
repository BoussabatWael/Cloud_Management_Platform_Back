package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Core_Notes;
import com.gcs.cmp.repository.Core_Notes_Repo;

@Service
public class Core_Notes_ServiceImpl implements Core_Notes_Service{

	@Autowired
	private Core_Notes_Repo core_Notes_Repo;
	
	@Override
	public Core_Notes addCore_Notes(Core_Notes core_Notes) {
		// TODO Auto-generated method stub
		return core_Notes_Repo.save(core_Notes);
	}

	@Override
	public List<Core_Notes> getAllCore_Notes() {
		// TODO Auto-generated method stub
		return core_Notes_Repo.findAll();
	}

	@Override
	public Core_Notes updateCore_Notes(Core_Notes core_Notes) {
		// TODO Auto-generated method stub
		return core_Notes_Repo.save(core_Notes);
	}

	@Override
	public Optional<Core_Notes> findCore_NotesById(Long id) {
		// TODO Auto-generated method stub
		return core_Notes_Repo.findById(id);
	}

	@Override
	public String deleteCore_Notes(Long id) {
		try {
			// TODO Auto-generated method stub
			core_Notes_Repo.deleteById(id);
			return "Note "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
	}
	
}

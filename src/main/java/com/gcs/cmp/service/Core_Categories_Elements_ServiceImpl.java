package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Core_Categories_Elements;
import com.gcs.cmp.repository.Core_Categories_Elements_Repo;

@Service
public class Core_Categories_Elements_ServiceImpl implements Core_Categories_Elements_Service{

	@Autowired
	private Core_Categories_Elements_Repo core_Categories_Elements_Repo;;
	
	@Override
	public Core_Categories_Elements addCore_Categories_Elements(Core_Categories_Elements core_Categories_Elements) {
		// TODO Auto-generated method stub
		return core_Categories_Elements_Repo.save(core_Categories_Elements);
	}

	@Override
	public List<Core_Categories_Elements> findCore_Categories_ElementsList(Long account_id) {
		// TODO Auto-generated method stub
		return core_Categories_Elements_Repo.findCore_Categories_ElementsList(account_id);
	}

	@Override
	public Core_Categories_Elements updateCore_Categories_Elements(Core_Categories_Elements core_Categories_Elements) {
		// TODO Auto-generated method stub
		return core_Categories_Elements_Repo.save(core_Categories_Elements);
	}

	@Override
	public Optional<Core_Categories_Elements> findCore_Categories_ElementsById(Long id) {
		// TODO Auto-generated method stub
		return core_Categories_Elements_Repo.findById(id);
	}

	@Override
	public String deleteCore_Categories_Elements(Long id) {
		try {
			// TODO Auto-generated method stub
			core_Categories_Elements_Repo.deleteById(id);
			return "Category element "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}	
	}
}

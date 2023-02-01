package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Core_Categories;
import com.gcs.cmp.repository.Core_Categories_Repo;

@Service
public class Core_Categories_ServiceImpl implements Core_Categories_Service{
	
	@Autowired
	private Core_Categories_Repo core_Categories_Repo;
	
	@Override
	public Core_Categories addCore_Categories(Core_Categories core_Categories) {
		// TODO Auto-generated method stub
		return core_Categories_Repo.save(core_Categories);	
	}

	@Override
	public List<Core_Categories> findCore_CategoriesList(Long account_id) {
		// TODO Auto-generated method stub
		return core_Categories_Repo.findCore_CategoriesList(account_id);
	}

	@Override
	public Core_Categories updateCore_Categories(Core_Categories core_Categories) {
		// TODO Auto-generated method stub
		return core_Categories_Repo.save(core_Categories);
	}

	@Override
	public Optional<Core_Categories> findCore_CategoriesById(Long id) {
		// TODO Auto-generated method stub
		return core_Categories_Repo.findById(id);
	}

	@Override
	public String deleteCore_Categories(Long id) {
		try {
			// TODO Auto-generated method stub
			core_Categories_Repo.deleteById(id);
			return "Category "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}	
	}

}

package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import com.gcs.cmp.entity.Core_Categories;


public interface Core_Categories_Service {
	
	public Core_Categories addCore_Categories(Core_Categories core_Categories) ;
	public List<Core_Categories> findCore_CategoriesList(Long account_id);
	public Core_Categories updateCore_Categories(Core_Categories core_Categories);
	public Optional<Core_Categories> findCore_CategoriesById(Long id) ;
	public String deleteCore_Categories(Long id) ;
	
}

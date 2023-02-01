package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import com.gcs.cmp.entity.Core_Categories_Elements;

public interface Core_Categories_Elements_Service {
	
	public Core_Categories_Elements addCore_Categories_Elements(Core_Categories_Elements core_Categories_Elements) ;
	public List<Core_Categories_Elements> findCore_Categories_ElementsList(Long account_id);
	public Core_Categories_Elements updateCore_Categories_Elements(Core_Categories_Elements core_Categories_Elements);
	public Optional<Core_Categories_Elements> findCore_Categories_ElementsById(Long id) ;
	public String deleteCore_Categories_Elements(Long id) ;
}

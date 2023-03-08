package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import com.gcs.cmp.entity.Core_Urls;


public interface Core_Urls_Service {

	public Core_Urls addCore_Urls(Core_Urls core_Urls) ;
	public List<Core_Urls> getAllCore_Urls();
	public Core_Urls updateCore_Urls(Core_Urls core_Urls);
	public Optional<Core_Urls> findCore_UrlsById(Long id) ;
	public String deleteCore_Urls(Long id) ;
	
}

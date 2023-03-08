package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import com.gcs.cmp.entity.Providers;


public interface Providers_Service {

	public Providers addCloud_Providers(Providers Cloud_Providers) ;
	public List<Providers> getProvidersList();
	public Providers updateCloud_Providers(Providers Cloud_Providers);
	public Optional<Providers> findCloud_ProvidersById(Long id) ;
	public String deleteCloud_Providers(Long id) ;
	
}

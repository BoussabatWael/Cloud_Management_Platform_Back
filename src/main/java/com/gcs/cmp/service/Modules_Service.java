package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import com.gcs.cmp.entity.Modules;


public interface Modules_Service {

	public Modules addCore_Modules(Modules core_Modules) ;
	public List<Modules> getModulesList();
	public Modules updateCore_Modules(Modules core_Modules);
	public Optional<Modules> findCore_ModulesById(Long id) ;
	public String deleteCore_Modules(Long id) ;
}

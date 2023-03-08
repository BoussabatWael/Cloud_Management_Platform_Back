package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import com.gcs.cmp.entity.Core_Notes;


public interface Core_Notes_Service {

	public Core_Notes addCore_Notes(Core_Notes core_Notes) ;
	public List<Core_Notes> getAllCore_Notes();
	public Core_Notes updateCore_Notes(Core_Notes core_Notes);
	public Optional<Core_Notes> findCore_NotesById(Long id) ;
	public String deleteCore_Notes(Long id) ;
	
}

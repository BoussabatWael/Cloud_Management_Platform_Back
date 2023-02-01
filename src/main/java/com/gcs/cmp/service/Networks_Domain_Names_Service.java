package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;
import com.gcs.cmp.entity.Networks_Domain_Names;

public interface Networks_Domain_Names_Service {

	public Networks_Domain_Names addNetworks_Domain_Names(Networks_Domain_Names networks_Domain_Names) ;
	public List<Networks_Domain_Names> getNetworks_Domain_NamesList(Long account_id);
	public List<Networks_Domain_Names> getNetworks_Sub_Domains_List(Long account_id, Long parent_id);
	public Networks_Domain_Names updateNetworks_Domain_Names(Networks_Domain_Names networks_Domain_Names);
	public Optional<Networks_Domain_Names> findNetworks_Domain_NamesById(Long id) ;
	public String deleteNetworks_Domain_Names(Long id) ;
}

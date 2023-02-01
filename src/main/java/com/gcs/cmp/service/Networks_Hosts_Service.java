package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;
import com.gcs.cmp.entity.Networks_Hosts;

public interface Networks_Hosts_Service {

	public Networks_Hosts addNetworks_Hosts(Networks_Hosts networks_Hosts) ;
	public List<Networks_Hosts> getNetworks_HostsList(Long account_id);
	public List<Networks_Hosts> getDomainNetworks_HostsList(Long account_id,Long domain_name_id);
	public Networks_Hosts updateNetworks_Hosts(Networks_Hosts networks_Hosts);
	public Optional<Networks_Hosts> findNetworks_HostsById(Long id) ;
	public String deleteNetworks_Hosts(Long id) ;
}

package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Networks_Hosts;
import com.gcs.cmp.repository.Networks_Hosts_Repo;

@Service
public class Networks_Hosts_ServiceImpl implements Networks_Hosts_Service{

	@Autowired
	private Networks_Hosts_Repo networks_Hosts_Repo;
	
	@Override
	public Networks_Hosts addNetworks_Hosts(Networks_Hosts networks_Hosts) {
		// TODO Auto-generated method stub
		return networks_Hosts_Repo.save(networks_Hosts);
	}

	@Override
	public List<Networks_Hosts> getNetworks_HostsList(Long account_id) {
		// TODO Auto-generated method stub
		return networks_Hosts_Repo.getNetworks_HostsList(account_id);
	}

	@Override
	public Networks_Hosts updateNetworks_Hosts(Networks_Hosts networks_Hosts) {
		// TODO Auto-generated method stub
		return networks_Hosts_Repo.save(networks_Hosts);
	}

	@Override
	public Optional<Networks_Hosts> findNetworks_HostsById(Long id) {
		// TODO Auto-generated method stub
		return networks_Hosts_Repo.findById(id);
	}

	@Override
	public String deleteNetworks_Hosts(Long id) {
		try {
			// TODO Auto-generated method stub
			networks_Hosts_Repo.deleteById(id);
			return "Network host "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
	}

	@Override
	public List<Networks_Hosts> getDomainNetworks_HostsList(Long account_id, Long domain_name_id) {
		// TODO Auto-generated method stub
		return networks_Hosts_Repo.getDomainNetworks_HostsList(account_id, domain_name_id);
	}
	
}

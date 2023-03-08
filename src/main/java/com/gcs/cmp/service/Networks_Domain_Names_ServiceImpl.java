package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Networks_Domain_Names;
import com.gcs.cmp.repository.Networks_Domain_Names_Repo;

@Service
public class Networks_Domain_Names_ServiceImpl implements Networks_Domain_Names_Service{

	@Autowired
	private Networks_Domain_Names_Repo networks_Domain_Names_Repo;
	
	@Override
	public Networks_Domain_Names addNetworks_Domain_Names(Networks_Domain_Names networks_Domain_Names) {
		// TODO Auto-generated method stub
		return networks_Domain_Names_Repo.save(networks_Domain_Names);
	}

	@Override
	public List<Networks_Domain_Names> getNetworks_Domain_NamesList(Long account_id) {
		// TODO Auto-generated method stub
		return networks_Domain_Names_Repo.getNetworks_Domain_NamesList(account_id);
	}

	@Override
	public Networks_Domain_Names updateNetworks_Domain_Names(Networks_Domain_Names networks_Domain_Names) {
		// TODO Auto-generated method stub
		return networks_Domain_Names_Repo.save(networks_Domain_Names);
	}

	@Override
	public Optional<Networks_Domain_Names> findNetworks_Domain_NamesById(Long id) {
		// TODO Auto-generated method stub
		return networks_Domain_Names_Repo.findById(id);
	}

	@Override
	public String deleteNetworks_Domain_Names(Long id) {
		try {
			// TODO Auto-generated method stub
			networks_Domain_Names_Repo.deleteById(id);
			return "Domain name "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}		
	}

	@Override
	public List<Networks_Domain_Names> getNetworks_Sub_Domains_List(Long account_id, Long parent_id) {
		// TODO Auto-generated method stub
		return networks_Domain_Names_Repo.getNetworks_Sub_Domains_List(account_id, parent_id);
	}
	
}

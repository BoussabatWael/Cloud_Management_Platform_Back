package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import com.gcs.cmp.entity.Core_Users_Instances;

public interface Core_Users_Instances_Service {

	public Core_Users_Instances addCore_Users_Instances(Core_Users_Instances core_Users_Instances) ;
	public List<Core_Users_Instances> getCore_Users_InstancesList(Long account_id);
	public List<Core_Users_Instances> getUsers_InstancesByUserID(Long account_id, Long user_id) ;
	public Core_Users_Instances updateCore_Users_Instances(Core_Users_Instances core_Users_Instances);
	public Optional<Core_Users_Instances> findCore_Users_InstancesById(Long id) ;
	public String deleteCore_Users_Instances(Long id) ;
}

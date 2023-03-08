package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;
import com.gcs.cmp.entity.Monitoring_Policies_Servers;

public interface Monitoring_Policies_Servers_Service {

	public Monitoring_Policies_Servers addMonitoring_Policies_Servers(Monitoring_Policies_Servers monitoring_Policies_Servers) ;
	public List<Monitoring_Policies_Servers> getMonitoring_Policies_ServersList(Long account_id);
	public Monitoring_Policies_Servers updateMonitoring_Policies_Servers(Monitoring_Policies_Servers monitoring_Policies_Servers);
	public Optional<Monitoring_Policies_Servers> findMonitoring_Policies_ServersById(Long id) ;
	public String deleteMonitoring_Policies_Servers(Long id) ;
	
}

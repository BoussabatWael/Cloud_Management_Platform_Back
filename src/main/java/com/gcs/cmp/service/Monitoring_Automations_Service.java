package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;
import com.gcs.cmp.entity.Monitoring_Automations;

public interface Monitoring_Automations_Service {

	public Monitoring_Automations addMonitoring_Automations(Monitoring_Automations monitoring_Automations) ;
	public List<Monitoring_Automations> getMonitoring_AutomationsList(Long account_id);
	public Monitoring_Automations updateMonitoring_Automations(Monitoring_Automations monitoring_Automations);
	public Optional<Monitoring_Automations> findMonitoring_AutomationsById(Long id) ;
	public String deleteMonitoring_Automations(Long id) ;
	
}

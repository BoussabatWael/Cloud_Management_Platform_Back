package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;
import com.gcs.cmp.entity.Monitoring_Policies_Alerts;

public interface Monitoring_Policies_Alerts_Service {

	public Monitoring_Policies_Alerts addMonitoring_Policies_Alerts(Monitoring_Policies_Alerts monitoring_Policies_Alerts) ;
	public List<Monitoring_Policies_Alerts> getMonitoring_Policies_AlertsList(Long account_id);
	public Monitoring_Policies_Alerts updateMonitoring_Policies_Alerts(Monitoring_Policies_Alerts monitoring_Policies_Alerts);
	public Optional<Monitoring_Policies_Alerts> findMonitoring_Policies_AlertsById(Long id) ;
	public String deleteMonitoring_Policies_Alerts(Long id) ;
}

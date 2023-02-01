package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;
import com.gcs.cmp.entity.Monitoring_Commands;

public interface Monitoring_Commands_Service {

	public Monitoring_Commands addMonitoring_Commands(Monitoring_Commands monitoring_Commands) ;
	public List<Monitoring_Commands> getMonitoring_CommandsList(Long account_id);
	public Monitoring_Commands updateMonitoring_Commands(Monitoring_Commands monitoring_Commands);
	public Optional<Monitoring_Commands> findMonitoring_CommandsById(Long id) ;
	public String deleteMonitoring_Commands(Long id) ;
}

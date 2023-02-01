package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;
import com.gcs.cmp.entity.Monitoring_Commands_Executions;

public interface Monitoring_Commands_Executions_Service {

	public Monitoring_Commands_Executions addMonitoring_Commands_Executions(Monitoring_Commands_Executions monitoring_Commands_Executions) ;
	public List<Monitoring_Commands_Executions> getMonitoring_Commands_ExecutionsList(Long account_id);
	public Monitoring_Commands_Executions updateMonitoring_Commands_Executions(Monitoring_Commands_Executions monitoring_Commands_Executions);
	public Optional<Monitoring_Commands_Executions> findMonitoring_Commands_ExecutionsById(Long id) ;
	public String deleteMonitoring_Commands_Executions(Long id) ;
}

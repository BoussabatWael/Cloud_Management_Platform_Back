package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;
import com.gcs.cmp.entity.Monitoring_Metrics;

public interface Monitoring_Metrics_Service {

	public Monitoring_Metrics addMonitoring_Metrics(Monitoring_Metrics monitoring_Metrics) ;
	public List<Monitoring_Metrics> getMonitoring_MetricsList(Long account_id);
	public Monitoring_Metrics updateMonitoring_Metrics(Monitoring_Metrics monitoring_Metrics);
	public Optional<Monitoring_Metrics> findMonitoring_MetricsById(Long id) ;
	public String deleteMonitoring_Metrics(Long id) ;
	
}

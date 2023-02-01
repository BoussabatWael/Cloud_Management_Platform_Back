package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;
import com.gcs.cmp.entity.Metrics;

public interface Metrics_Service {

	public Metrics addMetrics(Metrics metrics) ;
	public List<Metrics> getMetricsList();
	public Metrics updateMetrics(Metrics metrics);
	public Optional<Metrics> findMetricsById(Long id) ;
	public String deleteMetrics(Long id) ;
}

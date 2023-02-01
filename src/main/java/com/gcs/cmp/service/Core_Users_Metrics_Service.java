package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;
import com.gcs.cmp.entity.Core_Users_Metrics;

public interface Core_Users_Metrics_Service {

	public Core_Users_Metrics addCore_Users_Metrics(Core_Users_Metrics core_Users_Metrics) ;
	public List<Core_Users_Metrics> getCore_Users_MetricsList(Long account_id);
	public Core_Users_Metrics updateCore_Users_Metrics(Core_Users_Metrics core_Users_Metrics);
	public Optional<Core_Users_Metrics> findCore_Users_MetricsById(Long id) ;
	public String deleteCore_Users_Metrics(Long id) ;
}

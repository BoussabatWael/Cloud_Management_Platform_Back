package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;
import com.gcs.cmp.entity.Monitoring_Policies;

public interface Monitoring_Policies_Service {

	public Monitoring_Policies addMonitoring_Policies(Monitoring_Policies monitoring_Policies) ;
	public List<Monitoring_Policies> getMonitoring_PoliciesList(Long account_id);
	public Monitoring_Policies updateMonitoring_Policies(Monitoring_Policies monitoring_Policies);
	public Optional<Monitoring_Policies> findMonitoring_PoliciesById(Long id) ;
	public String deleteMonitoring_Policies(Long id) ;
}

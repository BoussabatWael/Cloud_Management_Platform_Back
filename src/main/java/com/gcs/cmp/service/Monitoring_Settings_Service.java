package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;
import com.gcs.cmp.entity.Monitoring_Settings;

public interface Monitoring_Settings_Service {

	public Monitoring_Settings addMonitoring_Settings(Monitoring_Settings monitoring_Settings) ;
	public List<Monitoring_Settings> getMonitoring_SettingsList(Long account_d);
	public Monitoring_Settings updateMonitoring_Settings(Monitoring_Settings monitoring_Settings);
	public Optional<Monitoring_Settings> findMonitoring_SettingsById(Long id) ;
	public String deleteMonitoring_Settings(Long id) ;
}

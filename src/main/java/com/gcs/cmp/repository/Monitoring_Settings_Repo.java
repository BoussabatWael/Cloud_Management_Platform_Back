package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gcs.cmp.entity.Monitoring_Settings;

public interface Monitoring_Settings_Repo extends JpaRepository<Monitoring_Settings, Long>{

	@Query(value="SELECT * FROM monitoring_settings WHERE account_id=?1",nativeQuery=true)
	public List<Monitoring_Settings> getMonitoring_SettingsList(Long account_id);
	
}

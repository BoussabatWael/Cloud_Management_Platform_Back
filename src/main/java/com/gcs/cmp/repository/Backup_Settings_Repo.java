package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gcs.cmp.entity.Backup_Settings;

public interface Backup_Settings_Repo extends JpaRepository<Backup_Settings, Long>{

	@Query(value="select * from backup_settings where account_id=?1",nativeQuery=true)
	public List<Backup_Settings> findBackup_SettingsList(Long account_id);
}

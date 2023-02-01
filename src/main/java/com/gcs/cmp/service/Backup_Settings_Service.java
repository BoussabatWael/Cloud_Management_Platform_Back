package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;
import com.gcs.cmp.entity.Backup_Settings;

public interface Backup_Settings_Service {

	public Backup_Settings addBackup_Settings(Backup_Settings backup_Settings) ;
	public List<Backup_Settings> findBackup_SettingsList(Long account_id);
	public Backup_Settings updateBackup_Settings(Backup_Settings backup_Settings);
	public Optional<Backup_Settings> findBackup_SettingsById(Long id) ;
	public String deleteBackup_Settings(Long id) ;
}

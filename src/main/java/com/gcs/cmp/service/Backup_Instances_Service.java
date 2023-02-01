package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;
import com.gcs.cmp.entity.Backup_Instances;

public interface Backup_Instances_Service {

	public Backup_Instances addBackup_Instances(Backup_Instances backup_Instances) ;
	public List<Backup_Instances> getBackup_InstancesList(Long account_id);
	public Backup_Instances updateBackup_Instances(Backup_Instances backup_Instances);
	public Optional<Backup_Instances> findBackup_InstancesById(Long id) ;
	public String deleteBackup_Instances(Long id) ;
}

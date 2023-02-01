package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;
import com.gcs.cmp.entity.Backup_Operations;

public interface Backup_Operations_Service {

	public Backup_Operations addBackup_Operations(Backup_Operations backup_Operations) ;
	public List<Backup_Operations> findBackup_OperationsList(Long account_id);
	public List<Backup_Operations> getBackup_OperationsByInstanceID(Long account_id, Long instance_id);
	public Backup_Operations updateBackup_Operations(Backup_Operations backup_Operations);
	public Optional<Backup_Operations> findBackup_OperationsById(Long id) ;
	public String deleteBackup_Operations(Long id) ;
}

package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import com.gcs.cmp.entity.Backup_Executions;

public interface Backup_Executions_Service {

	public Backup_Executions addBackup_Executions(Backup_Executions backup_Executions);
	public List<Backup_Executions> getBackup_ExecutionsList(Long account_id);
	public Backup_Executions updateBackup_Executions(Backup_Executions backup_Executions);
	public Optional<Backup_Executions> getBackup_ExecutionsByOperationID(Long account_id, Long operation_id);
	public Optional<Backup_Executions> findBackup_ExecutionsById(Long id);
	public String deleteBackup_Executions(Long id);
	
}

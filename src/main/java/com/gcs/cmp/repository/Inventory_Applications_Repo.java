package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gcs.cmp.entity.Inventory_Applications;

public interface Inventory_Applications_Repo extends JpaRepository<Inventory_Applications, Long>{

	@Query(value="SELECT * FROM inventory_applications WHERE account_id=?1 AND status IN (1,2,3)",nativeQuery=true)
	public List<Inventory_Applications> geInventory_ApplicationsList(Long account_id);
	
	@Query(value="SELECT app.* FROM inventory_applications app INNER JOIN inventory_applications_instances app_ins ON app.id = app_ins.application_id WHERE app.account_id=?1 AND app.status IN (1,2,3) AND app_ins.instance_id=?2 AND app_ins.status IN (1,2,3)",nativeQuery=true)
	public List<Inventory_Applications> getApplicationsByInstanceID(Long account_id, Long instance_id);
	
}

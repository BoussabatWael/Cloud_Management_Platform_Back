package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gcs.cmp.entity.Inventory_Templates_Apps;

public interface Inventory_Templates_Apps_Repo extends JpaRepository<Inventory_Templates_Apps, Long>{

	@Query(value="SELECT inventory_templates_apps.* FROM inventory_templates_apps INNER JOIN inventory_templates ON inventory_templates_apps.template_id = inventory_templates.id WHERE inventory_templates.account_id=?1 AND inventory_templates.status IN (1,2,3) AND inventory_templates_apps.status IN (1,2,3)",nativeQuery=true)
	public List<Inventory_Templates_Apps> getInventory_Templates_AppsList(Long account_id);
}

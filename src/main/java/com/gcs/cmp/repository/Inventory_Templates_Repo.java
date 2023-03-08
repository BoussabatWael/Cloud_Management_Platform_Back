package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gcs.cmp.entity.Inventory_Templates;

public interface Inventory_Templates_Repo extends JpaRepository<Inventory_Templates, Long>{

	@Query(value="SELECT * FROM inventory_templates WHERE account_id=?1 AND status IN (1,2,3)",nativeQuery=true)
	public List<Inventory_Templates> getInventory_TemplatesList(Long account_id);
	
}

package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gcs.cmp.entity.Core_Categories;


public interface Core_Categories_Repo extends JpaRepository<Core_Categories, Long>{

	@Query(value="select * from core_categories where status IN (1,2,3) AND account_id=?1",nativeQuery=true)
	public List<Core_Categories> findCore_CategoriesList(Long account_id);
	
}

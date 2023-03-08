package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gcs.cmp.entity.Core_Categories_Elements;


public interface Core_Categories_Elements_Repo extends JpaRepository<Core_Categories_Elements, Long>{
	
	@Query(value="SELECT core_categories_elements.* FROM core_categories_elements INNER JOIN core_categories ON core_categories_elements.category_id = core_categories.id WHERE core_categories.account_id =?1 AND core_categories.status IN (1,2,3)",nativeQuery=true)
	public List<Core_Categories_Elements> findCore_Categories_ElementsList(Long account_id);
	
}

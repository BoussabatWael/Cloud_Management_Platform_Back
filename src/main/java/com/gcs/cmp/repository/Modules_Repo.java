package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gcs.cmp.entity.Modules;


public interface Modules_Repo extends JpaRepository<Modules, Long> {

	@Query(value="SELECT * FROM modules WHERE status IN (1,2,3)",nativeQuery=true)
	public List<Modules> getModulesList();
}

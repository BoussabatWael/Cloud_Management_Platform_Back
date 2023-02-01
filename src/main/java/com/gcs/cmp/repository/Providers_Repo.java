package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gcs.cmp.entity.Providers;


public interface Providers_Repo extends JpaRepository<Providers, Long>{

	@Query(value="SELECT * FROM providers WHERE status IN (1,2,3)",nativeQuery=true)
	public List<Providers> getProvidersList();
}

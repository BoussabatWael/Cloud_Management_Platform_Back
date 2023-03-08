package com.gcs.cmp.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;
import com.gcs.cmp.entity.Inventory_Applications;

public interface Inventory_Applications_Service {

	public Inventory_Applications addInventory_Applications(Inventory_Applications inventory_Applications) ;
	public Inventory_Applications addApplication(String application, MultipartFile file) throws IOException;
	public List<Inventory_Applications> geInventory_ApplicationsList(Long account_id);
	public Inventory_Applications updateInventory_Applications(Inventory_Applications inventory_Applications);
	public Inventory_Applications updateApplication(String application, MultipartFile file) throws Exception;
	public List<Inventory_Applications> getApplicationsByInstanceID(Long account_id,Long instance_id);
	public Optional<Inventory_Applications> findInventory_ApplicationsById(Long id) ;
	public String deleteInventory_Applications(Long id) ;
	
}

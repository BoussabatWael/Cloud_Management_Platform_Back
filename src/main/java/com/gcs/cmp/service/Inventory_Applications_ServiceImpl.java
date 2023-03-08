package com.gcs.cmp.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gcs.cmp.entity.Inventory_Applications;
import com.gcs.cmp.repository.Inventory_Applications_Repo;

@Service
public class Inventory_Applications_ServiceImpl implements Inventory_Applications_Service{

	@Autowired
	private Inventory_Applications_Repo inventory_Applications_Repo;
	
	@Override
	public Inventory_Applications addInventory_Applications(Inventory_Applications inventory_Applications) {
		// TODO Auto-generated method stub
		inventory_Applications.setLogo("Unknown_app.png");
		return inventory_Applications_Repo.save(inventory_Applications);
	}

	@Override
	public List<Inventory_Applications> geInventory_ApplicationsList(Long account_id) {
		// TODO Auto-generated method stub
		return inventory_Applications_Repo.geInventory_ApplicationsList(account_id);
	}

	@Override
	public Inventory_Applications updateInventory_Applications(Inventory_Applications inventory_Applications) {
		// TODO Auto-generated method stub
		return inventory_Applications_Repo.save(inventory_Applications);
	}

	@Override
	public Optional<Inventory_Applications> findInventory_ApplicationsById(Long id) {
		// TODO Auto-generated method stub
		return inventory_Applications_Repo.findById(id);
	}

	@Override
	public String deleteInventory_Applications(Long id) {
		try {
			// TODO Auto-generated method stub
			inventory_Applications_Repo.deleteById(id);
			return "Application "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
	}

	@Override
	public List<Inventory_Applications> getApplicationsByInstanceID(Long account_id, Long instance_id) {
		// TODO Auto-generated method stub
		return inventory_Applications_Repo.getApplicationsByInstanceID(account_id, instance_id);
	}

	@Override
	public Inventory_Applications addApplication(String application, MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		String fileName=saveImage(file);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		Inventory_Applications app = objectMapper.readValue(application, Inventory_Applications.class);
		app.setLogo(fileName);
		return inventory_Applications_Repo.save(app);
	}

	@Override
	public Inventory_Applications updateApplication(String application, MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
		String fileName=saveImage(file);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		Inventory_Applications app = objectMapper.readValue(application, Inventory_Applications.class);
		app.setLogo(fileName);
		return inventory_Applications_Repo.save(app);
	}
		
	private String saveImage(MultipartFile file) throws IOException {
		
		String filename=file.getOriginalFilename();
		String tab[]=filename.split("\\.");
		String
		filenameModif=tab[0]+"_"+System.currentTimeMillis()+"."+tab[1];
		File f=new
		//File(System.getProperty("user.home")+"/OneDrive/Bureau/Argus/serverMonitoring/src/assets/images/uploads/"+filenameModif);
		File("/home/waelitwi/public_html/cloud_manager/assets/images/uploads/"+filenameModif);
		FileOutputStream fos=new FileOutputStream(f);
		fos.write(file.getBytes());
		fos.close();
		//FileUtils.writeByteArrayToFile(f, file.getBytes());
		return filenameModif;
	}
	
}

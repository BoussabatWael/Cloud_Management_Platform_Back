package com.gcs.cmp.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gcs.cmp.entity.Core_Users;
import com.gcs.cmp.entity.Core_Users_Security;
import com.gcs.cmp.repository.Core_Accounts_Repo;
import com.gcs.cmp.repository.Core_Users_Repo;
import com.gcs.cmp.repository.Core_Users_Security_Repo;

@Service
public class Core_Users_ServiceImpl implements Core_Users_Service{

	@Autowired
	private Core_Users_Repo core_Users_Repo;
	
	@Autowired
	private Core_Accounts_Repo core_Accounts_Repo;
	
	@Autowired
	private Core_Users_Security_Repo core_Users_Security_Repo;
	
	@Override
	public Core_Users addCore_Users(Core_Users core_Users) {
		// TODO Auto-generated method stub
		String firstname = core_Users.getFirstname();
		Optional<Core_Users_Security> user_sec = core_Users_Security_Repo.getUsersSecurityByLogin(firstname);
		if(!user_sec.equals(Optional.empty())){
			return null;
		}else {
			core_Users.setPhoto("UnknownUser_1660663421506.png");
			return core_Users_Repo.save(core_Users);
		}
	}

	@Override
	public List<Core_Users> findCore_UsersList(Long account_id) {
		// TODO Auto-generated method stub
		return core_Users_Repo.findCore_UsersList(account_id);
	}

	@Override
	public Core_Users updateCore_Users(Core_Users core_Users) {
		// TODO Auto-generated method stub
		if(core_Users.getAccount() == null) {
			return null;
		}else {
			if(core_Accounts_Repo.findById(core_Users.getAccount().getId()).equals(Optional.empty())) {
				return null;
			}else {
				core_Users.setAccount(core_Accounts_Repo.findById(core_Users.getAccount().getId()).get());
				return core_Users_Repo.save(core_Users);
			}
		}
	}

	public Core_Users updateUser(String users, MultipartFile file) throws Exception{
		String fileName=saveImage(file);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		Core_Users usr = objectMapper.readValue(users, Core_Users.class);
		usr.setPhoto(fileName);
		return core_Users_Repo.saveAndFlush(usr);
	}
	
	@Override
	public Optional<Core_Users> findCore_UsersId(Long id) {
		// TODO Auto-generated method stub
		return core_Users_Repo.findById(id);
	}

	@Override
	public String deleteCore_Users(Long id) {
		try {
			// TODO Auto-generated method stub
			core_Users_Repo.deleteById(id);
			return "User "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
		

	}

	public static String encrypt(String value) {
		String key = "AAAAAAAAAAAAAAAA";
		String initVector = "BBBBBBBBBBBBBBBB";
	    try {
	        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
	        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	 
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
	 
	        byte[] encrypted = cipher.doFinal(value.getBytes());
	        return Base64.getEncoder().encodeToString(encrypted);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}
	
	public String generateKey() {
		String generatedKey = "";
		String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	    try {
	    	for (int i = 0; i < 30; i++){
	    	      generatedKey += possible.charAt((int) Math.floor(Math.random() * possible.length()));
	    	    }
	    	System.out.println(generatedKey);
  	      return generatedKey;

	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}
	
	private String saveImage(MultipartFile file) throws Exception {
	String filename=file.getOriginalFilename();
	String tab[]=filename.split("\\.");
	String
	filenameModif=tab[0]+"_"+System.currentTimeMillis()+"."+tab[1];
	File f=new
	File(System.getProperty("user.home")+"/OneDrive/Bureau/Argus/serverMonitoring/src/assets/images/uploads/"+filenameModif);
	//File("/home/waelitwi/public_html/cloud_manager/assets/images/uploads/"+filenameModif);
	FileOutputStream fos=new FileOutputStream(f);
	fos.write(file.getBytes());
	fos.close();
	//FileUtils.writeByteArrayToFile(f, file.getBytes());
	return filenameModif;
	}
}

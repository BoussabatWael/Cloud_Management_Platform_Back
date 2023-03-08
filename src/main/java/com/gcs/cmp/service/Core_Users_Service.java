package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.gcs.cmp.entity.Core_Users;

public interface Core_Users_Service {
	
	public Core_Users addCore_Users(Core_Users core_Users) ;
	public List<Core_Users> findCore_UsersList(Long account_id);
	public Core_Users updateCore_Users(Core_Users core_Users);
	public Core_Users updateUser(String users, MultipartFile file) throws Exception;
	public Optional<Core_Users> findCore_UsersId(Long id) ;
	public String deleteCore_Users(Long id) ;
	
}

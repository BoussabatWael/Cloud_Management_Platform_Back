package com.gcs.cmp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Api_Keys;
import com.gcs.cmp.entity.Core_Accounts;
import com.gcs.cmp.entity.Core_Users;
import com.gcs.cmp.entity.Core_Users_Permissions;
import com.gcs.cmp.entity.Core_Users_Security;
import com.gcs.cmp.entity.Core_Users_Tokens;
import com.gcs.cmp.repository.Api_Keys_Repo;
import com.gcs.cmp.repository.Core_Accounts_Repo;
import com.gcs.cmp.repository.Core_Users_Permissions_Repo;
import com.gcs.cmp.repository.Core_Users_Repo;
import com.gcs.cmp.repository.Core_Users_Security_Repo;
import com.gcs.cmp.repository.Core_Users_Tokens_Repo;
import com.gcs.cmp.validators.Inputs_Validations;

@Service
public class Core_Accounts_ServiceImpl extends Inputs_Validations implements Core_Accounts_Service{
	
	@Autowired
	private Core_Accounts_Repo coreAccountsRepo;
	
	@Autowired
	private Core_Users_Repo core_Users_Repo;
	
	@Autowired
	private Core_Users_Security_Repo core_Users_Security_Repo;
	
	@Autowired
	private Core_Users_Tokens_Repo core_Users_Tokens_Repo;
	
	@Autowired
	private Core_Users_Permissions_Repo core_Users_Permissions_Repo;
	
	@Autowired
	private Api_Keys_Repo api_Keys_Repo;
	
	@Override
	public Core_Accounts addCloudAccount(Core_Accounts core_Accounts) {
		// TODO Auto-generated method stub
		return coreAccountsRepo.save(core_Accounts);
	}

	@Override
	public Core_Accounts addCloudAccountandOther(Core_Accounts core_Accounts) {
		// TODO Auto-generated method stub
		Random rand = new Random(); 
	    int upperbound = 50;
	    int int_random = rand.nextInt(upperbound); 
	      
		Core_Accounts account = coreAccountsRepo.save(core_Accounts);
		
		Api_Keys apikey = new Api_Keys();
		apikey.setAccount(account);
		apikey.setKey_value(generateKey());
		apikey.setStatus(1);
		apikey.setType(1);
		apikey.setStart_date(LocalDateTime.now());
		api_Keys_Repo.save(apikey);
				
		Core_Users user = new Core_Users();
		user.setFirstname(account.getName()+int_random);
		user.setLastname(account.getName()+int_random);
		user.setBrowser("chrome");
		user.setIp_address("0.0.0.0");
		user.setLanguage("EN");
		user.setLast_auth(LocalDateTime.now());
		user.setAccount(account);
		user.setHas_token(1);
		user.setRole(1);
		user.setStatus(1);
		user.setEmail(account.getName()+"@gmail.com");
		core_Users_Repo.save(user);
		
		Core_Users_Security user_security = new Core_Users_Security();
		user_security.setLogin(account.getName()+int_random);
		user_security.setPassword(encrypt(account.getName()+int_random));
		user_security.setUser(user);
		user_security.setStatus(1);
		user_security.setStart_date(LocalDateTime.now());
		core_Users_Security_Repo.save(user_security);
		
		Core_Users_Tokens user_token = new Core_Users_Tokens();
		user_token.setToken(generateKey());
		user_token.setStart_date(LocalDateTime.now());
		user_token.setUser(user);
		user_token.setStatus(1);
		core_Users_Tokens_Repo.save(user_token);
		
		Core_Users_Permissions user_permissions = new Core_Users_Permissions();
		user_permissions.setCode("{\"users\":[\"view\",\"update\",\"delete\",\"create\"],\"servers\":[\"create\",\"update\",\"view\",\"delete\"],\"domains\":[\"create\",\"update\",\"view\",\"delete\"],\"applications\":[\"create\",\"update\",\"view\",\"delete\"],\"backups\":[\"create\",\"update\",\"view\",\"delete\"],\"providers\":[\"create\",\"update\",\"view\",\"delete\"],\"deployments\":[\"create\",\"update\",\"view\",\"delete\"],\"access_credentials\":[\"create\",\"update\",\"view\",\"delete\"],\"hosts\":[\"create\",\"update\",\"view\",\"delete\"],\"ssl\":[\"create\",\"update\",\"view\",\"delete\"]}");
		user_permissions.setDependency(1);
		user_permissions.setStatus(1);
		user_permissions.setUser(user);
		core_Users_Permissions_Repo.save(user_permissions);
		
		return account;
	}
	
	@Override
	public List<Core_Accounts> findCore_AccountsList() {
		// TODO Auto-generated method stub
		return coreAccountsRepo.findCore_AccountsList();
	}

	@Override
	public Core_Accounts updateCore_Accounts(Core_Accounts core_Accounts) {
		// TODO Auto-generated method stub
		return coreAccountsRepo.save(core_Accounts);
	}

	@Override
	public Optional<Core_Accounts> findCore_AccountsById(Long id) {
		// TODO Auto-generated method stub
		return coreAccountsRepo.findById(id);
	}

	@Override
	public String deleteCore_Accounts(Long id) {
		try {
			// TODO Auto-generated method stub
			coreAccountsRepo.deleteById(id);
			return "Account "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}		
	}	
	
}

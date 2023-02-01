package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;
import com.gcs.cmp.entity.Core_Accounts;

public interface Core_Accounts_Service {
	
	public Core_Accounts addCloudAccount(Core_Accounts core_Accounts) ;
	public Core_Accounts addCloudAccountandOther(Core_Accounts core_Accounts) ;
	public List<Core_Accounts> findCore_AccountsList();
	public Core_Accounts updateCore_Accounts(Core_Accounts core_Accounts);
	public Optional<Core_Accounts> findCore_AccountsById(Long id) ;
	public String deleteCore_Accounts(Long id) ;
}

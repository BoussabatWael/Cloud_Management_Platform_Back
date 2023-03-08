package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;
import com.gcs.cmp.entity.Core_Users_Tokens;

public interface Core_Users_Tokens_Service {

	public Core_Users_Tokens addCore_Users_Tokens(Core_Users_Tokens core_Users_Tokens) ;
	public List<Core_Users_Tokens> getCore_Users_TokensList(Long account_id);
	public Core_Users_Tokens updateCore_Users_Tokens(Core_Users_Tokens core_Users_Tokens);
	public Optional<Core_Users_Tokens> findCore_Users_TokensById(Long id) ;
	public Optional<Core_Users_Tokens> getUsersTokenByUserID(Long user_id);
	public String deleteCore_Users_Tokens(Long id) ;
	
}

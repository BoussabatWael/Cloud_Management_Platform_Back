package com.gcs.cmp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gcs.cmp.entity.Core_Users_Tokens;

public interface Core_Users_Tokens_Repo extends JpaRepository<Core_Users_Tokens, Long>{
	
	@Query(value="SELECT core_users_tokens.* FROM core_users_tokens INNER JOIN core_users ON core_users_tokens.user_id = core_users.id WHERE core_users.account_id=?1 AND core_users.status IN (1,2,3) AND core_users_tokens.status IN (1,2,3)",nativeQuery=true)
	public List<Core_Users_Tokens> getCore_Users_TokensList(Long account_id);
	
	@Query(value="SELECT a.* FROM core_users_tokens a INNER JOIN core_users b ON a.user_id = b.id WHERE a.user_id=?1 AND a.status IN (1,2,3) AND b.status IN (1,2,3)",nativeQuery=true)
	public Optional<Core_Users_Tokens> getUsersTokenByUserID(Long user_id);
}

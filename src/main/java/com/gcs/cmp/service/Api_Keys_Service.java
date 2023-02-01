package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import com.gcs.cmp.entity.Api_Keys;

public interface Api_Keys_Service {

	public Api_Keys addApi_Keys(Api_Keys api_Keys) ;
	public List<Api_Keys> getApi_KeysList(Long account_id);
	public Api_Keys updateApi_Keys(Api_Keys api_Keys);
	public Optional<Api_Keys> getOneApiKey(Long account_id);
	public Optional<Api_Keys> findApi_KeysById(Long id) ;
	public String deleteApi_Keys(Long id) ;
}

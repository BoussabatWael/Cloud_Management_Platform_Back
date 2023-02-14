package com.gcs.cmp.providers.OVH;

import java.sql.SQLException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gcs.cmp.Exception.ResponseHandler;
import com.gcs.cmp.interceptors.BasicAuthInterceptor;
import com.gcs.cmp.providers.Provider;

@Service
public class OVH_ServiceImpl implements OVH_Service{

	@Override
	public ResponseEntity<Object> getDomainsList() throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/domain";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					return result;
				}catch(Exception e) {
					return null;
				}
		    }else {
		    	return null;
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/domain";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					return result;
				}catch(Exception e) {
					return null;
				}
		    }else {
		    	return null;
		    }
		}else {
			return ResponseHandler.ResponseListKo("Error... Account NOT Identified!", 0, HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public ResponseEntity<Object> getDomainProperties(String service_name) throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/domain/"+service_name;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					return result;
				}catch(Exception e) {
					return null;
				}
		    }else {
		    	return null;
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/domain/"+service_name;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					return result;
				}catch(Exception e) {
					return null;
				}
		    }else {
		    	return null;
		    }
		}else {
			return ResponseHandler.ResponseListKo("Error... Account NOT Identified!", 0, HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public ResponseEntity<Object> getDomainZoneProperties(String zone_name) throws SQLException {
	
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/domain/zone/"+zone_name;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					return result;
				}catch(Exception e) {
					return null;
				}
		    }else {
		    	return null;
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/domain/zone/"+zone_name;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					return result;
				}catch(Exception e) {
					return null;
				}
		    }else {
		    	return null;
		    }
		}else {
			return ResponseHandler.ResponseListKo("Error... Account NOT Identified!", 0, HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public ResponseEntity<Object> getDomainRecords(String zone_name) throws SQLException {
	
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/domain/zone/"+zone_name+"/record";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					return result;
				}catch(Exception e) {
					return null;
				}
		    }else {
		    	return null;
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/domain/zone/"+zone_name+"/record";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					return result;
				}catch(Exception e) {
					return null;
				}
		    }else {
		    	return null;
		    }
		}else {
			return ResponseHandler.ResponseListKo("Error... Account NOT Identified!", 0, HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public ResponseEntity<Object> getDomainRecordsZoneProperties(String zone_name, Long id) throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/domain/zone/"+zone_name+"/record/"+id;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					return result;
				}catch(Exception e) {
					return null;
				}
		    }else {
		    	return null;
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/domain/zone/"+zone_name+"/record/"+id;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					return result;
				}catch(Exception e) {
					return null;
				}
		    }else {
		    	return null;
		    }
		}else {
			return ResponseHandler.ResponseListKo("Error... Account NOT Identified!", 0, HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public ResponseEntity<Object> getDomainServiceProperties(String zone_name) throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/domain/zone/"+zone_name+"/serviceInfos";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					return result;
				}catch(Exception e) {
					return null;
				}
		    }else {
		    	return null;
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/domain/zone/"+zone_name+"/serviceInfos";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					return result;
				}catch(Exception e) {
					return null;
				}
		    }else {
		    	return null;
		    }
		}else {
			return ResponseHandler.ResponseListKo("Error... Account NOT Identified!", 0, HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public ResponseEntity<Object> getHostingList() throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/hosting/web";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					return result;
				}catch(Exception e) {
					return null;
				}
		    }else {
		    	return null;
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/hosting/web";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					return result;
				}catch(Exception e) {
					return null;
				}
		    }else {
		    	return null;
		    }
		}else {
			return ResponseHandler.ResponseListKo("Error... Account NOT Identified!", 0, HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public ResponseEntity<Object> getHostingAttachedDomain(String domain) throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/hosting/web/attachedDomain";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	 
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					return result;
				}catch(Exception e) {
					return null;
				}
		    }else {
		    	return null;
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/hosting/web/attachedDomain";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					return result;
				}catch(Exception e) {
					return null;
				}
		    }else {
		    	return null;
		    }
		}else {
			return ResponseHandler.ResponseListKo("Error... Account NOT Identified!", 0, HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public ResponseEntity<Object> getDomainAttachedToHost(String service_name) throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/hosting/web/"+service_name+"/attachedDomain";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					return result;
				}catch(Exception e) {
					return null;
				}
		    }else {
		    	return null;
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/hosting/web/"+service_name+"/attachedDomain";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					return result;
				}catch(Exception e) {
					return null;
				}
		    }else {
		    	return null;
		    }
		}else {
			return ResponseHandler.ResponseListKo("Error... Account NOT Identified!", 0, HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public ResponseEntity<Object> getSSLProperties(String service_name) throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/hosting/web/"+service_name+"/ssl";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					return result;
				}catch(Exception e) {
					return null;
				}
		    }else {
		    	return null;
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/hosting/web/"+service_name+"/ssl";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					return result;
				}catch(Exception e) {
					return null;
				}
		    }else {
		    	return null;
		    }
		}else {
			return ResponseHandler.ResponseListKo("Error... Account NOT Identified!", 0, HttpStatus.FORBIDDEN, null);
		}
	}

}

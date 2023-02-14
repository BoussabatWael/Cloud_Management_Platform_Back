package com.gcs.cmp.providers.Vultr;

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
public class Vultr_ServiceImpl  implements Vultr_Service{

	@Override
	public ResponseEntity<Object> getAccountInfo() throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			
			//get provider api key
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.vultr.com/v2/account";
		    	
		    	/*
		    	String url = "https://wael.local.itwise.pro/back_app/api/cmp/core/users";
		    	String auth = "Authorization:" + key;
		        byte[] encodedAuth = Base64.encodeBase64( 
		        auth.getBytes(Charset.forName("US-ASCII")) );
		        String authHeader = "Basic " + new String( encodedAuth );
		        */
		    	
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	
		    	//header.add("Authorization", authHeader);
		    	//header.add("Token", "JlFRX9GuPYrDHgkMG7Cyy0nqlUd3wY");
		    	
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
			
			//get provider api key
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
			
		    if(!key.equals("")) {
		    	
		    	String url = "https://api.vultr.com/v2/account";
		    	/*
		    	String url = "https://wael.local.itwise.pro/back_app/api/cmp/core/users";
		    	String auth = "Authorization:" + key;
		        byte[] encodedAuth = Base64.encodeBase64( 
		        auth.getBytes(Charset.forName("US-ASCII")) );
		        String authHeader = "Basic " + new String( encodedAuth );
		        */
		    	
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	
		    	//header.add("Authorization", authHeader);
		    	//header.add("Token", "JlFRX9GuPYrDHgkMG7Cyy0nqlUd3wY");
		    	
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
	public ResponseEntity<Object> getApplicationList() throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.vultr.com/v2/applications";
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
		    	String url = "https://api.vultr.com/v2/applications";
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
	public ResponseEntity<Object> getBackupsList() throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.vultr.com/v2/backups";
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
		    	String url = "https://api.vultr.com/v2/backups";
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
	public ResponseEntity<Object> getBillingHistoryList() throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.vultr.com/v2/billing/history";
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
		    	String url = "https://api.vultr.com/v2/billing/history";
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
	public ResponseEntity<Object> getInvoicesList() throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.vultr.com/v2/billing/invoices";
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
		    	String url = "https://api.vultr.com/v2/billing/invoices";
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
	public ResponseEntity<Object> getDnsDomainsList() throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.vultr.com/v2/domains";
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
		    	String url = "https://api.vultr.com/v2/domains";
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
	public ResponseEntity<Object> getSoaInformations(String dns_domain) throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.vultr.com/v2/domains/"+dns_domain+"/soa";
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
		    	String url = "https://api.vultr.com/v2/domains/"+dns_domain+"/soa";
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
	public ResponseEntity<Object> getDnsSecInfo(String dns_domain) throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.vultr.com/v2/domains/"+dns_domain+"/dnssec";
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
		    	String url = "https://api.vultr.com/v2/domains/"+dns_domain+"/dnssec";
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
	public ResponseEntity<Object> getRecordsList(String dns_domain) throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.vultr.com/v2/domains/"+dns_domain+"/records";
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
		    	String url = "https://api.vultr.com/v2/domains/"+dns_domain+"/records";
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
	public ResponseEntity<Object> getFirewallGroupsList() throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.vultr.com/v2/firewalls";
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
		    	String url = "https://api.vultr.com/v2/firewalls";
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
	public ResponseEntity<Object> getFirewallRulesList(String firewall_group_id) throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.vultr.com/v2/firewalls/"+firewall_group_id+"/rules";
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
		    	String url = "https://api.vultr.com/v2/firewalls/"+firewall_group_id+"/rules";
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
	public ResponseEntity<Object> getInstancesList() throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.vultr.com/v2/instances";
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
		    	String url = "https://api.vultr.com/v2/instances";
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
	public ResponseEntity<Object> getInstance(String instance_id) throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.vultr.com/v2/instances/"+instance_id;
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
		    	String url = "https://api.vultr.com/v2/instances/"+instance_id;
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
	public ResponseEntity<Object> getIPv4InstanceInformationList(String instance_id) throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.vultr.com/v2/instances/"+instance_id+"/ipv4";
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
		    	String url = "https://api.vultr.com/v2/instances/"+instance_id+"/ipv4";
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
	public ResponseEntity<Object> getIPv6InstanceInformation(String instance_id) throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.vultr.com/v2/instances/"+instance_id+"/ipv6";
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
		    	String url = "https://api.vultr.com/v2/instances/"+instance_id+"/ipv6";
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
	public ResponseEntity<Object> getIPv6InstanceReverseList(String instance_id) throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.vultr.com/v2/instances/"+instance_id+"/ipv6/reverse";
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
		    	String url = "https://api.vultr.com/v2/instances/"+instance_id+"/ipv6/reverse";
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
	public ResponseEntity<Object> getOsList() throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.vultr.com/v2/os";
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
		    	String url = "https://api.vultr.com/v2/os";
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
	public ResponseEntity<Object> getPlansList() throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.vultr.com/v2/plans";
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
		    	String url = "https://api.vultr.com/v2/plans";
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
	public ResponseEntity<Object> getRegionsList() throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.vultr.com/v2/regions";
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
		    	String url = "https://api.vultr.com/v2/regions";
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
	public ResponseEntity<Object> getAvailablePlansInRegionList(String region_id) throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.vultr.com/v2/regions/"+region_id+"/availability";
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
		    	String url = "https://api.vultr.com/v2/regions/"+region_id+"/availability";
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
	public ResponseEntity<Object> getSnapshotsList() throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.vultr.com/v2/snapshots";
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
		    	String url = "https://api.vultr.com/v2/snapshots";
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

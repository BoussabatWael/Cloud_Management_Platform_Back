package com.gcs.cmp.providers.OVH;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gcs.cmp.Exception.ResponseHandler;
import com.gcs.cmp.interceptors.BasicAuthInterceptor;
import com.gcs.cmp.providers.OVHApi;
import com.gcs.cmp.providers.Provider;

@Service
public class OVH_ServiceImpl implements OVH_Service{

	RestTemplate restTemplate = new RestTemplate();

	@Override
	public Object getDomainsList() throws SQLException {
		
		try {
			return testCall();			
		} catch (Exception e1) {
			// TODO Auto-generated catch block			
			return e1.getMessage();
		}		
		
		/*
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/#/domain";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				try {
					ResponseEntity<Object> result = this.restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);	
					Object res = result.getBody();		
					if(res != null)	{
						JSONObject jsonObj = new JSONObject();								
						jsonObj.put("domains", res);							
						JSONArray domains = jsonObj.getJSONArray("domains");
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", domains.length(), HttpStatus.OK, domains.toList());
					}else{
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}		
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/#/domain";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				try {
					ResponseEntity<Object> result = this.restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					Object res = result.getBody();					
					if(res != null)	{
						JSONObject jsonObj = new JSONObject();								
						jsonObj.put("domains", res);							
						JSONArray domains = jsonObj.getJSONArray("domains");
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", domains.length(), HttpStatus.OK, domains.toList());
					}else{
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
		*/
	}

	@Override
	public Object getDomainProperties(String service_name) throws SQLException {	
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/#/domain/"+service_name;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	 
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				try {
					ResponseEntity<Object> result = this.restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);   	
					Object res = result.getBody();	
					if(res != null) {
						JSONObject jsonObj = new JSONObject();								
						jsonObj.put("domain_properties", res);							
						Map<String, Object> domainProperties = jsonObj.getJSONObject("domain_properties").toMap();
						return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, domainProperties);
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/#/domain/"+service_name;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				try {
					ResponseEntity<Object> result = this.restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					Object res = result.getBody();					
					if(res != null) {
						JSONObject jsonObj = new JSONObject();								
						jsonObj.put("domain_properties", res);							
						Map<String, Object> domainProperties = jsonObj.getJSONObject("domain_properties").toMap();
						return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, domainProperties);
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public Object getDomainZoneProperties(String zone_name) throws SQLException {
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/#/domain/zone/"+zone_name;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	  
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				try {
					ResponseEntity<Object> result = this.restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					Object res = result.getBody();	
					if(res != null) {
						JSONObject jsonObj = new JSONObject();								
						jsonObj.put("domain_zone_properties", res);							
						Map<String, Object> domainZoneProperties = jsonObj.getJSONObject("domain_zone_properties").toMap();
						return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, domainZoneProperties);
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/#/domain/zone/"+zone_name;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				try {
					ResponseEntity<Object> result = this.restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					Object res = result.getBody();					
					if(res != null) {
						JSONObject jsonObj = new JSONObject();								
						jsonObj.put("domain_zone_properties", res);							
						Map<String, Object> domainZoneProperties = jsonObj.getJSONObject("domain_zone_properties").toMap();
						return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, domainZoneProperties);
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public Object getDomainRecords(String zone_name) throws SQLException {
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/#/domain/zone/"+zone_name+"/record";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	 
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				try {
					ResponseEntity<Object> result = this.restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);			    	
					Object res = result.getBody();	
					if(res != null) {
						JSONObject jsonObj = new JSONObject();								
						jsonObj.put("domain_records", res);							
						JSONArray domain_records = jsonObj.getJSONArray("domain_records");
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", domain_records.length(), HttpStatus.OK, domain_records.toList());
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/#/domain/zone/"+zone_name+"/record";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				try {
					ResponseEntity<Object> result = this.restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					Object res = result.getBody();					
					if(res != null) {
						JSONObject jsonObj = new JSONObject();								
						jsonObj.put("domain_records", res);							
						JSONArray domain_records = jsonObj.getJSONArray("domain_records");
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", domain_records.length(), HttpStatus.OK, domain_records.toList());
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}
	
	@Override
	public Object getDomainRecordsZoneProperties(String zone_name, Long id) throws SQLException {
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/#/domain/zone/"+zone_name+"/record/"+id;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				try {
					ResponseEntity<Object> result = this.restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					Object res = result.getBody();
					if(res != null) {
						JSONObject jsonObj = new JSONObject();								
						jsonObj.put("domain_records_zone_properties", res);							
						Map<String, Object> domain_records_zone_properties = jsonObj.getJSONObject("domain_records_zone_properties").toMap();
						return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, domain_records_zone_properties);
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/#/domain/zone/"+zone_name+"/record/"+id;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);

		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				try {
					ResponseEntity<Object> result = this.restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);			    	
					Object res = result.getBody();					
					if(res != null) {
						JSONObject jsonObj = new JSONObject();								
						jsonObj.put("domain_records_zone_properties", res);							
						Map<String, Object> domain_records_zone_properties = jsonObj.getJSONObject("domain_records_zone_properties").toMap();
						return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, domain_records_zone_properties);
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public Object getDomainServiceProperties(String zone_name) throws SQLException {
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/#/domain/zone/"+zone_name+"/serviceInfos";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	  
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				try {
					ResponseEntity<Object> result = this.restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					Object res = result.getBody();
					if(res != null) {
						JSONObject jsonObj = new JSONObject();								
						jsonObj.put("domain_service_properties", res);							
						Map<String, Object> domain_service_properties = jsonObj.getJSONObject("domain_service_properties").toMap();
						return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, domain_service_properties);
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/#/domain/zone/"+zone_name+"/serviceInfos";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				try {
					ResponseEntity<Object> result = this.restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					Object res = result.getBody();					
					if(res != null) {
						JSONObject jsonObj = new JSONObject();								
						jsonObj.put("domain_service_properties", res);							
						Map<String, Object> domain_service_properties = jsonObj.getJSONObject("domain_service_properties").toMap();
						return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, domain_service_properties);
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public Object getHostingList() throws SQLException {
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/#/hosting/web";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	   
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				try {
					ResponseEntity<Object> result = this.restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					Object res = result.getBody();				
					if(res != null) {
						JSONObject jsonObj = new JSONObject();								
						jsonObj.put("hosting", res);							
						JSONArray hosting = jsonObj.getJSONArray("hosting");
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", hosting.length(), HttpStatus.OK, hosting.toList());
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/#/hosting/web";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				try {
					ResponseEntity<Object> result = this.restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);			    	
					Object res = result.getBody();					
					if(res != null) {
						JSONObject jsonObj = new JSONObject();								
						jsonObj.put("hosting", res);							
						JSONArray hosting = jsonObj.getJSONArray("hosting");
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", hosting.length(), HttpStatus.OK, hosting.toList());
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public Object getHostingAttachedDomain(String domain) throws SQLException {
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/#/hosting/web/attachedDomain";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	 
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				try {
					ResponseEntity<Object> result = this.restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);			    	
					Object res = result.getBody();
					if(res != null) {
						JSONObject jsonObj = new JSONObject();								
						jsonObj.put("hosting_attached_domain", res);							
						JSONArray hosting_attached_domain = jsonObj.getJSONArray("hosting_attached_domain");
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", hosting_attached_domain.length(), HttpStatus.OK, hosting_attached_domain.toList());
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/#/hosting/web/attachedDomain";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				try {
					ResponseEntity<Object> result = this.restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					Object res = result.getBody();					
					if(res != null) {
						JSONObject jsonObj = new JSONObject();								
						jsonObj.put("hosting_attached_domain", res);							
						JSONArray hosting_attached_domain = jsonObj.getJSONArray("hosting_attached_domain");
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", hosting_attached_domain.length(), HttpStatus.OK, hosting_attached_domain.toList());
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public Object getDomainAttachedToHost(String service_name) throws SQLException {
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/#/hosting/web/"+service_name+"/attachedDomain";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	 
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				try {
					ResponseEntity<Object> result = this.restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);		    	
					Object res = result.getBody();
					if(res != null) {
						JSONObject jsonObj = new JSONObject();								
						jsonObj.put("domains_attached_host", res);							
						JSONArray domains_attached_host = jsonObj.getJSONArray("domains_attached_host");
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", domains_attached_host.length(), HttpStatus.OK, domains_attached_host.toList());
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/#/hosting/web/"+service_name+"/attachedDomain";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				try {
					ResponseEntity<Object> result = this.restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					Object res = result.getBody();					
					if(res != null) {
						JSONObject jsonObj = new JSONObject();								
						jsonObj.put("domains_attached_host", res);							
						JSONArray domains_attached_host = jsonObj.getJSONArray("domains_attached_host");
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", domains_attached_host.length(), HttpStatus.OK, domains_attached_host.toList());
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public Object getSSLProperties(String service_name) throws SQLException {
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/#/hosting/web/"+service_name+"/ssl";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	  
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				try {
					ResponseEntity<Object> result = this.restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);			    	
					Object res = result.getBody();
					if(res != null) {
						JSONObject jsonObj = new JSONObject();								
						jsonObj.put("ssl", res);							
						Map<String, Object> ssl = jsonObj.getJSONObject("ssl").toMap();
						return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, ssl);
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.ovh.com/console/#/hosting/web/"+service_name+"/ssl";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				try {
					ResponseEntity<Object> result = this.restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					Object res = result.getBody();					
					if(res != null) {
						JSONObject jsonObj = new JSONObject();								
						jsonObj.put("ssl", res);							
						Map<String, Object> ssl = jsonObj.getJSONObject("ssl").toMap();
						return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, ssl);
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

	 private Object testCall() throws Exception {

		 String	appKey = "600da4cf6fec5b7f";
		 String	appSecret = "cd5b8d1df08b8c794dff00305c0e9fbe";
		 String	consumerKey = "4a4506f468fbe20a01264a176d9a728e";

         OVHApi api = new OVHApi(appKey, appSecret, consumerKey);
         try {
                 return api.post("https://api.ovh.com/1.0/domain", null, true);
         } catch (Exception e) {
                 System.out.println(e);
         }
         return api.post("https://api.ovh.com/1.0/domain", null, true);
 }
}

package com.gcs.cmp.providers.OVH;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.DataBinder;
import org.springframework.web.client.RestTemplate;

import com.gcs.cmp.Exception.ResponseHandler;
import com.gcs.cmp.entity.Inventory_Hosts;
import com.gcs.cmp.entity.Networks_Domain_Names;
import com.gcs.cmp.interceptors.BasicAuthInterceptor;
import com.gcs.cmp.providers.DomainProperties;
import com.gcs.cmp.providers.DomainRecordsZoneProperties;
import com.gcs.cmp.providers.DomainServiceProperties;
import com.gcs.cmp.providers.DomainZoneProperties;
import com.gcs.cmp.providers.Provider;
import com.gcs.cmp.providers.SSLProperties;

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
					
					ArrayList<Networks_Domain_Names> domains = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("domains", result.getBody());
			    	DataBinder db = new DataBinder(domains);			    	
			    	db.bind(mpv);
			    	System.out.println(db.getBindingResult());
			    	
					return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, result);
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
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
					
					ArrayList<Networks_Domain_Names> domains = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("domains", result.getBody());
			    	DataBinder db = new DataBinder(domains);
			    	db.bind(mpv);
			    	System.out.println(db.getBindingResult());
			    	
					return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, result);
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
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
					
					DomainProperties domain_properties = new DomainProperties();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("domain_properties", result.getBody());
			    	DataBinder db = new DataBinder(domain_properties);			    	
			    	db.bind(mpv);
			    	System.out.println(db.getBindingResult());
			    	
					return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, result);
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
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
					
					DomainProperties domain_properties = new DomainProperties();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("domain_properties", result.getBody());
			    	DataBinder db = new DataBinder(domain_properties);			    	
			    	db.bind(mpv);
			    	System.out.println(db.getBindingResult());
			    	
					return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, result);
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
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
					
					DomainZoneProperties domain_zone_properties = new DomainZoneProperties();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("domain_zone_properties", result.getBody());
			    	DataBinder db = new DataBinder(domain_zone_properties);			    	
			    	db.bind(mpv);
			    	System.out.println(db.getBindingResult());
			    	
					return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, result);
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
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
					
					DomainZoneProperties domain_zone_properties = new DomainZoneProperties();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("domain_zone_properties", result.getBody());
			    	DataBinder db = new DataBinder(domain_zone_properties);			    	
			    	db.bind(mpv);
			    	System.out.println(db.getBindingResult());
			    	
					return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, result);
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
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
					
					ArrayList<Object> domain_records = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("domain_records", result.getBody());
			    	DataBinder db = new DataBinder(domain_records);			    	
			    	db.bind(mpv);
			    	System.out.println(db.getBindingResult());
			    	
					return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, result);
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
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
					
					ArrayList<Object> domain_records = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("domain_records", result.getBody());
			    	DataBinder db = new DataBinder(domain_records);			    	
			    	db.bind(mpv);
			    	System.out.println(db.getBindingResult());
			    	
					return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, result);
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
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
					
					DomainRecordsZoneProperties domain_records_zone_properties = new DomainRecordsZoneProperties();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("domain_records_zone_properties", result.getBody());
			    	DataBinder db = new DataBinder(domain_records_zone_properties);			    	
			    	db.bind(mpv);
			    	System.out.println(db.getBindingResult());
			    	
					return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, result);
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
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
					
					DomainRecordsZoneProperties domain_records_zone_properties = new DomainRecordsZoneProperties();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("domain_records_zone_properties", result.getBody());
			    	DataBinder db = new DataBinder(domain_records_zone_properties);			    	
			    	db.bind(mpv);
			    	System.out.println(db.getBindingResult());
			    	
					return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, result);
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
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
					
					DomainServiceProperties domain_service_properties = new DomainServiceProperties();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("domain_service_properties", result.getBody());
			    	DataBinder db = new DataBinder(domain_service_properties);			    	
			    	db.bind(mpv);
			    	System.out.println(db.getBindingResult());
			    	
					return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, result);
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
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
					
					DomainServiceProperties domain_service_properties = new DomainServiceProperties();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("domain_service_properties", result.getBody());
			    	DataBinder db = new DataBinder(domain_service_properties);			    	
			    	db.bind(mpv);
			    	System.out.println(db.getBindingResult());
			    	
					return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, result);
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
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
					
					ArrayList<Inventory_Hosts> hosts = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("hosts", result.getBody());
			    	DataBinder db = new DataBinder(hosts);			    	
			    	db.bind(mpv);
			    	System.out.println(db.getBindingResult());
			    	
					return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, result);
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
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
					
					ArrayList<Inventory_Hosts> hosts = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("hosts", result.getBody());
			    	DataBinder db = new DataBinder(hosts);			    	
			    	db.bind(mpv);
			    	System.out.println(db.getBindingResult());
			    	
					return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, result);
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
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
					
					ArrayList<Object> hosts = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("hosts", result.getBody());
			    	DataBinder db = new DataBinder(hosts);			    	
			    	db.bind(mpv);
			    	System.out.println(db.getBindingResult());
			    	
					return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, result);
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
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
					
					ArrayList<Object> hosts = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("hosts", result.getBody());
			    	DataBinder db = new DataBinder(hosts);			    	
			    	db.bind(mpv);
			    	System.out.println(db.getBindingResult());
			    	
					return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, result);
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
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
					
					ArrayList<Object> domains = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("domains", result.getBody());
			    	DataBinder db = new DataBinder(domains);			    	
			    	db.bind(mpv);
			    	System.out.println(db.getBindingResult());
			    	
					return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, result);
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
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
					
					ArrayList<Object> domains = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("domains", result.getBody());
			    	DataBinder db = new DataBinder(domains);			    	
			    	db.bind(mpv);
			    	System.out.println(db.getBindingResult());
			    	
					return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, result);
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
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
					
					SSLProperties ssl_properties = new SSLProperties();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("ssl_properties", result.getBody());
			    	DataBinder db = new DataBinder(ssl_properties);			    	
			    	db.bind(mpv);
			    	System.out.println(db.getBindingResult());
			    	
					return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, result);
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
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
					
					SSLProperties ssl_properties = new SSLProperties();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("ssl_properties", result.getBody());
			    	DataBinder db = new DataBinder(ssl_properties);			    	
			    	db.bind(mpv);
			    	System.out.println(db.getBindingResult());
			    			
					return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, result);
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

}

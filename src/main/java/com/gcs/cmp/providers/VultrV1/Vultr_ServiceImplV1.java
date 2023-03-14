package com.gcs.cmp.providers.VultrV1;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import com.gcs.cmp.entity.Backup_Operations;
import com.gcs.cmp.entity.Core_Accounts;
import com.gcs.cmp.entity.Inventory_Applications;
import com.gcs.cmp.entity.Inventory_Instances;
import com.gcs.cmp.entity.Inventory_Servers;
import com.gcs.cmp.entity.Networks_Domain_Names;
import com.gcs.cmp.interceptors.BasicAuthInterceptor;
import com.google.gson.Gson;

@Service
public class Vultr_ServiceImplV1 implements Vultr_ServiceV1{

	RestTemplate restTemplate  = new RestTemplate();
	
	@Override
	public Object getAccountInfo() throws SQLException {
		
    	String url = "https://api.vultr.com/v2/account"; 
    	
    	if (BasicAuthInterceptor.authHeader != null && BasicAuthInterceptor.authHeader !="undefined" && BasicAuthInterceptor.authHeader.toLowerCase().startsWith("bearer")) {
	    	HttpHeaders header = new HttpHeaders();
	    	header.add("Authorization", BasicAuthInterceptor.authHeader);
	        header.setContentType(MediaType.APPLICATION_JSON);
	    	HttpEntity<Object> entity = new HttpEntity<>(header);
			try {			    
				ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);
				Object res = result.getBody();	
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("account")) {
							JSONObject jsonObj = new JSONObject(js);											
							String name = jsonObj.getJSONObject("account").getString("name");						
							Object account = account_formatter(name);									
							return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, account);
						}else {
							return ResponseHandler.ResponseOk("Empty result.", HttpStatus.OK, null);
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}	
			}catch(Exception e) {
				return ResponseHandler.ResponseOk(e.getMessage(), HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}	
	}

	@Override
	public Object getApplicationList() throws SQLException {		

    	String url = "https://api.vultr.com/v2/applications";
    	
    	if (BasicAuthInterceptor.authHeader != null && BasicAuthInterceptor.authHeader != "undefined" && BasicAuthInterceptor.authHeader.toLowerCase().startsWith("bearer")) {
	    	HttpHeaders header = new HttpHeaders();
	    	header.add("Authorization", BasicAuthInterceptor.authHeader);
	        header.setContentType(MediaType.APPLICATION_JSON);
	    	HttpEntity<Object> entity = new HttpEntity<>(header);
			try {						    
				ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);
				Object res = result.getBody();				
					if(res != null) {
						Gson gson = new Gson();	
						String js = gson.toJson(res);
						if(js.contains("applications")) {
							JSONObject jsonObj = new JSONObject(js);								
							JSONArray apps = jsonObj.getJSONArray("applications");				
							ArrayList<Object> applsList = new ArrayList<>();
							for(int i=0;i<apps.length();i++) {
								JSONObject app_provider = (JSONObject) apps.get(i);
								Object app = application_formatter(app_provider.getInt("id"), app_provider.get("name").toString(), app_provider.get("image_id").toString());
								applsList.add(app);
							}				
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", applsList.size(), HttpStatus.OK, applsList.toArray());
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
						}	
					}else {
						return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
					}		
			}catch(Exception e) {
						return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}				
	}

	@Override
	public Object getBackupsList() throws SQLException {
		
    	String url = "https://api.vultr.com/v2/backups";
    	
    	if (BasicAuthInterceptor.authHeader != null && BasicAuthInterceptor.authHeader != "undefined" && BasicAuthInterceptor.authHeader.toLowerCase().startsWith("bearer")) {
	    	HttpHeaders header = new HttpHeaders();
	    	header.add("Authorization", BasicAuthInterceptor.authHeader);
	        header.setContentType(MediaType.APPLICATION_JSON);
	    	HttpEntity<Object> entity = new HttpEntity<>(header);
			try {						    
				ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);
				Object res = result.getBody();	
				if(res != null) {
					Gson gson = new Gson();				
					String js = gson.toJson(res);
					if(js.contains("backups")) {
						JSONObject jsonObj = new JSONObject(js);								
						JSONArray backups = jsonObj.getJSONArray("backups");				
						ArrayList<Object> backupsList = new ArrayList<>();
						for(int i=0;i<backups.length();i++) {
							JSONObject backups_provider = (JSONObject) backups.get(i);
							Object backup = backup_formatter(backups_provider.get("date_created").toString(), backups_provider.get("description").toString(), backups_provider.get("status").toString());
							backupsList.add(backup);
						}				
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", backupsList.size(), HttpStatus.OK, backupsList.toArray());
					}else {
						return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
					}
				}else {
					return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
				}	
			}catch(Exception e) {
						return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
	}

	@Override
	public Object getBillingHistoryList() throws SQLException {	
		
    	String url = "https://api.vultr.com/v2/billing/history";
    	
    	if (BasicAuthInterceptor.authHeader != null && BasicAuthInterceptor.authHeader != "undefined" && BasicAuthInterceptor.authHeader.toLowerCase().startsWith("bearer")) {
	    	HttpHeaders header = new HttpHeaders();
	    	header.add("Authorization", BasicAuthInterceptor.authHeader);
	        header.setContentType(MediaType.APPLICATION_JSON);
	    	HttpEntity<Object> entity = new HttpEntity<>(header);
			try {						    
				ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);
				Object res = result.getBody();	
				if(res != null) {
					Gson gson = new Gson();				
					String js = gson.toJson(res);
					if(js.contains("billing_history")) {
						JSONObject jsonObj = new JSONObject(js);								
						JSONArray billing_history_list = jsonObj.getJSONArray("billing_history");
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", billing_history_list.length(), HttpStatus.OK, billing_history_list.toList());
					}else {
						return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
					}	
				}else {
					return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
				}		
			}catch(Exception e) {
						return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
	}

	@Override
	public Object getInvoicesList() throws SQLException {
		
    	String url = "https://api.vultr.com/v2/billing/invoices";
    	
    	if (BasicAuthInterceptor.authHeader != null && BasicAuthInterceptor.authHeader != "undefined" && BasicAuthInterceptor.authHeader.toLowerCase().startsWith("bearer")) {
	    	HttpHeaders header = new HttpHeaders();
	    	header.add("Authorization", BasicAuthInterceptor.authHeader);
	        header.setContentType(MediaType.APPLICATION_JSON);
	    	HttpEntity<Object> entity = new HttpEntity<>(header);
			try {						    
				ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);
				Object res = result.getBody();	
				if(res != null) {
					Gson gson = new Gson();				
					String js = gson.toJson(res);
					if(js.contains("billing_invoices")) {
						JSONObject jsonObj = new JSONObject(js);								
						JSONArray billing_invoices_list = jsonObj.getJSONArray("billing_invoices");
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", billing_invoices_list.length(), HttpStatus.OK, billing_invoices_list.toList());
					}else {
						return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
					}	
				}else {
					return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
				}		
			}catch(Exception e) {
						return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
	}

	@Override
	public Object getDnsDomainsList() throws SQLException {	
		
    	String url = "https://api.vultr.com/v2/domains";
    	
    	if (BasicAuthInterceptor.authHeader != null && BasicAuthInterceptor.authHeader != "undefined" && BasicAuthInterceptor.authHeader.toLowerCase().startsWith("bearer")) {
	    	HttpHeaders header = new HttpHeaders();
	    	header.add("Authorization", BasicAuthInterceptor.authHeader);
	        header.setContentType(MediaType.APPLICATION_JSON);
	    	HttpEntity<Object> entity = new HttpEntity<>(header);
			try {						    
				ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);
				Object res = result.getBody();	
				if(res != null) {
					Gson gson = new Gson();					
					String js = gson.toJson(res);
					if(js.contains("domains")) {
						JSONObject jsonObj = new JSONObject(js);				
						JSONArray domains = jsonObj.getJSONArray("domains");
						ArrayList<Object> domainsList = new ArrayList<>();
						for(int i=0;i<domains.length();i++) {
							JSONObject domain_provider = (JSONObject) domains.get(i);
							Object domain = domain_formatter(domain_provider.get("domain").toString(), domain_provider.get("date_created").toString());
							domainsList.add(domain);
						}				
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", domainsList.size(), HttpStatus.OK, domainsList.toArray());
					}else {
						return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
					}	
				}else {
					return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
				}		
			}catch(Exception e) {
						return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
	}

	@Override
	public Object getSoaInformations(String dns_domain) throws SQLException {
		
    	String url = "https://api.vultr.com/v2/domains/"+dns_domain+"/soa";
    	
    	if (BasicAuthInterceptor.authHeader != null && BasicAuthInterceptor.authHeader != "undefined" && BasicAuthInterceptor.authHeader.toLowerCase().startsWith("bearer")) {
	    	HttpHeaders header = new HttpHeaders();
	    	header.add("Authorization", BasicAuthInterceptor.authHeader);
	        header.setContentType(MediaType.APPLICATION_JSON);
	    	HttpEntity<Object> entity = new HttpEntity<>(header);
			try {						    
				ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);
				Object res = result.getBody();	
				if(res != null) {
					Gson gson = new Gson();					
					String js = gson.toJson(res);
					if(js.contains("dns_soa")) {
						JSONObject jsonObj = new JSONObject(js);
						Map<String, Object> soa = jsonObj.getJSONObject("dns_soa").toMap();									
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", soa.size(), HttpStatus.OK, soa);
					}else {
						return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, null);
					}
				}else {
					return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
				}				
			}catch(Exception e) {
						return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
	}

	@Override
	public Object getDnsSecInfo(String dns_domain) throws SQLException {
				
    	String url = "https://api.vultr.com/v2/domains/"+dns_domain+"/dnssec";
    	
    	if (BasicAuthInterceptor.authHeader != null && BasicAuthInterceptor.authHeader != "undefined" && BasicAuthInterceptor.authHeader.toLowerCase().startsWith("bearer")) {
	    	HttpHeaders header = new HttpHeaders();
	    	header.add("Authorization", BasicAuthInterceptor.authHeader);
	        header.setContentType(MediaType.APPLICATION_JSON);
	    	HttpEntity<Object> entity = new HttpEntity<>(header);
			try {						    
				ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);
				Object res = result.getBody();	
				if(res != null) {
					Gson gson = new Gson();					
					String js = gson.toJson(res);
					if(js.contains("dns_sec")) {
						JSONObject jsonObj = new JSONObject(js);
						JSONArray dns_sec = jsonObj.getJSONArray("dns_sec");					
						return ResponseHandler.ResponseListKo("Successfully retrieved data.", dns_sec.length(), HttpStatus.OK, dns_sec.toList());
					}else {
						return ResponseHandler.ResponseListKo("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
					}
				}else {
					return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
				}		
			}catch(Exception e) {
						return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
	}

	@Override
	public Object getRecordsList(String dns_domain) throws SQLException {	
		
    	String url = "https://api.vultr.com/v2/domains/"+dns_domain+"/records";
    	
    	if (BasicAuthInterceptor.authHeader != null && BasicAuthInterceptor.authHeader != "undefined" && BasicAuthInterceptor.authHeader.toLowerCase().startsWith("bearer")) {
	    	HttpHeaders header = new HttpHeaders();
	    	header.add("Authorization", BasicAuthInterceptor.authHeader);
	        header.setContentType(MediaType.APPLICATION_JSON);
	    	HttpEntity<Object> entity = new HttpEntity<>(header);
			try {						    
				ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);
				Object res = result.getBody();	
				if(res != null) {
					Gson gson = new Gson();					
					String js = gson.toJson(res);
					if(js.contains("records")) {
						JSONObject jsonObj = new JSONObject(js);				
						JSONArray records = jsonObj.getJSONArray("records");
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", records.length(), HttpStatus.OK, records.toList());
					}else {
						return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
					}
				}else {
					return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
				}		
			}catch(Exception e) {
						return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
	}

	@Override
	public Object getFirewallGroupsList() throws SQLException {	
		
    	String url = "https://api.vultr.com/v2/firewalls";
    	
    	if (BasicAuthInterceptor.authHeader != null && BasicAuthInterceptor.authHeader != "undefined" && BasicAuthInterceptor.authHeader.toLowerCase().startsWith("bearer")) {
	    	HttpHeaders header = new HttpHeaders();
	    	header.add("Authorization", BasicAuthInterceptor.authHeader);
	        header.setContentType(MediaType.APPLICATION_JSON);
	    	HttpEntity<Object> entity = new HttpEntity<>(header);
			try {						    
				ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);
				Object res = result.getBody();	
				if(res != null) {
					Gson gson = new Gson();					
					String js = gson.toJson(res);
					if(js.contains("firewall_groups")) {
						JSONObject jsonObj = new JSONObject(js);				
						JSONArray firewall_groups_list = jsonObj.getJSONArray("firewall_groups");
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", firewall_groups_list.length(), HttpStatus.OK, firewall_groups_list.toList());
					}else {
						return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
					}	
				}else {
					return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
				}			
			}catch(Exception e) {
						return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
	}

	@Override
	public Object getFirewallRulesList(String firewall_group_id) throws SQLException {	
		
    	String url = "https://api.vultr.com/v2/firewalls/"+firewall_group_id+"/rules";
    	
    	if (BasicAuthInterceptor.authHeader != null && BasicAuthInterceptor.authHeader != "undefined" && BasicAuthInterceptor.authHeader.toLowerCase().startsWith("bearer")) {
	    	HttpHeaders header = new HttpHeaders();
	    	header.add("Authorization", BasicAuthInterceptor.authHeader);
	        header.setContentType(MediaType.APPLICATION_JSON);
	    	HttpEntity<Object> entity = new HttpEntity<>(header);
			try {						    
				ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);
				Object res = result.getBody();	
				if(res != null) {
					Gson gson = new Gson();					
					String js = gson.toJson(res);
					if(js.contains("firewall_rules")) {
						JSONObject jsonObj = new JSONObject(js);				
						JSONArray firewall_rules_list = jsonObj.getJSONArray("firewall_rules");
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", firewall_rules_list.length(), HttpStatus.OK, firewall_rules_list.toList());
					}else {
						return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
					}
				}else {
					return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
				}			
			}catch(Exception e) {
						return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
	}

	@Override
	public Object getInstancesList() throws SQLException {	
		
    	String url = "https://api.vultr.com/v2/instances";
    	
    	if (BasicAuthInterceptor.authHeader != null && BasicAuthInterceptor.authHeader != "undefined" && BasicAuthInterceptor.authHeader.toLowerCase().startsWith("bearer")) {
	    	HttpHeaders header = new HttpHeaders();
	    	header.add("Authorization", BasicAuthInterceptor.authHeader);
	        header.setContentType(MediaType.APPLICATION_JSON);
	    	HttpEntity<Object> entity = new HttpEntity<>(header);
			try {						    
				ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);
				Object res = result.getBody();	
				if(res != null) {
					Gson gson = new Gson();					
					String js = gson.toJson(res);
					if(js.contains("instances")) {
						JSONObject jsonObj = new JSONObject(js);							
						JSONArray instances = jsonObj.getJSONArray("instances");
						ArrayList<Object> instancesList = new ArrayList<>();
						for(int i=0;i<instances.length();i++) {
							JSONObject instance_provider = (JSONObject) instances.get(i);
							Object instance = instance_formatter(instance_provider.get("hostname").toString(), instance_provider.getInt("ram"), instance_provider.getInt("disk"), instance_provider.getInt("vcpu_count"), instance_provider.get("date_created").toString(), instance_provider.get("status").toString(), instance_provider.get("main_ip").toString(), instance_provider.get("os").toString());
							instancesList.add(instance);
						}				
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", instancesList.size(), HttpStatus.OK, instancesList.toArray());
					}else {
						return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
					}
				}else {
					return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
				}		
			}catch(Exception e) {
						return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
	}

	@Override
	public Object getInstance(String instance_id) throws SQLException {	
				
    	String url = "https://api.vultr.com/v2/instances/"+instance_id;
    	
    	if (BasicAuthInterceptor.authHeader != null && BasicAuthInterceptor.authHeader != "undefined" && BasicAuthInterceptor.authHeader.toLowerCase().startsWith("bearer")) {
	    	HttpHeaders header = new HttpHeaders();
	    	header.add("Authorization", BasicAuthInterceptor.authHeader);
	        header.setContentType(MediaType.APPLICATION_JSON);
	    	HttpEntity<Object> entity = new HttpEntity<>(header);
			try {						    
				ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);
				Object res = result.getBody();	
				if(res != null) {
					Gson gson = new Gson();					
					String js = gson.toJson(res);
					if(js.contains("instance")) {
						JSONObject jsonObj = new JSONObject(js);							
						Map<String, Object> instance = jsonObj.getJSONObject("instance").toMap();								
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", instance.size(), HttpStatus.OK, instance);
					}else {
						return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, null);
					}
				}else {
					return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
				}
			}catch(Exception e) {
						return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
	}

	@Override
	public Object getIPv4InstanceInformationList(String instance_id) throws SQLException {
		
    	String url = "https://api.vultr.com/v2/instances/"+instance_id+"/ipv4";
    	
    	if (BasicAuthInterceptor.authHeader != null && BasicAuthInterceptor.authHeader != "undefined" && BasicAuthInterceptor.authHeader.toLowerCase().startsWith("bearer")) {
	    	HttpHeaders header = new HttpHeaders();
	    	header.add("Authorization", BasicAuthInterceptor.authHeader);
	        header.setContentType(MediaType.APPLICATION_JSON);
	    	HttpEntity<Object> entity = new HttpEntity<>(header);
			try {						    
				ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);
				Object res = result.getBody();	
				if(res != null) {
					Gson gson = new Gson();					
					String js = gson.toJson(res);
					if(js.contains("ipv4s")) {
						JSONObject jsonObj = new JSONObject(js);							
						JSONArray ipv4_instances = jsonObj.getJSONArray("ipv4s");			
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", ipv4_instances.length(), HttpStatus.OK, ipv4_instances.toList());
					}else {
						return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
					}	
				}else {
					return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
				}	
			}catch(Exception e) {
						return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
	}

	@Override
	public Object getIPv6InstanceInformation(String instance_id) throws SQLException {
			
    	String url = "https://api.vultr.com/v2/instances/"+instance_id+"/ipv6";
    	
    	if (BasicAuthInterceptor.authHeader != null && BasicAuthInterceptor.authHeader != "undefined" && BasicAuthInterceptor.authHeader.toLowerCase().startsWith("bearer")) {
	    	HttpHeaders header = new HttpHeaders();
	    	header.add("Authorization", BasicAuthInterceptor.authHeader);
	        header.setContentType(MediaType.APPLICATION_JSON);
	    	HttpEntity<Object> entity = new HttpEntity<>(header);
			try {						    
				ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);
				Object res = result.getBody();	
				if(res != null) {
					Gson gson = new Gson();					
					String js = gson.toJson(res);
					if(js.contains("ipv6s")) {
						JSONObject jsonObj = new JSONObject(js);							
						JSONArray ipv6_instances = jsonObj.getJSONArray("ipv6s");			
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", ipv6_instances.length(), HttpStatus.OK, ipv6_instances.toList());
					}else {
						return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
					}
				}else {
					return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
				}	
			}catch(Exception e) {
						return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
	}

	@Override
	public Object getIPv6InstanceReverseList(String instance_id) throws SQLException {
		
    	String url = "https://api.vultr.com/v2/instances/"+instance_id+"/ipv6/reverse";
    	
    	if (BasicAuthInterceptor.authHeader != null && BasicAuthInterceptor.authHeader != "undefined" && BasicAuthInterceptor.authHeader.toLowerCase().startsWith("bearer")) {
	    	HttpHeaders header = new HttpHeaders();
	    	header.add("Authorization", BasicAuthInterceptor.authHeader);
	        header.setContentType(MediaType.APPLICATION_JSON);
	    	HttpEntity<Object> entity = new HttpEntity<>(header);
			try {						    
				ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);
				Object res = result.getBody();	
				if(res != null) {
					Gson gson = new Gson();					
					String js = gson.toJson(res);
					if(js.contains("reverse_ipv6s")) {
						JSONObject jsonObj = new JSONObject(js);							
						JSONArray reverse_ipv6s = jsonObj.getJSONArray("reverse_ipv6s");			
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", reverse_ipv6s.length(), HttpStatus.OK, reverse_ipv6s.toList());
					}else {
						return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
					}	
				}else {
					return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
				}	
			}catch(Exception e) {
						return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
	}

	@Override
	public Object getOsList() throws SQLException {
		
    	String url = "https://api.vultr.com/v2/os";
    	
    	if (BasicAuthInterceptor.authHeader != null && BasicAuthInterceptor.authHeader != "undefined" && BasicAuthInterceptor.authHeader.toLowerCase().startsWith("bearer")) {
	    	HttpHeaders header = new HttpHeaders();
	    	header.add("Authorization", BasicAuthInterceptor.authHeader);
	        header.setContentType(MediaType.APPLICATION_JSON);
	    	HttpEntity<Object> entity = new HttpEntity<>(header);
			try {						    
				ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);
				Object res = result.getBody();	
				if(res != null) {
					Gson gson = new Gson();					
					String js = gson.toJson(res);
					if(js.contains("os")) {
						JSONObject jsonObj = new JSONObject(js);							
						JSONArray osList = jsonObj.getJSONArray("os");			
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", osList.length(), HttpStatus.OK, osList.toList());
					}else {
						return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
					}	
				}else {
					return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
				}	
			}catch(Exception e) {
						return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
	}

	@Override
	public Object getPlansList() throws SQLException {
		
    	String url = "https://api.vultr.com/v2/plans";
    	
    	if (BasicAuthInterceptor.authHeader != null && BasicAuthInterceptor.authHeader != "undefined" && BasicAuthInterceptor.authHeader.toLowerCase().startsWith("bearer")) {
	    	HttpHeaders header = new HttpHeaders();
	    	header.add("Authorization", BasicAuthInterceptor.authHeader);
	        header.setContentType(MediaType.APPLICATION_JSON);
	    	HttpEntity<Object> entity = new HttpEntity<>(header);
			try {						    
				ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);
				Object res = result.getBody();	
				if(res != null) {
					Gson gson = new Gson();					
					String js = gson.toJson(res);
					if(js.contains("plans")) {
						JSONObject jsonObj = new JSONObject(js);							
						JSONArray plansList = jsonObj.getJSONArray("plans");			
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", plansList.length(), HttpStatus.OK, plansList.toList());
					}else {
						return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
					}	
				}else {
					return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
				}	
			}catch(Exception e) {
						return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
	}

	@Override
	public Object getRegionsList() throws SQLException {
		
    	String url = "https://api.vultr.com/v2/regions";
    	
    	if (BasicAuthInterceptor.authHeader != null && BasicAuthInterceptor.authHeader != "undefined" && BasicAuthInterceptor.authHeader.toLowerCase().startsWith("bearer")) {
	    	HttpHeaders header = new HttpHeaders();
	    	header.add("Authorization", BasicAuthInterceptor.authHeader);
	        header.setContentType(MediaType.APPLICATION_JSON);
	    	HttpEntity<Object> entity = new HttpEntity<>(header);
			try {						    
				ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);
				Object res = result.getBody();	
				if(res != null) {
					Gson gson = new Gson();					
					String js = gson.toJson(res);
					if(js.contains("regions")) {
						JSONObject jsonObj = new JSONObject(js);							
						JSONArray regionsList = jsonObj.getJSONArray("regions");			
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", regionsList.length(), HttpStatus.OK, regionsList.toList());
					}else {
						return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
					}	
				}else {
					return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
				}
			}catch(Exception e) {
						return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
	}

	@Override
	public Object getAvailablePlansInRegionList(String region_id) throws SQLException {
		
    	String url = "https://api.vultr.com/v2/regions/"+region_id+"/availability";
    	
    	if (BasicAuthInterceptor.authHeader != null && BasicAuthInterceptor.authHeader != "undefined" && BasicAuthInterceptor.authHeader.toLowerCase().startsWith("bearer")) {
	    	HttpHeaders header = new HttpHeaders();
	    	header.add("Authorization", BasicAuthInterceptor.authHeader);
	        header.setContentType(MediaType.APPLICATION_JSON);
	    	HttpEntity<Object> entity = new HttpEntity<>(header);
			try {						    
				ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);
				Object res = result.getBody();	
				if(res != null) {
					Gson gson = new Gson();					
					String js = gson.toJson(res);
					if(js.contains("available_plans")) {
						JSONObject jsonObj = new JSONObject(js);							
						JSONArray available_plans = jsonObj.getJSONArray("available_plans");			
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", available_plans.length(), HttpStatus.OK, available_plans.toList());
					}else {
						return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
					}	
				}else {
					return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
				}	
			}catch(Exception e) {
						return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
	}

	@Override
	public Object getSnapshotsList() throws SQLException {	
		
    	String url = "https://api.vultr.com/v2/snapshots";
    	
    	if (BasicAuthInterceptor.authHeader != null && BasicAuthInterceptor.authHeader != "undefined" && BasicAuthInterceptor.authHeader.toLowerCase().startsWith("bearer")) {
	    	HttpHeaders header = new HttpHeaders();
	    	header.add("Authorization", BasicAuthInterceptor.authHeader);
	        header.setContentType(MediaType.APPLICATION_JSON);
	    	HttpEntity<Object> entity = new HttpEntity<>(header);
			try {						    
				ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);
				Object res = result.getBody();	
				if(res != null) {
					Gson gson = new Gson();					
					String js = gson.toJson(res);
					if(js.contains("snapshots")) {
						JSONObject jsonObj = new JSONObject(js);							
						JSONArray snapshotsList = jsonObj.getJSONArray("snapshots");			
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", snapshotsList.length(), HttpStatus.OK, snapshotsList.toList());
					}else {
						return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
					}
				}else {
					return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
				}	
			}catch(Exception e) {
						return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
	}
	
	private Core_Accounts account_formatter(String name) {
		
		Core_Accounts account = new Core_Accounts();
		account.setName(name);
		return account;
	}
	
	private Inventory_Applications application_formatter(Integer id, String name, String image_id) {
		
		Inventory_Applications application = new Inventory_Applications();
		application.setId(id);
		application.setName(name);
		application.setLogo(image_id);
		return application;
	}
	
	private Backup_Operations backup_formatter(String date_created, String description, String status) {
		
		Backup_Operations backup = new Backup_Operations();
		DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME; 
		LocalDateTime start_date = LocalDateTime.parse(date_created, formatter);
		backup.setStart_date(start_date);
		backup.setName(description);
		if(status.equals("completed")) {
			backup.setStatus(1);
		}
		else if(status.equals("pending")) {
			backup.setStatus(3);
		}else {
			backup.setStatus(2);
		}
		return backup;
	}
	
	private Networks_Domain_Names domain_formatter(String domain, String date_created) {
		
		Networks_Domain_Names dns_domain = new Networks_Domain_Names();
		DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME; 
		LocalDateTime start_date = LocalDateTime.parse(date_created, formatter);
		dns_domain.setName(domain);
		dns_domain.setStart_date(start_date);
		return dns_domain;
	}
	
	private Inventory_Servers instance_formatter(String hostname, Integer ram, Integer disk, Integer cpu, String date_created, String status, String main_ip, String os) {
		
		Inventory_Servers server = new Inventory_Servers();
		Inventory_Instances instance = new Inventory_Instances();
		instance.setName(hostname);
		DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME; 
		LocalDateTime start_date = LocalDateTime.parse(date_created, formatter);
		instance.setStart_date(start_date);
		if(status.equals("active")) {
			instance.setStatus(1);
		}else if(status.equals("pending")) {
			instance.setStatus(3);
		}else {
			instance.setStatus(2);
		}
		server.setDisk_space(disk);
		server.setMemory(ram);
		server.setIp_address(main_ip);
		server.setCpu(cpu);
		if(os.contains("Windows")) {
			server.setOperating_system(1);
		}else if(os.contains("CentOS")) {
			server.setOperating_system(2);
		}else {
			server.setOperating_system(3);
		}
		server.setOs_version(os);
		server.setInstance(instance);
		return server;
	}
}

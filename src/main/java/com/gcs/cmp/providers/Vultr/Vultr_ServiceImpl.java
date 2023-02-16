package com.gcs.cmp.providers.Vultr;

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
import com.gcs.cmp.entity.Backup_Operations;
import com.gcs.cmp.entity.Core_Accounts;
import com.gcs.cmp.entity.Inventory_Applications;
import com.gcs.cmp.entity.Inventory_Instances;
import com.gcs.cmp.entity.Networks_Domain_Names;
import com.gcs.cmp.interceptors.BasicAuthInterceptor;
import com.gcs.cmp.providers.BillingHistory;
import com.gcs.cmp.providers.DomainRecords;
import com.gcs.cmp.providers.FirewallGroup;
import com.gcs.cmp.providers.FirewallRule;
import com.gcs.cmp.providers.IPv4Instance;
import com.gcs.cmp.providers.IPv6Instance;
import com.gcs.cmp.providers.IPv6InstanceReverse;
import com.gcs.cmp.providers.Invoices;
import com.gcs.cmp.providers.OS;
import com.gcs.cmp.providers.Plans;
import com.gcs.cmp.providers.Provider;
import com.gcs.cmp.providers.Region;
import com.gcs.cmp.providers.SOA;
import com.gcs.cmp.providers.Snapshot;

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

					Core_Accounts account = new Core_Accounts();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("account", result.getBody());
			    	DataBinder db = new DataBinder(account);
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
		    	String url = "https://api.vultr.com/v2/account";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					Core_Accounts account = new Core_Accounts();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("account", result.getBody());
			    	DataBinder db = new DataBinder(account);
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
					
					ArrayList<Inventory_Applications> applications = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("applications", result.getBody());
			    	DataBinder db = new DataBinder(applications);
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
		    	String url = "https://api.vultr.com/v2/applications";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					ArrayList<Inventory_Applications> applications = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("applications", result.getBody());
			    	DataBinder db = new DataBinder(applications);
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
					
					ArrayList<Backup_Operations> backups = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("backups", result.getBody());
			    	DataBinder db = new DataBinder(backups);
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
		    	String url = "https://api.vultr.com/v2/backups";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					ArrayList<Backup_Operations> backups = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("backups", result.getBody());
			    	DataBinder db = new DataBinder(backups);
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
					
					ArrayList<BillingHistory> billing_history = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("billing_history", result.getBody());
			    	DataBinder db = new DataBinder(billing_history);			    	
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
		    	String url = "https://api.vultr.com/v2/billing/history";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					ArrayList<BillingHistory> billing_history = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("billing_history", result.getBody());
			    	DataBinder db = new DataBinder(billing_history);			    	
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
					
					ArrayList<Invoices> invoices = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("invoices", result.getBody());
			    	DataBinder db = new DataBinder(invoices);			    	
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
		    	String url = "https://api.vultr.com/v2/billing/invoices";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					ArrayList<Invoices> invoices = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("invoices", result.getBody());
			    	DataBinder db = new DataBinder(invoices);			    	
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
		    	String url = "https://api.vultr.com/v2/domains";
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
					
					SOA soa = new SOA();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("soa", result.getBody());
			    	DataBinder db = new DataBinder(soa);			    	
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
		    	String url = "https://api.vultr.com/v2/domains/"+dns_domain+"/soa";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					SOA soa = new SOA();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("soa", result.getBody());
			    	DataBinder db = new DataBinder(soa);			    	
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
					
					ArrayList<String> dnssec = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("dnssec", result.getBody());
			    	DataBinder db = new DataBinder(dnssec);			    	
			    	db.bind(mpv);
			    	System.out.println(db.getBindingResult());
			    	
					return ResponseHandler.ResponseOk("Successfully retrieved data.!", HttpStatus.OK, result);
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
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
					
					ArrayList<String> dnssec = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("dnssec", result.getBody());
			    	DataBinder db = new DataBinder(dnssec);			    	
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
					
					ArrayList<DomainRecords> records = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("records", result.getBody());
			    	DataBinder db = new DataBinder(records);			    	
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
		    	String url = "https://api.vultr.com/v2/domains/"+dns_domain+"/records";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					ArrayList<DomainRecords> records = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("records", result.getBody());
			    	DataBinder db = new DataBinder(records);			    	
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
					
					ArrayList<FirewallGroup> firewall_groups = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("firewall_groups", result.getBody());
			    	DataBinder db = new DataBinder(firewall_groups);			    	
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
		    	String url = "https://api.vultr.com/v2/firewalls";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					ArrayList<FirewallGroup> firewall_groups = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("firewall_groups", result.getBody());
			    	DataBinder db = new DataBinder(firewall_groups);			    	
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
					
					ArrayList<FirewallRule> firewall_rules = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("firewall_rules", result.getBody());
			    	DataBinder db = new DataBinder(firewall_rules);			    	
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
		    	String url = "https://api.vultr.com/v2/firewalls/"+firewall_group_id+"/rules";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					ArrayList<FirewallRule> firewall_rules = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("firewall_rules", result.getBody());
			    	DataBinder db = new DataBinder(firewall_rules);			    	
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
					
					ArrayList<Inventory_Instances> instances = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("instances", result.getBody());
			    	DataBinder db = new DataBinder(instances);
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
		    	String url = "https://api.vultr.com/v2/instances";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					ArrayList<Inventory_Instances> instances = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("instances", result.getBody());
			    	DataBinder db = new DataBinder(instances);
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
					
					Inventory_Instances instance = new Inventory_Instances();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("instance", result.getBody());
			    	DataBinder db = new DataBinder(instance);
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
		    	String url = "https://api.vultr.com/v2/instances/"+instance_id;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					Inventory_Instances instance = new Inventory_Instances();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("instance", result.getBody());
			    	DataBinder db = new DataBinder(instance);
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
					
					ArrayList<IPv4Instance> ipv4_instances = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("ipv4_instances", result.getBody());
			    	DataBinder db = new DataBinder(ipv4_instances);			    	
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
		    	String url = "https://api.vultr.com/v2/instances/"+instance_id+"/ipv4";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					ArrayList<IPv4Instance> ipv4_instances = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("ipv4_instances", result.getBody());
			    	DataBinder db = new DataBinder(ipv4_instances);			    	
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
					
					ArrayList<IPv6Instance> ipv6_instances = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("ipv6_instances", result.getBody());
			    	DataBinder db = new DataBinder(ipv6_instances);			    	
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
		    	String url = "https://api.vultr.com/v2/instances/"+instance_id+"/ipv6";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					ArrayList<IPv6Instance> ipv6_instances = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("ipv6_instances", result.getBody());
			    	DataBinder db = new DataBinder(ipv6_instances);			    	
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
					
					ArrayList<IPv6InstanceReverse> ipv6_instances_reverse = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("ipv6_instances_reverse", result.getBody());
			    	DataBinder db = new DataBinder(ipv6_instances_reverse);			    	
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
		    	String url = "https://api.vultr.com/v2/instances/"+instance_id+"/ipv6/reverse";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					ArrayList<IPv6InstanceReverse> ipv6_instances_reverse = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("ipv6_instances_reverse", result.getBody());
			    	DataBinder db = new DataBinder(ipv6_instances_reverse);			    	
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
					
					ArrayList<OS> os = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("os", result.getBody());
			    	DataBinder db = new DataBinder(os);			    	
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
		    	String url = "https://api.vultr.com/v2/os";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					ArrayList<OS> os = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("os", result.getBody());
			    	DataBinder db = new DataBinder(os);			    	
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
					
					ArrayList<Plans> plans = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("plans", result.getBody());
			    	DataBinder db = new DataBinder(plans);			    	
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
		    	String url = "https://api.vultr.com/v2/plans";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					ArrayList<Plans> plans = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("plans", result.getBody());
			    	DataBinder db = new DataBinder(plans);			    	
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
					
					ArrayList<Region> regions = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("regions", result.getBody());
			    	DataBinder db = new DataBinder(regions);			    	
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
		    	String url = "https://api.vultr.com/v2/regions";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					ArrayList<Region> regions = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("regions", result.getBody());
			    	DataBinder db = new DataBinder(regions);			    	
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
					
					ArrayList<Plans> plans = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("plans", result.getBody());
			    	DataBinder db = new DataBinder(plans);			    	
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
		    	String url = "https://api.vultr.com/v2/regions/"+region_id+"/availability";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					ArrayList<Plans> plans = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("plans", result.getBody());
			    	DataBinder db = new DataBinder(plans);			    	
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
					
					ArrayList<Snapshot> snapshots = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("snapshots", result.getBody());
			    	DataBinder db = new DataBinder(snapshots);			    	
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
		    	String url = "https://api.vultr.com/v2/snapshots";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					ArrayList<Snapshot> snapshots = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("snapshots", result.getBody());
			    	DataBinder db = new DataBinder(snapshots);			    	
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

package com.gcs.cmp.providers.DigitalOcean;

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
import com.gcs.cmp.entity.Inventory_Applications;
import com.gcs.cmp.entity.Networks_Domain_Names;
import com.gcs.cmp.interceptors.BasicAuthInterceptor;
import com.gcs.cmp.providers.Actions;
import com.gcs.cmp.providers.BillingHistory;
import com.gcs.cmp.providers.CustomerBalance;
import com.gcs.cmp.providers.DomainRecords;
import com.gcs.cmp.providers.Droplet;
import com.gcs.cmp.providers.DropletSize;
import com.gcs.cmp.providers.FirewallGroup;
import com.gcs.cmp.providers.Invoices;
import com.gcs.cmp.providers.Provider;
import com.gcs.cmp.providers.Region;
import com.gcs.cmp.providers.Snapshot;

@Service
public class DigitalOcean_ServiceImpl implements DigitalOcean_Service{

	@Override
	public ResponseEntity<Object> getActionsList() throws SQLException {
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/actions";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					ArrayList<Actions> actions = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("actions", result.getBody());
			    	DataBinder db = new DataBinder(actions);			    	
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
		    	String url = "https://api.digitalocean.com/v2/actions";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					ArrayList<Actions> actions = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("actions", result.getBody());
			    	DataBinder db = new DataBinder(actions);			    	
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
	public ResponseEntity<Object> getAppsList() throws SQLException {	
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/apps";
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
		    	String url = "https://api.digitalocean.com/v2/apps";
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
	public ResponseEntity<Object> getExistingApp(String id) throws SQLException {
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/apps/"+id;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					Inventory_Applications application = new Inventory_Applications();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("application", result.getBody());
			    	DataBinder db = new DataBinder(application);
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
		    	String url = "https://api.digitalocean.com/v2/apps/"+id;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					Inventory_Applications application = new Inventory_Applications();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("application", result.getBody());
			    	DataBinder db = new DataBinder(application);
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
	public ResponseEntity<Object> getCustomerBalance() throws SQLException {
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/customers/my/balance";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					CustomerBalance customer_balance = new CustomerBalance();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("customer_balance", result.getBody());
			    	DataBinder db = new DataBinder(customer_balance);			    	
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
		    	String url = "https://api.digitalocean.com/v2/customers/my/balance";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					CustomerBalance customer_balance = new CustomerBalance();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("customer_balance", result.getBody());
			    	DataBinder db = new DataBinder(customer_balance);			    	
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
		    	String url = "https://api.digitalocean.com/v2/customers/my/billing_history";
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
		    	String url = "https://api.digitalocean.com/v2/customers/my/billing_history";
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
		    	String url = "https://api.digitalocean.com/v2/customers/my/invoices";
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
		    	String url = "https://api.digitalocean.com/v2/customers/my/invoices";
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
	public ResponseEntity<Object> getDomainRecordsList(String domain_name) throws SQLException {
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/domains/"+domain_name+"/records";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					ArrayList<DomainRecords> domain_records = new ArrayList<>();
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
		    	String url = "https://api.digitalocean.com/v2/domains/"+domain_name+"/records";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					ArrayList<DomainRecords> domain_records = new ArrayList<>();
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
	public ResponseEntity<Object> getExistingDomainRecord(String domain_name, Long domain_record_id) throws SQLException {
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/domains/"+domain_name+"/records/"+domain_record_id;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					DomainRecords domain_records = new DomainRecords();
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
		    	String url = "https://api.digitalocean.com/v2/domains/"+domain_name+"/records/"+domain_record_id;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					DomainRecords domain_records = new DomainRecords();
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
	public ResponseEntity<Object> getDomainsList() throws SQLException {
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/domains";
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
		    	String url = "https://api.digitalocean.com/v2/domains";
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
	public ResponseEntity<Object> getExistingDomain(String domain_name) throws SQLException {
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/domains/"+domain_name;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					Networks_Domain_Names domain = new Networks_Domain_Names();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("domain", result.getBody());
			    	DataBinder db = new DataBinder(domain);
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
		    	String url = "https://api.digitalocean.com/v2/domains/"+domain_name;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					Networks_Domain_Names domain = new Networks_Domain_Names();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("domain", result.getBody());
			    	DataBinder db = new DataBinder(domain);
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
	public ResponseEntity<Object> getActionsDropletList(Long droplet_id) throws SQLException {
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/droplets/"+droplet_id+"/actions";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					ArrayList<Actions> actions = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("actions", result.getBody());
			    	DataBinder db = new DataBinder(actions);			    	
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
		    	String url = "https://api.digitalocean.com/v2/droplets/"+droplet_id+"/actions";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					ArrayList<Actions> actions = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("actions", result.getBody());
			    	DataBinder db = new DataBinder(actions);			    	
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
	public ResponseEntity<Object> getDropletsList() throws SQLException {
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/droplets";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					ArrayList<Droplet> droplets = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("droplets", result.getBody());
			    	DataBinder db = new DataBinder(droplets);			    	
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
		    	String url = "https://api.digitalocean.com/v2/droplets";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					ArrayList<Droplet> droplets = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("droplets", result.getBody());
			    	DataBinder db = new DataBinder(droplets);			    	
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
	public ResponseEntity<Object> getExistingDroplet(Long droplet_id) throws SQLException {
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/droplets/"+droplet_id;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					Droplet droplet = new Droplet();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("droplet", result.getBody());
			    	DataBinder db = new DataBinder(droplet);			    	
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
		    	String url = "https://api.digitalocean.com/v2/droplets/"+droplet_id;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					Droplet droplet = new Droplet();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("droplet", result.getBody());
			    	DataBinder db = new DataBinder(droplet);			    	
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
	public ResponseEntity<Object> getBackupsDropletList(Long droplet_id) throws SQLException {
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/droplets/"+droplet_id+"/backups";
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
		    	String url = "https://api.digitalocean.com/v2/droplets/"+droplet_id+"/backups";
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
	public ResponseEntity<Object> getSnapshotsDropletList(Long droplet_id) throws SQLException {
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/droplets/"+droplet_id+"/snapshots";
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
		    	String url = "https://api.digitalocean.com/v2/droplets/"+droplet_id+"/snapshots";
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

	@Override
	public ResponseEntity<Object> getFirewallDropletList(Long droplet_id) throws SQLException {
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/droplets/"+droplet_id+"/firewalls";
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
		    	String url = "https://api.digitalocean.com/v2/droplets/"+droplet_id+"/firewalls";
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
	public ResponseEntity<Object> getFirewallsList() throws SQLException {
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/firewalls";
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
		    	String url = "https://api.digitalocean.com/v2/firewalls";
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
	public ResponseEntity<Object> getExistingFirewall(String firewall_id) throws SQLException {
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/firewalls/"+firewall_id;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					FirewallGroup firewall_group = new FirewallGroup();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("firewall_group", result.getBody());
			    	DataBinder db = new DataBinder(firewall_group);			    	
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
		    	String url = "https://api.digitalocean.com/v2/firewalls/"+firewall_id;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					FirewallGroup firewall_group = new FirewallGroup();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("firewall_group", result.getBody());
			    	DataBinder db = new DataBinder(firewall_group);			    	
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
		    	String url = "https://api.digitalocean.com/v2/regions";
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
		    	String url = "https://api.digitalocean.com/v2/regions";
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
	public ResponseEntity<Object> getDropletSizesList() throws SQLException {
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/sizes";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					ArrayList<DropletSize> droplet_sizes = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("droplet_sizes", result.getBody());
			    	DataBinder db = new DataBinder(droplet_sizes);			    	
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
		    	String url = "https://api.digitalocean.com/v2/sizes";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		    	HttpEntity<Object> entity = new HttpEntity<Object>(header);
				RestTemplate restTemplate = new RestTemplate();
				try {
					ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
					
					ArrayList<DropletSize> droplet_sizes = new ArrayList<>();
			    	MutablePropertyValues mpv = new MutablePropertyValues();
			    	mpv.add("droplet_sizes", result.getBody());
			    	DataBinder db = new DataBinder(droplet_sizes);			    	
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

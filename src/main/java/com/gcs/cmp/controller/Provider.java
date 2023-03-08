package com.gcs.cmp.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.gcs.cmp.Exception.ResponseHandler;
import com.gcs.cmp.entity.Api_Keys;
import com.gcs.cmp.entity.Core_Api_Logs;
import com.gcs.cmp.entity.Providers;
import com.gcs.cmp.interceptors.BasicAuthInterceptor;
import com.gcs.cmp.providers.DigitalOcean.DigitalOcean_Service;
import com.gcs.cmp.providers.OVH.OVH_Service;
import com.gcs.cmp.providers.Vultr.Vultr_Service;
import com.gcs.cmp.repository.Api_Keys_Repo;
import com.gcs.cmp.repository.Core_Api_Logs_Repo;

import com.gcs.cmp.service.Providers_Service;
import com.gcs.cmp.validators.Inputs_Validations;
import com.google.gson.Gson;

import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Providers", description = "Manage providers")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/providers")
public class Provider {

	@Autowired
	private Providers_Service cloud_Providers_Service;
	
	@Autowired
	private Vultr_Service vultr_Service;
	
	@Autowired
	private DigitalOcean_Service digitalOcean_Service;
	
	@Autowired
	private OVH_Service ovh_Service;
	
	@Autowired
	private Core_Api_Logs_Repo core_Api_Logs_Repo;
	
	@Autowired
	private Api_Keys_Repo api_Keys_Repo;
	
	private HttpMessageNotReadableException ex;
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Object> handleException(HttpMessageNotReadableException exception) {
		String msg = null;
	    Throwable cause = exception.getCause();

	    if (cause instanceof JsonParseException) {
	    	JsonParseException jpe = (JsonParseException) cause;
	        msg = jpe.getOriginalMessage();
	    }
	    else if (cause instanceof MismatchedInputException) {
	    	MismatchedInputException mie = (MismatchedInputException) cause;
	        if (mie.getPath() != null && mie.getPath().size() > 0) {
	        	msg = "Invalid request field : " + mie.getPath().get(0).getFieldName();
	        }
	        else {
	        	msg = "Invalid request message";
	        }
	    }
	    else if (cause instanceof JsonMappingException) {
	    	JsonMappingException jme = (JsonMappingException) cause;
	        msg = jme.getOriginalMessage();
	        if (jme.getPath() != null && jme.getPath().size() > 0) {
	        	msg = "Invalid request field : " + jme.getPath().get(0).getFieldName() +
	                      ": " + msg;
	        }
	    }
        return ResponseHandler.ResponseBadRequest(msg, HttpStatus.BAD_REQUEST, null);
	}
    
	@GetMapping("")
	public ResponseEntity<Object> getAllCloud_Providers(){		
		try {
			List<Providers> result =  cloud_Providers_Service.getProvidersList();	
			return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/ovh/domains")
	public Object domains(){		
		try {
			return ovh_Service.getDomainsList();
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/digitalocean/actions")
	public Object actionsList(){		
		try {
			return digitalOcean_Service.getActionsList();
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/digitalocean/applications")
	public Object appsList(){		
		try {
			return digitalOcean_Service.getAppsList();
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/digitalocean/application")
	public Object getApp(@RequestParam(name="id") String id){		
		try {
			return digitalOcean_Service.getExistingApp(id);
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/digitalocean/customerbalance")
	public Object customerBalance(){		
		try {
			return digitalOcean_Service.getCustomerBalance();
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/digitalocean/billinghistory")
	public Object billingHistory(){		
		try {
			return digitalOcean_Service.getBillingHistoryList();
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/digitalocean/invoices")
	public Object invoicesList(){		
		try {
			return digitalOcean_Service.getInvoicesList();
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/digitalocean/domain/records")
	public Object domainrecordslist(@RequestParam(name="domain_name") String domain_name){		
		try {
			return digitalOcean_Service.getDomainRecordsList(domain_name);
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/digitalocean/domain/record")
	public Object domainrecord(@RequestParam(name="domain_name") String domain_name, @RequestParam(name="domain_record_id") Long domain_record_id){		
		try {
			return digitalOcean_Service.getExistingDomainRecord(domain_name, domain_record_id);
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/digitalocean/domains")
	public Object domainslist(){		
		try {
			return digitalOcean_Service.getDomainsList();
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/digitalocean/domain")
	public Object getDomain(@RequestParam(name="domain_name") String domain_name){		
		try {
			return digitalOcean_Service.getExistingDomain(domain_name);
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/digitalocean/droplet/actions")
	public Object dropletActions(@RequestParam(name="droplet_id") Long droplet_id){		
		try {
			return digitalOcean_Service.getActionsDropletList(droplet_id);
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/digitalocean/droplets")
	public Object dropletsList(){		
		try {
			return digitalOcean_Service.getDropletsList();
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/digitalocean/droplet")
	public Object droplet(@RequestParam(name="droplet_id") Long droplet_id){		
		try {
			return digitalOcean_Service.getExistingDroplet(droplet_id);
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/digitalocean/droplet/backups")
	public Object dropletBackups(@RequestParam(name="droplet_id") Long droplet_id){		
		try {
			return digitalOcean_Service.getBackupsDropletList(droplet_id);
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/digitalocean/droplet/snapshots")
	public Object dropletSnapshots(@RequestParam(name="droplet_id") Long droplet_id){		
		try {
			return digitalOcean_Service.getSnapshotsDropletList(droplet_id);
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/digitalocean/droplet/firewalls")
	public Object dropletFirewalls(@RequestParam(name="droplet_id") Long droplet_id){		
		try {
			return digitalOcean_Service.getFirewallDropletList(droplet_id);
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/digitalocean/firewalls")
	public Object firewalls(){		
		try {
			return digitalOcean_Service.getFirewallsList();
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/digitalocean/firewall")
	public Object firewall(@RequestParam(name="firewall_id") String firewall_id){		
		try {
			return digitalOcean_Service.getExistingFirewall(firewall_id);
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/digitalocean/regions")
	public Object regions(){		
		try {
			return digitalOcean_Service.getRegionsList();
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/digitalocean/sizes")
	public Object sizes(){		
		try {
			return digitalOcean_Service.getDropletSizesList();
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	/*
	@GetMapping("/vultr/account")
	public Object accountInfo(){		
		try {
			return vultr_Service.getAccountInfo();							
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/vultr/applications")
	public Object applicationsList(){		
		try {
			return vultr_Service.getApplicationList();							
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/vultr/backups")
	public Object backupsList(){		
		try {
			return vultr_Service.getBackupsList();	
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/vultr/billinghistory")
	public Object billingHistoryList(){		
		try {
			return vultr_Service.getBillingHistoryList();
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/vultr/invoices")
	public Object invoicesList(){		
		try {
			return vultr_Service.getInvoicesList();				
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/vultr/domains")
	public Object domainsList(){		
		try {
			return vultr_Service.getDnsDomainsList();
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/vultr/soa")
	public Object soa(@RequestParam(name="dns_domain") String dns_domain){		
		try {
			return vultr_Service.getSoaInformations(dns_domain);							
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/vultr/dnssec")
	public Object dnssec(@RequestParam(name="dns_domain") String dns_domain){		
		try {
			return vultr_Service.getDnsSecInfo(dns_domain);							
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/vultr/records")
	public Object recordsList(@RequestParam(name="dns_domain") String dns_domain){		
		try {
			return vultr_Service.getRecordsList(dns_domain);							
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/vultr/firewallgroups")
	public Object firewallGroupsList(){		
		try {
			return vultr_Service.getFirewallGroupsList();							
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/vultr/firewallrules")
	public Object firewallRulesList(@RequestParam(name="firewall_group_id") String firewall_group_id){		
		try {
			return vultr_Service.getFirewallRulesList(firewall_group_id);							
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/vultr/instances")
	public Object instancesList(){		
		try {
			return vultr_Service.getInstancesList();							
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/vultr/instance")
	public Object getInstance(@RequestParam(name="instance_id") String instance_id){		
		try {
			return vultr_Service.getInstance(instance_id);							
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/vultr/ipv4instance")
	public Object ipv4InstanceList(@RequestParam(name="instance_id") String instance_id){		
		try {
			return vultr_Service.getIPv4InstanceInformationList(instance_id);							
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/vultr/os")
	public Object osList(){		
		try {
			return vultr_Service.getOsList();							
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/vultr/plans")
	public Object plansList(){		
		try {
			return vultr_Service.getPlansList();							
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/vultr/regions")
	public Object regionsList(){		
		try {
			return vultr_Service.getRegionsList();							
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/vultr/plansinregion")
	public Object availablePlansInRegion(@RequestParam(name="region_id") String region_id){		
		try {
			return vultr_Service.getAvailablePlansInRegionList(region_id);							
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@GetMapping("/vultr/snapshots")
	public Object snapshotsList(){		
		try {
			return vultr_Service.getSnapshotsList();							
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
 	*/	
	
	@PostMapping("/create")
	public ResponseEntity<Object> addCloud_Providers(@Validated @RequestBody Providers cloud_Providers,Errors errors){	
		if(ex != null) {
			handleException(ex);
		}
		if(errors.hasErrors()) {
            return ResponseHandler.ResponseBadRequest(errors.getFieldError().getField()+" "+errors.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST, null);
		}else {
			try {
				cloud_Providers.setStart_date(LocalDateTime.now());
				Providers result = cloud_Providers_Service.addCloud_Providers(cloud_Providers);
				if(result != null) {
					/*api logs 
					 * backup_executions:1,backup_instances:2,backup_operations:3,backup_settings:4,cloud_providers_account:5,core_access_credentials:6,core_accounts_modules:7,core_accounts:8,core_categories_elements:9,core_categories:10,
					 * core_logs:11,core_notes:12,core_notifications:13,core_urls:14,core_users_instances:15,core_users_metrics:16,core_users_modules:17,core_users_permissions:18,core_users_security:19,core_users_tokens:20,core_users:21,
					 * Inventory_applications_dependencies:22,Inventory_applications_instances:23,Inventory_applications_sources:24,Inventory_applications_versions:25,Inventory_applications:26,Inventory_hosts:27,Inventory_instances:28,
					 * Inventory_servers_versions:29,Inventory_servers:30,Inventory_templates_apps:31,Inventory_templates:32,metrics:33,modules:34,monitoring_automations:35,monitoring_commands_executions:36,monitoring_commands:37,monitoring_metrics:38,
					 * monitoring_policies_alerts:39,monitoring_policies_servers:40,monitoring_policies:41,monitoring_settings:42,networks_domain_names:43,networks_hosts:44,networks_ssl_certificates:45,providers:46,app_api_key:47,api_key:48,
					 * api_keys_permissions:49, core_logs:50
					*/
					Core_Api_Logs api_logs = new Core_Api_Logs();
					
					Api_Keys api_key = api_Keys_Repo.findById(Long.parseLong(BasicAuthInterceptor.GLOBAL_APIKEY)).get();
					
					api_logs.setAction("create");
					api_logs.setApikey(api_key);
					api_logs.setElement(46);
					api_logs.setElement_id((int)result.getId());
					api_logs.setLog_date(LocalDateTime.now());
					core_Api_Logs_Repo.save(api_logs);
					
					return ResponseHandler.ResponseOk("Successfully added data!", HttpStatus.OK, result);
				}else {
		            return ResponseHandler.ResponseBadRequest("Your request was malformed!", HttpStatus.BAD_REQUEST, null);
				}

			}catch(Exception e) {
	            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }
		}
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Object> getCloud_ProvidersById(@PathVariable(name="id") String id){
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
				Optional<Providers> result = cloud_Providers_Service.findCloud_ProvidersById(Long.parseLong(id));
				if(result.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else {
					if(result.get().getStatus() == 4 || result.get().getEnd_date() != null) {
			        	return ResponseHandler.ResponseForbidden("Resources has been deleted!", HttpStatus.FORBIDDEN, null);
					}else {
						return ResponseHandler.ResponseOk("Successfully retrieved data!", HttpStatus.OK, result);
					}
				}
			}catch(Exception e) {
	            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }
		}		
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updateCloud_Providers(@RequestBody Providers cloud_Providers,@PathVariable(name="id") String id, Errors errors){	
		if(ex != null) {
			handleException(ex);
		}
		if(errors.hasErrors()) {
            return ResponseHandler.ResponseBadRequest(errors.getFieldError().getField()+" "+errors.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST, null);
		}
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}
		else {
			try {
				Optional<Providers> provider = cloud_Providers_Service.findCloud_ProvidersById(Long.parseLong(id));
				if(provider.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else {
					if(provider.get().getStatus() == 4 || provider.get().getEnd_date() != null) {
			        	return ResponseHandler.ResponseForbidden("Resources has been deleted! We were unable to perform the request", HttpStatus.FORBIDDEN, null);
					}else {
						
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"); 

						cloud_Providers.setStart_date(provider.get().getStart_date());
						
						if(cloud_Providers.getEnd_date() == null) {
							cloud_Providers.setEnd_date(provider.get().getEnd_date());
						}
						if(cloud_Providers.getStatus() == 0) {
							cloud_Providers.setStatus(provider.get().getStatus());
						}
						if(cloud_Providers.getActivity() == 0) {
							cloud_Providers.setActivity(provider.get().getActivity());
						}
						if(cloud_Providers.getApi() == 0) {
							cloud_Providers.setApi(provider.get().getApi());
						}
						if(cloud_Providers.getDescription() == null) {
							cloud_Providers.setDescription(provider.get().getDescription());
						}
						if(cloud_Providers.getLogo() == null) {
							cloud_Providers.setLogo(provider.get().getLogo());
						}
						if(cloud_Providers.getName() == null) {
							cloud_Providers.setName(provider.get().getName());
						}
						if(cloud_Providers.getWebsite() == null) {
							cloud_Providers.setWebsite(provider.get().getWebsite());
						}
						if(provider.get().getLogo() == null) {
							if(provider.get().getStart_date().toString().equals(cloud_Providers.getStart_date().format(formatter)) && cloud_Providers.getEnd_date() == null &&
									provider.get().getStatus() == cloud_Providers.getStatus() && provider.get().getActivity() == cloud_Providers.getActivity() && 
									provider.get().getApi() == cloud_Providers.getApi() && provider.get().getDescription().equals(cloud_Providers.getDescription()) && 
									provider.get().getLogo() == cloud_Providers.getLogo() && provider.get().getName().equals(cloud_Providers.getName()) && provider.get().getWebsite().equals(cloud_Providers.getWebsite())) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						}else {
							if(provider.get().getStart_date().toString().equals(cloud_Providers.getStart_date().format(formatter)) && cloud_Providers.getEnd_date() == null &&
									provider.get().getStatus() == cloud_Providers.getStatus() && provider.get().getActivity() == cloud_Providers.getActivity() && 
									provider.get().getApi() == cloud_Providers.getApi() && provider.get().getDescription().equals(cloud_Providers.getDescription()) && 
									provider.get().getLogo().equals(cloud_Providers.getLogo()) && provider.get().getName().equals(cloud_Providers.getName()) && provider.get().getWebsite().equals(cloud_Providers.getWebsite())) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						}

						cloud_Providers.setId(Long.parseLong(id));
						Providers result = cloud_Providers_Service.updateCloud_Providers(cloud_Providers);
						if(result != null) {
							/*api logs 
							 * backup_executions:1,backup_instances:2,backup_operations:3,backup_settings:4,cloud_providers_account:5,core_access_credentials:6,core_accounts_modules:7,core_accounts:8,core_categories_elements:9,core_categories:10,
							 * core_logs:11,core_notes:12,core_notifications:13,core_urls:14,core_users_instances:15,core_users_metrics:16,core_users_modules:17,core_users_permissions:18,core_users_security:19,core_users_tokens:20,core_users:21,
							 * Inventory_applications_dependencies:22,Inventory_applications_instances:23,Inventory_applications_sources:24,Inventory_applications_versions:25,Inventory_applications:26,Inventory_hosts:27,Inventory_instances:28,
							 * Inventory_servers_versions:29,Inventory_servers:30,Inventory_templates_apps:31,Inventory_templates:32,metrics:33,modules:34,monitoring_automations:35,monitoring_commands_executions:36,monitoring_commands:37,monitoring_metrics:38,
							 * monitoring_policies_alerts:39,monitoring_policies_servers:40,monitoring_policies:41,monitoring_settings:42,networks_domain_names:43,networks_hosts:44,networks_ssl_certificates:45,providers:46,app_api_key:47,api_key:48,
							 * api_keys_permissions:49, core_logs:50
							*/
							Core_Api_Logs api_logs = new Core_Api_Logs();
							
							Api_Keys api_key = api_Keys_Repo.findById(Long.parseLong(BasicAuthInterceptor.GLOBAL_APIKEY)).get();
							
							api_logs.setAction("edit");
							api_logs.setApikey(api_key);
							api_logs.setElement(46);
							api_logs.setElement_id((int)result.getId());
							api_logs.setLog_date(LocalDateTime.now());
							core_Api_Logs_Repo.save(api_logs);
							
							return ResponseHandler.ResponseOk("Updated!", HttpStatus.OK, result);
						}else {
							return ResponseHandler.ResponseBadRequest("Your request was malformed!", HttpStatus.BAD_REQUEST, null);
						}
					}
					
				}
			}catch(Exception e) {
	            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }
		}
		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteCloud_Providers(@PathVariable("id") String id){
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}
		else {
			try {
				Optional<Providers> provider = cloud_Providers_Service.findCloud_ProvidersById(Long.parseLong(id));
				if(provider.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else {
					if(provider.get().getStatus() == 4 || provider.get().getEnd_date() != null) {
			        	return ResponseHandler.ResponseForbidden("Resources already deleted! We were unable to perform the request", HttpStatus.FORBIDDEN, null);
					}else {
						String result = cloud_Providers_Service.deleteCloud_Providers(Long.parseLong(id));
						if(result != null) {
							/*api logs 
							 * backup_executions:1,backup_instances:2,backup_operations:3,backup_settings:4,cloud_providers_account:5,core_access_credentials:6,core_accounts_modules:7,core_accounts:8,core_categories_elements:9,core_categories:10,
							 * core_logs:11,core_notes:12,core_notifications:13,core_urls:14,core_users_instances:15,core_users_metrics:16,core_users_modules:17,core_users_permissions:18,core_users_security:19,core_users_tokens:20,core_users:21,
							 * Inventory_applications_dependencies:22,Inventory_applications_instances:23,Inventory_applications_sources:24,Inventory_applications_versions:25,Inventory_applications:26,Inventory_hosts:27,Inventory_instances:28,
							 * Inventory_servers_versions:29,Inventory_servers:30,Inventory_templates_apps:31,Inventory_templates:32,metrics:33,modules:34,monitoring_automations:35,monitoring_commands_executions:36,monitoring_commands:37,monitoring_metrics:38,
							 * monitoring_policies_alerts:39,monitoring_policies_servers:40,monitoring_policies:41,monitoring_settings:42,networks_domain_names:43,networks_hosts:44,networks_ssl_certificates:45,providers:46,app_api_key:47,api_key:48,
							 * api_keys_permissions:49, core_logs:50
							*/
							Core_Api_Logs api_logs = new Core_Api_Logs();
							
							Api_Keys api_key = api_Keys_Repo.findById(Long.parseLong(BasicAuthInterceptor.GLOBAL_APIKEY)).get();
							
							api_logs.setAction("delete");
							api_logs.setApikey(api_key);
							api_logs.setElement(46);
							api_logs.setElement_id((int)Long.parseLong(id));
							api_logs.setLog_date(LocalDateTime.now());
							core_Api_Logs_Repo.save(api_logs);
							
							return ResponseHandler.ResponseOk("Deleted!", HttpStatus.OK, result);
						}else {
							return ResponseHandler.ResponseBadRequest("Your request was malformed!", HttpStatus.BAD_REQUEST, null);
						}
					}
					
				}
			}catch(Exception e) {
	            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }
		}
	}
	
}

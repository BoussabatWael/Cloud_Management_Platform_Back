package com.gcs.cmp.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.gcs.cmp.Exception.ResponseHandler;
import com.gcs.cmp.entity.Api_Keys;
import com.gcs.cmp.entity.Cloud_Providers_Accounts;
import com.gcs.cmp.entity.Core_Api_Logs;
import com.gcs.cmp.entity.Inventory_Hosts;
import com.gcs.cmp.entity.Inventory_Instances;
import com.gcs.cmp.entity.Networks_Domain_Names;
import com.gcs.cmp.entity.Networks_Hosts;
import com.gcs.cmp.interceptors.BasicAuthInterceptor;
import com.gcs.cmp.repository.Api_Keys_Repo;
import com.gcs.cmp.repository.Cloud_Providers_Accounts_Repo;
import com.gcs.cmp.repository.Core_Api_Logs_Repo;
import com.gcs.cmp.repository.Inventory_Hosts_Repo;
import com.gcs.cmp.repository.Inventory_Instances_Repo;
import com.gcs.cmp.repository.Networks_Domain_Names_Repo;
import com.gcs.cmp.service.Networks_Hosts_Service;
import com.gcs.cmp.validators.Inputs_Validations;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/networks/hosts")
public class NetworksHosts {

	@Autowired
	private Networks_Hosts_Service networks_Hosts_Service;
	
	@Autowired
	private Core_Api_Logs_Repo core_Api_Logs_Repo;
	
	@Autowired
	private Api_Keys_Repo api_Keys_Repo;
	
	@Autowired
	private Networks_Domain_Names_Repo networks_Domain_Names_Repo;
	
	@Autowired
	private Inventory_Hosts_Repo inventory_Hosts_Repo;
	
	@Autowired
	private Inventory_Instances_Repo inventory_Instances_Repo;
	
	@Autowired
	private Cloud_Providers_Accounts_Repo cloud_Providers_Accounts_Repo;
	
	private HttpMessageNotReadableException ex;
	
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleException(HttpMessageNotReadableException exception) {
        String msg = null;
        Throwable cause = exception.getCause();

        if (cause instanceof JsonParseException) {
            JsonParseException jpe = (JsonParseException) cause;
            msg = jpe.getOriginalMessage();
        }

        // special case of JsonMappingException below, too much class detail in error messages
        else if (cause instanceof MismatchedInputException) {
            MismatchedInputException mie = (MismatchedInputException) cause;
            if (mie.getPath() != null && mie.getPath().size() > 0) {
                msg = "Invalid request field : " + mie.getPath().get(0).getFieldName();
            }

            // just in case, haven't seen this condition
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
	public ResponseEntity<Object> getAllNetworks_Hosts(){	
		try {
			if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
				List <Networks_Hosts> result = networks_Hosts_Service.getNetworks_HostsList(Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT));	
				return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
			}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
				List <Networks_Hosts> result = networks_Hosts_Service.getNetworks_HostsList(Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT));	
				return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
			}else {
				return ResponseHandler.ResponseListKo("Error... Account NOT Identified!", 0, HttpStatus.FORBIDDEN, null);
			}
			
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@PostMapping("/create")
	public ResponseEntity<Object> addNetworks_Hosts(@Validated @RequestBody Networks_Hosts networks_Hosts,Errors errors){	
		if(ex != null) {
			handleException(ex);
		}
		if(errors.hasErrors()) {
            return ResponseHandler.ResponseBadRequest(errors.getFieldError().getField()+" "+errors.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST, null);
		}else {
			try {
				if(networks_Hosts.getDomain() == null || networks_Hosts.getHosting() == null || networks_Hosts.getInstance() == null || networks_Hosts.getCloud_provider_account() == null) {
			        return ResponseHandler.ResponseBadRequest("Your request was malformed! Foreign key required!", HttpStatus.BAD_REQUEST, null);
				}else {
					if(networks_Hosts.getDomain().getId() == 0 || networks_Hosts.getHosting().getId() == 0 || networks_Hosts.getInstance().getId() == 0 || networks_Hosts.getCloud_provider_account().getId() == 0) {
				        return ResponseHandler.ResponseBadRequest("Your request was malformed... ID must not be null", HttpStatus.BAD_REQUEST, null);
					}
					Optional<Networks_Domain_Names> domain_name = networks_Domain_Names_Repo.findById(networks_Hosts.getDomain().getId());
					Optional<Inventory_Hosts> host = inventory_Hosts_Repo.findById(networks_Hosts.getHosting().getId());
					Optional<Inventory_Instances> inv_ins = inventory_Instances_Repo.findById(networks_Hosts.getInstance().getId());
					Optional<Cloud_Providers_Accounts> cloud_provider = cloud_Providers_Accounts_Repo.findById(networks_Hosts.getCloud_provider_account().getId());
					
					if(domain_name.equals(Optional.empty()) || host.equals(Optional.empty()) || inv_ins.equals(Optional.empty()) || cloud_provider.equals(Optional.empty())) {
				        return ResponseHandler.ResponseBadRequest("Your request was malformed! Foreign key required!", HttpStatus.BAD_REQUEST, null);
					}else {
						if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
							if(domain_name.get().getAccount() != null && host.get().getAccount() != null && inv_ins.get().getAccount() != null) {
								if(domain_name.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT) || host.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT) || inv_ins.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
						        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
								}else {
									networks_Hosts.setStart_date(LocalDateTime.now());
									networks_Hosts.setDomain(domain_name.get());
									networks_Hosts.setHosting(host.get());
									networks_Hosts.setInstance(inv_ins.get());
									networks_Hosts.setCloud_provider_account(cloud_provider.get());
									
									Networks_Hosts result = networks_Hosts_Service.addNetworks_Hosts(networks_Hosts);	
									if(result != null) {
										// create logs 
										// backup_executions:1,backup_instances:2,backup_operations:3,backup_settings:4,cloud_providers_account:5,core_access_credentials:6,core_accounts_modules:7,core_accounts:8,core_categories_elements:9,core_categories:10,
										//core_logs:11,core_notes:12,core_notifications:13,core_urls:14,core_users_instances:15,core_users_metrics:16,core_users_modules:17,core_users_permissions:18,core_users_security:19,core_users_tokens:20,core_users:21,
										//Inventory_applications_dependencies:22,Inventory_applications_instances:23,Inventory_applications_sources:24,Inventory_applications_versions:25,Inventory_applications:26,Inventory_hosts:27,Inventory_instances:28,
										//Inventory_servers_versions:29,Inventory_servers:30,Inventory_templates_apps:31,Inventory_templates:32,metrics:33,modules:34,monitoring_automations:35,monitoring_commands_executions:36,monitoring_commands:37,monitoring_metrics:38
										//monitoring_policies_alerts:39,monitoring_policies_servers:40,monitoring_policies:41,monitoring_settings:42,networks_domain_names:43,networks_hosts:44,networks_ssl_certificates:45,providers:46
										
										Core_Api_Logs api_logs = new Core_Api_Logs();
										
										Api_Keys api_key = api_Keys_Repo.findById(Long.parseLong(BasicAuthInterceptor.GLOBAL_APIKEY)).get();
										
										api_logs.setAction("create");
										api_logs.setApikey(api_key);
										api_logs.setElement(44);
										api_logs.setElement_id((int)result.getId());
										api_logs.setLog_date(LocalDateTime.now());
		
										core_Api_Logs_Repo.save(api_logs);
										
								        return ResponseHandler.ResponseOk("Successfully added data!", HttpStatus.OK, result);
									}else {
								        return ResponseHandler.ResponseBadRequest("Your request was malformed!", HttpStatus.BAD_REQUEST, null);
									}
								}
							}else {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}
						}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
							if(domain_name.get().getAccount() != null && host.get().getAccount() != null && inv_ins.get().getAccount() != null) {
								if(domain_name.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT) || host.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT) || inv_ins.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
						        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
								}else {
									networks_Hosts.setStart_date(LocalDateTime.now());
									networks_Hosts.setDomain(domain_name.get());
									networks_Hosts.setHosting(host.get());
									networks_Hosts.setInstance(inv_ins.get());
									networks_Hosts.setCloud_provider_account(cloud_provider.get());
									
									Networks_Hosts result = networks_Hosts_Service.addNetworks_Hosts(networks_Hosts);	
									if(result != null) {
										// create logs 
										// backup_executions:1,backup_instances:2,backup_operations:3,backup_settings:4,cloud_providers_account:5,core_access_credentials:6,core_accounts_modules:7,core_accounts:8,core_categories_elements:9,core_categories:10,
										//core_logs:11,core_notes:12,core_notifications:13,core_urls:14,core_users_instances:15,core_users_metrics:16,core_users_modules:17,core_users_permissions:18,core_users_security:19,core_users_tokens:20,core_users:21,
										//Inventory_applications_dependencies:22,Inventory_applications_instances:23,Inventory_applications_sources:24,Inventory_applications_versions:25,Inventory_applications:26,Inventory_hosts:27,Inventory_instances:28,
										//Inventory_servers_versions:29,Inventory_servers:30,Inventory_templates_apps:31,Inventory_templates:32,metrics:33,modules:34,monitoring_automations:35,monitoring_commands_executions:36,monitoring_commands:37,monitoring_metrics:38
										//monitoring_policies_alerts:39,monitoring_policies_servers:40,monitoring_policies:41,monitoring_settings:42,networks_domain_names:43,networks_hosts:44,networks_ssl_certificates:45,providers:46
										
										Core_Api_Logs api_logs = new Core_Api_Logs();
										
										Api_Keys api_key = api_Keys_Repo.findById(Long.parseLong(BasicAuthInterceptor.GLOBAL_APIKEY)).get();
										
										api_logs.setAction("create");
										api_logs.setApikey(api_key);
										api_logs.setElement(44);
										api_logs.setElement_id((int)result.getId());
										api_logs.setLog_date(LocalDateTime.now());
										core_Api_Logs_Repo.save(api_logs);
										
								        return ResponseHandler.ResponseOk("Successfully added data!", HttpStatus.OK, result);
									}else {
								        return ResponseHandler.ResponseBadRequest("Your request was malformed!", HttpStatus.BAD_REQUEST, null);
									}
								}
							}else {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}
						}else {
					        return ResponseHandler.ResponseForbidden("Error... Account NOT Identified!", HttpStatus.FORBIDDEN, null);
						}
					}
					
				}
			}catch (Exception e) {
	            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }
		}
		
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Object> getNetworks_HostsById(@PathVariable(name="id") String id){
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
				Optional<Networks_Hosts> result = networks_Hosts_Service.findNetworks_HostsById(Long.parseLong(id));
				if(result.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else {
					if(result.get().getStatus() == 4 || result.get().getEnd_date() != null) {
			        	return ResponseHandler.ResponseForbidden("Resources has been deleted!", HttpStatus.FORBIDDEN, null);
					}
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						if(result.get().getDomain().getAccount() != null && result.get().getHosting().getAccount() != null && result.get().getInstance().getAccount() != null) {
							if(result.get().getDomain().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT) || result.get().getHosting().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT) || result.get().getInstance().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
						        return ResponseHandler.ResponseOk("Successfully retrieved data!", HttpStatus.OK, result);
							}
						}else {
				        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
						}
					}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
						if(result.get().getDomain().getAccount() != null && result.get().getHosting().getAccount() != null && result.get().getInstance().getAccount() != null) {
							if(result.get().getDomain().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT) || result.get().getHosting().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT) || result.get().getInstance().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
						        return ResponseHandler.ResponseOk("Successfully retrieved data!", HttpStatus.OK, result);
							}
						}else {
				        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
						}
					}else {
				        return ResponseHandler.ResponseForbidden("Error... Account NOT Identified!", HttpStatus.FORBIDDEN, null);
					}
				}
			}catch(Exception e) {
	            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }
		}
		
	}
	
	@GetMapping("/domain/get/{domain_id}")
	public ResponseEntity<Object> getDomainNetworks_HostsList(@PathVariable(name="domain_id") String domain_id){
		if(Inputs_Validations.CheckInt(domain_id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						List<Networks_Hosts> result = networks_Hosts_Service.getDomainNetworks_HostsList(Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT), Long.parseLong(domain_id));
						return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
							
					}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
						List<Networks_Hosts> result = networks_Hosts_Service.getDomainNetworks_HostsList(Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT), Long.parseLong(domain_id));
						return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
							
					}else {
				        return ResponseHandler.ResponseForbidden("Error... Account NOT Identified!", HttpStatus.FORBIDDEN, null);
					}
				
			}catch(Exception e) {
	            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }
		}
		
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updateNetworks_Hosts(@RequestBody Networks_Hosts networks_Hosts,@PathVariable(name="id") String id, Errors errors){	
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
				Optional<Networks_Hosts> network_host = networks_Hosts_Service.findNetworks_HostsById(Long.parseLong(id));
				if(network_host.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else if(network_host.get().getStatus() == 4 || network_host.get().getEnd_date() != null) {
		        	return ResponseHandler.ResponseForbidden("Resources has been deleted! We were unable to perform the request", HttpStatus.FORBIDDEN, null);
				}else if(network_host.get().getDomain() == null || network_host.get().getHosting() == null || network_host.get().getInstance() == null || network_host.get().getCloud_provider_account() == null) {
			        return ResponseHandler.ResponseBadRequest("Your request was malformed! Foreign key required!", HttpStatus.BAD_REQUEST, null);
				}else {

					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"); 

					networks_Hosts.setDomain(network_host.get().getDomain());
					networks_Hosts.setHosting(network_host.get().getHosting());
					networks_Hosts.setInstance(network_host.get().getInstance());
					networks_Hosts.setCloud_provider_account(network_host.get().getCloud_provider_account());
					
					networks_Hosts.setStart_date(network_host.get().getStart_date());
					
					if(networks_Hosts.getEnd_date() == null) {
						networks_Hosts.setEnd_date(network_host.get().getEnd_date());
					}
					if(networks_Hosts.getStatus() == 0) {
						networks_Hosts.setStatus(network_host.get().getStatus());
					}
					if(networks_Hosts.getParent_id() == 0) {
						networks_Hosts.setParent_id(network_host.get().getParent_id());
					}

					if(network_host.get().getDomain() == networks_Hosts.getDomain() && network_host.get().getHosting() == networks_Hosts.getHosting() && network_host.get().getInstance() == networks_Hosts.getInstance() && network_host.get().getCloud_provider_account() == networks_Hosts.getCloud_provider_account() && 
							network_host.get().getStart_date().toString().equals(networks_Hosts.getStart_date().format(formatter)) && networks_Hosts.getEnd_date() == null && network_host.get().getParent_id() == networks_Hosts.getParent_id()) {
			        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
					}

					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						if(network_host.get().getDomain().getAccount() != null && network_host.get().getHosting().getAccount() != null && network_host.get().getInstance().getAccount() != null) {
							if(network_host.get().getDomain().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT) || network_host.get().getHosting().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT) || network_host.get().getInstance().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								networks_Hosts.setId(Long.parseLong(id));
								Networks_Hosts result = networks_Hosts_Service.updateNetworks_Hosts(networks_Hosts);
								if(result != null) {
									// create logs 
									// backup_executions:1,backup_instances:2,backup_operations:3,backup_settings:4,cloud_providers_account:5,core_access_credentials:6,core_accounts_modules:7,core_accounts:8,core_categories_elements:9,core_categories:10,
									//core_logs:11,core_notes:12,core_notifications:13,core_urls:14,core_users_instances:15,core_users_metrics:16,core_users_modules:17,core_users_permissions:18,core_users_security:19,core_users_tokens:20,core_users:21,
									//Inventory_applications_dependencies:22,Inventory_applications_instances:23,Inventory_applications_sources:24,Inventory_applications_versions:25,Inventory_applications:26,Inventory_hosts:27,Inventory_instances:28,
									//Inventory_servers_versions:29,Inventory_servers:30,Inventory_templates_apps:31,Inventory_templates:32,metrics:33,modules:34,monitoring_automations:35,monitoring_commands_executions:36,monitoring_commands:37,monitoring_metrics:38
									//monitoring_policies_alerts:39,monitoring_policies_servers:40,monitoring_policies:41,monitoring_settings:42,networks_domain_names:43,networks_hosts:44,networks_ssl_certificates:45,providers:46
									
									Core_Api_Logs api_logs = new Core_Api_Logs();
									
									Api_Keys api_key = api_Keys_Repo.findById(Long.parseLong(BasicAuthInterceptor.GLOBAL_APIKEY)).get();
									
									api_logs.setAction("edit");
									api_logs.setApikey(api_key);
									api_logs.setElement(44);
									api_logs.setElement_id((int)result.getId());
									api_logs.setLog_date(LocalDateTime.now());
									core_Api_Logs_Repo.save(api_logs);
									
							        return ResponseHandler.ResponseOk("Updated", HttpStatus.OK, result);
								}else {
									return ResponseHandler.ResponseBadRequest("Your request was malformed!", HttpStatus.BAD_REQUEST, null);
								}
							}
						}else {
				        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
						}
					}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
						if(network_host.get().getDomain().getAccount() != null && network_host.get().getHosting().getAccount() != null && network_host.get().getInstance().getAccount() != null) {
							if(network_host.get().getDomain().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT) || network_host.get().getHosting().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT) || network_host.get().getInstance().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								networks_Hosts.setId(Long.parseLong(id));
								Networks_Hosts result = networks_Hosts_Service.updateNetworks_Hosts(networks_Hosts);
								if(result != null) {
									// create logs 
									// backup_executions:1,backup_instances:2,backup_operations:3,backup_settings:4,cloud_providers_account:5,core_access_credentials:6,core_accounts_modules:7,core_accounts:8,core_categories_elements:9,core_categories:10,
									//core_logs:11,core_notes:12,core_notifications:13,core_urls:14,core_users_instances:15,core_users_metrics:16,core_users_modules:17,core_users_permissions:18,core_users_security:19,core_users_tokens:20,core_users:21,
									//Inventory_applications_dependencies:22,Inventory_applications_instances:23,Inventory_applications_sources:24,Inventory_applications_versions:25,Inventory_applications:26,Inventory_hosts:27,Inventory_instances:28,
									//Inventory_servers_versions:29,Inventory_servers:30,Inventory_templates_apps:31,Inventory_templates:32,metrics:33,modules:34,monitoring_automations:35,monitoring_commands_executions:36,monitoring_commands:37,monitoring_metrics:38
									//monitoring_policies_alerts:39,monitoring_policies_servers:40,monitoring_policies:41,monitoring_settings:42,networks_domain_names:43,networks_hosts:44,networks_ssl_certificates:45,providers:46
									
									Core_Api_Logs api_logs = new Core_Api_Logs();
									
									Api_Keys api_key = api_Keys_Repo.findById(Long.parseLong(BasicAuthInterceptor.GLOBAL_APIKEY)).get();
									
									api_logs.setAction("edit");
									api_logs.setApikey(api_key);
									api_logs.setElement(44);
									api_logs.setElement_id((int)result.getId());
									api_logs.setLog_date(LocalDateTime.now());
									core_Api_Logs_Repo.save(api_logs);
									
							        return ResponseHandler.ResponseOk("Updated", HttpStatus.OK, result);
								}else {
									return ResponseHandler.ResponseBadRequest("Your request was malformed!", HttpStatus.BAD_REQUEST, null);
								}
							}
						}else {
				        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
						}
					}else {
				        return ResponseHandler.ResponseForbidden("Error... Account NOT Identified!", HttpStatus.FORBIDDEN, null);
					}
				}
			}catch (Exception e) {
	            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }
		}
		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteNetworks_Hosts(@PathVariable("id") String id){
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}
		else {
			try {
				Optional<Networks_Hosts> network_host = networks_Hosts_Service.findNetworks_HostsById(Long.parseLong(id));
				if(network_host.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else {
					if(network_host.get().getStatus() == 4 || network_host.get().getEnd_date() != null) {
			        	return ResponseHandler.ResponseForbidden("Resources already deleted! We were unable to perform the request", HttpStatus.FORBIDDEN, null);
					}
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						if(network_host.get().getDomain().getAccount() != null && network_host.get().getHosting().getAccount() != null && network_host.get().getInstance().getAccount() != null) {
							if(network_host.get().getDomain().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT) || network_host.get().getHosting().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT) || network_host.get().getInstance().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								String result = networks_Hosts_Service.deleteNetworks_Hosts(Long.parseLong(id));
								if(result != null) {
									// create logs 
									// backup_executions:1,backup_instances:2,backup_operations:3,backup_settings:4,cloud_providers_account:5,core_access_credentials:6,core_accounts_modules:7,core_accounts:8,core_categories_elements:9,core_categories:10,
									//core_logs:11,core_notes:12,core_notifications:13,core_urls:14,core_users_instances:15,core_users_metrics:16,core_users_modules:17,core_users_permissions:18,core_users_security:19,core_users_tokens:20,core_users:21,
									//Inventory_applications_dependencies:22,Inventory_applications_instances:23,Inventory_applications_sources:24,Inventory_applications_versions:25,Inventory_applications:26,Inventory_hosts:27,Inventory_instances:28,
									//Inventory_servers_versions:29,Inventory_servers:30,Inventory_templates_apps:31,Inventory_templates:32,metrics:33,modules:34,monitoring_automations:35,monitoring_commands_executions:36,monitoring_commands:37,monitoring_metrics:38
									//monitoring_policies_alerts:39,monitoring_policies_servers:40,monitoring_policies:41,monitoring_settings:42,networks_domain_names:43,networks_hosts:44,networks_ssl_certificates:45,providers:46
									
									Core_Api_Logs api_logs = new Core_Api_Logs();
									
									Api_Keys api_key = api_Keys_Repo.findById(Long.parseLong(BasicAuthInterceptor.GLOBAL_APIKEY)).get();
									
									api_logs.setAction("delete");
									api_logs.setApikey(api_key);
									api_logs.setElement(44);
									api_logs.setElement_id((int)Long.parseLong(id));
									api_logs.setLog_date(LocalDateTime.now());
									core_Api_Logs_Repo.save(api_logs);
									
							        return ResponseHandler.ResponseOk("Deleted!", HttpStatus.OK, result);
								}else {
						            return ResponseHandler.ResponseBadRequest("Your request was malformed!", HttpStatus.BAD_REQUEST, null);
								}
							}
						}else {
				        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
						}
					}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
						if(network_host.get().getDomain().getAccount() != null && network_host.get().getHosting().getAccount() != null && network_host.get().getInstance().getAccount() != null) {
							if(network_host.get().getDomain().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT) || network_host.get().getHosting().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT) || network_host.get().getInstance().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								String result = networks_Hosts_Service.deleteNetworks_Hosts(Long.parseLong(id));
								if(result != null) {
									// create logs 
									// backup_executions:1,backup_instances:2,backup_operations:3,backup_settings:4,cloud_providers_account:5,core_access_credentials:6,core_accounts_modules:7,core_accounts:8,core_categories_elements:9,core_categories:10,
									//core_logs:11,core_notes:12,core_notifications:13,core_urls:14,core_users_instances:15,core_users_metrics:16,core_users_modules:17,core_users_permissions:18,core_users_security:19,core_users_tokens:20,core_users:21,
									//Inventory_applications_dependencies:22,Inventory_applications_instances:23,Inventory_applications_sources:24,Inventory_applications_versions:25,Inventory_applications:26,Inventory_hosts:27,Inventory_instances:28,
									//Inventory_servers_versions:29,Inventory_servers:30,Inventory_templates_apps:31,Inventory_templates:32,metrics:33,modules:34,monitoring_automations:35,monitoring_commands_executions:36,monitoring_commands:37,monitoring_metrics:38
									//monitoring_policies_alerts:39,monitoring_policies_servers:40,monitoring_policies:41,monitoring_settings:42,networks_domain_names:43,networks_hosts:44,networks_ssl_certificates:45,providers:46
									
									Core_Api_Logs api_logs = new Core_Api_Logs();
									
									Api_Keys api_key = api_Keys_Repo.findById(Long.parseLong(BasicAuthInterceptor.GLOBAL_APIKEY)).get();
									
									api_logs.setAction("delete");
									api_logs.setApikey(api_key);
									api_logs.setElement(44);
									api_logs.setElement_id((int)Long.parseLong(id));
									api_logs.setLog_date(LocalDateTime.now());
									core_Api_Logs_Repo.save(api_logs);
									
							        return ResponseHandler.ResponseOk("Deleted!", HttpStatus.OK, result);
								}else {
						            return ResponseHandler.ResponseBadRequest("Your request was malformed!", HttpStatus.BAD_REQUEST, null);
								}
							}
						}else {
				        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
						}
					}else {
				        return ResponseHandler.ResponseForbidden("Error... Account NOT Identified!", HttpStatus.FORBIDDEN, null);
					}
				}
			}catch (Exception e) {
	            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }
		}
		
	}
}

package com.gcs.cmp.controller;

import java.time.LocalDateTime;
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
import com.gcs.cmp.entity.Core_Api_Logs;
import com.gcs.cmp.entity.Inventory_Instances;
import com.gcs.cmp.entity.Inventory_Servers;
import com.gcs.cmp.interceptors.BasicAuthInterceptor;
import com.gcs.cmp.repository.Api_Keys_Repo;
import com.gcs.cmp.repository.Core_Api_Logs_Repo;
import com.gcs.cmp.repository.Inventory_Instances_Repo;
import com.gcs.cmp.service.Inventory_Servers_Service;
import com.gcs.cmp.validators.Inputs_Validations;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Servers", description = "Manage inventory servers")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/inventory/servers")
public class InventoryServers {

	@Autowired
	private Inventory_Servers_Service inventory_Servers_Service;
	
	@Autowired
	private Core_Api_Logs_Repo core_Api_Logs_Repo;
	
	@Autowired
	private Api_Keys_Repo api_Keys_Repo;
	
	@Autowired
	private Inventory_Instances_Repo inventory_Instances_Repo;
	
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
	public ResponseEntity<Object> getAllInventory_Servers(){	
		try {
			if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
				List <Inventory_Servers> result = inventory_Servers_Service.getInventory_ServersList(Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT));	
				return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
			}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
				List <Inventory_Servers> result = inventory_Servers_Service.getInventory_ServersList(Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT));	
				return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
			}else {
				return ResponseHandler.ResponseListKo("Error... Account NOT Identified!", 0, HttpStatus.FORBIDDEN, null);
			}
			
			
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@PostMapping("/create")
	public ResponseEntity<Object> addInventory_Servers(@Validated @RequestBody Inventory_Servers inventory_Servers,Errors errors){	
		if(ex != null) {
			handleException(ex);
		}
		
		if(errors.hasErrors()) {
            return ResponseHandler.ResponseBadRequest(errors.getFieldError().getField()+" "+errors.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST, null);
		}else {
			try {
				if(inventory_Servers.getInstance() == null) {
			        return ResponseHandler.ResponseBadRequest("Your request was malformed! Foreign key required!", HttpStatus.BAD_REQUEST, null);
				}else {
					if(inventory_Servers.getInstance().getId() == 0) {
				        return ResponseHandler.ResponseBadRequest("Your request was malformed... ID must not be null", HttpStatus.BAD_REQUEST, null);
					}
					Optional<Inventory_Instances> inv_ins = inventory_Instances_Repo.findById(inventory_Servers.getInstance().getId());
					if(inv_ins.equals(Optional.empty())) {
				        return ResponseHandler.ResponseBadRequest("Your request was malformed! Foreign key required!", HttpStatus.BAD_REQUEST, null);
					}else {
						if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
							if(inv_ins.get().getAccount() != null) {
								if(inv_ins.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
						        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
								}else {
									inventory_Servers.setInstance(inv_ins.get());
									Inventory_Servers result = inventory_Servers_Service.addInventory_Servers(inventory_Servers);	
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
										api_logs.setElement(30);
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
							if(inv_ins.get().getAccount() != null) {
								if(inv_ins.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
						        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
								}else {
									inventory_Servers.setInstance(inv_ins.get());
									Inventory_Servers result = inventory_Servers_Service.addInventory_Servers(inventory_Servers);	
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
										api_logs.setElement(30);
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
	public ResponseEntity<Object> getInventory_ServersById(@PathVariable(name="id") String id){	
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
				Optional<Inventory_Servers> result = inventory_Servers_Service.findInventory_ServersById(Long.parseLong(id));
				if(result.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else {
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						if(result.get().getInstance().getAccount() != null) {
							if(result.get().getInstance().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
					        	return ResponseHandler.ResponseOk("Successfully retrieved data!", HttpStatus.OK, result);
							}
						}else {
				        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
						}
						
					}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
						if(result.get().getInstance().getAccount() != null) {
							if(result.get().getInstance().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
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
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updateInventory_Servers(@RequestBody Inventory_Servers inventory_Servers,@PathVariable(name="id") String id, Errors errors){	
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
				Optional<Inventory_Servers> inv_ser = inventory_Servers_Service.findInventory_ServersById(Long.parseLong(id));
				if(inv_ser.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else if(inv_ser.get().getInstance() == null) {
			        return ResponseHandler.ResponseBadRequest("Your request was malformed! Foreign key required!", HttpStatus.BAD_REQUEST, null);
				}else {

					inventory_Servers.setInstance(inv_ser.get().getInstance());
					
					if(inventory_Servers.getClasse() == 0) {
						inventory_Servers.setClasse(inv_ser.get().getClasse());
					}
					if(inventory_Servers.getCpu() == 0) {
						inventory_Servers.setCpu(inv_ser.get().getCpu());
					}
					if(inventory_Servers.getDisk_space() == 0) {
						inventory_Servers.setDisk_space(inv_ser.get().getDisk_space());
					}
					if(inventory_Servers.getEnvironment() == 0) {
						inventory_Servers.setEnvironment(inv_ser.get().getEnvironment());
					}
					if(inventory_Servers.getExternal_id() == null) {
						inventory_Servers.setExternal_id(inv_ser.get().getExternal_id());
					}
					if(inventory_Servers.getIp_address() == null) {
						inventory_Servers.setIp_address(inv_ser.get().getIp_address());
					}
					if(inventory_Servers.getLocation() == 0) {
						inventory_Servers.setLocation(inv_ser.get().getLocation());
					}
					if(inventory_Servers.getMemory() == 0) {
						inventory_Servers.setMemory(inv_ser.get().getMemory());
					}
					if(inventory_Servers.getOperating_system() == 0) {
						inventory_Servers.setOperating_system(inv_ser.get().getOperating_system());
					}
					if(inventory_Servers.getOs_version() == null) {
						inventory_Servers.setOs_version(inv_ser.get().getOs_version());
					}
					if(inv_ser.get().getExternal_id() == null && inv_ser.get().getOs_version() != null) {
						if(inv_ser.get().getInstance() == inventory_Servers.getInstance() && inv_ser.get().getClasse() == inventory_Servers.getClasse() && inv_ser.get().getCpu() == inventory_Servers.getCpu() &&
								inv_ser.get().getDisk_space() == inventory_Servers.getDisk_space() && inv_ser.get().getEnvironment() == inventory_Servers.getEnvironment() && inv_ser.get().getExternal_id() == inventory_Servers.getExternal_id()
								&& inv_ser.get().getIp_address().equals(inventory_Servers.getIp_address()) && inv_ser.get().getLocation() == inventory_Servers.getLocation() && inv_ser.get().getMemory() == inventory_Servers.getMemory() 
								&& inv_ser.get().getOperating_system() == inventory_Servers.getOperating_system() && inv_ser.get().getOs_version().equals(inventory_Servers.getOs_version())) {
			        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
					}
					}
					else if(inv_ser.get().getExternal_id() != null && inv_ser.get().getOs_version() == null) {
						if(inv_ser.get().getInstance() == inventory_Servers.getInstance() && inv_ser.get().getClasse() == inventory_Servers.getClasse() && inv_ser.get().getCpu() == inventory_Servers.getCpu() &&
								inv_ser.get().getDisk_space() == inventory_Servers.getDisk_space() && inv_ser.get().getEnvironment() == inventory_Servers.getEnvironment() && inv_ser.get().getExternal_id().equals(inventory_Servers.getExternal_id())
								&& inv_ser.get().getIp_address().equals(inventory_Servers.getIp_address()) && inv_ser.get().getLocation() == inventory_Servers.getLocation() && inv_ser.get().getMemory() == inventory_Servers.getMemory() 
								&& inv_ser.get().getOperating_system() == inventory_Servers.getOperating_system() && inv_ser.get().getOs_version() == inventory_Servers.getOs_version()) {
			        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
					}
					}
					else if(inv_ser.get().getExternal_id() == null && inv_ser.get().getOs_version() == null) {
						if(inv_ser.get().getInstance() == inventory_Servers.getInstance() && inv_ser.get().getClasse() == inventory_Servers.getClasse() && inv_ser.get().getCpu() == inventory_Servers.getCpu() &&
								inv_ser.get().getDisk_space() == inventory_Servers.getDisk_space() && inv_ser.get().getEnvironment() == inventory_Servers.getEnvironment() && inv_ser.get().getExternal_id() == inventory_Servers.getExternal_id()
								&& inv_ser.get().getIp_address().equals(inventory_Servers.getIp_address()) && inv_ser.get().getLocation() == inventory_Servers.getLocation() && inv_ser.get().getMemory() == inventory_Servers.getMemory() 
								&& inv_ser.get().getOperating_system() == inventory_Servers.getOperating_system() && inv_ser.get().getOs_version() == inventory_Servers.getOs_version()) {
			        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
					}
					}
					else {
						if(inv_ser.get().getInstance() == inventory_Servers.getInstance() && inv_ser.get().getClasse() == inventory_Servers.getClasse() && inv_ser.get().getCpu() == inventory_Servers.getCpu() &&
								inv_ser.get().getDisk_space() == inventory_Servers.getDisk_space() && inv_ser.get().getEnvironment() == inventory_Servers.getEnvironment() && inv_ser.get().getExternal_id().equals(inventory_Servers.getExternal_id())
								&& inv_ser.get().getIp_address().equals(inventory_Servers.getIp_address()) && inv_ser.get().getLocation() == inventory_Servers.getLocation() && inv_ser.get().getMemory() == inventory_Servers.getMemory() 
								&& inv_ser.get().getOperating_system() == inventory_Servers.getOperating_system() && inv_ser.get().getOs_version().equals(inventory_Servers.getOs_version())) {
			        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
					}
					}
					
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						if(inv_ser.get().getInstance().getAccount() != null) {
							if(inv_ser.get().getInstance().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								inventory_Servers.setId(Long.parseLong(id));
								Inventory_Servers result = inventory_Servers_Service.updateInventory_Servers(inventory_Servers);
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
									api_logs.setElement(30);
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
						if(inv_ser.get().getInstance().getAccount() != null) {
							if(inv_ser.get().getInstance().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								inventory_Servers.setId(Long.parseLong(id));
								Inventory_Servers result = inventory_Servers_Service.updateInventory_Servers(inventory_Servers);
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
									api_logs.setElement(30);
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
	public ResponseEntity<Object> deleteInventory_Servers(@PathVariable("id") String id){
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}
		else {
			try {
				Optional<Inventory_Servers> inv_ser = inventory_Servers_Service.findInventory_ServersById(Long.parseLong(id));
				if(inv_ser.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else {
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						if(inv_ser.get().getInstance().getAccount() != null) {
							if(inv_ser.get().getInstance().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								String result = inventory_Servers_Service.deleteInventory_Servers(Long.parseLong(id));
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
									api_logs.setElement(30);
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
						if(inv_ser.get().getInstance().getAccount() != null) {
							if(inv_ser.get().getInstance().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								String result = inventory_Servers_Service.deleteInventory_Servers(Long.parseLong(id));
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
									api_logs.setElement(30);
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

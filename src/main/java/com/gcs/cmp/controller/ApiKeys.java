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
import com.gcs.cmp.entity.Core_Api_Logs;
import com.gcs.cmp.interceptors.BasicAuthInterceptor;
import com.gcs.cmp.repository.Api_Keys_Repo;
import com.gcs.cmp.repository.Core_Accounts_Repo;
import com.gcs.cmp.repository.Core_Api_Logs_Repo;
import com.gcs.cmp.service.Api_Keys_Service;
import com.gcs.cmp.validators.Inputs_Validations;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/apikeys")
public class ApiKeys {

	@Autowired
	private Api_Keys_Service api_Keys_Service;
	
	@Autowired
	private Core_Api_Logs_Repo core_Api_Logs_Repo;
	
	@Autowired
	private Api_Keys_Repo api_Keys_Repo;
	
	@Autowired
	private Core_Accounts_Repo core_Accounts_Repo;
	
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
	    
	//@Operation(security = @SecurityRequirement(name = "Basic Auth"))
	@GetMapping("")
	public ResponseEntity<Object> getApi_KeysList(){	
		try {
			if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
				List <Api_Keys> result = api_Keys_Service.getApi_KeysList(Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT));	
				return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
			}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null){
				List <Api_Keys> result = api_Keys_Service.getApi_KeysList(Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT));	
				return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
			}else {
				return ResponseHandler.ResponseListKo("Error... Account NOT Identified!", 0, HttpStatus.FORBIDDEN, null);
			}
			
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@PostMapping("/create")
	public ResponseEntity<Object> addApi_Keys(@Validated @RequestBody Api_Keys api_Keys,Errors errors){
		if(ex != null) {
			handleException(ex);
		}
		if(errors.hasErrors()) {
            return ResponseHandler.ResponseBadRequest(errors.getFieldError().getField()+" "+errors.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST, null);
		}else {
			try {
				if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
					api_Keys.setStart_date(LocalDateTime.now());
					api_Keys.setAccount(core_Accounts_Repo.findById(Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)).get());
					Api_Keys result = api_Keys_Service.addApi_Keys(api_Keys);	
					if(result != null) {
						// create logs 
						// backup_executions:1,backup_instances:2,backup_operations:3,backup_settings:4,cloud_providers_account:5,core_access_credentials:6,core_accounts_modules:7,core_accounts:8,core_categories_elements:9,core_categories:10,
						//core_logs:11,core_notes:12,core_notifications:13,core_urls:14,core_users_instances:15,core_users_metrics:16,core_users_modules:17,core_users_permissions:18,core_users_security:19,core_users_tokens:20,core_users:21,
						//Inventory_applications_dependencies:22,Inventory_applications_instances:23,Inventory_applications_sources:24,Inventory_applications_versions:25,Inventory_applications:26,Inventory_hosts:27,Inventory_instances:28,
						//Inventory_servers_versions:29,Inventory_servers:30,Inventory_templates_apps:31,Inventory_templates:32,metrics:33,modules:34,monitoring_automations:35,monitoring_commands_executions:36,monitoring_commands:37,monitoring_metrics:38
						//monitoring_policies_alerts:39,monitoring_policies_servers:40,monitoring_policies:41,monitoring_settings:42,networks_domain_names:43,networks_hosts:44,networks_ssl_certificates:45,providers:46,app_api_key:47,api_key:48,api_keys_permissions:49
						Core_Api_Logs api_logs = new Core_Api_Logs();
						
						Api_Keys api_key = api_Keys_Repo.findById(Long.parseLong(BasicAuthInterceptor.GLOBAL_APIKEY)).get();
						
						api_logs.setAction("create");
						api_logs.setApikey(api_key);
						api_logs.setElement(48);
						api_logs.setElement_id((int)result.getId());
						api_logs.setLog_date(LocalDateTime.now());
						core_Api_Logs_Repo.save(api_logs);
						
				        return ResponseHandler.ResponseOk("Successfully added data!", HttpStatus.OK, result);
				        
					}else {
				        return ResponseHandler.ResponseBadRequest("Your request was malformed!", HttpStatus.BAD_REQUEST, null);
					}
				}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
					api_Keys.setStart_date(LocalDateTime.now());
					api_Keys.setAccount(core_Accounts_Repo.findById(Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)).get());
					Api_Keys result = api_Keys_Service.addApi_Keys(api_Keys);	
					
					if(result != null) {
						// create logs 
						// backup_executions:1,backup_instances:2,backup_operations:3,backup_settings:4,cloud_providers_account:5,core_access_credentials:6,core_accounts_modules:7,core_accounts:8,core_categories_elements:9,core_categories:10,
						//core_logs:11,core_notes:12,core_notifications:13,core_urls:14,core_users_instances:15,core_users_metrics:16,core_users_modules:17,core_users_permissions:18,core_users_security:19,core_users_tokens:20,core_users:21,
						//Inventory_applications_dependencies:22,Inventory_applications_instances:23,Inventory_applications_sources:24,Inventory_applications_versions:25,Inventory_applications:26,Inventory_hosts:27,Inventory_instances:28,
						//Inventory_servers_versions:29,Inventory_servers:30,Inventory_templates_apps:31,Inventory_templates:32,metrics:33,modules:34,monitoring_automations:35,monitoring_commands_executions:36,monitoring_commands:37,monitoring_metrics:38
						//monitoring_policies_alerts:39,monitoring_policies_servers:40,monitoring_policies:41,monitoring_settings:42,networks_domain_names:43,networks_hosts:44,networks_ssl_certificates:45,providers:46,app_api_key:47,api_key:48,api_keys_permissions:49
						Core_Api_Logs api_logs = new Core_Api_Logs();
						
						Api_Keys api_key = api_Keys_Repo.findById(Long.parseLong(BasicAuthInterceptor.GLOBAL_APIKEY)).get();
						
						api_logs.setAction("create");
						api_logs.setApikey(api_key);
						api_logs.setElement(48);
						api_logs.setElement_id((int)result.getId());
						api_logs.setLog_date(LocalDateTime.now());
						core_Api_Logs_Repo.save(api_logs);
						
				        return ResponseHandler.ResponseOk("Successfully added data!", HttpStatus.OK, result);
				        
					}else {
				        return ResponseHandler.ResponseBadRequest("Your request was malformed!", HttpStatus.BAD_REQUEST, null);
					}
				}else {
			        return ResponseHandler.ResponseForbidden("Error... Account NOT Identified!", HttpStatus.FORBIDDEN, null);
				}


			}catch (Exception e) {
	            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }
		}
		
	}
    
	@GetMapping("/get/{id}")
	public ResponseEntity<Object> getApiKeysById(@PathVariable(name="id") String id){	
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
				Optional<Api_Keys> result = api_Keys_Service.findApi_KeysById(Long.parseLong(id));
				if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
					if(result.equals(Optional.empty())) {
			        	return ResponseHandler.ResponseNotFound("No results were found with id "+id, HttpStatus.NOT_FOUND, null);
					}
					if(result.get().getStatus() == 4 || result.get().getEnd_date() != null) {
			        	return ResponseHandler.ResponseForbidden("Resources has been deleted!", HttpStatus.FORBIDDEN, null);
					}
					if( !result.equals(Optional.empty()) && result.get().getAccount() != null) {
						if(result.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
				        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
						}else {
				        	return ResponseHandler.ResponseOk("Successfully retrieved data!", HttpStatus.OK, result);
						}
					}else {
			        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
					}
					
				}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null){
					if(result.equals(Optional.empty())) {
			        	return ResponseHandler.ResponseNotFound("No results were found with id "+id, HttpStatus.NOT_FOUND, null);
					}
					if(result.get().getStatus() == 4) {
			        	return ResponseHandler.ResponseForbidden("Resources has been deleted!", HttpStatus.FORBIDDEN, null);
					}
					if( !result.equals(Optional.empty()) && result.get().getAccount() != null) {
						if(result.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
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
				
			}catch(Exception e) {
				return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
			}
		}
	}
	
	@GetMapping("/account/get/{account_id}")
	public ResponseEntity<Object> getOneApiKey(@PathVariable(name="account_id") String account_id){	
		if(Inputs_Validations.CheckInt(account_id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
				Optional<Api_Keys> result = api_Keys_Service.getOneApiKey(Long.parseLong(account_id));
					if(result.equals(Optional.empty())) {
			        	return ResponseHandler.ResponseNotFound("No results were found with id "+account_id, HttpStatus.NOT_FOUND, null);
					}else {
			        	return ResponseHandler.ResponseOk("Successfully retrieved data!", HttpStatus.OK, result);
					}
				
			}catch(Exception e) {
				return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
			}
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updatApi_Keys(@RequestBody Api_Keys Api_Keys,@PathVariable(name="id") String id ,Errors errors){	
		if(ex != null) {
			handleException(ex);
		}
		if(errors.hasErrors()) {
            return ResponseHandler.ResponseBadRequest(errors.getFieldError().getField()+" "+errors.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST, null);
		}if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}
		else {
			try {
				Optional<Api_Keys> apikey = api_Keys_Service.findApi_KeysById(Long.parseLong(id));

				if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
					if(apikey.equals(Optional.empty())) {
						return ResponseHandler.ResponseNotFound("No results were found with id "+id, HttpStatus.NOT_FOUND, null);
					}else {
						if(apikey.get().getStatus() == 4 || apikey.get().getEnd_date() != null ) {
				        	return ResponseHandler.ResponseForbidden("Resources has been deleted! We were unable to perform the request", HttpStatus.FORBIDDEN, null);
						}
						if(apikey.get().getAccount() != null){
							if(apikey.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								
						 		Api_Keys.setAccount(apikey.get().getAccount());
						 		Api_Keys.setStart_date(apikey.get().getStart_date());

								DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"); 
								
								if(Api_Keys.getEnd_date() == null) {
									Api_Keys.setEnd_date(apikey.get().getEnd_date());
								}
								if(Api_Keys.getStatus() == 0) {
									Api_Keys.setStatus(apikey.get().getStatus());
								}
								if(Api_Keys.getKey_value() == null) {
									Api_Keys.setKey_value(apikey.get().getKey_value());
								}
								if(Api_Keys.getType() == 0) {
									Api_Keys.setType(apikey.get().getType());
								}

								if(apikey.get().getAccount() == Api_Keys.getAccount() && apikey.get().getStart_date().toString().equals(Api_Keys.getStart_date().format(formatter)) && Api_Keys.getEnd_date() == null &&
										apikey.get().getStatus() == Api_Keys.getStatus() && apikey.get().getKey_value().equals(Api_Keys.getKey_value()) && apikey.get().getType() == Api_Keys.getType()) {
						        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
								}
								Api_Keys.setId(Long.parseLong(id));
								Api_Keys result = api_Keys_Service.updateApi_Keys(Api_Keys);
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
									api_logs.setElement(48);
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
					}
						
				}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
					if(apikey.equals(Optional.empty())) {
						return ResponseHandler.ResponseNotFound("No results were found with id "+id, HttpStatus.NOT_FOUND, null);
					}else {
						if(apikey.get().getStatus() == 4 || apikey.get().getEnd_date() != null) {
				        	return ResponseHandler.ResponseForbidden("Resources has been deleted! We were unable to perform the request", HttpStatus.FORBIDDEN, null);
						}
						 if(apikey.get().getAccount() != null){
							 if(apikey.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
						        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							 }else {

							 		Api_Keys.setAccount(apikey.get().getAccount());
							 		Api_Keys.setStart_date(apikey.get().getStart_date());

									DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"); 
									
									if(Api_Keys.getEnd_date() == null) {
										Api_Keys.setEnd_date(apikey.get().getEnd_date());
									}
									if(Api_Keys.getStatus() == 0) {
										Api_Keys.setStatus(apikey.get().getStatus());
									}
									if(Api_Keys.getKey_value() == null) {
										Api_Keys.setKey_value(apikey.get().getKey_value());
									}
									if(Api_Keys.getType() == 0) {
										Api_Keys.setType(apikey.get().getType());
									}

									if(apikey.get().getAccount() == Api_Keys.getAccount() && apikey.get().getStart_date().toString().equals(Api_Keys.getStart_date().format(formatter)) && Api_Keys.getEnd_date() == null &&
											apikey.get().getStatus() == Api_Keys.getStatus() && apikey.get().getKey_value().equals(Api_Keys.getKey_value()) && apikey.get().getType() == Api_Keys.getType()) {
							        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
									}
									Api_Keys.setId(Long.parseLong(id));
									Api_Keys result = api_Keys_Service.updateApi_Keys(Api_Keys);
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
										api_logs.setElement(48);
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
						}
					}else {
				        return ResponseHandler.ResponseForbidden("Error... Account NOT Identified!", HttpStatus.FORBIDDEN, null);
					}
					
			}catch (Exception e) {
	            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }
		}
		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteApi_Keys(@PathVariable("id") String id){
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
				Optional<Api_Keys> apikey = api_Keys_Service.findApi_KeysById(Long.parseLong(id));
				if(apikey.equals(Optional.empty())) {
					return ResponseHandler.ResponseNotFound("No results were found with id "+id, HttpStatus.NOT_FOUND, null);
				}else {
					if(apikey.get().getStatus() == 4 || apikey.get().getEnd_date() != null) {
			        	return ResponseHandler.ResponseForbidden("Resources already deleted! We were unable to perform the request", HttpStatus.FORBIDDEN, null);
					}
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						if(apikey.get().getAccount() != null) {
							if(apikey.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								String result = api_Keys_Service.deleteApi_Keys(Long.parseLong(id));
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
									api_logs.setElement(48);
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
						if(apikey.get().getAccount() != null) {
							if(apikey.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!!!!!", HttpStatus.FORBIDDEN, null);
							}else {
								String result = api_Keys_Service.deleteApi_Keys(Long.parseLong(id));
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
									api_logs.setElement(48);
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

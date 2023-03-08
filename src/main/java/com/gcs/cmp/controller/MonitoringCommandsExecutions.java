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
import com.gcs.cmp.entity.Inventory_Instances;
import com.gcs.cmp.entity.Monitoring_Commands;
import com.gcs.cmp.entity.Monitoring_Commands_Executions;
import com.gcs.cmp.interceptors.BasicAuthInterceptor;
import com.gcs.cmp.repository.Api_Keys_Repo;
import com.gcs.cmp.repository.Core_Api_Logs_Repo;
import com.gcs.cmp.repository.Inventory_Instances_Repo;
import com.gcs.cmp.repository.Monitoring_Commands_Repo;
import com.gcs.cmp.service.Monitoring_Commands_Executions_Service;
import com.gcs.cmp.validators.Inputs_Validations;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Commands executions", description = "Manage monitoring commands executions")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/monitoring/commands/executions")
public class MonitoringCommandsExecutions {

	@Autowired
	private Monitoring_Commands_Executions_Service monitoring_Commands_Executions_Service;
	
	@Autowired
	private Core_Api_Logs_Repo core_Api_Logs_Repo;
	
	@Autowired
	private Api_Keys_Repo api_Keys_Repo;
	
	@Autowired
	private Monitoring_Commands_Repo monitoring_Commands_Repo;
	
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
	public ResponseEntity<Object> getAllMonitoring_Commands_Executions(){	
		try {
			if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
				List <Monitoring_Commands_Executions> result = monitoring_Commands_Executions_Service.getMonitoring_Commands_ExecutionsList(Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT));	
				return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
			}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
				List <Monitoring_Commands_Executions> result = monitoring_Commands_Executions_Service.getMonitoring_Commands_ExecutionsList(Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT));	
				return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
			}else {
				return ResponseHandler.ResponseListKo("Error... Account NOT Identified!", 0, HttpStatus.FORBIDDEN, null);
			}
			
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@PostMapping("/create")
	public ResponseEntity<Object> addMonitoring_Commands_Executions(@Validated @RequestBody Monitoring_Commands_Executions monitoring_Commands_Executions,Errors errors){	
		if(ex != null) {
			handleException(ex);
		}
		if(errors.hasErrors()) {
            return ResponseHandler.ResponseBadRequest(errors.getFieldError().getField()+" "+errors.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST, null);
		}else {
			try {
				if(monitoring_Commands_Executions.getCommand() == null || monitoring_Commands_Executions.getInstance() == null) {
			        return ResponseHandler.ResponseBadRequest("Your request was malformed! Foreign key required!", HttpStatus.BAD_REQUEST, null);
				}else {
					if(monitoring_Commands_Executions.getCommand().getId() == 0 || monitoring_Commands_Executions.getInstance().getId() == 0) {
				        return ResponseHandler.ResponseBadRequest("Your request was malformed... ID must not be null", HttpStatus.BAD_REQUEST, null);
					}
					Optional<Monitoring_Commands> command = monitoring_Commands_Repo.findById(monitoring_Commands_Executions.getCommand().getId());
					Optional<Inventory_Instances> inv_ins = inventory_Instances_Repo.findById(monitoring_Commands_Executions.getInstance().getId());
					if(command.equals(Optional.empty()) || inv_ins.equals(Optional.empty())) {
				        return ResponseHandler.ResponseBadRequest("Your request was malformed! Foreign key required!", HttpStatus.BAD_REQUEST, null);
					}else {
						if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
							if(command.get().getAccount() != null && inv_ins.get().getAccount() != null) {
								if(command.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT) || inv_ins.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
						        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
								}else {
									monitoring_Commands_Executions.setStart_date(LocalDateTime.now());
									monitoring_Commands_Executions.setCommand(command.get());
									monitoring_Commands_Executions.setInstance(inv_ins.get());
									Monitoring_Commands_Executions result = monitoring_Commands_Executions_Service.addMonitoring_Commands_Executions(monitoring_Commands_Executions);	
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
										api_logs.setElement(36);
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
							if(command.get().getAccount() != null && inv_ins.get().getAccount() != null) {
								if(command.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT) || inv_ins.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
						        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
								}else {
									monitoring_Commands_Executions.setStart_date(LocalDateTime.now());
									monitoring_Commands_Executions.setCommand(command.get());
									monitoring_Commands_Executions.setInstance(inv_ins.get());
									Monitoring_Commands_Executions result = monitoring_Commands_Executions_Service.addMonitoring_Commands_Executions(monitoring_Commands_Executions);	
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
										api_logs.setElement(36);
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
	public ResponseEntity<Object> getMonitoring_Commands_ExecutionsById(@PathVariable(name="id") String id){
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
				Optional<Monitoring_Commands_Executions> result = monitoring_Commands_Executions_Service.findMonitoring_Commands_ExecutionsById(Long.parseLong(id));
				if(result.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else {
					if(result.get().getStatus() == 4 || result.get().getEnd_date() != null) {
			        	return ResponseHandler.ResponseForbidden("Resources has been deleted!", HttpStatus.FORBIDDEN, null);
					}
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						if(result.get().getCommand().getAccount() != null && result.get().getInstance().getAccount() != null) {
							if(result.get().getCommand().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT) || result.get().getInstance().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
						        return ResponseHandler.ResponseOk("Successfully retrieved data!", HttpStatus.OK, result);
							}
						}else {
				        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
						}
					}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
						if(result.get().getCommand().getAccount() != null && result.get().getInstance().getAccount() != null) {
							if(result.get().getCommand().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT) || result.get().getInstance().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
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
	public ResponseEntity<Object> updateMonitoring_Commands_Executions(@RequestBody Monitoring_Commands_Executions monitoring_Commands_Executions,@PathVariable(name="id") String id, Errors errors){
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
				Optional<Monitoring_Commands_Executions> monitoring_com_exec = monitoring_Commands_Executions_Service.findMonitoring_Commands_ExecutionsById(Long.parseLong(id));
				if(monitoring_com_exec.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else if(monitoring_com_exec.get().getStatus() == 4 || monitoring_com_exec.get().getEnd_date() != null) {
		        	return ResponseHandler.ResponseForbidden("Resources has been deleted! We were unable to perform the request", HttpStatus.FORBIDDEN, null);
				}else if(monitoring_com_exec.get().getCommand() == null || monitoring_com_exec.get().getInstance() == null) {
			        return ResponseHandler.ResponseBadRequest("Your request was malformed! Foreign key required!", HttpStatus.BAD_REQUEST, null);
				}else {
	
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"); 

					monitoring_Commands_Executions.setCommand(monitoring_com_exec.get().getCommand());
					monitoring_Commands_Executions.setInstance(monitoring_com_exec.get().getInstance());
					monitoring_Commands_Executions.setStart_date(monitoring_com_exec.get().getStart_date());
					
					if(monitoring_Commands_Executions.getEnd_date() == null) {
						monitoring_Commands_Executions.setEnd_date(monitoring_com_exec.get().getEnd_date());
					}
					if(monitoring_Commands_Executions.getStatus() == 0) {
						monitoring_Commands_Executions.setStatus(monitoring_com_exec.get().getStatus());
					}
				
					if(monitoring_com_exec.get().getCommand() == monitoring_Commands_Executions.getCommand() && monitoring_com_exec.get().getInstance() == monitoring_Commands_Executions.getInstance() && monitoring_com_exec.get().getStart_date().toString().equals(monitoring_Commands_Executions.getStart_date().format(formatter)) && 
							monitoring_Commands_Executions.getEnd_date() == null && monitoring_com_exec.get().getStatus() == monitoring_Commands_Executions.getStatus()) {
			        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
					}

					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						if(monitoring_com_exec.get().getCommand().getAccount() != null && monitoring_com_exec.get().getInstance().getAccount() != null) {
							if(monitoring_com_exec.get().getCommand().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT) && monitoring_com_exec.get().getInstance().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								monitoring_Commands_Executions.setId(Long.parseLong(id));
								Monitoring_Commands_Executions result = monitoring_Commands_Executions_Service.updateMonitoring_Commands_Executions(monitoring_Commands_Executions);
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
									api_logs.setElement(36);
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
						if(monitoring_com_exec.get().getCommand().getAccount() != null && monitoring_com_exec.get().getInstance().getAccount() != null) {
							if(monitoring_com_exec.get().getCommand().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT) && monitoring_com_exec.get().getInstance().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								monitoring_Commands_Executions.setId(Long.parseLong(id));
								Monitoring_Commands_Executions result = monitoring_Commands_Executions_Service.updateMonitoring_Commands_Executions(monitoring_Commands_Executions);
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
									api_logs.setElement(36);
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
	public ResponseEntity<Object> deleteMonitoring_Commands_Executions(@PathVariable("id") String id){
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}
		else {
			try {
				Optional<Monitoring_Commands_Executions> monitoring_com_exec = monitoring_Commands_Executions_Service.findMonitoring_Commands_ExecutionsById(Long.parseLong(id));
				if(monitoring_com_exec.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else {
					if(monitoring_com_exec.get().getStatus() == 4 || monitoring_com_exec.get().getEnd_date() != null) {
			        	return ResponseHandler.ResponseForbidden("Resources already deleted! We were unable to perform the request", HttpStatus.FORBIDDEN, null);
					}
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						if(monitoring_com_exec.get().getCommand().getAccount() != null && monitoring_com_exec.get().getInstance().getAccount() != null) {
							if(monitoring_com_exec.get().getCommand().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT) && monitoring_com_exec.get().getInstance().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								String result = monitoring_Commands_Executions_Service.deleteMonitoring_Commands_Executions(Long.parseLong(id));
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
									api_logs.setElement(36);
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
						if(monitoring_com_exec.get().getCommand().getAccount() != null && monitoring_com_exec.get().getInstance().getAccount() != null) {
							if(monitoring_com_exec.get().getCommand().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT) && monitoring_com_exec.get().getInstance().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								String result = monitoring_Commands_Executions_Service.deleteMonitoring_Commands_Executions(Long.parseLong(id));
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
									api_logs.setElement(36);
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

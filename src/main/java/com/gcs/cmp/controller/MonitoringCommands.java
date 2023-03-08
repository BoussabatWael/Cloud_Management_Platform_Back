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
import com.gcs.cmp.entity.Core_Accounts;
import com.gcs.cmp.entity.Core_Api_Logs;

import com.gcs.cmp.entity.Monitoring_Commands;
import com.gcs.cmp.interceptors.BasicAuthInterceptor;
import com.gcs.cmp.repository.Api_Keys_Repo;
import com.gcs.cmp.repository.Core_Accounts_Repo;
import com.gcs.cmp.repository.Core_Api_Logs_Repo;
import com.gcs.cmp.service.Monitoring_Commands_Service;
import com.gcs.cmp.validators.Inputs_Validations;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Commands", description = "Manage monitoring commands")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/monitoring/commands")
public class MonitoringCommands {

	@Autowired
	private Monitoring_Commands_Service monitoring_Commands_Service;
	
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
	public ResponseEntity<Object> getAllMonitoring_Commands(){	
		try {
			if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
				List <Monitoring_Commands> result = monitoring_Commands_Service.getMonitoring_CommandsList(Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT));	
				return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
			}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
				List <Monitoring_Commands> result = monitoring_Commands_Service.getMonitoring_CommandsList(Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT));	
				return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
			}else {
				return ResponseHandler.ResponseListKo("Error... Account NOT Identified!", 0, HttpStatus.FORBIDDEN, null);
			}
			
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@PostMapping("/create")
	public ResponseEntity<Object> addMonitoring_Commands(@Validated @RequestBody Monitoring_Commands monitoring_Commands,Errors errors){
		if(ex != null) {
			handleException(ex);
		}
		if(errors.hasErrors()) {
            return ResponseHandler.ResponseBadRequest(errors.getFieldError().getField()+" "+errors.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST, null);
		}else {
			try {
				if(monitoring_Commands.getAccount() == null) {
			        return ResponseHandler.ResponseBadRequest("Your request was malformed! Foreign key required!", HttpStatus.BAD_REQUEST, null);
				}else {
					if(monitoring_Commands.getAccount().getId() == 0) {
				        return ResponseHandler.ResponseBadRequest("Your request was malformed... ID must not be null", HttpStatus.BAD_REQUEST, null);
					}
					Optional<Core_Accounts> account = core_Accounts_Repo.findById(monitoring_Commands.getAccount().getId());
					if(account.equals(Optional.empty())) {
				        return ResponseHandler.ResponseBadRequest("Your request was malformed! Foreign key required!", HttpStatus.BAD_REQUEST, null);
					}else {
						if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
							if(account.get().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								monitoring_Commands.setStart_date(LocalDateTime.now());
								monitoring_Commands.setAccount(account.get());
								Monitoring_Commands result = monitoring_Commands_Service.addMonitoring_Commands(monitoring_Commands);
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
									api_logs.setElement(37);
									api_logs.setElement_id((int)result.getId());
									api_logs.setLog_date(LocalDateTime.now());
									core_Api_Logs_Repo.save(api_logs);
									
							        return ResponseHandler.ResponseOk("Successfully added data!", HttpStatus.OK, result);
								}else {
							        return ResponseHandler.ResponseBadRequest("Your request was malformed!", HttpStatus.BAD_REQUEST, null);
								}
							}
						}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
							if(account.get().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								monitoring_Commands.setStart_date(LocalDateTime.now());
								monitoring_Commands.setAccount(account.get());
								Monitoring_Commands result = monitoring_Commands_Service.addMonitoring_Commands(monitoring_Commands);
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
									api_logs.setElement(37);
									api_logs.setElement_id((int)result.getId());
									api_logs.setLog_date(LocalDateTime.now());
									core_Api_Logs_Repo.save(api_logs);
									
							        return ResponseHandler.ResponseOk("Successfully added data!", HttpStatus.OK, result);
								}else {
							        return ResponseHandler.ResponseBadRequest("Your request was malformed!", HttpStatus.BAD_REQUEST, null);
								}
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
	public ResponseEntity<Object> getMonitoring_CommandsById(@PathVariable(name="id") String id){	
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
				Optional<Monitoring_Commands> result = monitoring_Commands_Service.findMonitoring_CommandsById(Long.parseLong(id));
				if(result.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else {
					if(result.get().getStatus() == 4 || result.get().getEnd_date() != null) {
			        	return ResponseHandler.ResponseForbidden("Resources has been deleted!", HttpStatus.FORBIDDEN, null);
					}
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						if(result.get().getAccount() != null) {
							if(result.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
						        return ResponseHandler.ResponseOk("Successfully retrieved data!", HttpStatus.OK, result);
							}
						}else {
				        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
						}
					}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
						if(result.get().getAccount() != null) {
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
				}
			}catch(Exception e) {
	            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updateMonitoring_Commands(@RequestBody Monitoring_Commands monitoring_Commands,@PathVariable(name="id") String id, Errors errors){
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
				Optional<Monitoring_Commands> monitoring_command = monitoring_Commands_Service.findMonitoring_CommandsById(Long.parseLong(id));
				if(monitoring_command.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else if(monitoring_command.get().getStatus() == 4 || monitoring_command.get().getEnd_date() != null) {
		        	return ResponseHandler.ResponseForbidden("Resources has been deleted! We were unable to perform the request", HttpStatus.FORBIDDEN, null);
				}else if(monitoring_command.get().getAccount() == null) {
			        return ResponseHandler.ResponseBadRequest("Your request was malformed! Foreign key required!", HttpStatus.BAD_REQUEST, null);
				}else {
					
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"); 

					monitoring_Commands.setAccount(monitoring_command.get().getAccount());
					monitoring_Commands.setStart_date(monitoring_command.get().getStart_date());
					
					if(monitoring_Commands.getEnd_date() == null) {
						monitoring_Commands.setEnd_date(monitoring_command.get().getEnd_date());
					}
					if(monitoring_Commands.getStatus() == 0) {
						monitoring_Commands.setStatus(monitoring_command.get().getStatus());
					}
					if(monitoring_Commands.getClasse() == 0) {
						monitoring_Commands.setClasse(monitoring_command.get().getClasse());
					}
					if(monitoring_Commands.getCommand() == null) {
						monitoring_Commands.setCommand(monitoring_command.get().getCommand());
					}
					if(monitoring_Commands.getEvent() == 0) {
						monitoring_Commands.setEvent(monitoring_command.get().getEvent());
					}
					if(monitoring_Commands.getFile_command() == null) {
						monitoring_Commands.setFile_command(monitoring_command.get().getFile_command());
					}
					if(monitoring_Commands.getName() == null) {
						monitoring_Commands.setName(monitoring_command.get().getName());
					}
					if(monitoring_Commands.getSystem() == 0) {
						monitoring_Commands.setSystem(monitoring_command.get().getSystem());
					}
					if(monitoring_Commands.getUser() == null) {
						monitoring_Commands.setUser(monitoring_command.get().getUser());
					}
					if(monitoring_command.get().getCommand() == null) {
						if(monitoring_command.get().getFile_command() != null && monitoring_command.get().getUser() != null) {
							if(monitoring_command.get().getAccount() == monitoring_Commands.getAccount() && monitoring_command.get().getStart_date().toString().equals(monitoring_Commands.getStart_date().format(formatter)) && monitoring_Commands.getEnd_date() == null &&
									monitoring_command.get().getStatus() == monitoring_Commands.getStatus() && monitoring_command.get().getClasse() == monitoring_Commands.getClasse() && monitoring_command.get().getCommand() == monitoring_Commands.getCommand() && 
									monitoring_command.get().getEvent() == monitoring_Commands.getEvent() && monitoring_command.get().getFile_command().equals(monitoring_Commands.getFile_command()) && monitoring_command.get().getName().equals(monitoring_Commands.getName()) && 
									monitoring_command.get().getSystem() == monitoring_Commands.getSystem() && monitoring_command.get().getUser().equals(monitoring_Commands.getUser())) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						}else if(monitoring_command.get().getFile_command() == null && monitoring_command.get().getUser() == null) {
							if(monitoring_command.get().getAccount() == monitoring_Commands.getAccount() && monitoring_command.get().getStart_date().toString().equals(monitoring_Commands.getStart_date().format(formatter)) && monitoring_Commands.getEnd_date() == null &&
									monitoring_command.get().getStatus() == monitoring_Commands.getStatus() && monitoring_command.get().getClasse() == monitoring_Commands.getClasse() && monitoring_command.get().getCommand() == monitoring_Commands.getCommand() && 
									monitoring_command.get().getEvent() == monitoring_Commands.getEvent() && monitoring_command.get().getFile_command() == monitoring_Commands.getFile_command() && monitoring_command.get().getName().equals(monitoring_Commands.getName()) && 
									monitoring_command.get().getSystem() == monitoring_Commands.getSystem() && monitoring_command.get().getUser() == monitoring_Commands.getUser()) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						}else if(monitoring_command.get().getFile_command() != null && monitoring_command.get().getUser() == null) {
							if(monitoring_command.get().getAccount() == monitoring_Commands.getAccount() && monitoring_command.get().getStart_date().toString().equals(monitoring_Commands.getStart_date().format(formatter)) && monitoring_Commands.getEnd_date() == null &&
									monitoring_command.get().getStatus() == monitoring_Commands.getStatus() && monitoring_command.get().getClasse() == monitoring_Commands.getClasse() && monitoring_command.get().getCommand() == monitoring_Commands.getCommand() && 
									monitoring_command.get().getEvent() == monitoring_Commands.getEvent() && monitoring_command.get().getFile_command().equals(monitoring_Commands.getFile_command()) && monitoring_command.get().getName().equals(monitoring_Commands.getName()) && 
									monitoring_command.get().getSystem() == monitoring_Commands.getSystem() && monitoring_command.get().getUser() == monitoring_Commands.getUser()) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						}else if(monitoring_command.get().getFile_command() == null && monitoring_command.get().getUser() != null) {
							if(monitoring_command.get().getAccount() == monitoring_Commands.getAccount() && monitoring_command.get().getStart_date().toString().equals(monitoring_Commands.getStart_date().format(formatter)) && monitoring_Commands.getEnd_date() == null &&
									monitoring_command.get().getStatus() == monitoring_Commands.getStatus() && monitoring_command.get().getClasse() == monitoring_Commands.getClasse() && monitoring_command.get().getCommand() == monitoring_Commands.getCommand() && 
									monitoring_command.get().getEvent() == monitoring_Commands.getEvent() && monitoring_command.get().getFile_command() == monitoring_Commands.getFile_command() && monitoring_command.get().getName().equals(monitoring_Commands.getName()) && 
									monitoring_command.get().getSystem() == monitoring_Commands.getSystem() && monitoring_command.get().getUser().equals(monitoring_Commands.getUser())) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						}
						
						
					}else if(monitoring_command.get().getFile_command() == null) {
						if(monitoring_command.get().getCommand() != null && monitoring_command.get().getUser() != null) {
							if(monitoring_command.get().getAccount() == monitoring_Commands.getAccount() && monitoring_command.get().getStart_date().toString().equals(monitoring_Commands.getStart_date().format(formatter)) && monitoring_Commands.getEnd_date() == null &&
									monitoring_command.get().getStatus() == monitoring_Commands.getStatus() && monitoring_command.get().getClasse() == monitoring_Commands.getClasse() && monitoring_command.get().getCommand().equals(monitoring_Commands.getCommand()) && 
									monitoring_command.get().getEvent() == monitoring_Commands.getEvent() && monitoring_command.get().getFile_command() == monitoring_Commands.getFile_command() && monitoring_command.get().getName().equals(monitoring_Commands.getName()) && 
									monitoring_command.get().getSystem() == monitoring_Commands.getSystem() && monitoring_command.get().getUser().equals(monitoring_Commands.getUser())) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						}else if(monitoring_command.get().getCommand() == null && monitoring_command.get().getUser() == null) {
							if(monitoring_command.get().getAccount() == monitoring_Commands.getAccount() && monitoring_command.get().getStart_date().toString().equals(monitoring_Commands.getStart_date().format(formatter)) && monitoring_Commands.getEnd_date() == null &&
									monitoring_command.get().getStatus() == monitoring_Commands.getStatus() && monitoring_command.get().getClasse() == monitoring_Commands.getClasse() && monitoring_command.get().getCommand() == monitoring_Commands.getCommand() && 
									monitoring_command.get().getEvent() == monitoring_Commands.getEvent() && monitoring_command.get().getFile_command() == monitoring_Commands.getFile_command() && monitoring_command.get().getName().equals(monitoring_Commands.getName()) && 
									monitoring_command.get().getSystem() == monitoring_Commands.getSystem() && monitoring_command.get().getUser() == monitoring_Commands.getUser()) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						}else if(monitoring_command.get().getCommand() != null && monitoring_command.get().getUser() == null) {
							if(monitoring_command.get().getAccount() == monitoring_Commands.getAccount() && monitoring_command.get().getStart_date().toString().equals(monitoring_Commands.getStart_date().format(formatter)) && monitoring_Commands.getEnd_date() == null &&
									monitoring_command.get().getStatus() == monitoring_Commands.getStatus() && monitoring_command.get().getClasse() == monitoring_Commands.getClasse() && monitoring_command.get().getCommand().equals(monitoring_Commands.getCommand()) && 
									monitoring_command.get().getEvent() == monitoring_Commands.getEvent() && monitoring_command.get().getFile_command() == monitoring_Commands.getFile_command() && monitoring_command.get().getName().equals(monitoring_Commands.getName()) && 
									monitoring_command.get().getSystem() == monitoring_Commands.getSystem() && monitoring_command.get().getUser() == monitoring_Commands.getUser()) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						}else if(monitoring_command.get().getCommand() == null && monitoring_command.get().getUser() != null) {
							if(monitoring_command.get().getAccount() == monitoring_Commands.getAccount() && monitoring_command.get().getStart_date().toString().equals(monitoring_Commands.getStart_date().format(formatter)) && monitoring_Commands.getEnd_date() == null &&
									monitoring_command.get().getStatus() == monitoring_Commands.getStatus() && monitoring_command.get().getClasse() == monitoring_Commands.getClasse() && monitoring_command.get().getCommand() == monitoring_Commands.getCommand() && 
									monitoring_command.get().getEvent() == monitoring_Commands.getEvent() && monitoring_command.get().getFile_command() == monitoring_Commands.getFile_command() && monitoring_command.get().getName().equals(monitoring_Commands.getName()) && 
									monitoring_command.get().getSystem() == monitoring_Commands.getSystem() && monitoring_command.get().getUser().equals(monitoring_Commands.getUser())) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						}
						
						
					}else if(monitoring_command.get().getUser() == null) {
						if(monitoring_command.get().getCommand() != null && monitoring_command.get().getFile_command() != null) {
							if(monitoring_command.get().getAccount() == monitoring_Commands.getAccount() && monitoring_command.get().getStart_date().toString().equals(monitoring_Commands.getStart_date().format(formatter)) && monitoring_Commands.getEnd_date() == null &&
									monitoring_command.get().getStatus() == monitoring_Commands.getStatus() && monitoring_command.get().getClasse() == monitoring_Commands.getClasse() && monitoring_command.get().getCommand().equals(monitoring_Commands.getCommand()) && 
									monitoring_command.get().getEvent() == monitoring_Commands.getEvent() && monitoring_command.get().getFile_command().equals(monitoring_Commands.getFile_command()) && monitoring_command.get().getName().equals(monitoring_Commands.getName()) && 
									monitoring_command.get().getSystem() == monitoring_Commands.getSystem() && monitoring_command.get().getUser() == monitoring_Commands.getUser()) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						}else if(monitoring_command.get().getCommand() == null && monitoring_command.get().getFile_command() == null) {
							if(monitoring_command.get().getAccount() == monitoring_Commands.getAccount() && monitoring_command.get().getStart_date().toString().equals(monitoring_Commands.getStart_date().format(formatter)) && monitoring_Commands.getEnd_date() == null &&
									monitoring_command.get().getStatus() == monitoring_Commands.getStatus() && monitoring_command.get().getClasse() == monitoring_Commands.getClasse() && monitoring_command.get().getCommand() == monitoring_Commands.getCommand() && 
									monitoring_command.get().getEvent() == monitoring_Commands.getEvent() && monitoring_command.get().getFile_command() == monitoring_Commands.getFile_command() && monitoring_command.get().getName().equals(monitoring_Commands.getName()) && 
									monitoring_command.get().getSystem() == monitoring_Commands.getSystem() && monitoring_command.get().getUser() == monitoring_Commands.getUser()) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						}else if(monitoring_command.get().getCommand() != null && monitoring_command.get().getFile_command() == null) {
							if(monitoring_command.get().getAccount() == monitoring_Commands.getAccount() && monitoring_command.get().getStart_date().toString().equals(monitoring_Commands.getStart_date().format(formatter)) && monitoring_Commands.getEnd_date() == null &&
									monitoring_command.get().getStatus() == monitoring_Commands.getStatus() && monitoring_command.get().getClasse() == monitoring_Commands.getClasse() && monitoring_command.get().getCommand().equals(monitoring_Commands.getCommand()) && 
									monitoring_command.get().getEvent() == monitoring_Commands.getEvent() && monitoring_command.get().getFile_command() == monitoring_Commands.getFile_command() && monitoring_command.get().getName().equals(monitoring_Commands.getName()) && 
									monitoring_command.get().getSystem() == monitoring_Commands.getSystem() && monitoring_command.get().getUser() == monitoring_Commands.getUser()) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						}else if(monitoring_command.get().getCommand() == null && monitoring_command.get().getFile_command() != null) {
							if(monitoring_command.get().getAccount() == monitoring_Commands.getAccount() && monitoring_command.get().getStart_date().toString().equals(monitoring_Commands.getStart_date().format(formatter)) && monitoring_Commands.getEnd_date() == null &&
									monitoring_command.get().getStatus() == monitoring_Commands.getStatus() && monitoring_command.get().getClasse() == monitoring_Commands.getClasse() && monitoring_command.get().getCommand() == monitoring_Commands.getCommand() && 
									monitoring_command.get().getEvent() == monitoring_Commands.getEvent() && monitoring_command.get().getFile_command().equals(monitoring_Commands.getFile_command()) && monitoring_command.get().getName().equals(monitoring_Commands.getName()) && 
									monitoring_command.get().getSystem() == monitoring_Commands.getSystem() && monitoring_command.get().getUser() == monitoring_Commands.getUser()) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						}
					}else {
						if(monitoring_command.get().getAccount() == monitoring_Commands.getAccount() && monitoring_command.get().getStart_date().toString().equals(monitoring_Commands.getStart_date().format(formatter)) && monitoring_Commands.getEnd_date() == null &&
								monitoring_command.get().getStatus() == monitoring_Commands.getStatus() && monitoring_command.get().getClasse() == monitoring_Commands.getClasse() && monitoring_command.get().getCommand() == monitoring_Commands.getCommand() && 
								monitoring_command.get().getEvent() == monitoring_Commands.getEvent() && monitoring_command.get().getFile_command().equals(monitoring_Commands.getFile_command()) && monitoring_command.get().getName().equals(monitoring_Commands.getName()) && 
								monitoring_command.get().getSystem() == monitoring_Commands.getSystem() && monitoring_command.get().getUser() == monitoring_Commands.getUser()) {
			        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
					}
					}

					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						if(monitoring_command.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
				        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
						}else {
							monitoring_Commands.setId(Long.parseLong(id));
							Monitoring_Commands result = monitoring_Commands_Service.updateMonitoring_Commands(monitoring_Commands);
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
								api_logs.setElement(37);
								api_logs.setElement_id((int)result.getId());
								api_logs.setLog_date(LocalDateTime.now());
								core_Api_Logs_Repo.save(api_logs);
								
						        return ResponseHandler.ResponseOk("Updated", HttpStatus.OK, result);
							}else {
								return ResponseHandler.ResponseBadRequest("Your request was malformed!", HttpStatus.BAD_REQUEST, null);
							}
						}
					}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
						if(monitoring_command.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
				        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
						}else {
							monitoring_Commands.setId(Long.parseLong(id));
							Monitoring_Commands result = monitoring_Commands_Service.updateMonitoring_Commands(monitoring_Commands);
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
								api_logs.setElement(37);
								api_logs.setElement_id((int)result.getId());
								api_logs.setLog_date(LocalDateTime.now());
								core_Api_Logs_Repo.save(api_logs);
								
						        return ResponseHandler.ResponseOk("Updated", HttpStatus.OK, result);
							}else {
								return ResponseHandler.ResponseBadRequest("Your request was malformed!", HttpStatus.BAD_REQUEST, null);
							}
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
	public ResponseEntity<Object> deleteMonitoring_Commands(@PathVariable("id") String id){
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}
		else {
			try {
				Optional<Monitoring_Commands> monitoring_command = monitoring_Commands_Service.findMonitoring_CommandsById(Long.parseLong(id));
				if(monitoring_command.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else {
					if(monitoring_command.get().getStatus() == 4 || monitoring_command.get().getEnd_date() != null) {
			        	return ResponseHandler.ResponseForbidden("Resources already deleted! We were unable to perform the request", HttpStatus.FORBIDDEN, null);
					}
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						if(monitoring_command.get().getAccount() != null) {
							if(monitoring_command.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								String result = monitoring_Commands_Service.deleteMonitoring_Commands(Long.parseLong(id));
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
									api_logs.setElement(37);
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
						if(monitoring_command.get().getAccount() != null) {
							if(monitoring_command.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								String result = monitoring_Commands_Service.deleteMonitoring_Commands(Long.parseLong(id));
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
									api_logs.setElement(37);
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

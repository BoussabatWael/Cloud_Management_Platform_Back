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
import com.gcs.cmp.entity.Backup_Executions;
import com.gcs.cmp.entity.Backup_Operations;
import com.gcs.cmp.entity.Core_Api_Logs;
import com.gcs.cmp.interceptors.BasicAuthInterceptor;
import com.gcs.cmp.repository.Api_Keys_Repo;
import com.gcs.cmp.repository.Backup_Operations_Repo;
import com.gcs.cmp.repository.Core_Api_Logs_Repo;
import com.gcs.cmp.service.Backup_Executions_Service;
import com.gcs.cmp.validators.Inputs_Validations;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Backups executions", description = "Manage backups executions")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/backup/executions")
public class BackupExecutions {

	@Autowired
	private Backup_Executions_Service backup_Executions_Service;
	
	@Autowired
	private Core_Api_Logs_Repo core_Api_Logs_Repo;
	
	@Autowired
	private Api_Keys_Repo api_Keys_Repo;
	
	@Autowired
	private Backup_Operations_Repo backup_Operations_Repo;
	
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
	public ResponseEntity<Object> getAllBackup_Executions(){	
		try {
			if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
				List <Backup_Executions> result = backup_Executions_Service.getBackup_ExecutionsList(Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT));	
				return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
			}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
				List <Backup_Executions> result = backup_Executions_Service.getBackup_ExecutionsList(Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT));	
				return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
			}else {
				return ResponseHandler.ResponseListKo("Error... Account NOT Identified!", 0, HttpStatus.FORBIDDEN, null);
			}
			
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@PostMapping("/create")
	public ResponseEntity<Object> addBackup_Executions(@Validated @RequestBody Backup_Executions backup_Executions,Errors errors){
		if(ex != null) {
			handleException(ex);
		}
		if(errors.hasErrors()) {
            return ResponseHandler.ResponseBadRequest(errors.getFieldError().getField()+" "+errors.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST, null);
		}else {
			if(backup_Executions.getOperation() == null) {
		        return ResponseHandler.ResponseBadRequest("Your request was malformed! Foreign key required!", HttpStatus.BAD_REQUEST, null);
			}else {
				if(backup_Executions.getOperation().getId() == 0) {
			        return ResponseHandler.ResponseBadRequest("Your request was malformed... ID must not be null", HttpStatus.BAD_REQUEST, null);
				}
				Optional<Backup_Operations> operation = backup_Operations_Repo.findById(backup_Executions.getOperation().getId());
				if(operation.equals(Optional.empty())) {
			        return ResponseHandler.ResponseBadRequest("Your request was malformed! Foreign key required!", HttpStatus.BAD_REQUEST, null);
				}else {
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						if(operation.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
				        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
						}else {
							try {
								backup_Executions.setStart_date(LocalDateTime.now());
								backup_Executions.setOperation(operation.get());
								Backup_Executions result = backup_Executions_Service.addBackup_Executions(backup_Executions);	
								
								/*api logs 
								 * backup_executions:1,backup_instances:2,backup_operations:3,backup_settings:4,cloud_providers_account:5,core_access_credentials:6,core_accounts_modules:7,core_accounts:8,core_categories_elements:9,core_categories:10,
								 * core_logs:11,core_notes:12,core_notifications:13,core_urls:14,core_users_instances:15,core_users_metrics:16,core_users_modules:17,core_users_permissions:18,core_users_security:19,core_users_tokens:20,core_users:21,
								 * Inventory_applications_dependencies:22,Inventory_applications_instances:23,Inventory_applications_sources:24,Inventory_applications_versions:25,Inventory_applications:26,Inventory_hosts:27,Inventory_instances:28,
								 * Inventory_servers_versions:29,Inventory_servers:30,Inventory_templates_apps:31,Inventory_templates:32,metrics:33,modules:34,monitoring_automations:35,monitoring_commands_executions:36,monitoring_commands:37,monitoring_metrics:38,
								 * monitoring_policies_alerts:39,monitoring_policies_servers:40,monitoring_policies:41,monitoring_settings:42,networks_domain_names:43,networks_hosts:44,networks_ssl_certificates:45,providers:46,app_api_key:47,api_key:48,
								 * api_keys_permissions:49, core_logs:50
								*/
								if(result != null) {
									Core_Api_Logs api_logs = new Core_Api_Logs();
									
									Api_Keys api_key = api_Keys_Repo.findById(Long.parseLong(BasicAuthInterceptor.GLOBAL_APIKEY)).get();
									
									api_logs.setAction("create");
									api_logs.setApikey(api_key);
									api_logs.setElement(1);
									api_logs.setElement_id((int)result.getId());
									api_logs.setLog_date(LocalDateTime.now());
									core_Api_Logs_Repo.save(api_logs);
									
							        return ResponseHandler.ResponseOk("Successfully added data!", HttpStatus.OK, result);
							        
								}else {
							        return ResponseHandler.ResponseBadRequest("Your request was malformed!", HttpStatus.BAD_REQUEST, null);
								}

							}catch (Exception e) {
					            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
					        }
						}
					}else if (BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
						if(operation.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
				        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
						}else {
							try {
								backup_Executions.setStart_date(LocalDateTime.now());
								backup_Executions.setOperation(operation.get());
								Backup_Executions result = backup_Executions_Service.addBackup_Executions(backup_Executions);	
								
								/*api logs 
								 * backup_executions:1,backup_instances:2,backup_operations:3,backup_settings:4,cloud_providers_account:5,core_access_credentials:6,core_accounts_modules:7,core_accounts:8,core_categories_elements:9,core_categories:10,
								 * core_logs:11,core_notes:12,core_notifications:13,core_urls:14,core_users_instances:15,core_users_metrics:16,core_users_modules:17,core_users_permissions:18,core_users_security:19,core_users_tokens:20,core_users:21,
								 * Inventory_applications_dependencies:22,Inventory_applications_instances:23,Inventory_applications_sources:24,Inventory_applications_versions:25,Inventory_applications:26,Inventory_hosts:27,Inventory_instances:28,
								 * Inventory_servers_versions:29,Inventory_servers:30,Inventory_templates_apps:31,Inventory_templates:32,metrics:33,modules:34,monitoring_automations:35,monitoring_commands_executions:36,monitoring_commands:37,monitoring_metrics:38,
								 * monitoring_policies_alerts:39,monitoring_policies_servers:40,monitoring_policies:41,monitoring_settings:42,networks_domain_names:43,networks_hosts:44,networks_ssl_certificates:45,providers:46,app_api_key:47,api_key:48,
								 * api_keys_permissions:49, core_logs:50
								*/
								if(result != null) {
									Core_Api_Logs api_logs = new Core_Api_Logs();
									
									Api_Keys api_key = api_Keys_Repo.findById(Long.parseLong(BasicAuthInterceptor.GLOBAL_APIKEY)).get();
									
									api_logs.setAction("create");
									api_logs.setApikey(api_key);
									api_logs.setElement(1);
									api_logs.setElement_id((int)result.getId());
									api_logs.setLog_date(LocalDateTime.now());
									core_Api_Logs_Repo.save(api_logs);
									
							        return ResponseHandler.ResponseOk("Successfully added data!", HttpStatus.OK, result);
							        
								}else {
							        return ResponseHandler.ResponseBadRequest("Your request was malformed!", HttpStatus.BAD_REQUEST, null);
								}

							}catch (Exception e) {
					            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
					        }
						}
					}else {
				        return ResponseHandler.ResponseForbidden("Error... Account NOT Identified!", HttpStatus.FORBIDDEN, null);
					}
				}
			}
		}
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Object> getBackup_ExecutionsById(@PathVariable(name="id") String id){	
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
				Optional<Backup_Executions> result = backup_Executions_Service.findBackup_ExecutionsById(Long.parseLong(id));
				if(result.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else {
					if(result.get().getStatus() == 4 || result.get().getEnd_date() != null) {
			        	return ResponseHandler.ResponseForbidden("Resources has been deleted!", HttpStatus.FORBIDDEN, null);
					}
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						if(result.get().getOperation().getAccount() != null) {
							if(result.get().getOperation().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
					        	return ResponseHandler.ResponseOk("Successfully retrieved data!", HttpStatus.OK, result);
							}
						}else {
				        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
						}
					}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
						if(result.get().getOperation().getAccount() != null) {
							if(result.get().getOperation().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
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
	
	@GetMapping("/operation/get/{operation_id}")
	public ResponseEntity<Object> getBackup_ExecutionsByOperationID(@PathVariable(name="operation_id") String operation_id){	
		if(Inputs_Validations.CheckInt(operation_id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						Optional<Backup_Executions> result = backup_Executions_Service.getBackup_ExecutionsByOperationID(Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT), Long.parseLong(operation_id));
					    return ResponseHandler.ResponseOk("Successfully retrieved data!", HttpStatus.OK, result);

					}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
						Optional<Backup_Executions> result = backup_Executions_Service.getBackup_ExecutionsByOperationID(Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT), Long.parseLong(operation_id));
					    return ResponseHandler.ResponseOk("Successfully retrieved data!", HttpStatus.OK, result);

					}else {
				        return ResponseHandler.ResponseForbidden("Error... Account NOT Identified!", HttpStatus.FORBIDDEN, null);
					}	
			}catch(Exception e) {
	            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }
		}
		
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updatBackup_Executions(@RequestBody Backup_Executions backup_Executions,@PathVariable(name="id") String id ,Errors errors){	
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
				Optional<Backup_Executions> back_exe = backup_Executions_Service.findBackup_ExecutionsById(Long.parseLong(id));
				if(back_exe.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found with id "+id, HttpStatus.NOT_FOUND, null);
				}else if(back_exe.get().getStatus() == 4 || back_exe.get().getEnd_date() != null) {
		        	return ResponseHandler.ResponseForbidden("Resources has been deleted! We were unable to perform the request", HttpStatus.FORBIDDEN, null);
				}else if(back_exe.get().getOperation() == null) {
			        return ResponseHandler.ResponseBadRequest("Your request was malformed! Foreign key required!", HttpStatus.BAD_REQUEST, null);
				}else {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"); 

					backup_Executions.setOperation(back_exe.get().getOperation());
					backup_Executions.setStart_date(back_exe.get().getStart_date());
					
					if(backup_Executions.getEnd_date() == null) {
						backup_Executions.setEnd_date(back_exe.get().getEnd_date());
					}
					if(backup_Executions.getStatus() == 0) {
						backup_Executions.setStatus(back_exe.get().getStatus());
					}
					if(backup_Executions.getFile() == null) {
						backup_Executions.setFile(back_exe.get().getFile());
					}
					if(back_exe.get().getFile() != null) {
						if(back_exe.get().getOperation() == backup_Executions.getOperation() && back_exe.get().getStart_date().toString().equals(backup_Executions.getStart_date().format(formatter)) && backup_Executions.getEnd_date() == null &&
								back_exe.get().getStatus() == backup_Executions.getStatus() && back_exe.get().getFile().equals(backup_Executions.getFile())) {
								return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
							}
					}else {
						if(back_exe.get().getOperation() == backup_Executions.getOperation() && back_exe.get().getStart_date().toString().equals(backup_Executions.getStart_date().format(formatter)) && backup_Executions.getEnd_date() == null &&
								back_exe.get().getStatus() == backup_Executions.getStatus() && back_exe.get().getFile() == backup_Executions.getFile()) {
								return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
							}
					}
					
					
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						if(back_exe.get().getOperation().getAccount() != null) {
							if(back_exe.get().getOperation().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								backup_Executions.setId(Long.parseLong(id));
								Backup_Executions result = backup_Executions_Service.updateBackup_Executions(backup_Executions);
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
									api_logs.setElement(1);
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
						if(back_exe.get().getOperation().getAccount() != null) {
							if(back_exe.get().getOperation().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								backup_Executions.setId(Long.parseLong(id));
								Backup_Executions result = backup_Executions_Service.updateBackup_Executions(backup_Executions);
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
									api_logs.setElement(1);
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
	public ResponseEntity<Object> deleteBackup_Executions(@PathVariable("id") String id){
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
				Optional<Backup_Executions> back_exe = backup_Executions_Service.findBackup_ExecutionsById(Long.parseLong(id));
				if(back_exe.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found with id "+id, HttpStatus.NOT_FOUND, null);
				}else {
					if(back_exe.get().getStatus() == 4 || back_exe.get().getEnd_date() != null) {
			        	return ResponseHandler.ResponseForbidden("Resources already deleted! We were unable to perform the request", HttpStatus.FORBIDDEN, null);
					}
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						if(back_exe.get().getOperation().getAccount() != null) {
							if(back_exe.get().getOperation().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								String result = backup_Executions_Service.deleteBackup_Executions(Long.parseLong(id));
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
									api_logs.setElement(1);
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
						if(back_exe.get().getOperation().getAccount() != null) {
							if(back_exe.get().getOperation().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								String result = backup_Executions_Service.deleteBackup_Executions(Long.parseLong(id));
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
									api_logs.setElement(1);
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

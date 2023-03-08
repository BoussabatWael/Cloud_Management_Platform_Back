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
import com.gcs.cmp.entity.Inventory_Applications;
import com.gcs.cmp.entity.Inventory_Applications_Sources;
import com.gcs.cmp.interceptors.BasicAuthInterceptor;
import com.gcs.cmp.repository.Api_Keys_Repo;
import com.gcs.cmp.repository.Core_Api_Logs_Repo;
import com.gcs.cmp.repository.Inventory_Applications_Repo;
import com.gcs.cmp.service.Inventory_Applications_Sources_Service;
import com.gcs.cmp.validators.Inputs_Validations;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Applications sources", description = "Manage inventory applications sources")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/inventory/applications/sources")
public class InventoryApplicationsSources {

	@Autowired
	private Inventory_Applications_Sources_Service inventory_Applications_Sources_Service;
	
	@Autowired
	private Core_Api_Logs_Repo core_Api_Logs_Repo;
	
	@Autowired
	private Api_Keys_Repo api_Keys_Repo;
	
	@Autowired
	private Inventory_Applications_Repo inventory_Applications_Repo;
	
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
	public ResponseEntity<Object> getAllInventory_Applications_Sources(){	
		try {
			if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
				List <Inventory_Applications_Sources> result = inventory_Applications_Sources_Service.getInventory_Applications_SourcesList(Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT));	
				return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
			}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
				List <Inventory_Applications_Sources> result = inventory_Applications_Sources_Service.getInventory_Applications_SourcesList(Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT));	
				return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
			}else {
				return ResponseHandler.ResponseListKo("Error... Account NOT Identified!", 0, HttpStatus.FORBIDDEN, null);
			}
			
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@PostMapping("/create")
	public ResponseEntity<Object> addInventory_Applications_Sources(@Validated @RequestBody Inventory_Applications_Sources inventory_Applications_Sources,Errors errors){	
		if(ex != null) {
			handleException(ex);
		}
		if(errors.hasErrors()) {
            return ResponseHandler.ResponseBadRequest(errors.getFieldError().getField()+" "+errors.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST, null);
		}else {
			try {
				if(inventory_Applications_Sources.getApplication() == null) {
			        return ResponseHandler.ResponseBadRequest("Your request was malformed! Foreign key required!", HttpStatus.BAD_REQUEST, null);
				}else {
					if(inventory_Applications_Sources.getApplication().getId() == 0) {
				        return ResponseHandler.ResponseBadRequest("Your request was malformed... ID must not be null", HttpStatus.BAD_REQUEST, null);
					}
					Optional<Inventory_Applications> inv_app = inventory_Applications_Repo.findById(inventory_Applications_Sources.getApplication().getId());
					if(inv_app.equals(Optional.empty())) {
				        return ResponseHandler.ResponseBadRequest("Your request was malformed! Foreign key required!", HttpStatus.BAD_REQUEST, null);
					}else {
						if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
							if(inv_app.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								inventory_Applications_Sources.setStart_date(LocalDateTime.now());
								inventory_Applications_Sources.setApplication(inv_app.get());
								Inventory_Applications_Sources result = inventory_Applications_Sources_Service.addInventory_Applications_Sources(inventory_Applications_Sources);	
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
									api_logs.setElement(24);
									api_logs.setElement_id((int)result.getId());
									api_logs.setLog_date(LocalDateTime.now());
									core_Api_Logs_Repo.save(api_logs);
									
							        return ResponseHandler.ResponseOk("Successfully added data!", HttpStatus.OK, result);
								}else {
							        return ResponseHandler.ResponseBadRequest("Your request was malformed!", HttpStatus.BAD_REQUEST, null);
								}
							}
						}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
							if(inv_app.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								inventory_Applications_Sources.setStart_date(LocalDateTime.now());
								inventory_Applications_Sources.setApplication(inv_app.get());
								Inventory_Applications_Sources result = inventory_Applications_Sources_Service.addInventory_Applications_Sources(inventory_Applications_Sources);	
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
									api_logs.setElement(24);
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
	public ResponseEntity<Object> getInventory_Applications_SourcesById(@PathVariable(name="id") String id){
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
				Optional<Inventory_Applications_Sources> result = inventory_Applications_Sources_Service.findInventory_Applications_SourcesId(Long.parseLong(id));
				if(result.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else {
					if(result.get().getStatus() == 4 || result.get().getEnd_date() != null) {
			        	return ResponseHandler.ResponseForbidden("Resources has been deleted!", HttpStatus.FORBIDDEN, null);
					}
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						if(result.get().getApplication().getAccount() != null) {
							if(result.get().getApplication().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
					        	return ResponseHandler.ResponseOk("Successfully retrieved data!", HttpStatus.OK, result);
							}
						}else {
				        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
						}
						
					}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
						if(result.get().getApplication().getAccount() != null) {
							if(result.get().getApplication().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
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
	
	@GetMapping("/application/get/{application_id}")
	public ResponseEntity<Object> getApplications_SourcesListByApplicationID(@PathVariable(name="application_id") String application_id){
		if(Inputs_Validations.CheckInt(application_id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						List<Inventory_Applications_Sources> result = inventory_Applications_Sources_Service.getApplications_SourcesListByApplicationID(Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT), Long.parseLong(application_id));
						return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);

					}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
						List<Inventory_Applications_Sources> result = inventory_Applications_Sources_Service.getApplications_SourcesListByApplicationID(Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT), Long.parseLong(application_id));
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
	public ResponseEntity<Object> updateInventory_Applications_Sources(@RequestBody Inventory_Applications_Sources inventory_Applications_Sources,@PathVariable(name="id") String id, Errors errors){
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
				Optional<Inventory_Applications_Sources> inv_app_sour = inventory_Applications_Sources_Service.findInventory_Applications_SourcesId(Long.parseLong(id));
				if(inv_app_sour.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else if(inv_app_sour.get().getStatus() == 4 || inv_app_sour.get().getEnd_date() != null) {
		        	return ResponseHandler.ResponseForbidden("Resources has been deleted! We were unable to perform the request", HttpStatus.FORBIDDEN, null);
				}else if(inv_app_sour.get().getApplication() == null) {
			        return ResponseHandler.ResponseBadRequest("Your request was malformed! Foreign key required!", HttpStatus.BAD_REQUEST, null);
				}else {
					
					
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"); 

					inventory_Applications_Sources.setApplication(inv_app_sour.get().getApplication());
					inventory_Applications_Sources.setStart_date(inv_app_sour.get().getStart_date());
					
					if(inventory_Applications_Sources.getEnd_date() == null) {
						inventory_Applications_Sources.setEnd_date(inv_app_sour.get().getEnd_date());
					}
					if(inventory_Applications_Sources.getStatus() == 0) {
						inventory_Applications_Sources.setStatus(inv_app_sour.get().getStatus());
					}
					if(inventory_Applications_Sources.getClasse() == 0) {
						inventory_Applications_Sources.setClasse(inv_app_sour.get().getClasse());
					}
					if(inventory_Applications_Sources.getSource_type() == 0) {
						inventory_Applications_Sources.setSource_type(inv_app_sour.get().getSource_type());
					}
					if(inventory_Applications_Sources.getSource_build() == null) {
						inventory_Applications_Sources.setSource_build(inv_app_sour.get().getSource_build());
					}
					if(inventory_Applications_Sources.getSource_account() == null) {
						inventory_Applications_Sources.setSource_account(inv_app_sour.get().getSource_account());
					}
					if(inventory_Applications_Sources.getSource_url() == null) {
						inventory_Applications_Sources.setSource_url(inv_app_sour.get().getSource_url());
					}
					if(inv_app_sour.get().getSource_account() == null) {
						if(inv_app_sour.get().getSource_build() != null && inv_app_sour.get().getSource_url() != null) {
							if(inv_app_sour.get().getApplication() == inventory_Applications_Sources.getApplication() && inv_app_sour.get().getStart_date().toString().equals(inventory_Applications_Sources.getStart_date().format(formatter)) && inventory_Applications_Sources.getEnd_date() == null &&
									inv_app_sour.get().getStatus() == inventory_Applications_Sources.getStatus() && inv_app_sour.get().getClasse() == inventory_Applications_Sources.getClasse() && inv_app_sour.get().getSource_type() == inventory_Applications_Sources.getSource_type() && 
									inv_app_sour.get().getSource_build().equals(inventory_Applications_Sources.getSource_build()) && inv_app_sour.get().getSource_url().equals(inventory_Applications_Sources.getSource_url()) && inv_app_sour.get().getSource_account() == inventory_Applications_Sources.getSource_account()) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						}else if(inv_app_sour.get().getSource_build() == null && inv_app_sour.get().getSource_url() == null) {
							if(inv_app_sour.get().getApplication() == inventory_Applications_Sources.getApplication() && inv_app_sour.get().getStart_date().toString().equals(inventory_Applications_Sources.getStart_date().format(formatter)) && inventory_Applications_Sources.getEnd_date() == null &&
									inv_app_sour.get().getStatus() == inventory_Applications_Sources.getStatus() && inv_app_sour.get().getClasse() == inventory_Applications_Sources.getClasse() && inv_app_sour.get().getSource_type() == inventory_Applications_Sources.getSource_type() && 
									inv_app_sour.get().getSource_build() == inventory_Applications_Sources.getSource_build() && inv_app_sour.get().getSource_url() == inventory_Applications_Sources.getSource_url() && inv_app_sour.get().getSource_account() == inventory_Applications_Sources.getSource_account()) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						}else if(inv_app_sour.get().getSource_build() != null && inv_app_sour.get().getSource_url() == null) {
							if(inv_app_sour.get().getApplication() == inventory_Applications_Sources.getApplication() && inv_app_sour.get().getStart_date().toString().equals(inventory_Applications_Sources.getStart_date().format(formatter)) && inventory_Applications_Sources.getEnd_date() == null &&
									inv_app_sour.get().getStatus() == inventory_Applications_Sources.getStatus() && inv_app_sour.get().getClasse() == inventory_Applications_Sources.getClasse() && inv_app_sour.get().getSource_type() == inventory_Applications_Sources.getSource_type() && 
									inv_app_sour.get().getSource_build().equals(inventory_Applications_Sources.getSource_build()) && inv_app_sour.get().getSource_url() == inventory_Applications_Sources.getSource_url() && inv_app_sour.get().getSource_account() == inventory_Applications_Sources.getSource_account()) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						}
						else if(inv_app_sour.get().getSource_build() == null && inv_app_sour.get().getSource_url() != null) {
							if(inv_app_sour.get().getApplication() == inventory_Applications_Sources.getApplication() && inv_app_sour.get().getStart_date().toString().equals(inventory_Applications_Sources.getStart_date().format(formatter)) && inventory_Applications_Sources.getEnd_date() == null &&
									inv_app_sour.get().getStatus() == inventory_Applications_Sources.getStatus() && inv_app_sour.get().getClasse() == inventory_Applications_Sources.getClasse() && inv_app_sour.get().getSource_type() == inventory_Applications_Sources.getSource_type() && 
									inv_app_sour.get().getSource_build() == inventory_Applications_Sources.getSource_build() && inv_app_sour.get().getSource_url().equals(inventory_Applications_Sources.getSource_url()) && inv_app_sour.get().getSource_account() == inventory_Applications_Sources.getSource_account()) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						}
						
						
					}else if(inv_app_sour.get().getSource_build() == null) {
						if(inv_app_sour.get().getSource_account() != null && inv_app_sour.get().getSource_url() != null) {
							if(inv_app_sour.get().getApplication() == inventory_Applications_Sources.getApplication() && inv_app_sour.get().getStart_date().toString().equals(inventory_Applications_Sources.getStart_date().format(formatter)) && inventory_Applications_Sources.getEnd_date() == null &&
									inv_app_sour.get().getStatus() == inventory_Applications_Sources.getStatus() && inv_app_sour.get().getClasse() == inventory_Applications_Sources.getClasse() && inv_app_sour.get().getSource_type() == inventory_Applications_Sources.getSource_type() && 
									inv_app_sour.get().getSource_build() == inventory_Applications_Sources.getSource_build() && inv_app_sour.get().getSource_url().equals(inventory_Applications_Sources.getSource_url()) && inv_app_sour.get().getSource_account().equals(inventory_Applications_Sources.getSource_account())) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						}else if(inv_app_sour.get().getSource_account() == null && inv_app_sour.get().getSource_url() == null) {
							if(inv_app_sour.get().getApplication() == inventory_Applications_Sources.getApplication() && inv_app_sour.get().getStart_date().toString().equals(inventory_Applications_Sources.getStart_date().format(formatter)) && inventory_Applications_Sources.getEnd_date() == null &&
									inv_app_sour.get().getStatus() == inventory_Applications_Sources.getStatus() && inv_app_sour.get().getClasse() == inventory_Applications_Sources.getClasse() && inv_app_sour.get().getSource_type() == inventory_Applications_Sources.getSource_type() && 
									inv_app_sour.get().getSource_build() == inventory_Applications_Sources.getSource_build() && inv_app_sour.get().getSource_url() == inventory_Applications_Sources.getSource_url() && inv_app_sour.get().getSource_account() == inventory_Applications_Sources.getSource_account()) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						}else if(inv_app_sour.get().getSource_account() == null && inv_app_sour.get().getSource_url() != null) {
							if(inv_app_sour.get().getApplication() == inventory_Applications_Sources.getApplication() && inv_app_sour.get().getStart_date().toString().equals(inventory_Applications_Sources.getStart_date().format(formatter)) && inventory_Applications_Sources.getEnd_date() == null &&
									inv_app_sour.get().getStatus() == inventory_Applications_Sources.getStatus() && inv_app_sour.get().getClasse() == inventory_Applications_Sources.getClasse() && inv_app_sour.get().getSource_type() == inventory_Applications_Sources.getSource_type() && 
									inv_app_sour.get().getSource_build() == inventory_Applications_Sources.getSource_build() && inv_app_sour.get().getSource_url().equals(inventory_Applications_Sources.getSource_url()) && inv_app_sour.get().getSource_account() == inventory_Applications_Sources.getSource_account()) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						}
						else if(inv_app_sour.get().getSource_account() != null && inv_app_sour.get().getSource_url() == null) {
							if(inv_app_sour.get().getApplication() == inventory_Applications_Sources.getApplication() && inv_app_sour.get().getStart_date().toString().equals(inventory_Applications_Sources.getStart_date().format(formatter)) && inventory_Applications_Sources.getEnd_date() == null &&
									inv_app_sour.get().getStatus() == inventory_Applications_Sources.getStatus() && inv_app_sour.get().getClasse() == inventory_Applications_Sources.getClasse() && inv_app_sour.get().getSource_type() == inventory_Applications_Sources.getSource_type() && 
									inv_app_sour.get().getSource_build() == inventory_Applications_Sources.getSource_build() && inv_app_sour.get().getSource_url() == inventory_Applications_Sources.getSource_url() && inv_app_sour.get().getSource_account().equals(inventory_Applications_Sources.getSource_account())) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						}
					}
					
					else if(inv_app_sour.get().getSource_url() == null) {
						if(inv_app_sour.get().getSource_account() != null && inv_app_sour.get().getSource_build() != null) {
							if(inv_app_sour.get().getApplication() == inventory_Applications_Sources.getApplication() && inv_app_sour.get().getStart_date().toString().equals(inventory_Applications_Sources.getStart_date().format(formatter)) && inventory_Applications_Sources.getEnd_date() == null &&
									inv_app_sour.get().getStatus() == inventory_Applications_Sources.getStatus() && inv_app_sour.get().getClasse() == inventory_Applications_Sources.getClasse() && inv_app_sour.get().getSource_type() == inventory_Applications_Sources.getSource_type() && 
									inv_app_sour.get().getSource_build().equals(inventory_Applications_Sources.getSource_build()) && inv_app_sour.get().getSource_url() == inventory_Applications_Sources.getSource_url() && inv_app_sour.get().getSource_account().equals(inventory_Applications_Sources.getSource_account())) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						}else if(inv_app_sour.get().getSource_account() == null && inv_app_sour.get().getSource_build() == null) {
							if(inv_app_sour.get().getApplication() == inventory_Applications_Sources.getApplication() && inv_app_sour.get().getStart_date().toString().equals(inventory_Applications_Sources.getStart_date().format(formatter)) && inventory_Applications_Sources.getEnd_date() == null &&
									inv_app_sour.get().getStatus() == inventory_Applications_Sources.getStatus() && inv_app_sour.get().getClasse() == inventory_Applications_Sources.getClasse() && inv_app_sour.get().getSource_type() == inventory_Applications_Sources.getSource_type() && 
									inv_app_sour.get().getSource_build() == inventory_Applications_Sources.getSource_build() && inv_app_sour.get().getSource_url() == inventory_Applications_Sources.getSource_url() && inv_app_sour.get().getSource_account() == inventory_Applications_Sources.getSource_account()) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						}else if(inv_app_sour.get().getSource_account() == null && inv_app_sour.get().getSource_build() != null) {
							if(inv_app_sour.get().getApplication() == inventory_Applications_Sources.getApplication() && inv_app_sour.get().getStart_date().toString().equals(inventory_Applications_Sources.getStart_date().format(formatter)) && inventory_Applications_Sources.getEnd_date() == null &&
									inv_app_sour.get().getStatus() == inventory_Applications_Sources.getStatus() && inv_app_sour.get().getClasse() == inventory_Applications_Sources.getClasse() && inv_app_sour.get().getSource_type() == inventory_Applications_Sources.getSource_type() && 
									inv_app_sour.get().getSource_build().equals(inventory_Applications_Sources.getSource_build()) && inv_app_sour.get().getSource_url() == inventory_Applications_Sources.getSource_url() && inv_app_sour.get().getSource_account() == inventory_Applications_Sources.getSource_account()) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						}
						else if(inv_app_sour.get().getSource_account() != null && inv_app_sour.get().getSource_build() == null) {
							if(inv_app_sour.get().getApplication() == inventory_Applications_Sources.getApplication() && inv_app_sour.get().getStart_date().toString().equals(inventory_Applications_Sources.getStart_date().format(formatter)) && inventory_Applications_Sources.getEnd_date() == null &&
									inv_app_sour.get().getStatus() == inventory_Applications_Sources.getStatus() && inv_app_sour.get().getClasse() == inventory_Applications_Sources.getClasse() && inv_app_sour.get().getSource_type() == inventory_Applications_Sources.getSource_type() && 
									inv_app_sour.get().getSource_build() == inventory_Applications_Sources.getSource_build() && inv_app_sour.get().getSource_url() == inventory_Applications_Sources.getSource_url() && inv_app_sour.get().getSource_account() == inventory_Applications_Sources.getSource_account()) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						}
					}else {
						if(inv_app_sour.get().getApplication() == inventory_Applications_Sources.getApplication() && inv_app_sour.get().getStart_date().toString().equals(inventory_Applications_Sources.getStart_date().format(formatter)) && inventory_Applications_Sources.getEnd_date() == null &&
								inv_app_sour.get().getStatus() == inventory_Applications_Sources.getStatus() && inv_app_sour.get().getClasse() == inventory_Applications_Sources.getClasse() && inv_app_sour.get().getSource_type() == inventory_Applications_Sources.getSource_type() && 
								inv_app_sour.get().getSource_build().equals(inventory_Applications_Sources.getSource_build()) && inv_app_sour.get().getSource_url().equals(inventory_Applications_Sources.getSource_url()) && inv_app_sour.get().getSource_account().equals(inventory_Applications_Sources.getSource_account())) {
			        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
					}
					}
					
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						if(inv_app_sour.get().getApplication().getAccount() !=null) {
							if(inv_app_sour.get().getApplication().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								inventory_Applications_Sources.setId(Long.parseLong(id));
								Inventory_Applications_Sources result = inventory_Applications_Sources_Service.updateInventory_Applications_Sources(inventory_Applications_Sources);
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
									api_logs.setElement(24);
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
						if(inv_app_sour.get().getApplication().getAccount() !=null) {
							if(inv_app_sour.get().getApplication().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								inventory_Applications_Sources.setId(Long.parseLong(id));
								Inventory_Applications_Sources result = inventory_Applications_Sources_Service.updateInventory_Applications_Sources(inventory_Applications_Sources);
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
									api_logs.setElement(24);
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
	public ResponseEntity<Object> deleteInventory_Applications_Sources(@PathVariable("id") String id){
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}
		else {
			try {
				Optional<Inventory_Applications_Sources> inv_app_sour = inventory_Applications_Sources_Service.findInventory_Applications_SourcesId(Long.parseLong(id));
				if(inv_app_sour.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else {
					if(inv_app_sour.get().getStatus() == 4 || inv_app_sour.get().getEnd_date() != null) {
			        	return ResponseHandler.ResponseForbidden("Resources already deleted! We were unable to perform the request", HttpStatus.FORBIDDEN, null);
					}
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						if(inv_app_sour.get().getApplication().getAccount() != null) {
							if(inv_app_sour.get().getApplication().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								String result = inventory_Applications_Sources_Service.deleteInventory_Applications_Sources(Long.parseLong(id));
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
									api_logs.setElement(24);
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
						if(inv_app_sour.get().getApplication().getAccount() != null) {
							if(inv_app_sour.get().getApplication().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								String result = inventory_Applications_Sources_Service.deleteInventory_Applications_Sources(Long.parseLong(id));
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
									api_logs.setElement(24);
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

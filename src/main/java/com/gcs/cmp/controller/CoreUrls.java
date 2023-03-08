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
import com.gcs.cmp.entity.Core_Urls;
import com.gcs.cmp.interceptors.BasicAuthInterceptor;
import com.gcs.cmp.repository.Api_Keys_Repo;
import com.gcs.cmp.repository.Core_Api_Logs_Repo;
import com.gcs.cmp.service.Core_Urls_Service;
import com.gcs.cmp.validators.Inputs_Validations;

import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "URLs", description = "Manage core URLs")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/core/urls")
public class CoreUrls {

	@Autowired
	private Core_Urls_Service core_Urls_Service;
	
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
	public ResponseEntity<Object> getAllCore_Urls(){	
		try {
			List <Core_Urls> result = core_Urls_Service.getAllCore_Urls();	
			return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@PostMapping("/create")
	public ResponseEntity<Object> addCore_Urls(@Validated @RequestBody Core_Urls core_Urls,Errors errors){	
		if(ex != null) {
			handleException(ex);
		}
		if(errors.hasErrors()) {
            return ResponseHandler.ResponseBadRequest(errors.getFieldError().getField()+" "+errors.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST, null);
		}else {
			try {
				Core_Urls result = core_Urls_Service.addCore_Urls(core_Urls);	
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
					api_logs.setElement(14);
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
		
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Object> getCore_UrlsById(@PathVariable(name="id") String id){	
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
				Optional<Core_Urls> result = core_Urls_Service.findCore_UrlsById(Long.parseLong(id));
				if(result.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else {
			        return ResponseHandler.ResponseOk("Successfully retrieved data!", HttpStatus.OK, result);
				}
			}catch(Exception e) {
	            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }
		}
		
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updateCore_Urls(@RequestBody Core_Urls core_Urls,@PathVariable(name="id") String id, Errors errors){	
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
				Optional<Core_Urls> url = core_Urls_Service.findCore_UrlsById(Long.parseLong(id));
				if(url.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else {
					
					if(core_Urls.getUrl() == null) {
						core_Urls.setUrl(url.get().getUrl());
					}
					if(core_Urls.getElement() == 0) {
						core_Urls.setElement(url.get().getElement());
					}
					if(core_Urls.getElement_id() == 0) {
						core_Urls.setElement_id(url.get().getElement_id());
					}
					if(core_Urls.getClasse() == 0) {
						core_Urls.setClasse(url.get().getClasse());
					}

					if(url.get().getElement() == core_Urls.getElement() && url.get().getUrl().equals(core_Urls.getUrl()) &&
							url.get().getElement_id() == core_Urls.getElement_id() && url.get().getClasse() == core_Urls.getClasse()) {
			        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
					}
					
					core_Urls.setId(Long.parseLong(id));
					Core_Urls result = core_Urls_Service.updateCore_Urls(core_Urls);
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
						api_logs.setElement(14);
						api_logs.setElement_id((int)result.getId());
						api_logs.setLog_date(LocalDateTime.now());
						core_Api_Logs_Repo.save(api_logs);
						
				        return ResponseHandler.ResponseOk("Updated", HttpStatus.OK, result);
					}else {
			            return ResponseHandler.ResponseBadRequest("Your request was malformed!", HttpStatus.BAD_REQUEST, null);
					}
				}
			}catch (Exception e) {
	            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }
		}
		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteCore_Urls(@PathVariable("id") String id){
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
				Optional<Core_Urls> url = core_Urls_Service.findCore_UrlsById(Long.parseLong(id));
				if(url.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else {
					String result = core_Urls_Service.deleteCore_Urls(Long.parseLong(id));
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
						api_logs.setElement(14);
						api_logs.setElement_id((int)Long.parseLong(id));
						api_logs.setLog_date(LocalDateTime.now());
						core_Api_Logs_Repo.save(api_logs);
						
				        return ResponseHandler.ResponseOk("Deleted!", HttpStatus.OK, result);
					}else {
			            return ResponseHandler.ResponseBadRequest("Your request was malformed!", HttpStatus.BAD_REQUEST, null);
					}
				}	
			}catch (Exception e) {
	            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }
		}
	}
	
}

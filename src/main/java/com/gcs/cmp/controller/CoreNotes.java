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
import com.gcs.cmp.entity.Core_Notes;
import com.gcs.cmp.interceptors.BasicAuthInterceptor;
import com.gcs.cmp.repository.Api_Keys_Repo;
import com.gcs.cmp.repository.Core_Api_Logs_Repo;
import com.gcs.cmp.service.Core_Notes_Service;
import com.gcs.cmp.validators.Inputs_Validations;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Notes", description = "Manage core notes")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/core/notes")
public class CoreNotes {

	@Autowired
	private Core_Notes_Service core_Notes_Service;
	
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
	public ResponseEntity<Object> getAllCore_Notes(){	
		try {
			List <Core_Notes> result = core_Notes_Service.getAllCore_Notes();	
			return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@PostMapping("/create")
	public ResponseEntity<Object> addCore_Notes(@Validated @RequestBody Core_Notes core_Notes,Errors errors){
		if(ex != null) {
			handleException(ex);
		}
		if(errors.hasErrors()) {
            return ResponseHandler.ResponseBadRequest(errors.getFieldError().getField()+" "+errors.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST, null);
		}else {
			try {
				Core_Notes result = core_Notes_Service.addCore_Notes(core_Notes);	
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
					api_logs.setElement(12);
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
	public ResponseEntity<Object> getCore_NotesById(@PathVariable(name="id") String id){	
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
				Optional<Core_Notes> result = core_Notes_Service.findCore_NotesById(Long.parseLong(id));
				if(result.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else{
			        return ResponseHandler.ResponseOk("Successfully retrieved data!", HttpStatus.OK, result);
				}
			}catch(Exception e) {
	            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }
		}
		
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updateCore_Notes(@RequestBody Core_Notes core_Notes,@PathVariable(name="id") String id, Errors errors){	
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
				Optional<Core_Notes> note = core_Notes_Service.findCore_NotesById(Long.parseLong(id));
				if(note.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else {
					
					if(core_Notes.getContent() == null) {
						core_Notes.setContent(note.get().getContent());
					}
					if(core_Notes.getElement() == 0) {
						core_Notes.setElement(note.get().getElement());
					}
					if(core_Notes.getElement_id() == 0) {
						core_Notes.setElement_id(note.get().getElement_id());
					}

					if(note.get().getElement() == core_Notes.getElement() && note.get().getElement_id() == core_Notes.getElement_id() && note.get().getContent().equals(core_Notes.getContent())) {
			        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
					}

					core_Notes.setId(Long.parseLong(id));
					Core_Notes result = core_Notes_Service.updateCore_Notes(core_Notes);
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
						api_logs.setElement(12);
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
	public ResponseEntity<Object> deleteCore_Notes(@PathVariable("id") String id){
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}
		else {
			try {
				Optional<Core_Notes> note = core_Notes_Service.findCore_NotesById(Long.parseLong(id));
				if(note.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else {
					String result = core_Notes_Service.deleteCore_Notes(Long.parseLong(id));
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
						api_logs.setElement(12);
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

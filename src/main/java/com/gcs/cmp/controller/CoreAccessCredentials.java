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
import com.gcs.cmp.entity.Core_Access_Credentials;
import com.gcs.cmp.entity.Core_Accounts;
import com.gcs.cmp.entity.Core_Api_Logs;
import com.gcs.cmp.interceptors.BasicAuthInterceptor;
import com.gcs.cmp.repository.Api_Keys_Repo;
import com.gcs.cmp.repository.Core_Accounts_Repo;
import com.gcs.cmp.repository.Core_Api_Logs_Repo;
import com.gcs.cmp.service.Core_Access_Credentials_Service;
import com.gcs.cmp.validators.Inputs_Validations;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Access credentials", description = "Manage core access credentials")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/core/access/credentials")
public class CoreAccessCredentials {

	@Autowired
	private Core_Access_Credentials_Service core_Credentials_Service;
	
	@Autowired
	private Core_Api_Logs_Repo core_Api_Logs_Repo;
	
	@Autowired
	private Core_Accounts_Repo core_Accounts_Repo;
	
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
	public ResponseEntity<Object> getAllCore_Credentials(){	
		try {
			if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
				List <Core_Access_Credentials> result = core_Credentials_Service.findCore_Access_CredentialsList(Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT));	
				for(int i=0;i<result.size();i++) {
					result.get(i).setPassword(Inputs_Validations.decrypt(result.get(i).getPassword()));
				}
				return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
			}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
				List <Core_Access_Credentials> result = core_Credentials_Service.findCore_Access_CredentialsList(Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT));	
				for(int i=0;i<result.size();i++) {
					result.get(i).setPassword(Inputs_Validations.decrypt(result.get(i).getPassword()));
				}
				return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
			}else {
				return ResponseHandler.ResponseListKo("Error... Account NOT Identified!", 0, HttpStatus.FORBIDDEN, null);
			}
			
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@PostMapping("/create")
	public ResponseEntity<Object> addCore_Credentials(@Validated @RequestBody Core_Access_Credentials core_Credentials,Errors errors){	
		if(ex != null) {
			handleException(ex);
		}
		if(errors.hasErrors()) {
            return ResponseHandler.ResponseBadRequest(errors.getFieldError().getField()+" "+errors.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST, null);
		}else {
			try {
				if(core_Credentials.getAccount() == null) {
			        return ResponseHandler.ResponseBadRequest("Your request was malformed! Foreign key required!", HttpStatus.BAD_REQUEST, null);
				}else {
					if(core_Credentials.getAccount().getId() == 0) {
				        return ResponseHandler.ResponseBadRequest("Your request was malformed... ID must not be null", HttpStatus.BAD_REQUEST, null);
					}
					Optional<Core_Accounts> account = core_Accounts_Repo.findById(core_Credentials.getAccount().getId());
					if(account.equals(Optional.empty())) {
				        return ResponseHandler.ResponseBadRequest("Your request was malformed! Foreign key required!", HttpStatus.BAD_REQUEST, null);
					}else {
						if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
							if(account.get().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								core_Credentials.setStart_date(LocalDateTime.now());
								core_Credentials.setPassword(Inputs_Validations.encrypt(core_Credentials.getPassword()));
								core_Credentials.setAccount(account.get());
								Core_Access_Credentials result = core_Credentials_Service.addCore_Credentials(core_Credentials);
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
									api_logs.setElement(6);
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
								core_Credentials.setStart_date(LocalDateTime.now());
								core_Credentials.setPassword(Inputs_Validations.encrypt(core_Credentials.getPassword()));
								core_Credentials.setAccount(account.get());
								Core_Access_Credentials result = core_Credentials_Service.addCore_Credentials(core_Credentials);
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
									api_logs.setElement(6);
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
	public ResponseEntity<Object> getCore_CredentialsById(@PathVariable(name="id") String id){	
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
				Optional<Core_Access_Credentials> result = core_Credentials_Service.findCore_CredentialsById(Long.parseLong(id));
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
								result.get().setPassword(Inputs_Validations.decrypt(result.get().getPassword()));
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
								result.get().setPassword(Inputs_Validations.decrypt(result.get().getPassword()));
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
	
	@GetMapping("/element/get/{element_id}")
	public ResponseEntity<Object> getAccess_CredentialsByElement(@PathVariable(name="element_id") String element_id){	
		if(Inputs_Validations.CheckInt(element_id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						List<Core_Access_Credentials> cred = core_Credentials_Service.getServersAccess_CredentialsByElementID(Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT), Long.parseLong(element_id));
							for(int i=0;i<cred.size();i++) {
								cred.get(i).setPassword(Inputs_Validations.decrypt(cred.get(i).getPassword()));
							}
						    return ResponseHandler.ResponseOk("Successfully retrieved data!", HttpStatus.OK, cred);
					}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
						List<Core_Access_Credentials> cred = core_Credentials_Service.getServersAccess_CredentialsByElementID(Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT), Long.parseLong(element_id));
							for(int i=0;i<cred.size();i++) {
								cred.get(i).setPassword(Inputs_Validations.decrypt(cred.get(i).getPassword()));
							}
						    return ResponseHandler.ResponseOk("Successfully retrieved data!", HttpStatus.OK, cred);
					}else {
				        return ResponseHandler.ResponseForbidden("Error... Account NOT Identified!", HttpStatus.FORBIDDEN, null);
					}
			}catch(Exception e) {
	            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updateCore_Credentials(@RequestBody Core_Access_Credentials core_Credentials,@PathVariable(name="id") String id, Errors errors){	
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
				Optional<Core_Access_Credentials> acc_cred = core_Credentials_Service.findCore_CredentialsById(Long.parseLong(id));
				if(acc_cred.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else if(acc_cred.get().getStatus() == 4 || acc_cred.get().getEnd_date() != null) {
		        	return ResponseHandler.ResponseForbidden("Resources has been deleted! We were unable to perform the request", HttpStatus.FORBIDDEN, null);
				}else if(acc_cred.get().getAccount() == null) {
			        return ResponseHandler.ResponseBadRequest("Your request was malformed! Foreign key required!", HttpStatus.BAD_REQUEST, null);
				}else {
					core_Credentials.setAccount(acc_cred.get().getAccount());
					
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"); 

					core_Credentials.setAccount(acc_cred.get().getAccount());
					core_Credentials.setStart_date(acc_cred.get().getStart_date());
					
					if(core_Credentials.getEnd_date() == null) {
						core_Credentials.setEnd_date(acc_cred.get().getEnd_date());
					}
					if(core_Credentials.getStatus() == 0) {
						core_Credentials.setStatus(acc_cred.get().getStatus());
					}
					if(core_Credentials.getClasse() == 0) {
						core_Credentials.setClasse(acc_cred.get().getClasse());
					}
					if(core_Credentials.getElement() == 0) {
						core_Credentials.setElement(acc_cred.get().getElement());
					}
					if(core_Credentials.getElement_id() == 0) {
						core_Credentials.setElement_id(acc_cred.get().getElement_id());
					}
					if(core_Credentials.getLogin() == null) {
						core_Credentials.setLogin(acc_cred.get().getLogin());
					}
					if(core_Credentials.getName() == null) {
						core_Credentials.setName(acc_cred.get().getName());
					}
					if(core_Credentials.getPassword() == null) {
						core_Credentials.setPassword(Inputs_Validations.decrypt(acc_cred.get().getPassword()));
					}
					if(core_Credentials.getPort() == null) {
						core_Credentials.setPort(acc_cred.get().getPort());
					}
					if(core_Credentials.getUrl() == null) {
						core_Credentials.setUrl(acc_cred.get().getUrl());
					}
					if(acc_cred.get().getPort() == null && acc_cred.get().getUrl() != null) {
						if(acc_cred.get().getAccount() == core_Credentials.getAccount() && acc_cred.get().getStart_date().toString().equals(core_Credentials.getStart_date().format(formatter)) && core_Credentials.getEnd_date() == null &&
								acc_cred.get().getStatus() == core_Credentials.getStatus() && acc_cred.get().getClasse() == core_Credentials.getClasse() && acc_cred.get().getElement() == core_Credentials.getElement() && acc_cred.get().getElement_id() == core_Credentials.getElement_id() && 
								acc_cred.get().getLogin().equals(core_Credentials.getLogin()) && acc_cred.get().getName().equals(core_Credentials.getName()) && (acc_cred.get().getPassword().equals(Inputs_Validations.encrypt(core_Credentials.getPassword())) || acc_cred.get().getPassword().equals(core_Credentials.getPassword())) && acc_cred.get().getPort() == core_Credentials.getPort()
								&& acc_cred.get().getUrl().equals(core_Credentials.getUrl())) {
							return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
					}else if(acc_cred.get().getUrl() == null && acc_cred.get().getPort() != null) {
						if(acc_cred.get().getAccount() == core_Credentials.getAccount() && acc_cred.get().getStart_date().toString().equals(core_Credentials.getStart_date().format(formatter)) && core_Credentials.getEnd_date() == null &&
								acc_cred.get().getStatus() == core_Credentials.getStatus() && acc_cred.get().getClasse() == core_Credentials.getClasse() && acc_cred.get().getElement() == core_Credentials.getElement() && acc_cred.get().getElement_id() == core_Credentials.getElement_id() && 
								acc_cred.get().getLogin().equals(core_Credentials.getLogin()) && acc_cred.get().getName().equals(core_Credentials.getName()) && (acc_cred.get().getPassword().equals(Inputs_Validations.encrypt(core_Credentials.getPassword())) || acc_cred.get().getPassword().equals(core_Credentials.getPassword())) && acc_cred.get().getPort().equals(core_Credentials.getPort())
								&& acc_cred.get().getUrl() == core_Credentials.getUrl()) {
							return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						
					}else if(acc_cred.get().getUrl() == null && acc_cred.get().getPort() == null) {
						if(acc_cred.get().getAccount() == core_Credentials.getAccount() && acc_cred.get().getStart_date().toString().equals(core_Credentials.getStart_date().format(formatter)) && core_Credentials.getEnd_date() == null &&
								acc_cred.get().getStatus() == core_Credentials.getStatus() && acc_cred.get().getClasse() == core_Credentials.getClasse() && acc_cred.get().getElement() == core_Credentials.getElement() && acc_cred.get().getElement_id() == core_Credentials.getElement_id() && 
								acc_cred.get().getLogin().equals(core_Credentials.getLogin()) && acc_cred.get().getName().equals(core_Credentials.getName()) && (acc_cred.get().getPassword().equals(Inputs_Validations.encrypt(core_Credentials.getPassword())) || acc_cred.get().getPassword().equals(core_Credentials.getPassword())) && acc_cred.get().getPort() == core_Credentials.getPort()
								&& acc_cred.get().getUrl() == core_Credentials.getUrl()) {
							return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
					}else {
						if(acc_cred.get().getAccount() == core_Credentials.getAccount() && acc_cred.get().getStart_date().toString().equals(core_Credentials.getStart_date().format(formatter)) && core_Credentials.getEnd_date() == null &&
								acc_cred.get().getStatus() == core_Credentials.getStatus() && acc_cred.get().getClasse() == core_Credentials.getClasse() && acc_cred.get().getElement() == core_Credentials.getElement() && acc_cred.get().getElement_id() == core_Credentials.getElement_id() && 
								acc_cred.get().getLogin().equals(core_Credentials.getLogin()) && acc_cred.get().getName().equals(core_Credentials.getName()) && acc_cred.get().getPassword().equals(Inputs_Validations.encrypt(core_Credentials.getPassword())) && acc_cred.get().getPort().equals(core_Credentials.getPort())
								&& acc_cred.get().getUrl().equals(core_Credentials.getUrl())) {
							return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
					}

					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						if(acc_cred.get().getAccount() != null) {
							if(acc_cred.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								if(core_Credentials.getPassword() == null || core_Credentials.getPassword().equals(Inputs_Validations.decrypt(acc_cred.get().getPassword())) || core_Credentials.getPassword().equals(acc_cred.get().getPassword())) {
									core_Credentials.setPassword(acc_cred.get().getPassword());
								}else {
									core_Credentials.setPassword(Inputs_Validations.encrypt(core_Credentials.getPassword()));
								}
								core_Credentials.setId(Long.parseLong(id));
								Core_Access_Credentials result = core_Credentials_Service.updateCore_Credentials(core_Credentials);
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
									api_logs.setElement(6);
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
						if(acc_cred.get().getAccount() != null) {
							if(acc_cred.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								if(core_Credentials.getPassword() == null || core_Credentials.getPassword().equals(Inputs_Validations.decrypt(acc_cred.get().getPassword())) || core_Credentials.getPassword().equals(acc_cred.get().getPassword())) {
									core_Credentials.setPassword(acc_cred.get().getPassword());
								}else {
									core_Credentials.setPassword(Inputs_Validations.encrypt(core_Credentials.getPassword()));
								}
								core_Credentials.setId(Long.parseLong(id));
								Core_Access_Credentials result = core_Credentials_Service.updateCore_Credentials(core_Credentials);
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
									api_logs.setElement(6);
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
	public ResponseEntity<Object> deleteCore_Credentials(@PathVariable("id") String id){
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
				Optional<Core_Access_Credentials> acc_cred = core_Credentials_Service.findCore_CredentialsById(Long.parseLong(id));
				if(acc_cred.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else {
					if(acc_cred.get().getStatus() == 4 || acc_cred.get().getEnd_date() != null) {
			        	return ResponseHandler.ResponseForbidden("Resources already deleted! We were unable to perform the request", HttpStatus.FORBIDDEN, null);
					}
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						if(acc_cred.get().getAccount() != null) {
							if(acc_cred.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								String result = core_Credentials_Service.deleteCore_Credentials(Long.parseLong(id));
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
									api_logs.setElement(6);
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
						if(acc_cred.get().getAccount() != null) {
							if(acc_cred.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								String result = core_Credentials_Service.deleteCore_Credentials(Long.parseLong(id));
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
									api_logs.setElement(6);
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

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gcs.cmp.Exception.ResponseHandler;
import com.gcs.cmp.entity.Api_Keys;
import com.gcs.cmp.entity.Core_Accounts;
import com.gcs.cmp.entity.Core_Api_Logs;
import com.gcs.cmp.entity.Core_Users;
import com.gcs.cmp.interceptors.BasicAuthInterceptor;
import com.gcs.cmp.repository.Api_Keys_Repo;
import com.gcs.cmp.repository.Core_Accounts_Repo;
import com.gcs.cmp.repository.Core_Api_Logs_Repo;
import com.gcs.cmp.service.Core_Users_Service;
import com.gcs.cmp.validators.Inputs_Validations;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/core/users")
public class CoreUsers {

	@Autowired
	private Core_Users_Service core_Users_Service;
	
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
    
	@GetMapping("")
	public ResponseEntity<Object> getAllCore_Users(){	
		try {
			if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
				List <Core_Users> result = core_Users_Service.findCore_UsersList(Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT));	
				return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
			}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
				List <Core_Users> result = core_Users_Service.findCore_UsersList(Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT));	
				return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
			}else {
				return ResponseHandler.ResponseListKo("Error... Account NOT Identified!", 0, HttpStatus.FORBIDDEN, null);
			}
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@PostMapping("/create")
	public ResponseEntity<Object> addCore_Users(@Validated @RequestBody Core_Users core_Users,Errors errors){	
		if(ex != null) {
			handleException(ex);
		}
		if(errors.hasErrors()) {
            return ResponseHandler.ResponseBadRequest(errors.getFieldError().getField()+" "+errors.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST, null);
		}else {
			try {
				if(core_Users.getAccount() == null) {
			        return ResponseHandler.ResponseBadRequest("Your request was malformed! Foreign key required!", HttpStatus.BAD_REQUEST, null);
				}else {
					if(core_Users.getAccount().getId() == 0) {
				        return ResponseHandler.ResponseBadRequest("Your request was malformed... ID must not be null", HttpStatus.BAD_REQUEST, null);
					}
					Optional<Core_Accounts> account = core_Accounts_Repo.findById(core_Users.getAccount().getId());
					if(account.equals(Optional.empty())) {
				        return ResponseHandler.ResponseBadRequest("Your request was malformed! Foreign key required!", HttpStatus.BAD_REQUEST, null);
					}else {
						if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
							if(account.get().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								core_Users.setAccount(account.get());
								Core_Users result = core_Users_Service.addCore_Users(core_Users);	
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
									api_logs.setElement(21);
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
								core_Users.setAccount(account.get());
								Core_Users result = core_Users_Service.addCore_Users(core_Users);	
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
									api_logs.setElement(21);
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
	public ResponseEntity<Object> getCore_UsersById(@PathVariable(name="id") String id){	
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
				Optional<Core_Users> result = core_Users_Service.findCore_UsersId(Long.parseLong(id));
				if(result.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else {
					if(result.get().getStatus() == 4) {
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
	public ResponseEntity<Object> updateCore_Users(@RequestBody Core_Users core_Users,@PathVariable(name="id") String id, Errors errors){
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
				Optional<Core_Users> user = core_Users_Service.findCore_UsersId(Long.parseLong(id));
				if(user.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else if(user.get().getStatus() == 4) {
		        	return ResponseHandler.ResponseForbidden("Resources has been deleted! We were unable to perform the request", HttpStatus.FORBIDDEN, null);
				}else if(user.get().getAccount() == null) {
			        return ResponseHandler.ResponseBadRequest("Your request was malformed! Foreign key required!", HttpStatus.BAD_REQUEST, null);
				}else {
					
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"); 

					core_Users.setAccount(user.get().getAccount());
					
					if(core_Users.getBrowser() == null) {
						core_Users.setBrowser(user.get().getBrowser());
					}
					if(core_Users.getFirstname() == null) {
						core_Users.setFirstname(user.get().getFirstname());
					}
					if(core_Users.getHas_token() == 0) {
						core_Users.setHas_token(user.get().getHas_token());
					}
					if(core_Users.getIp_address() == null) {
						core_Users.setIp_address(user.get().getIp_address());
					}
					if(core_Users.getLanguage() == null) {
						core_Users.setLanguage(user.get().getLanguage());
					}
					if(core_Users.getLast_auth() == null) {
						core_Users.setLast_auth(user.get().getLast_auth());
					}
					if(core_Users.getLastname() == null) {
						core_Users.setLastname(user.get().getLastname());
					}
					if(core_Users.getPhoto() == null) {
						core_Users.setPhoto(user.get().getPhoto());
					}
					if(core_Users.getRole() == 0) {
						core_Users.setRole(user.get().getRole());
					}
					if(core_Users.getStatus() == 0) {
						core_Users.setStatus(user.get().getStatus());
					}
					if(core_Users.getTimezone() == null) {
						core_Users.setTimezone(user.get().getTimezone());
					}
					if(core_Users.getEmail() == null) {
						core_Users.setEmail(user.get().getEmail());
					}
					
					if(user.get().getLanguage() == null) {
						if(user.get().getPhoto() == null && user.get().getTimezone() != null) {
							if(user.get().getAccount() == core_Users.getAccount() && user.get().getBrowser().equals(core_Users.getBrowser()) && user.get().getFirstname().equals(core_Users.getFirstname()) &&  user.get().getHas_token() == core_Users.getHas_token() &&
									user.get().getIp_address().equals(core_Users.getIp_address()) && user.get().getLanguage() == core_Users.getLanguage() && user.get().getLast_auth().toString().equals(core_Users.getLast_auth().format(formatter)) && 
									user.get().getLastname().equals(core_Users.getLastname()) && user.get().getPhoto() == core_Users.getPhoto() && user.get().getRole() == core_Users.getRole() && user.get().getStatus() == core_Users.getStatus() && 
									user.get().getTimezone().equals(core_Users.getTimezone()) && user.get().getEmail().equals(core_Users.getEmail()) ) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
							
						}else if(user.get().getPhoto() != null && user.get().getTimezone() == null) {
							if(user.get().getAccount() == core_Users.getAccount() && user.get().getBrowser().equals(core_Users.getBrowser()) && user.get().getFirstname().equals(core_Users.getFirstname()) &&  user.get().getHas_token() == core_Users.getHas_token() &&
									user.get().getIp_address().equals(core_Users.getIp_address()) && user.get().getLanguage() == core_Users.getLanguage() && user.get().getLast_auth().toString().equals(core_Users.getLast_auth().format(formatter)) && 
									user.get().getLastname().equals(core_Users.getLastname()) && user.get().getPhoto().equals(core_Users.getPhoto()) && user.get().getRole() == core_Users.getRole() && user.get().getStatus() == core_Users.getStatus() && 
									user.get().getTimezone() == core_Users.getTimezone() ) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						
						}else if(user.get().getPhoto() == null && user.get().getTimezone() == null) {
							System.out.println("aaaaaaaaaa");
							if(user.get().getAccount() == core_Users.getAccount() && user.get().getBrowser().equals(core_Users.getBrowser()) && user.get().getFirstname().equals(core_Users.getFirstname()) &&  user.get().getHas_token() == core_Users.getHas_token() &&
									user.get().getIp_address().equals(core_Users.getIp_address()) && user.get().getLanguage() == core_Users.getLanguage() && user.get().getLast_auth().toString().equals(core_Users.getLast_auth().format(formatter)) && 
									user.get().getLastname().equals(core_Users.getLastname()) && user.get().getPhoto() == core_Users.getPhoto() && user.get().getRole() == core_Users.getRole() && user.get().getStatus() == core_Users.getStatus() && 
									user.get().getTimezone() == core_Users.getTimezone() ) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						
						}else {
							if(user.get().getAccount() == core_Users.getAccount() && user.get().getBrowser().equals(core_Users.getBrowser()) && user.get().getFirstname().equals(core_Users.getFirstname()) &&  user.get().getHas_token() == core_Users.getHas_token() &&
									user.get().getIp_address().equals(core_Users.getIp_address()) && user.get().getLanguage() == core_Users.getLanguage() && user.get().getLast_auth().toString().equals(core_Users.getLast_auth().format(formatter)) && 
									user.get().getLastname().equals(core_Users.getLastname()) && user.get().getPhoto().equals(core_Users.getPhoto()) && user.get().getRole() == core_Users.getRole() && user.get().getStatus() == core_Users.getStatus() && 
									user.get().getTimezone().equals(core_Users.getTimezone())) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
							}
						}
					}else {
						if(user.get().getPhoto() == null && user.get().getTimezone() != null) {
							if(user.get().getAccount() == core_Users.getAccount() && user.get().getBrowser().equals(core_Users.getBrowser()) && user.get().getFirstname().equals(core_Users.getFirstname()) &&  user.get().getHas_token() == core_Users.getHas_token() &&
									user.get().getIp_address().equals(core_Users.getIp_address()) && user.get().getLanguage().equals(core_Users.getLanguage()) && user.get().getLast_auth().toString().equals(core_Users.getLast_auth().format(formatter)) && 
									user.get().getLastname().equals(core_Users.getLastname()) && user.get().getPhoto() == core_Users.getPhoto() && user.get().getRole() == core_Users.getRole() && user.get().getStatus() == core_Users.getStatus() && 
									user.get().getTimezone().equals(core_Users.getTimezone()) && user.get().getEmail().equals(core_Users.getEmail()) ) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
							
						}else if(user.get().getPhoto() != null && user.get().getTimezone() == null) {
							if(user.get().getAccount() == core_Users.getAccount() && user.get().getBrowser().equals(core_Users.getBrowser()) && user.get().getFirstname().equals(core_Users.getFirstname()) &&  user.get().getHas_token() == core_Users.getHas_token() &&
									user.get().getIp_address().equals(core_Users.getIp_address()) && user.get().getLanguage().equals(core_Users.getLanguage()) && user.get().getLast_auth().toString().equals(core_Users.getLast_auth().format(formatter)) && 
									user.get().getLastname().equals(core_Users.getLastname()) && user.get().getPhoto().equals(core_Users.getPhoto()) && user.get().getRole() == core_Users.getRole() && user.get().getStatus() == core_Users.getStatus() && 
									user.get().getTimezone() == core_Users.getTimezone() ) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						
						}else if(user.get().getPhoto() == null && user.get().getTimezone() == null) {
							System.out.println("aaaaaaaaaa");
							if(user.get().getAccount() == core_Users.getAccount() && user.get().getBrowser().equals(core_Users.getBrowser()) && user.get().getFirstname().equals(core_Users.getFirstname()) &&  user.get().getHas_token() == core_Users.getHas_token() &&
									user.get().getIp_address().equals(core_Users.getIp_address()) && user.get().getLanguage().equals(core_Users.getLanguage()) && user.get().getLast_auth().toString().equals(core_Users.getLast_auth().format(formatter)) && 
									user.get().getLastname().equals(core_Users.getLastname()) && user.get().getPhoto() == core_Users.getPhoto() && user.get().getRole() == core_Users.getRole() && user.get().getStatus() == core_Users.getStatus() && 
									user.get().getTimezone() == core_Users.getTimezone() ) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						
						}else {
							if(user.get().getAccount() == core_Users.getAccount() && user.get().getBrowser().equals(core_Users.getBrowser()) && user.get().getFirstname().equals(core_Users.getFirstname()) &&  user.get().getHas_token() == core_Users.getHas_token() &&
									user.get().getIp_address().equals(core_Users.getIp_address()) && user.get().getLanguage().equals(core_Users.getLanguage()) && user.get().getLast_auth().toString().equals(core_Users.getLast_auth().format(formatter)) && 
									user.get().getLastname().equals(core_Users.getLastname()) && user.get().getPhoto().equals(core_Users.getPhoto()) && user.get().getRole() == core_Users.getRole() && user.get().getStatus() == core_Users.getStatus() && 
									user.get().getTimezone().equals(core_Users.getTimezone())) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
							}
						}
					}

					
					

					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						if(user.get().getAccount() != null) {
							if(user.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								core_Users.setId(Long.parseLong(id));
								Core_Users result = core_Users_Service.updateCore_Users(core_Users);
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
									api_logs.setElement(21);
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
						if(user.get().getAccount() != null) {
							if(user.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								core_Users.setId(Long.parseLong(id));
								Core_Users result = core_Users_Service.updateCore_Users(core_Users);
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
									api_logs.setElement(21);
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
	
	@PutMapping("/other/update/{id}")
	public ResponseEntity<Object> updateUser(@RequestParam String users, @PathVariable(name="id") String id, @RequestParam("file") MultipartFile file)throws Exception{
		if(ex != null) {
			handleException(ex);
		}
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}
		else {
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.registerModule(new JavaTimeModule());
				Core_Users core_Users = objectMapper.readValue(users,Core_Users.class);
				Optional<Core_Users> user = core_Users_Service.findCore_UsersId(Long.parseLong(id));
				if(user.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else if(user.get().getStatus() == 4) {
		        	return ResponseHandler.ResponseForbidden("Resources has been deleted! We were unable to perform the request", HttpStatus.FORBIDDEN, null);
				}else if(user.get().getAccount() == null) {
			        return ResponseHandler.ResponseBadRequest("Your request was malformed! Foreign key required!", HttpStatus.BAD_REQUEST, null);
				}else {
					
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"); 

					core_Users.setAccount(user.get().getAccount());
					
					if(core_Users.getBrowser() == null) {
						core_Users.setBrowser(user.get().getBrowser());
					}
					if(core_Users.getFirstname() == null) {
						core_Users.setFirstname(user.get().getFirstname());
					}
					if(core_Users.getHas_token() == 0) {
						core_Users.setHas_token(user.get().getHas_token());
					}
					if(core_Users.getIp_address() == null) {
						core_Users.setIp_address(user.get().getIp_address());
					}
					if(core_Users.getLanguage() == null) {
						core_Users.setLanguage(user.get().getLanguage());
					}
					if(core_Users.getLast_auth() == null) {
						core_Users.setLast_auth(user.get().getLast_auth());
					}
					if(core_Users.getLastname() == null) {
						core_Users.setLastname(user.get().getLastname());
					}
					if(core_Users.getPhoto() == null) {
						core_Users.setPhoto(user.get().getPhoto());
					}
					if(core_Users.getRole() == 0) {
						core_Users.setRole(user.get().getRole());
					}
					if(core_Users.getStatus() == 0) {
						core_Users.setStatus(user.get().getStatus());
					}
					if(core_Users.getTimezone() == null) {
						core_Users.setTimezone(user.get().getTimezone());
					}
					if(core_Users.getEmail() == null) {
						core_Users.setEmail(user.get().getEmail());
					}
					
					if(user.get().getLanguage() == null) {
						if(user.get().getPhoto() == null && user.get().getTimezone() != null) {
							if(user.get().getAccount() == core_Users.getAccount() && user.get().getBrowser().equals(core_Users.getBrowser()) && user.get().getFirstname().equals(core_Users.getFirstname()) &&  user.get().getHas_token() == core_Users.getHas_token() &&
									user.get().getIp_address().equals(core_Users.getIp_address()) && user.get().getLanguage() == core_Users.getLanguage() && user.get().getLast_auth().toString().equals(core_Users.getLast_auth().format(formatter)) && 
									user.get().getLastname().equals(core_Users.getLastname()) && user.get().getPhoto() == core_Users.getPhoto() && user.get().getRole() == core_Users.getRole() && user.get().getStatus() == core_Users.getStatus() && 
									user.get().getTimezone().equals(core_Users.getTimezone()) && user.get().getEmail().equals(core_Users.getEmail()) ) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
							
						}else if(user.get().getPhoto() != null && user.get().getTimezone() == null) {
							if(user.get().getAccount() == core_Users.getAccount() && user.get().getBrowser().equals(core_Users.getBrowser()) && user.get().getFirstname().equals(core_Users.getFirstname()) &&  user.get().getHas_token() == core_Users.getHas_token() &&
									user.get().getIp_address().equals(core_Users.getIp_address()) && user.get().getLanguage() == core_Users.getLanguage() && user.get().getLast_auth().toString().equals(core_Users.getLast_auth().format(formatter)) && 
									user.get().getLastname().equals(core_Users.getLastname()) && user.get().getPhoto().equals(core_Users.getPhoto()) && user.get().getRole() == core_Users.getRole() && user.get().getStatus() == core_Users.getStatus() && 
									user.get().getTimezone() == core_Users.getTimezone() ) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						
						}else if(user.get().getPhoto() == null && user.get().getTimezone() == null) {
							System.out.println("aaaaaaaaaa");
							if(user.get().getAccount() == core_Users.getAccount() && user.get().getBrowser().equals(core_Users.getBrowser()) && user.get().getFirstname().equals(core_Users.getFirstname()) &&  user.get().getHas_token() == core_Users.getHas_token() &&
									user.get().getIp_address().equals(core_Users.getIp_address()) && user.get().getLanguage() == core_Users.getLanguage() && user.get().getLast_auth().toString().equals(core_Users.getLast_auth().format(formatter)) && 
									user.get().getLastname().equals(core_Users.getLastname()) && user.get().getPhoto() == core_Users.getPhoto() && user.get().getRole() == core_Users.getRole() && user.get().getStatus() == core_Users.getStatus() && 
									user.get().getTimezone() == core_Users.getTimezone() ) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						
						}else {
							if(user.get().getAccount() == core_Users.getAccount() && user.get().getBrowser().equals(core_Users.getBrowser()) && user.get().getFirstname().equals(core_Users.getFirstname()) &&  user.get().getHas_token() == core_Users.getHas_token() &&
									user.get().getIp_address().equals(core_Users.getIp_address()) && user.get().getLanguage() == core_Users.getLanguage() && user.get().getLast_auth().toString().equals(core_Users.getLast_auth().format(formatter)) && 
									user.get().getLastname().equals(core_Users.getLastname()) && user.get().getPhoto().equals(core_Users.getPhoto()) && user.get().getRole() == core_Users.getRole() && user.get().getStatus() == core_Users.getStatus() && 
									user.get().getTimezone().equals(core_Users.getTimezone())) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
							}
						}
					}else {
						if(user.get().getPhoto() == null && user.get().getTimezone() != null) {
							if(user.get().getAccount() == core_Users.getAccount() && user.get().getBrowser().equals(core_Users.getBrowser()) && user.get().getFirstname().equals(core_Users.getFirstname()) &&  user.get().getHas_token() == core_Users.getHas_token() &&
									user.get().getIp_address().equals(core_Users.getIp_address()) && user.get().getLanguage().equals(core_Users.getLanguage()) && user.get().getLast_auth().toString().equals(core_Users.getLast_auth().format(formatter)) && 
									user.get().getLastname().equals(core_Users.getLastname()) && user.get().getPhoto() == core_Users.getPhoto() && user.get().getRole() == core_Users.getRole() && user.get().getStatus() == core_Users.getStatus() && 
									user.get().getTimezone().equals(core_Users.getTimezone()) && user.get().getEmail().equals(core_Users.getEmail()) ) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
							
						}else if(user.get().getPhoto() != null && user.get().getTimezone() == null) {
							if(user.get().getAccount() == core_Users.getAccount() && user.get().getBrowser().equals(core_Users.getBrowser()) && user.get().getFirstname().equals(core_Users.getFirstname()) &&  user.get().getHas_token() == core_Users.getHas_token() &&
									user.get().getIp_address().equals(core_Users.getIp_address()) && user.get().getLanguage().equals(core_Users.getLanguage()) && user.get().getLast_auth().toString().equals(core_Users.getLast_auth().format(formatter)) && 
									user.get().getLastname().equals(core_Users.getLastname()) && user.get().getPhoto().equals(core_Users.getPhoto()) && user.get().getRole() == core_Users.getRole() && user.get().getStatus() == core_Users.getStatus() && 
									user.get().getTimezone() == core_Users.getTimezone() ) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						
						}else if(user.get().getPhoto() == null && user.get().getTimezone() == null) {
							System.out.println("aaaaaaaaaa");
							if(user.get().getAccount() == core_Users.getAccount() && user.get().getBrowser().equals(core_Users.getBrowser()) && user.get().getFirstname().equals(core_Users.getFirstname()) &&  user.get().getHas_token() == core_Users.getHas_token() &&
									user.get().getIp_address().equals(core_Users.getIp_address()) && user.get().getLanguage().equals(core_Users.getLanguage()) && user.get().getLast_auth().toString().equals(core_Users.getLast_auth().format(formatter)) && 
									user.get().getLastname().equals(core_Users.getLastname()) && user.get().getPhoto() == core_Users.getPhoto() && user.get().getRole() == core_Users.getRole() && user.get().getStatus() == core_Users.getStatus() && 
									user.get().getTimezone() == core_Users.getTimezone() ) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
						}
						
						}else {
							if(user.get().getAccount() == core_Users.getAccount() && user.get().getBrowser().equals(core_Users.getBrowser()) && user.get().getFirstname().equals(core_Users.getFirstname()) &&  user.get().getHas_token() == core_Users.getHas_token() &&
									user.get().getIp_address().equals(core_Users.getIp_address()) && user.get().getLanguage().equals(core_Users.getLanguage()) && user.get().getLast_auth().toString().equals(core_Users.getLast_auth().format(formatter)) && 
									user.get().getLastname().equals(core_Users.getLastname()) && user.get().getPhoto().equals(core_Users.getPhoto()) && user.get().getRole() == core_Users.getRole() && user.get().getStatus() == core_Users.getStatus() && 
									user.get().getTimezone().equals(core_Users.getTimezone())) {
				        	return ResponseHandler.ResponseOk("Nothing was changed!", HttpStatus.OK, null);
							}
						}
					}

					
					

					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						if(user.get().getAccount() != null) {
							if(user.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								core_Users.setId(Long.parseLong(id));
								Core_Users result = core_Users_Service.updateUser(users, file);
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
									api_logs.setElement(21);
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
						if(user.get().getAccount() != null) {
							if(user.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								core_Users.setId(Long.parseLong(id));
								Core_Users result = core_Users_Service.updateUser(users, file);
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
									api_logs.setElement(21);
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
	public ResponseEntity<Object> deleteCore_Users(@PathVariable("id") String id){
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
				Optional<Core_Users> user = core_Users_Service.findCore_UsersId(Long.parseLong(id));
				if(user.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else {
					if(user.get().getStatus() == 4) {
			        	return ResponseHandler.ResponseForbidden("Resources already deleted! We were unable to perform the request", HttpStatus.FORBIDDEN, null);
					}
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						if(user.get().getAccount() != null) {
							if(user.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								String result = core_Users_Service.deleteCore_Users(Long.parseLong(id));
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
									api_logs.setElement(21);
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
						if(user.get().getAccount() != null) {
							if(user.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								String result = core_Users_Service.deleteCore_Users(Long.parseLong(id));
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
									api_logs.setElement(21);
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

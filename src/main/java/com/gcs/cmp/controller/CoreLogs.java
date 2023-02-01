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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.gcs.cmp.Exception.ResponseHandler;
import com.gcs.cmp.entity.Api_Keys;
import com.gcs.cmp.entity.Core_Api_Logs;
import com.gcs.cmp.entity.Core_Logs;
import com.gcs.cmp.entity.Core_Users;
import com.gcs.cmp.interceptors.BasicAuthInterceptor;
import com.gcs.cmp.repository.Api_Keys_Repo;
import com.gcs.cmp.repository.Core_Api_Logs_Repo;
import com.gcs.cmp.repository.Core_Users_Repo;
import com.gcs.cmp.service.Core_Logs_Service;
import com.gcs.cmp.validators.Inputs_Validations;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/core/logs")
public class CoreLogs {

	@Autowired
	private Core_Logs_Service core_Logs_Service;
	
	@Autowired
	private Core_Api_Logs_Repo core_Api_Logs_Repo;
	
	@Autowired
	private Api_Keys_Repo api_Keys_Repo;
	
	@Autowired
	private Core_Users_Repo core_Users_Repo;
	
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
	public ResponseEntity<Object> getAllCore_Logs(){	
		try {
			if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
				List <Core_Logs> result = core_Logs_Service.getCore_LogsList(Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT));	
				return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
			}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
				List <Core_Logs> result = core_Logs_Service.getCore_LogsList(Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT));	
				return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
			}else {
				return ResponseHandler.ResponseListKo("Error... Account NOT Identified!", 0, HttpStatus.FORBIDDEN, null);
			}
		}catch(Exception e) {
            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
	}
	
	@PostMapping("/create")
	public ResponseEntity<Object> addCore_Logs(@Validated @RequestBody Core_Logs core_Logs,Errors errors){
		if(ex != null) {
			handleException(ex);
		}
		if(errors.hasErrors()) {
            return ResponseHandler.ResponseBadRequest(errors.getFieldError().getField()+" "+errors.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST, null);
		}else {
			try {
				if(core_Logs.getUser() == null) {
			        return ResponseHandler.ResponseBadRequest("Your request was malformed! Foreign key required!", HttpStatus.BAD_REQUEST, null);
				}else {
					if(core_Logs.getUser().getId() == 0) {
				        return ResponseHandler.ResponseBadRequest("Your request was malformed... ID must not be null", HttpStatus.BAD_REQUEST, null);
					}
					Optional<Core_Users> user = core_Users_Repo.findById(core_Logs.getUser().getId());
					if(user.equals(Optional.empty())) {
				        return ResponseHandler.ResponseBadRequest("Your request was malformed! Foreign key required!", HttpStatus.BAD_REQUEST, null);
					}else {
						if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
							if(user.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								core_Logs.setUser(user.get());
								Core_Logs result = core_Logs_Service.addCore_Logs(core_Logs);	
								if(result != null) {
									//core logs :50
									Core_Api_Logs api_logs = new Core_Api_Logs();
									
									Api_Keys api_key = api_Keys_Repo.findById(Long.parseLong(BasicAuthInterceptor.GLOBAL_APIKEY)).get();
									
									api_logs.setAction("create");
									api_logs.setApikey(api_key);
									api_logs.setElement(50);
									api_logs.setElement_id((int)result.getId());
									api_logs.setLog_date(LocalDateTime.now());
									core_Api_Logs_Repo.save(api_logs);
							        return ResponseHandler.ResponseOk("Successfully added data!", HttpStatus.OK, result);
								}else {
							        return ResponseHandler.ResponseBadRequest("Your request was malformed!", HttpStatus.BAD_REQUEST, null);
								}
							}
						}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
							if(user.get().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								core_Logs.setUser(user.get());
								Core_Logs result = core_Logs_Service.addCore_Logs(core_Logs);	
								if(result != null) {
									//core logs :50
									Core_Api_Logs api_logs = new Core_Api_Logs();
									
									Api_Keys api_key = api_Keys_Repo.findById(Long.parseLong(BasicAuthInterceptor.GLOBAL_APIKEY)).get();
									
									api_logs.setAction("create");
									api_logs.setApikey(api_key);
									api_logs.setElement(50);
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
	public ResponseEntity<Object> getCore_LogsById(@PathVariable(name="id") String id){	
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
				Optional<Core_Logs> result = core_Logs_Service.findCore_LogsById(Long.parseLong(id));
				if(result.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else {
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						if(result.get().getUser().getAccount() != null) {
							if(result.get().getUser().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
						        return ResponseHandler.ResponseOk("Successfully retrieved data!", HttpStatus.OK, result);
							}
						}else {
				        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
						}
					}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
						if(result.get().getUser().getAccount() != null) {
							if(result.get().getUser().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
						        return ResponseHandler.ResponseOk("Successfully retrieved data!", HttpStatus.OK, result);
							}
						}else {
				        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
						}
					}else{
				        return ResponseHandler.ResponseForbidden("Error... Account NOT Identified!", HttpStatus.FORBIDDEN, null);
					}
				}	
			}catch(Exception e) {
	            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }
		}
		
	}
	
	@GetMapping("/list/get/{user_id}")
	public ResponseEntity<Object> getLogsListByUserID(@PathVariable(name="user_id") String user_id){	
		if(Inputs_Validations.CheckInt(user_id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						List<Core_Logs> result = core_Logs_Service.getLogsListByUserID(Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT), Long.parseLong(user_id));
						return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);

					}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
						List<Core_Logs> result = core_Logs_Service.getLogsListByUserID(Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT), Long.parseLong(user_id));
						return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
					}else{
				        return ResponseHandler.ResponseForbidden("Error... Account NOT Identified!", HttpStatus.FORBIDDEN, null);
					}
			}catch(Exception e) {
	            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }
		}
		
	}
	
	@GetMapping("/user/get/{user_id}")
	public ResponseEntity<Object> getUserLogsList(@PathVariable(name="user_id") String user_id){	
		if(Inputs_Validations.CheckInt(user_id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						List<Core_Logs> result = core_Logs_Service.getUserLogsList(Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT), Long.parseLong(user_id));
						return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);

					}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
						List<Core_Logs> result = core_Logs_Service.getUserLogsList(Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT), Long.parseLong(user_id));
						return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
					}else{
				        return ResponseHandler.ResponseForbidden("Error... Account NOT Identified!", HttpStatus.FORBIDDEN, null);
					}
			}catch(Exception e) {
	            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }
		}
		
	}
	
	@GetMapping("/provider/get/{provider_id}")
	public ResponseEntity<Object> getProviderLogsList(@PathVariable(name="provider_id") String provider_id){	
		if(Inputs_Validations.CheckInt(provider_id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						List<Core_Logs> result = core_Logs_Service.getProviderLogsList(Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT), Long.parseLong(provider_id));
						return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);

					}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
						List<Core_Logs> result = core_Logs_Service.getProviderLogsList(Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT), Long.parseLong(provider_id));
						return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
					}else{
				        return ResponseHandler.ResponseForbidden("Error... Account NOT Identified!", HttpStatus.FORBIDDEN, null);
					}
			}catch(Exception e) {
	            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }
		}
		
	}
	
	@GetMapping("/server/get/{instance_id}")
	public ResponseEntity<Object> getServerLogsList(@PathVariable(name="instance_id") String instance_id){	
		if(Inputs_Validations.CheckInt(instance_id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						List<Core_Logs> result = core_Logs_Service.getServerLogsList(Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT), Long.parseLong(instance_id));
						return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);

					}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
						List<Core_Logs> result = core_Logs_Service.getServerLogsList(Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT), Long.parseLong(instance_id));
						return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
					}else{
				        return ResponseHandler.ResponseForbidden("Error... Account NOT Identified!", HttpStatus.FORBIDDEN, null);
					}
			}catch(Exception e) {
	            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }
		}
		
	}
	
	@GetMapping("/domain/get/{domain_id}")
	public ResponseEntity<Object> getDomainNamesLogsList(@PathVariable(name="domain_id") String domain_id){	
		if(Inputs_Validations.CheckInt(domain_id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						List<Core_Logs> result = core_Logs_Service.getDomainNamesLogsList(Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT), Long.parseLong(domain_id));
						return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);

					}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
						List<Core_Logs> result = core_Logs_Service.getDomainNamesLogsList(Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT), Long.parseLong(domain_id));
						return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
					}else{
				        return ResponseHandler.ResponseForbidden("Error... Account NOT Identified!", HttpStatus.FORBIDDEN, null);
					}
			}catch(Exception e) {
	            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }
		}
		
	}
	
	@GetMapping("/application/get/{application_id}")
	public ResponseEntity<Object> getApplicationLogsList(@PathVariable(name="application_id") String application_id){	
		if(Inputs_Validations.CheckInt(application_id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						List<Core_Logs> result = core_Logs_Service.getApplicationLogsList(Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT), Long.parseLong(application_id));
						return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);

					}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
						List<Core_Logs> result = core_Logs_Service.getApplicationLogsList(Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT), Long.parseLong(application_id));
						return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
					}else{
				        return ResponseHandler.ResponseForbidden("Error... Account NOT Identified!", HttpStatus.FORBIDDEN, null);
					}
			}catch(Exception e) {
	            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }
		}
		
	}
	
	@GetMapping("/backup/get/{backup_id}")
	public ResponseEntity<Object> getBackupLogsList(@PathVariable(name="backup_id") String backup_id){	
		if(Inputs_Validations.CheckInt(backup_id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						List<Core_Logs> result = core_Logs_Service.getBackupLogsList(Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT), Long.parseLong(backup_id));
						return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);

					}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
						List<Core_Logs> result = core_Logs_Service.getBackupLogsList(Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT), Long.parseLong(backup_id));
						return ResponseHandler.ResponseListOk("Successfully retrieved data!", result.size(), HttpStatus.OK, result);
					}else{
				        return ResponseHandler.ResponseForbidden("Error... Account NOT Identified!", HttpStatus.FORBIDDEN, null);
					}
			}catch(Exception e) {
	            return ResponseHandler.ResponseMulti(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }
		}
		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteCore_Logs(@PathVariable("id") String id){
		if(Inputs_Validations.CheckInt(id) == false) {
            return ResponseHandler.ResponseMulti("PATH malformed!", HttpStatus.MULTI_STATUS, null);
		}else {
			try {
				Optional<Core_Logs> logs = core_Logs_Service.findCore_LogsById(Long.parseLong(id));
				if(logs.equals(Optional.empty())) {
		        	return ResponseHandler.ResponseNotFound("No results were found for id "+id, HttpStatus.NOT_FOUND, null);
				}else {
					if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
						if(logs.get().getUser().getAccount() != null) {
							if(logs.get().getUser().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								String result = core_Logs_Service.deleteCore_Logs(Long.parseLong(id));
								if(result != null) {
									//core logs :50
									Core_Api_Logs api_logs = new Core_Api_Logs();
									
									Api_Keys api_key = api_Keys_Repo.findById(Long.parseLong(BasicAuthInterceptor.GLOBAL_APIKEY)).get();
									
									api_logs.setAction("delete");
									api_logs.setApikey(api_key);
									api_logs.setElement(50);
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
						if(logs.get().getUser().getAccount() != null) {
							if(logs.get().getUser().getAccount().getId() != Long.parseLong(BasicAuthInterceptor.GLOBAL_ACCOUNT)) {
					        	return ResponseHandler.ResponseForbidden("You are not allowed to perform that action!", HttpStatus.FORBIDDEN, null);
							}else {
								String result = core_Logs_Service.deleteCore_Logs(Long.parseLong(id));
								if(result != null) {
									//core logs :50
									Core_Api_Logs api_logs = new Core_Api_Logs();
									
									Api_Keys api_key = api_Keys_Repo.findById(Long.parseLong(BasicAuthInterceptor.GLOBAL_APIKEY)).get();
									
									api_logs.setAction("delete");
									api_logs.setApikey(api_key);
									api_logs.setElement(50);
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

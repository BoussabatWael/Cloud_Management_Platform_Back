package com.gcs.cmp.providers.DigitalOcean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gcs.cmp.Exception.ResponseHandler;
import com.gcs.cmp.interceptors.BasicAuthInterceptor;
import com.gcs.cmp.providers.Provider;
import com.google.gson.Gson;

@Service
public class DigitalOcean_ServiceImpl implements DigitalOcean_Service{

	RestTemplate restTemplate  = new RestTemplate();

	@Override
	public Object getActionsList() throws SQLException {
		/*
    	String url = "https://api.digitalocean.com/v2/actions";   	
    	if (BasicAuthInterceptor.authHeader != null && BasicAuthInterceptor.authHeader != "undefined" && BasicAuthInterceptor.authHeader.toLowerCase().startsWith("bearer")) {
	    	HttpHeaders header = new HttpHeaders();
	    	header.add("Authorization", BasicAuthInterceptor.authHeader);
	        header.setContentType(MediaType.APPLICATION_JSON);
	    	HttpEntity<Object> entity = new HttpEntity<>(header);
			try {			    
				ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
				Object res = result.getBody();					
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("actions")) {
							JSONObject jsonObj = new JSONObject(js);								
							JSONArray actions = jsonObj.getJSONArray("actions");
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", actions.length(), HttpStatus.OK, actions.toList());
						}else {
							return ResponseHandler.ResponseListOk("Emtpy result.", 0, HttpStatus.OK, new ArrayList<>());
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}			
			}catch(Exception e) {
				return ResponseHandler.ResponseOk(e.getMessage(), HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
		*/
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/actions";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();	
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("actions")) {
							JSONObject jsonObj = new JSONObject(js);								
							JSONArray actions = jsonObj.getJSONArray("actions");
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", actions.length(), HttpStatus.OK, actions.toList());
						}else {
							return ResponseHandler.ResponseListOk("Emtpy result.", 0, HttpStatus.OK, new ArrayList<>());
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/actions";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();					
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("actions")) {
							JSONObject jsonObj = new JSONObject(js);								
							JSONArray actions = jsonObj.getJSONArray("actions");
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", actions.length(), HttpStatus.OK, actions.toList());
						}else {
							return ResponseHandler.ResponseListOk("Emtpy result.", 0, HttpStatus.OK, new ArrayList<>());
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public Object getAppsList() throws SQLException {
		/*
    	String url = "https://api.digitalocean.com/v2/apps";
    	if (BasicAuthInterceptor.authHeader != null && BasicAuthInterceptor.authHeader != "undefined" && BasicAuthInterceptor.authHeader.toLowerCase().startsWith("bearer")) {
	    	HttpHeaders header = new HttpHeaders();
	    	header.add("Authorization", BasicAuthInterceptor.authHeader);
	        header.setContentType(MediaType.APPLICATION_JSON);
	    	HttpEntity<Object> entity = new HttpEntity<>(header);
			try {			    
				ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
				Object res = result.getBody();					
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("apps")) {
							JSONObject jsonObj = new JSONObject(js);								
							JSONArray applicationsList = jsonObj.getJSONArray("apps");
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", applicationsList.length(), HttpStatus.OK, applicationsList.toList());	
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());	
						}	
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}	
			}catch(Exception e) {
				return ResponseHandler.ResponseOk(e.getMessage(), HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
    	*/
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/apps";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();		
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("apps")) {
							JSONObject jsonObj = new JSONObject(js);								
							JSONArray applicationsList = jsonObj.getJSONArray("apps");
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", applicationsList.length(), HttpStatus.OK, applicationsList.toList());	
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());	
						}	
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/apps";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();					
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("apps")) {
							JSONObject jsonObj = new JSONObject(js);								
							JSONArray applicationsList = jsonObj.getJSONArray("apps");
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", applicationsList.length(), HttpStatus.OK, applicationsList.toList());	
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());	
						}	
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public Object getExistingApp(String id) throws SQLException {
		/*
    	String url = "https://api.digitalocean.com/v2/apps/"+id;
    	if (BasicAuthInterceptor.authHeader != null && BasicAuthInterceptor.authHeader != "undefined" && BasicAuthInterceptor.authHeader.toLowerCase().startsWith("bearer")) {
	    	HttpHeaders header = new HttpHeaders();
	    	header.add("Authorization", BasicAuthInterceptor.authHeader);
	        header.setContentType(MediaType.APPLICATION_JSON);
	    	HttpEntity<Object> entity = new HttpEntity<>(header);
			try {			    
				ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
				Object res = result.getBody();					
				Gson gson = new Gson();					
				String js = gson.toJson(res);
				if(js.contains("app")) {
					JSONObject jsonObj = new JSONObject(js);	
					Map<String, Object> app = jsonObj.getJSONObject("app").toMap();				
					return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, app);
				}else {
					return ResponseHandler.ResponseOk("Empty result.", HttpStatus.OK, null);
				}			
			}catch(Exception e) {
				return ResponseHandler.ResponseOk(e.getMessage(), HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
    	*/
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/apps/"+id;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("app")) {
							JSONObject jsonObj = new JSONObject(js);	
							Map<String, Object> app = jsonObj.getJSONObject("app").toMap();				
							return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, app);
						}else {
							return ResponseHandler.ResponseOk("Empty result.", HttpStatus.OK, null);
						}	
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/apps/"+id;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();					
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("app")) {
							JSONObject jsonObj = new JSONObject(js);	
							Map<String, Object> app = jsonObj.getJSONObject("app").toMap();				
							return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, app);
						}else {
							return ResponseHandler.ResponseOk("Empty result.", HttpStatus.OK, null);
						}	
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}	
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.UNAUTHORIZED, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public Object getCustomerBalance() throws SQLException {

		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/customers/my/balance";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						Map<String, Object> customerBalance = new JSONObject(js).toMap();
						return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, customerBalance);
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/customers/my/balance";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();					
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						Map<String, Object> customerBalance = new JSONObject(js).toMap();
						return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, customerBalance);
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public Object getBillingHistoryList() throws SQLException {

		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/customers/my/billing_history";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();	
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("billing_history")) {
							JSONObject jsonObj = new JSONObject(js);	
							JSONArray billing_history = jsonObj.getJSONArray("billing_history");				
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", billing_history.length(), HttpStatus.OK, billing_history.toList());
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
						}	
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/customers/my/billing_history";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();					
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("billing_history")) {
							JSONObject jsonObj = new JSONObject(js);	
							JSONArray billing_history = jsonObj.getJSONArray("billing_history");				
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", billing_history.length(), HttpStatus.OK, billing_history.toList());
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
						}	
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public Object getInvoicesList() throws SQLException {

		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/customers/my/invoices";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("invoices")) {
							JSONObject jsonObj = new JSONObject(js);	
							JSONArray invoices = jsonObj.getJSONArray("invoices");				
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", invoices.length(), HttpStatus.OK, invoices.toList());		
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());		
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}	
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/customers/my/invoices";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();					
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("invoices")) {
							JSONObject jsonObj = new JSONObject(js);	
							JSONArray invoices = jsonObj.getJSONArray("invoices");				
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", invoices.length(), HttpStatus.OK, invoices.toList());		
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());		
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}		
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public Object getDomainRecordsList(String domain_name) throws SQLException {

		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/domains/"+domain_name+"/records";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("domain_records")) {
							JSONObject jsonObj = new JSONObject(js);	
							JSONArray domain_records = jsonObj.getJSONArray("domain_records");				
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", domain_records.length(), HttpStatus.OK, domain_records.toList());	
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());	
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/domains/"+domain_name+"/records";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();					
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("domain_records")) {
							JSONObject jsonObj = new JSONObject(js);	
							JSONArray domain_records = jsonObj.getJSONArray("domain_records");				
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", domain_records.length(), HttpStatus.OK, domain_records.toList());	
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());	
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public Object getExistingDomainRecord(String domain_name, Long domain_record_id) throws SQLException {

		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/domains/"+domain_name+"/records/"+domain_record_id;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("domain_record")) {
							JSONObject jsonObj = new JSONObject(js);	
							Map<String, Object> domain_record = jsonObj.getJSONObject("domain_record").toMap();				
							return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, domain_record);	
						}else {
							return ResponseHandler.ResponseOk("Empty result.", HttpStatus.OK, null);	
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/domains/"+domain_name+"/records/"+domain_record_id;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();					
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("domain_record")) {
							JSONObject jsonObj = new JSONObject(js);	
							Map<String, Object> domain_record = jsonObj.getJSONObject("domain_record").toMap();				
							return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, domain_record);	
						}else {
							return ResponseHandler.ResponseOk("Empty result.", HttpStatus.OK, null);	
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public Object getDomainsList() throws SQLException {

		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/domains";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();		
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						JSONObject jsonObj = new JSONObject(js);
						if(js.contains("domains")) {
							JSONArray domains = jsonObj.getJSONArray("domains");				
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", domains.length(), HttpStatus.OK, domains.toList());	
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());	
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/domains";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();					
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						JSONObject jsonObj = new JSONObject(js);
						if(js.contains("domains")) {
							JSONArray domains = jsonObj.getJSONArray("domains");				
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", domains.length(), HttpStatus.OK, domains.toList());	
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());	
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public Object getExistingDomain(String domain_name) throws SQLException {
		
		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/domains/"+domain_name;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();	
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("domain")) {
							JSONObject jsonObj = new JSONObject(js);	
							Map<String, Object> domain = jsonObj.getJSONObject("domain").toMap();				
							return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, domain);
						}else {
							return ResponseHandler.ResponseOk("Empty result.", HttpStatus.OK, null);
						}	
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/domains/"+domain_name;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();					
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("domain")) {
							JSONObject jsonObj = new JSONObject(js);	
							Map<String, Object> domain = jsonObj.getJSONObject("domain").toMap();				
							return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, domain);
						}else {
							return ResponseHandler.ResponseOk("Empty result.", HttpStatus.OK, null);
						}	
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}	
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public Object getActionsDropletList(Long droplet_id) throws SQLException {

		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/droplets/"+droplet_id+"/actions";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();				
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("actions")) {
							JSONObject jsonObj = new JSONObject(js);	
							JSONArray actions = jsonObj.getJSONArray("actions");				
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", actions.length(), HttpStatus.OK, actions.toList());
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
						}	
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/droplets/"+droplet_id+"/actions";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();					
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("actions")) {
							JSONObject jsonObj = new JSONObject(js);	
							JSONArray actions = jsonObj.getJSONArray("actions");				
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", actions.length(), HttpStatus.OK, actions.toList());
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
						}	
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}	
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.UNAUTHORIZED, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}
	
	@Override
	public Object getDropletsList() throws SQLException {

		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/droplets";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();	
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("droplets")) {
							JSONObject jsonObj = new JSONObject(js);	
							JSONArray droplets = jsonObj.getJSONArray("droplets");				
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", droplets.length(), HttpStatus.OK, droplets.toList());		
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());		
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}	
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/droplets";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();					
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("droplets")) {
							JSONObject jsonObj = new JSONObject(js);	
							JSONArray droplets = jsonObj.getJSONArray("droplets");				
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", droplets.length(), HttpStatus.OK, droplets.toList());		
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());		
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}	
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public Object getExistingDroplet(Long droplet_id) throws SQLException {

		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/droplets/"+droplet_id;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();	
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("droplet")) {
							JSONObject jsonObj = new JSONObject(js);	
							Map<String, Object> droplets = jsonObj.getJSONObject("droplet").toMap();				
							return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, droplets);	
						}else {
							return ResponseHandler.ResponseOk("Empty result.", HttpStatus.OK, null);	
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/droplets/"+droplet_id;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();					
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("droplet")) {
							JSONObject jsonObj = new JSONObject(js);	
							Map<String, Object> droplets = jsonObj.getJSONObject("droplet").toMap();				
							return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, droplets);	
						}else {
							return ResponseHandler.ResponseOk("Empty result.", HttpStatus.OK, null);	
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public Object getBackupsDropletList(Long droplet_id) throws SQLException {

		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/droplets/"+droplet_id+"/backups";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("backups")) {
							JSONObject jsonObj = new JSONObject(js);	
							JSONArray backups = jsonObj.getJSONArray("backups");				
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", backups.length(), HttpStatus.OK, backups.toList());
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}	
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/droplets/"+droplet_id+"/backups";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();					
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("backups")) {
							JSONObject jsonObj = new JSONObject(js);	
							JSONArray backups = jsonObj.getJSONArray("backups");				
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", backups.length(), HttpStatus.OK, backups.toList());
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}	
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public Object getSnapshotsDropletList(Long droplet_id) throws SQLException {

		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/droplets/"+droplet_id+"/snapshots";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("snapshots")) {
							JSONObject jsonObj = new JSONObject(js);	
							JSONArray snapshots = jsonObj.getJSONArray("snapshots");				
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", snapshots.length(), HttpStatus.OK, snapshots.toList());
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}	
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/droplets/"+droplet_id+"/snapshots";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();					
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("snapshots")) {
							JSONObject jsonObj = new JSONObject(js);	
							JSONArray snapshots = jsonObj.getJSONArray("snapshots");				
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", snapshots.length(), HttpStatus.OK, snapshots.toList());
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public Object getFirewallDropletList(Long droplet_id) throws SQLException {

		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/droplets/"+droplet_id+"/firewalls";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();	
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("firewalls")) {
							JSONObject jsonObj = new JSONObject(js);	
							JSONArray firewalls = jsonObj.getJSONArray("firewalls");				
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", firewalls.length(), HttpStatus.OK, firewalls.toList());	
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());	
						}	
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/droplets/"+droplet_id+"/firewalls";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();					
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("firewalls")) {
							JSONObject jsonObj = new JSONObject(js);	
							JSONArray firewalls = jsonObj.getJSONArray("firewalls");				
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", firewalls.length(), HttpStatus.OK, firewalls.toList());	
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());	
						}	
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}	
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public Object getFirewallsList() throws SQLException {

		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/firewalls";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();		
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("firewalls")) {
							JSONObject jsonObj = new JSONObject(js);	
							JSONArray firewalls = jsonObj.getJSONArray("firewalls");				
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", firewalls.length(), HttpStatus.OK, firewalls.toList());
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/firewalls";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();					
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("firewalls")) {
							JSONObject jsonObj = new JSONObject(js);	
							JSONArray firewalls = jsonObj.getJSONArray("firewalls");				
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", firewalls.length(), HttpStatus.OK, firewalls.toList());
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public Object getExistingFirewall(String firewall_id) throws SQLException {

		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/firewalls/"+firewall_id;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("firewall")) {
							JSONObject jsonObj = new JSONObject(js);	
							Map<String, Object> firewall = jsonObj.getJSONObject("firewall").toMap();				
							return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, firewall);	
						}else {
							return ResponseHandler.ResponseOk("Empty result.", HttpStatus.OK, null);	
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/firewalls/"+firewall_id;
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();					
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("firewall")) {
							JSONObject jsonObj = new JSONObject(js);	
							Map<String, Object> firewall = jsonObj.getJSONObject("firewall").toMap();				
							return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, firewall);	
						}else {
							return ResponseHandler.ResponseOk("Empty result.", HttpStatus.OK, null);	
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public Object getRegionsList() throws SQLException {

		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/regions";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();	
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("regions")) {
							JSONObject jsonObj = new JSONObject(js);	
							JSONArray regions = jsonObj.getJSONArray("regions");				
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", regions.length(), HttpStatus.OK, regions.toList());
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/regions";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();					
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("regions")) {
							JSONObject jsonObj = new JSONObject(js);	
							JSONArray regions = jsonObj.getJSONArray("regions");				
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", regions.length(), HttpStatus.OK, regions.toList());
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

	@Override
	public Object getDropletSizesList() throws SQLException {

		if(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_USER_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/sizes";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);	    	
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("sizes")) {
							JSONObject jsonObj = new JSONObject(js);	
							JSONArray sizes = jsonObj.getJSONArray("sizes");				
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", sizes.length(), HttpStatus.OK, sizes.toList());
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else if(BasicAuthInterceptor.GLOBAL_ACCOUNT != null) {
			String key = Provider.getProviderKey(BasicAuthInterceptor.GLOBAL_ACCOUNT);
		    if(!key.equals("")) {
		    	String url = "https://api.digitalocean.com/v2/sizes";
		    	HttpHeaders header = new HttpHeaders();
		    	header.add("Authorization", "Bearer "+key);
		        header.setContentType(MediaType.APPLICATION_JSON);
		    	HttpEntity<Object> entity = new HttpEntity<>(header);
				try {			    
					ResponseEntity<Object> result = this.restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);		
					Object res = result.getBody();					
					if(res != null) {
						Gson gson = new Gson();					
						String js = gson.toJson(res);
						if(js.contains("sizes")) {
							JSONObject jsonObj = new JSONObject(js);	
							JSONArray sizes = jsonObj.getJSONArray("sizes");				
							return ResponseHandler.ResponseListOk("Successfully retrieved data.", sizes.length(), HttpStatus.OK, sizes.toList());
						}else {
							return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, new ArrayList<>());
						}
					}else {
						return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
					}
				}catch(Exception e) {
					return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
				}
		    }else {
				return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
		    }
		}else {
			return ResponseHandler.ResponseOk("Error... Account not identified.", HttpStatus.FORBIDDEN, null);
		}
	}

}

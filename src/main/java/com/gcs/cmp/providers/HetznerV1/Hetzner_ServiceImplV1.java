package com.gcs.cmp.providers.HetznerV1;

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
import com.google.gson.Gson;

@Service
public class Hetzner_ServiceImplV1 implements Hetzner_ServiceV1{

	RestTemplate restTemplate  = new RestTemplate();

	@Override
	public Object getAllImages() throws SQLException {
		
    	String url = "https://api.hetzner.cloud/v1/images"; 
    	
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
					if(js.contains("images")) {
						JSONObject jsonObj = new JSONObject(js);								
						JSONArray images = jsonObj.getJSONArray("images");
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", images.length(), HttpStatus.OK, images.toList());
					}else {
						return ResponseHandler.ResponseListOk("Emtpy result.", 0, HttpStatus.OK, new ArrayList<>());
					}
				}else {
					return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
				}			
			}catch(Exception e) {
						return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
	}

	@Override
	public Object getAllLocations() throws SQLException {

    	String url = "https://api.hetzner.cloud/v1/locations";   
    	
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
					if(js.contains("locations")) {
						JSONObject jsonObj = new JSONObject(js);								
						JSONArray locations = jsonObj.getJSONArray("locations");
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", locations.length(), HttpStatus.OK, locations.toList());
					}else {
						return ResponseHandler.ResponseListOk("Emtpy result.", 0, HttpStatus.OK, new ArrayList<>());
					}
				}else {
					return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
				}			
			}catch(Exception e) {
						return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
	}

	@Override
	public Object getAllServers() throws SQLException {
		
    	String url = "https://api.hetzner.cloud/v1/servers";   	
    	
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
					if(js.contains("servers")) {
						JSONObject jsonObj = new JSONObject(js);								
						JSONArray servers = jsonObj.getJSONArray("servers");
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", servers.length(), HttpStatus.OK, servers.toList());
					}else {
						return ResponseHandler.ResponseListOk("Emtpy result.", 0, HttpStatus.OK, new ArrayList<>());
					}
				}else {
					return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
				}		
			}catch(Exception e) {
						return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
	}

	@Override
	public Object getServer(Long id) throws SQLException {

    	String url = "https://api.hetzner.cloud/v1/servers/"+id; 
    	
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
					if(js.contains("server")) {
						JSONObject jsonObj = new JSONObject(js);
						Map<String, Object> server = jsonObj.getJSONObject("server").toMap();									
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", server.size(), HttpStatus.OK, server);
					}else {
						return ResponseHandler.ResponseListOk("Empty result.", 0, HttpStatus.OK, null);
					}
				}else {
					return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
				}			
			}catch(Exception e) {
						return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
	}

	@Override
	public Object getAllActionsForServer(Long id) throws SQLException {
		
    	String url = "https://api.hetzner.cloud/v1/servers/"+id+"/actions";
    	
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
					return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
				}		
			}catch(Exception e) {
						return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
	}

	@Override
	public Object getAllServerTypes() throws SQLException {
		
    	String url = "https://api.hetzner.cloud/v1/server_types"; 
    	
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
					if(js.contains("server_types")) {
						JSONObject jsonObj = new JSONObject(js);								
						JSONArray server_types = jsonObj.getJSONArray("server_types");
						return ResponseHandler.ResponseListOk("Successfully retrieved data.", server_types.length(), HttpStatus.OK, server_types.toList());
					}else {
						return ResponseHandler.ResponseListOk("Emtpy result.", 0, HttpStatus.OK, new ArrayList<>());
					}
				}else {
					return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
				}	
			}catch(Exception e) {
						return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}
    	}else {
			return ResponseHandler.ResponseOk("Unable to authenticate you.", HttpStatus.UNAUTHORIZED, null);
    	}
	}
	
}

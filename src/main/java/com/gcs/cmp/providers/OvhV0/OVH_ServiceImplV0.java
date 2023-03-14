package com.gcs.cmp.providers.OvhV0;

import java.sql.SQLException;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gcs.cmp.Exception.ResponseHandler;
import com.gcs.cmp.providers.OVHApi;


@Service
public class OVH_ServiceImplV0 implements OVH_ServiceV0{

	RestTemplate restTemplate = new RestTemplate();

	 String	appKey = "0000000000";
	 String	appSecret = "888888888888888888";
	 String	consumerKey = "aaaaaaaaaaaaaaaaaaaaaaaa";
	 
	@Override
	public Object getDomainsList() throws SQLException {		
		try {
			String res = (String) apiCall("https://api.ovh.com/1.0/domain", appKey, appSecret, consumerKey);
			if(!res.contains("Something went wrong."))	{				
				JSONArray response = new JSONArray(res);
				return ResponseHandler.ResponseListOk("Successfully retrieved data.", response.length(), HttpStatus.OK, response.toList());
			}else{
				return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}		
		} catch (Exception e1) {
			// TODO Auto-generated catch block			
			return e1.getMessage();
		}		
	}

	@Override
	public Object getDomainProperties(String serviceName) throws SQLException {	
		try {
			String res = (String) apiCall("https://api.ovh.com/1.0/domain/"+serviceName, appKey, appSecret, consumerKey);
			if(!res.contains("Something went wrong."))	{				
				Map<String, Object> response = new JSONObject(res).toMap();
				return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, response);
			}else{
				return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
			}		
		} catch (Exception e1) {
			// TODO Auto-generated catch block			
			return e1.getMessage();
		}		
	}

	@Override
	public Object getDomainZoneProperties(String zoneName) throws SQLException {		
		try {
			String res = (String) apiCall("https://api.ovh.com/1.0/domain/zone/"+zoneName, appKey, appSecret, consumerKey);
			if(!res.contains("Something went wrong."))	{				
				Map<String, Object> response = new JSONObject(res).toMap();
				return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, response);
			}else{
				return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
			}		
		} catch (Exception e1) {
			// TODO Auto-generated catch block			
			return e1.getMessage();
		}	
	}

	@Override
	public Object getDomainRecords(String zoneName) throws SQLException {			
		try {
			String res = (String) apiCall("https://api.ovh.com/1.0/domain/zone/"+zoneName+"/record", appKey, appSecret, consumerKey);
			if(!res.contains("Something went wrong."))	{				
				JSONArray response = new JSONArray(res);
				return ResponseHandler.ResponseListOk("Successfully retrieved data.", response.length(), HttpStatus.OK, response.toList());
			}else{
				return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}		
		} catch (Exception e1) {
			// TODO Auto-generated catch block			
			return e1.getMessage();
		}	
	}
	
	@Override
	public Object getDomainRecordsZoneProperties(String zoneName, Long id) throws SQLException {	
		try {
			String res = (String) apiCall("https://api.ovh.com/1.0/domain/zone/"+zoneName+"/record/"+id, appKey, appSecret, consumerKey);
			if(!res.contains("Something went wrong."))	{				
				Map<String, Object> response = new JSONObject(res).toMap();
				return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, response);
			}else{
				return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
			}	
		} catch (Exception e1) {
			// TODO Auto-generated catch block			
			return e1.getMessage();
		}	
	}

	@Override
	public Object getDomainServiceProperties(String zoneName) throws SQLException {		
		try {
			String res = (String) apiCall("https://api.ovh.com/1.0/domain/zone/"+zoneName+"/serviceInfos", appKey, appSecret, consumerKey);
			if(!res.contains("Something went wrong."))	{				
				Map<String, Object> response = new JSONObject(res).toMap();
				return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, response);
			}else{
				return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
			}			
		} catch (Exception e1) {
			// TODO Auto-generated catch block			
			return e1.getMessage();
		}	
	}

	@Override
	public Object getHostingList() throws SQLException {	
		try {
			String res = (String) apiCall("https://api.ovh.com/1.0/hosting/web", appKey, appSecret, consumerKey);
			if(!res.contains("Something went wrong."))	{				
				JSONArray response = new JSONArray(res);
				return ResponseHandler.ResponseListOk("Successfully retrieved data.", response.length(), HttpStatus.OK, response.toList());
			}else{
				return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}			
		} catch (Exception e1) {
			// TODO Auto-generated catch block			
			return e1.getMessage();
		}
	}

	@Override
	public Object getHostingAttachedDomain(String domain) throws SQLException {		
		try {
			String res = (String) apiCall("https://api.ovh.com/1.0/hosting/web/attachedDomain?domain="+domain, appKey, appSecret, consumerKey);
			if(!res.contains("Something went wrong."))	{				
				JSONArray response = new JSONArray(res);
				return ResponseHandler.ResponseListOk("Successfully retrieved data.", response.length(), HttpStatus.OK, response.toList());
			}else{
				return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}	
		} catch (Exception e1) {
			// TODO Auto-generated catch block			
			return e1.getMessage();
		}
	}

	@Override
	public Object getDomainAttachedToHost(String serviceName) throws SQLException {		
		try {
			String res = (String) apiCall("https://api.ovh.com/1.0/hosting/web/"+serviceName+"/attachedDomain", appKey, appSecret, consumerKey);
			if(!res.contains("Something went wrong."))	{				
				JSONArray response = new JSONArray(res);
				return ResponseHandler.ResponseListOk("Successfully retrieved data.", response.length(), HttpStatus.OK, response.toList());
			}else{
				return ResponseHandler.ResponseListOk("Something went wrong.", 0, HttpStatus.BAD_REQUEST, null);
			}	
		} catch (Exception e1) {
			// TODO Auto-generated catch block			
			return e1.getMessage();
		}
	}

	@Override
	public Object getSSLProperties(String serviceName) throws SQLException {		
		try {
			String res = (String) apiCall("https://api.ovh.com/1.0/hosting/web/"+serviceName+"/ssl", appKey, appSecret, consumerKey);
			if(!res.contains("Something went wrong."))	{				
				Map<String, Object> response = new JSONObject(res).toMap();
				return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, response);
			}else{
				return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
			}	
		} catch (Exception e1) {
			// TODO Auto-generated catch block			
			return e1.getMessage();
		}	
	}

	private Object apiCall(String url,String appKey, String appSecret, String consumerKey) throws Exception {		 			
         OVHApi api = new OVHApi(appKey, appSecret, consumerKey);
         try {
             return api.get(url);
         } catch (Exception e) {
             System.out.println(e.getMessage());
         }
         return api.get(url);
	 }
	 
}

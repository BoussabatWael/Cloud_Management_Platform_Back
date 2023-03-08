package com.gcs.cmp.providers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import com.gcs.cmp.Exception.CustomException;
import com.gcs.cmp.Exception.ResponseHandler;

/**
 * Simple low level wrapper over the OVH REST API.
 * 
 * 
 * @author mbsk
 *
 */
public class OVHApi {
	
	private String appKey = "";
	private String appSecret = "";
	private String consumerKey = "";
	

	public OVHApi() throws Exception {
		super();
		
			appKey = "600da4cf6fec5b7f";
			appSecret = "cd5b8d1df08b8c794dff00305c0e9fbe";
			consumerKey = "4a4506f468fbe20a01264a176d9a728e";
	}
	
	public OVHApi(String appKey, String appSecret, String consumerKey) {		
		this.appKey = appKey;
		this.appSecret = appSecret;
		this.consumerKey = consumerKey;
	}
	
	private void assertAllConfigNotNull() throws  Exception{
		if(appKey==null || appSecret==null || consumerKey==null) {
			throw new CustomException("Something went wrong.");
		}
	}
	
	public Object get(String path) throws Exception {
		assertAllConfigNotNull();
		return get(path, "", true);
	}
	
	public Object get(String path, boolean needAuth) throws Exception {
		assertAllConfigNotNull();
		return get(path, "", needAuth);
	}
	
	public Object get(String path, String body, boolean needAuth) throws Exception {
		assertAllConfigNotNull();
		return call("GET", body, appKey, appSecret, consumerKey, path, needAuth);
	}
	
	public Object put(String path, String body, boolean needAuth) throws Exception {
		assertAllConfigNotNull();
		return call("PUT", body, appKey, appSecret, consumerKey, path, needAuth);
	}
	
	public Object post(String path, String body, boolean needAuth) throws Exception {
		assertAllConfigNotNull();
		return call("POST", body, appKey, appSecret, consumerKey, path, needAuth);
	}
	
	public Object delete(String path, String body, boolean needAuth) throws Exception {
		assertAllConfigNotNull();
		return call("DELETE", body, appKey, appSecret, consumerKey, path, needAuth);
	}
	
    private Object call(String method, String body, String appKey, String appSecret, String consumerKey, String path, boolean needAuth) throws Exception
    {
	
		try {
			
			URL url = new URL(path);

			// prepare 
			HttpURLConnection request = (HttpURLConnection) url.openConnection();
			request.setRequestMethod(method);
			request.setReadTimeout(30000);
			request.setConnectTimeout(30000);
			request.setRequestProperty("Content-Type", "application/json");
			request.setRequestProperty("X-Ovh-Application", appKey);
			// handle authentification
			if(needAuth) {
				// get timestamp from local system
				long timestamp = System.currentTimeMillis() / 1000;

				// build signature
				String toSign = new StringBuilder(appSecret)
									.append("+")
									.append(consumerKey)
									.append("+")
									.append(method)
									.append("+")
									.append(url)
									.append("+")
									.append(body)
									.append("+")
									.append(timestamp)
									.toString();
				String signature = new StringBuilder("$1$").append(HashSHA1(toSign)).toString();
				
				// set HTTP headers for authentication
				request.setRequestProperty("X-Ovh-Consumer", consumerKey);
				request.setRequestProperty("X-Ovh-Signature", signature);
				request.setRequestProperty("X-Ovh-Timestamp", Long.toString(timestamp));
			}
			
			if(body != null && !body.isEmpty())
            {
				request.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(request.getOutputStream());
                out.writeBytes(body);
                out.flush();
                out.close();
            }
			
			
			String inputLine;
			BufferedReader in;
			int responseCode = request.getResponseCode();
			if (responseCode == 200) {
				in = new BufferedReader(new InputStreamReader(request.getInputStream()));
			} else {
				in = new BufferedReader(new InputStreamReader(request.getErrorStream()));
			}
			
			// build response
			StringBuilder response = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			if(responseCode == 200) {
				return ResponseHandler.ResponseOk("Successfully retrieved data.", HttpStatus.OK, response.toString());
			} else if(responseCode == 400) {
				return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
			} else if (responseCode == 403) {
				return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.FORBIDDEN, null);
			} else if (responseCode == 404) {
				return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.NOT_FOUND, null);
			} else if (responseCode == 409) {
				return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.CONFLICT, null);
			} else {
				return ResponseHandler.ResponseOk("Something went wrong.", HttpStatus.BAD_REQUEST, null);
			}			
		} catch (Exception e) {
			return ResponseHandler.ResponseOk(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}
	
	public static String HashSHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
	    MessageDigest md;
        md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash = new byte[40];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        sha1hash = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < sha1hash.length; i++) {
            sb.append(Integer.toString((sha1hash[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
	}

}
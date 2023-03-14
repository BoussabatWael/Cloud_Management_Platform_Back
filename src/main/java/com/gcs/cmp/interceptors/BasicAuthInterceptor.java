package com.gcs.cmp.interceptors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gcs.cmp.Exception.CustomException;
import com.gcs.cmp.Exception.UnauthorizedException;
import com.gcs.cmp.validators.Inputs_Validations;


@SuppressWarnings("deprecation")
public class BasicAuthInterceptor extends HandlerInterceptorAdapter{
						
	public static String TARGET = null;
	
	public static String TARGET_TOKEN = null;
	
	public static String GLOBAL_ACCOUNT = null;
	
	public static String GLOBAL_USER_ACCOUNT = null;

	public static String GLOBAL_USER = null;
		
	public static String GLOBAL_APIKEY = null;

	public static String TYPE = null;
	
	public static String route = null;
	
	public static String authHeader = null;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String method = request.getMethod();
		route = request.getServletPath();

		if(route.startsWith("/swagger-ui") || route.startsWith("/v3/api-docs")) {
			return true;
		}
		else {
			if(!method.equals("OPTIONS")) {
				String authHeader = request.getHeader("Authorization");
				if (authHeader != null && authHeader !="undefined" && authHeader.toLowerCase().startsWith("basic")) {
				    String base64Credentials = authHeader.substring("Basic".length()).trim();
				    byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
				    String credentials = new String(credDecoded, "UTF-8");
				    //credentials = username:password
				    TARGET = credentials.split(":")[0];
				    String key = credentials.split(":")[1];
				    
				    if(TARGET.equals("front")) {
				    	return true;
				    }
				    
				    ArrayList<Map<String, String>> res = checkApiKey(key);
				    if(!(res == null)) {
				    	/// type 1 : front
				    	if(TYPE.equals("1")) {
				    		String token = request.getHeader("Token");
				    		if (token != null && token !="undefined") {
							    String result = checkUserKey(token);
							    if(!result.equals("")) {
							    	/// filter by global user account
							    	String acc = checkAccountValidation(GLOBAL_USER_ACCOUNT);
							    	if(!acc.equals("")) {
								    	return true;
							    	}else {
										throw new UnauthorizedException("Account status inactive.");
							    	}
							    }else {
							    	throw new UnauthorizedException("Invalid token.");
							    }							    
				    		}else {
				    			if(GLOBAL_ACCOUNT != null) {
				    				String acc = checkAccountValidation(GLOBAL_ACCOUNT);
					    			if(acc.equals("")) {
										throw new UnauthorizedException("Account status inactive.");
							    	}
				    			}
				    			/// no need for account id
						    	if(method.equals("GET")) {
						    		String[] ch = route.split("/");
						    		if(ch[ch.length-2].equals("get")) {
						    			if( Inputs_Validations.CheckInt(ch[ch.length-1]) == false) {
											throw new CustomException("Error "+ch[ch.length-1]+", PATH malformed.");
							    		}
						    		}
						    	      char[] chars = route.toCharArray();
						    	      StringBuilder sb = new StringBuilder();
						    	      for(char c : chars){
						    	         if(!Character.isDigit(c)){
						    	            sb.append(c);
						    	         }
						    	      }						    	      
							    	boolean permission_exists = res.toString().contains(route+"."+method);
								    boolean permission_exists1 = res.toString().contains(sb.deleteCharAt(sb.length() -1)+"."+method);
							    	if(permission_exists == true || permission_exists1 == true) {
							    		return true;
							    	}else {
										throw new UnauthorizedException("Permission denied.");
							    	}
						    	}
						    	else if(method.equals("POST")) {
							    	boolean permission_exists = res.toString().contains(route+"."+method);
							    	if(permission_exists == true) {
							    		return true;
							    	}else {
										throw new UnauthorizedException("Permission denied.");
							    	}
						    	}
						    	else if(method.equals("PUT")) {
						    		String[] ch = route.split("/");
						    		if(Inputs_Validations.CheckInt(ch[ch.length-1]) == false) {
										throw new CustomException("Error "+ch[ch.length-1]+", PATH malformed.");
						    		}						    		
						    	      char[] chars = route.toCharArray();
						    	      StringBuilder sb = new StringBuilder();
						    	      for(char c : chars){
						    	         if(!Character.isDigit(c)){
						    	            sb.append(c);
						    	         }
						    	      }						    	      
								    boolean permission_exists = res.toString().contains(sb.deleteCharAt(sb.length() -1)+"."+method);
							    	if(permission_exists == true) {
							    		return true;
							    	}else {
										throw new UnauthorizedException("Permission denied.");
							    	}
						    	}
						    	else if(method.equals("DELETE")) {
						    		String[] ch = route.split("/");
						    		if(Inputs_Validations.CheckInt(ch[ch.length-1]) == false) {
										throw new CustomException("Error "+ch[ch.length-1]+", PATH malformed.");
						    		}						    		
						    	      char[] chars = route.toCharArray();
						    	      StringBuilder sb = new StringBuilder();
						    	      for(char c : chars){
						    	         if(!Character.isDigit(c)){
						    	            sb.append(c);
						    	         }
						    	      }
							    	boolean permission_exists = res.toString().contains(sb.deleteCharAt(sb.length() -1)+"."+method);
							    	if(permission_exists == true) {
							    		return true;
							    	}else {
										throw new UnauthorizedException("Permission denied.");
							    	}							    	
						    	}else {
							    	throw new CustomException("Request "+request.getMethod()+" is NOT supported.");
						    	}
							}
				    	}else {
				    		// filter by global account
					    	if(method.equals("GET")) {
					    		String[] ch = route.split("/");
					    		if(ch[ch.length-2].equals("get")) {
					    			if( Inputs_Validations.CheckInt(ch[ch.length-1]) == false) {
										throw new CustomException("Error "+ch[ch.length-1]+", PATH malformed.");
						    		}
					    		}
					    	    char[] chars = route.toCharArray();
					    	    StringBuilder sb = new StringBuilder();
					    	    for(char c : chars){
					    	       if(!Character.isDigit(c)){
					    	          sb.append(c);
					    	       }
					    	    }
						    	boolean permission_exists = res.toString().contains(route+"."+method);
							    boolean permission_exists1 = res.toString().contains(sb.deleteCharAt(sb.length() -1)+"."+method);
						    	if(permission_exists == true || permission_exists1 == true) {
						    		return true;
						    	}else {
									throw new UnauthorizedException("Permission denied.");
						    	}
					    	}
					    	else if(method.equals("POST")) {
						    	boolean permission_exists = res.toString().contains(route+"."+method);
						    	if(permission_exists == true) {
						    		return true;
						    	}else {
									throw new UnauthorizedException("Permission denied.");
						    	}
					    	}
					    	else if(method.equals("PUT")) {
					    		String[] ch = route.split("/");
					    		if(Inputs_Validations.CheckInt(ch[ch.length-1]) == false) {
									throw new CustomException("Error "+ch[ch.length-1]+", PATH malformed.");
					    		}
					    	      char[] chars = route.toCharArray();
					    	      StringBuilder sb = new StringBuilder();
					    	      for(char c : chars){
					    	         if(!Character.isDigit(c)){
					    	            sb.append(c);
					    	         }
					    	      }
							    boolean permission_exists = res.toString().contains(sb.deleteCharAt(sb.length() -1)+"."+method);
						    	if(permission_exists == true) {
						    		return true;
						    	}else {
									throw new UnauthorizedException("Permission denied.");
						    	}
					    	}
					    	else if(method.equals("DELETE")) {
					    		String[] ch = route.split("/");					    		
					    		if(Inputs_Validations.CheckInt(ch[ch.length-1]) == false) {
									throw new CustomException("Error "+ch[ch.length-1]+", PATH malformed.");
					    		}
					    	      char[] chars = route.toCharArray();
					    	      StringBuilder sb = new StringBuilder();
					    	      for(char c : chars){
					    	         if(!Character.isDigit(c)){
					    	            sb.append(c);
					    	         }
					    	      }
						    	boolean permission_exists = res.toString().contains(sb.deleteCharAt(sb.length() -1)+"."+method);
						    	if(permission_exists == true) {
						    		return true;
						    	}else {
									throw new UnauthorizedException("Permission denied.");
						    	}
					    	}else {
						    	throw new CustomException("Request "+request.getMethod()+" is NOT supported.");
					    	}
				    	}
				    }else {
						throw new UnauthorizedException("Invalid API key.");
				    }
				}else {
					throw new UnauthorizedException("API key required.");
				}
			}else {
				return true;
			}
		}
	}
	
	public ArrayList<Map<String, String>> checkApiKey(String apikey) throws SQLException {
		Statement st =null;
		ResultSet rs = null;
		ArrayList<Map<String, String>> list = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/cloud_management_platform","root",""); 
			st=con.createStatement();  
			rs=st.executeQuery("SELECT akp.permission,ak.* FROM api_keys ak LEFT JOIN api_keys_permissions akp ON ak.id = akp.apikey_id WHERE ak.key_value = '"+apikey+"' AND ak.status IN (1,2,3) AND (akp.status IN (1,2,3) OR akp.apikey_id IS NULL)");
			if(rs != null && rs.next()) {
    			ResultSetMetaData md = rs.getMetaData();
    	        int columns = md.getColumnCount();
    	        GLOBAL_ACCOUNT = rs.getString("account_id");
    	        GLOBAL_APIKEY = rs.getString("id");
    	        TYPE = rs.getString("type");
    	        HashMap<String, String> row = new HashMap<String, String>(columns);
    	        for (int i = 1; i <= columns; ++i) {
    	        	String columnName = md.getColumnName(i);
    	            String columnVal = String.valueOf(rs.getObject(i));
    	            row.put(columnName, columnVal);
    	        }
    	        list.add(row);
    	        con.close();
    	        return list;
    	        
        	}else {
        		GLOBAL_ACCOUNT = null;
        		GLOBAL_APIKEY = null;
        		TYPE = null;
        		st=null;
        		rs=null;
        		con.close();
        		return null;
        	}
		}catch(Exception e){ 
			System.out.println(e);
		}
		st=null;
		rs=null;
		return null;  
	}
	
	public String checkUserKey(String apikey) throws SQLException {
		String result="";
		Statement st1 = null;
		ResultSet rs1 = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			Connection con1=DriverManager.getConnection("jdbc:mysql://localhost:3306/cloud_management_platform","root",""); 
			st1=con1.createStatement();  
			rs1=st1.executeQuery("SELECT u.id, u.account_id FROM core_users u INNER JOIN core_users_tokens ut ON u.id = ut.user_id WHERE ut.token='"+apikey+"' AND u.status IN (1,2,3) AND ut.status IN (1,2,3)");
			if(rs1 != null && rs1.next()) {
		        GLOBAL_USER = rs1.getString("id");
		        GLOBAL_USER_ACCOUNT = rs1.getString("account_id");
		        result = GLOBAL_USER+","+GLOBAL_USER_ACCOUNT;
		        con1.close();
				return result;
	        }else {
	        	GLOBAL_USER = null;
	        	GLOBAL_USER_ACCOUNT = null;
	            st1=null;
	            rs1=null;
	        	con1.close();
	        	return result;
	        }
		}catch(Exception e){ 
			System.out.println(e);
		}
        st1=null;
        rs1=null;
		return result;  
	}
	
	public String checkAccountValidation(String account_id) throws SQLException {
		String result="";
		Statement st2 = null;
		ResultSet rs2 = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			Connection con2=DriverManager.getConnection("jdbc:mysql://localhost:3306/cloud_management_platform","root",""); 
			st2=con2.createStatement();  
			rs2=st2.executeQuery("SELECT * FROM core_accounts WHERE id='"+account_id+"' AND status IN (1,2,3)");
	        if(rs2 != null && rs2.next()) {
	        	result = "Account is active";
	        	con2.close();
				return result;
	        }else {
	            st2=null;
	            rs2=null;
	        	con2.close();
	        	return result;
	        }
		}catch(Exception e){ 
			System.out.println(e);
		}
        st2=null;
        rs2=null;
		return result;  
	}
}

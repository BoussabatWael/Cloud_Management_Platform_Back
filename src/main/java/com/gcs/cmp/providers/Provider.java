package com.gcs.cmp.providers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Provider {

	public static String PROVIDER_KEY = "";
	public static String PROVIDER_NAME = "";

	public static String getProviderKey(String account_id) throws SQLException {
		Statement st = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/cloud_management_platform","root",""); 
			//Connection con=DriverManager.getConnection("jdbc:mysql://143.198.55.254:3306/waelitwi_cloud_management_platform","waelitwi","n9K@c0Xdr!oeiw@1985");
			st=con.createStatement();  
			rs=st.executeQuery("SELECT acc.password,p.name FROM core_access_credentials acc INNER JOIN cloud_providers_accounts cpa ON cpa.credential_id = acc.id INNER JOIN providers p ON p.id = cpa.provider_id WHERE cpa.account_id='"+account_id+"' AND cpa.status IN (1,2,3) AND acc.status IN (1,2,3) AND p.status IN (1,2,3)");
	        if(rs != null && rs.next()) {
	        		//get the encrypted api key
	        		PROVIDER_KEY = rs.getString("password");
	        		//get provider name
	        		PROVIDER_NAME = rs.getString("name");
	        		con.close();
	        		//return the decrypted api key
					return decrypt(PROVIDER_KEY);
	        }else {
	            st=null;
	            rs=null;
	        	con.close();
	        	return PROVIDER_KEY;
	        }

		}catch(Exception e){ System.out.println("e3 "+e);}
        st=null;
        rs=null;
		return PROVIDER_KEY;  
	}
	
	public static String encrypt(String value) {
		String key = "AAAAAAAAAAAAAAAA";
		String initVector = "BBBBBBBBBBBBBBBB";
	    try {
	        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
	        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	 
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
	 
	        byte[] encrypted = cipher.doFinal(value.getBytes());
	        return Base64.getEncoder().encodeToString(encrypted);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}
	
	public static String decrypt(String encrypted) {
		String key = "AAAAAAAAAAAAAAAA";
		String initVector = "BBBBBBBBBBBBBBBB";
	    try {
	        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
	        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	 
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
	        byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
	 
	        return new String(original);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}
	 
}

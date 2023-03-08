package com.gcs.cmp.validators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.gcs.cmp.Exception.ExceptionResponse;

public class Inputs_Validations extends ExceptionResponse{

	public ExceptionResponse StringValidation(String input) {
		if(input.equals("") || input.length() == 0) {
			return new ExceptionResponse(input+" is invalid", "field invalid", getTimestamp());
		}else {
			return new ExceptionResponse(input+" is valid", "field valid", getTimestamp());
		}
	}
	
	public boolean IntValidation(String input) {
		    try {
		        Integer.parseInt(input);
				return true;
		    } catch (NumberFormatException ex) {
				return false;
		    }
	}
		
	public ExceptionResponse DateValidation(String input) {
		  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        try {
	            dateFormat.parse(input.trim());
	        } catch (ParseException pe) {
				return new ExceptionResponse(input+" is invalid", "field invalid", getTimestamp());
	        }
			return new ExceptionResponse(input+" is valid", "field valid", getTimestamp());
	}
		
	public static boolean CheckInt(String s) {
	    try {
	        Integer.parseInt(s);
	        return true;
	    } catch (NumberFormatException ex) {
	    	return false;
	    } 
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
		
	public static String decrypt(String value) {
		String key = "AAAAAAAAAAAAAAAA";
		String initVector = "BBBBBBBBBBBBBBBB";
	    try {
	        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
	        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	 
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
	 
	        byte[] decrypted = cipher.doFinal(Base64.getDecoder()
	                .decode(value));
	        return new String(decrypted);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}
		
	public static String generateKey() {
		String generatedKey = "";
		String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	    try {
	    	for (int i = 0; i < 30; i++){
	    	      generatedKey += possible.charAt((int) Math.floor(Math.random() * possible.length()));
	    	    }
	    	System.out.println(generatedKey);
  	      return generatedKey;

	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}	
}

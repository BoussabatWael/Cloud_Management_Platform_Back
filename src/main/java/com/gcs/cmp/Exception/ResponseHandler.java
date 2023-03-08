package com.gcs.cmp.Exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ResponseHandler {

	@ResponseStatus(HttpStatus.MULTI_STATUS)
	public static ResponseEntity<Object> ResponseMulti(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
            map.put("message", message);
            map.put("status", status.value());
            map.put("data", responseObj);

            return new ResponseEntity<Object>(map,status);
    }
	
    @ResponseStatus(HttpStatus.OK)
	public static ResponseEntity<Object> ResponseOk(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
            map.put("message", message);
            map.put("status", status.value());
            map.put("data", responseObj);

            return new ResponseEntity<Object>(map,status);
    }

    @ResponseStatus(HttpStatus.OK)
	public static ResponseEntity<Object> ResponseListOk(String message,Integer total, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
            map.put("message", message);
            map.put("status", status.value());
            map.put("total", total);
            map.put("data", responseObj);

            return new ResponseEntity<Object>(map,status);
    }
    
    @ResponseStatus(HttpStatus.FORBIDDEN)
	public static ResponseEntity<Object> ResponseListKo(String message,Integer total, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
            map.put("message", message);
            map.put("status", status.value());
            map.put("total", total);
            map.put("data", responseObj);

            return new ResponseEntity<Object>(map,status);
    }
    
    @ResponseStatus(HttpStatus.FORBIDDEN)
   	public static ResponseEntity<Object> ResponseForbidden(String message, HttpStatus status, Object responseObj) {
           Map<String, Object> map = new HashMap<String, Object>();
               map.put("message", message);
               map.put("status", status.value());
               map.put("data", responseObj);

               return new ResponseEntity<Object>(map,status);
    }
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
   	public static ResponseEntity<Object> ResponseNotFound(String message, HttpStatus status, Object responseObj) {
           Map<String, Object> map = new HashMap<String, Object>();
               map.put("message", message);
               map.put("status", status.value());
               map.put("data", responseObj);

               return new ResponseEntity<Object>(map,status);
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
   	public static ResponseEntity<Object> ResponseBadRequest(String message, HttpStatus status, Object responseObj) {
           Map<String, Object> map = new HashMap<String, Object>();
               map.put("message", message);
               map.put("status", status.value());
               map.put("data", responseObj);

               return new ResponseEntity<Object>(map,status);
    }
}

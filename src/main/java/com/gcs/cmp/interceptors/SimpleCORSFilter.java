package com.gcs.cmp.interceptors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.MethodNotAllowedException;

import com.gcs.cmp.Exception.CustomException;
import com.gcs.cmp.Exception.MethodnotallowedException;


@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class SimpleCORSFilter implements Filter{
	private final Logger log = LoggerFactory.getLogger(SimpleCORSFilter.class);

	public SimpleCORSFilter() {
	    log.info("SimpleCORSFilter init");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException,MethodNotAllowedException {
		
	    HttpServletRequest request = (HttpServletRequest) req;
	    HttpServletResponse response = (HttpServletResponse) res;

	    String allowed_methods = "POST,PUT,GET,OPTIONS,DELETE";
		String route = request.getServletPath();
	    if(allowed_methods.contains(request.getMethod())) {
	    	if(request.getMethod().equals("POST")) {
				if(!route.toString().endsWith("create")) {
			    	throw new CustomException("Invalid Request URL...");
				}
	    	}if(request.getMethod().equals("PUT")) {
				if(!route.toString().contains("update")) {
			    	throw new CustomException("Invalid Request URL...");
				}
	    	}if(request.getMethod().equals("DELETE")) {
				if(!route.toString().contains("delete")) {
			    	throw new CustomException("Invalid Request URL...");
				}
	    	}
	    	response.setHeader("Access-Control-Allow-Origin", "*");
		    //response.setHeader("Access-Control-Allow-Credentials", "true");
		    response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
		    //response.setHeader("Access-Control-Max-Age", "3600");
		    response.setHeader("Access-Control-Allow-Headers", "*");
		    chain.doFilter(req, res);

	    }
	    else {
	    	throw new MethodnotallowedException("Request "+request.getMethod()+" is NOT supported!");
	    }
	    
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void destroy() {
	}


}

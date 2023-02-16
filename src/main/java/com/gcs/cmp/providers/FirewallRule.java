package com.gcs.cmp.providers;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class FirewallRule implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private String ip_type;
	private String action;
	private String protocol;
	private String port;
	private String subnet;
	private Integer subnet_size;
	private String source;
	private String notes;
}

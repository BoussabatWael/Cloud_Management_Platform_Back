package com.gcs.cmp.providers;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class IPv4Instance implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ip;
	private String netmask;
	private String geteway;
	private String type;
	private String reverse;
	private String mac_address;
}

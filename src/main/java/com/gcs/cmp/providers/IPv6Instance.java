package com.gcs.cmp.providers;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class IPv6Instance implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ip;
	private String network;
	private Integer network_size;
	private String type;
}

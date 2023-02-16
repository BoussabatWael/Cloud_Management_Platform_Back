package com.gcs.cmp.providers;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class DomainRecords implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String type;
	private String name;
	private String data;
	private Integer priority;
	private Integer ttl;
}

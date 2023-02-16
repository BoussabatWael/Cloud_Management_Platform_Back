package com.gcs.cmp.providers;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Actions implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private String status;
	private String type;
	private Date started_at;
	private Date completed_at;
	private Integer resource_id;
	private String resource_type;
	private Object region;
	private String region_slug;	
}

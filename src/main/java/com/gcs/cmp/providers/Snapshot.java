package com.gcs.cmp.providers;

import java.io.Serializable;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Snapshot implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private Date date_created;
	private String description;
	private Integer size;
	private String status;
	private Integer os_id;
	private Integer app_id;
}

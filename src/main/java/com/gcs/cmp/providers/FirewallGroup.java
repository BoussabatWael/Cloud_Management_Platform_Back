package com.gcs.cmp.providers;

import java.io.Serializable;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class FirewallGroup implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String description;
	private Date date_created;
	private Date date_modified;
	private Integer instance_count;
	private Integer rule_count;
	private Integer max_rule_count;
}

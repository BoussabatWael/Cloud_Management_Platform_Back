package com.gcs.cmp.providers;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Plans implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private Integer vcpu_count;
	private Integer ram;
	private Integer disk;
	private Integer bandwith;
	private Integer monthly_cost;
	private String type;
	private List<String> locations;
	private Integer disk_count;
}

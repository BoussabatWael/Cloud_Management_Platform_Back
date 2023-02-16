package com.gcs.cmp.providers;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class DropletSize implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String slug;
	private Integer memory;
	private Integer vcpus;
	private Integer disk;
	private long transfer;
	private long price_monthly;
	private long price_hourly;
	private List<String> regions;
	private Boolean available;
	private String description;
}

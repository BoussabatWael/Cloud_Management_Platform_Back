package com.gcs.cmp.providers;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class DomainZoneProperties implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Boolean dnssecSupported;
	private List<String> nameServers;
	private String name;
	private Date lastUpdate;
	private Boolean hasDnsAnycast;
}

package com.gcs.cmp.providers;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class DomainRecordsZoneProperties implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private String subDomain;
	private String zone;
	private String fieldType;
	private String target;
	private long ttl;
}

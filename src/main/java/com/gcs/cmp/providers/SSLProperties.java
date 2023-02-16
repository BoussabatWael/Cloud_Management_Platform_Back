package com.gcs.cmp.providers;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class SSLProperties implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String provider;
	private String taskId;
	private String type;
	private String status;
	private Boolean regenerable;
	private Boolean isReportable;
}

package com.gcs.cmp.providers;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class DomainProperties implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String state;
	private String whoisOwner;
	private String nameServerType;
	private String offer;
	private String parentService;
	private Boolean dnssecSupported;
	private Boolean glueRecordIpv6Supported;
	private Boolean glueRecordMultiIpSupported;
	private String suspensionState;
	private Boolean owoSupported;
	private String domain;
	private Boolean hostSupported;
	private Date lastUpdate;
	private String transferLockStatus;
}

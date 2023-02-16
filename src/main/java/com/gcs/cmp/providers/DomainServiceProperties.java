package com.gcs.cmp.providers;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class DomainServiceProperties implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Integer> possibleRenewPeriod;
	private Integer serviceId;
	private Boolean canDeleteAtExpiration;
	private Date expiration;
	private String domain;
	private String renewalType;
	private String contactAdmin;
	private String contactTech;
	private Object renew;
	private Date creation;
	private String contactBilling;
	private String engagedUpTo;
	private String status;
}

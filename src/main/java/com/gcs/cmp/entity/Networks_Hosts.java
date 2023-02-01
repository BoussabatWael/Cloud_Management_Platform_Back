package com.gcs.cmp.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="networks_hosts")
@Data @AllArgsConstructor @NoArgsConstructor
public class Networks_Hosts implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="domain_id",nullable = false )
	private Networks_Domain_Names domain;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="cloud_provider_id",nullable = false )
	private Cloud_Providers_Accounts cloud_provider_account;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="instance_id",nullable = false )
	private Inventory_Instances instance;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="hosting_id",nullable = false )
	private Inventory_Hosts hosting;
	
	@Nullable
	@NumberFormat
	@Min(message = "must be greater than or equal to 0", value = 0)
	private int parent_id;
	
	@NotNull
	@NumberFormat
	@Min(message = "must be greater than or equal to 1", value = 1)
	private int status;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime start_date;
	
	@Nullable
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime end_date;
}

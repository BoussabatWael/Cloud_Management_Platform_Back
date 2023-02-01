package com.gcs.cmp.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="cloud_providers_accounts")
@Data @AllArgsConstructor @NoArgsConstructor
public class Cloud_Providers_Accounts implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="account_id",nullable = false )
	private Core_Accounts account;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="provider_id",nullable = false )
	private Providers provider;
	
	@NotNull
	@NumberFormat
	@Min(message = "must be greater than or equal to 1", value = 1)
	private int credential_id;
	
	@NotNull
	@NumberFormat
	@Min(message = "must be greater than or equal to 1", value = 1)
	private int status;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime start_date;
	
	@Nullable
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime end_date;
	
	@OneToMany(mappedBy = "cloud_provider_account")
	@JsonIgnore
	private List<Networks_Hosts> network_hosts=new ArrayList<>();
	
	@OneToMany(mappedBy = "cloud_provider_account")
	@JsonIgnore
	private List<Inventory_Instances> inventory_instances=new ArrayList<>();
	
	@OneToMany(mappedBy = "cloud_provider_account")
	@JsonIgnore
	private List<Networks_Domain_Names> network_domain_names=new ArrayList<>();
	
	@OneToMany(mappedBy = "cloud_provider_account")
	@JsonIgnore
	private List<Inventory_Hosts> inventory_hosts=new ArrayList<>();
}

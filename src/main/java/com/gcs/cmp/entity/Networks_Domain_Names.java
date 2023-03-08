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
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="networks_domain_names")
@Data @AllArgsConstructor @NoArgsConstructor
public class Networks_Domain_Names implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="account_id",nullable = false )
	private Core_Accounts account;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="cloud_provider_id",nullable = false )
	private Cloud_Providers_Accounts cloud_provider_account;
	
	@NotNull(message = "must not be null")
	@Size(max=100, message = "size too long... Max 100 characters")
	@Size(min=3, message = "size too short... At least 3 characters")
	private String name;
	
	@NotNull
	@NumberFormat
	@Min(message = "must be greater than or equal to 1", value = 1)
	private int status;
	
	@Nullable
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime renew_date;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime start_date;
	
	@Nullable
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime end_date;
	
	@Nullable
	@NumberFormat
	@Min(message = "must be greater than or equal to 0", value = 0)
	private int parent_id;
	
	@OneToMany(mappedBy = "domain")
	@JsonIgnore
	private List<Networks_Hosts> network_hosts=new ArrayList<>();
	
	@OneToMany(mappedBy = "domain")
	@JsonIgnore
	private List<Networks_SSL_Certificates> network_ssl_certificates=new ArrayList<>();
}

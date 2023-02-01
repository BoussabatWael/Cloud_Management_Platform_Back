package com.gcs.cmp.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name="core_accounts")
@Data @AllArgsConstructor @NoArgsConstructor
public class Core_Accounts implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull(message = "must not be null")
	@Size(max=100, message = "size too long... Max 100 characters")
	@Size(min=3, message = "size too short... At least 3 characters")
	private String name;
	
	@NotNull
	@NumberFormat
	@Min(message = "must be greater than or equal to 1", value = 1)
	private int status;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime start_date;
	
	@Nullable
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime end_date;
	
	@OneToMany(mappedBy = "account")
	@JsonIgnore
	private List<Cloud_Providers_Accounts> cloud_accounts=new ArrayList<>();
	
	@OneToMany(mappedBy = "account")
	@JsonIgnore
	private List<Core_Accounts_Modules> core_accounts_modules=new ArrayList<>();
	
	@OneToMany(mappedBy = "account")
	@JsonIgnore
	private List<Core_Categories> core_categories=new ArrayList<>();
	
	@OneToMany(mappedBy = "account")
	@JsonIgnore
	private List<Core_Access_Credentials> core_credentials=new ArrayList<>();
	
	@OneToMany(mappedBy = "account")
	@JsonIgnore
	private List<Core_Users> core_users=new ArrayList<>();
	
	@OneToMany(mappedBy = "account")
	@JsonIgnore
	private List<Inventory_Applications> inventory_applications=new ArrayList<>();
	
	@OneToMany(mappedBy = "account")
	@JsonIgnore
	private List<Backup_Operations> backup_operations=new ArrayList<>();
	
	@OneToMany(mappedBy = "account")
	@JsonIgnore
	private List<Backup_Settings> backup_settings=new ArrayList<>();
	
	@OneToMany(mappedBy = "account")
	@JsonIgnore
	private List<Core_Notifications> core_notifications=new ArrayList<>();
	
	@OneToMany(mappedBy = "account")
	@JsonIgnore
	private List<Inventory_Hosts> inventory_hosts=new ArrayList<>();
	
	@OneToMany(mappedBy = "account")
	@JsonIgnore
	private List<Inventory_Instances> inventory_instances=new ArrayList<>();
	
	@OneToMany(mappedBy = "account")
	@JsonIgnore
	private List<Inventory_Templates> inventory_templates=new ArrayList<>();
	
	@OneToMany(mappedBy = "account")
	@JsonIgnore
	private List<Monitoring_Automations> monitoring_automations=new ArrayList<>();
	
	@OneToMany(mappedBy = "account")
	@JsonIgnore
	private List<Monitoring_Commands> monitoring_commands=new ArrayList<>();
	
	@OneToMany(mappedBy = "account")
	@JsonIgnore
	private List<Monitoring_Metrics> monitoring_metrics=new ArrayList<>();
	
	@OneToMany(mappedBy = "account")
	@JsonIgnore
	private List<Monitoring_Policies> monitoring_policies=new ArrayList<>();
	
	@OneToMany(mappedBy = "account")
	@JsonIgnore
	private List<Monitoring_Settings> monitoring_settings=new ArrayList<>();
	
	@OneToMany(mappedBy = "account")
	@JsonIgnore
	private List<Networks_Domain_Names> network_domain_names=new ArrayList<>();
	
	@OneToMany(mappedBy = "account")
	@JsonIgnore
	private List<Api_Keys> api_keys=new ArrayList<>();
	
}

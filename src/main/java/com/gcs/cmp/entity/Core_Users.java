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
@Table(name="core_users")
@Data @AllArgsConstructor @NoArgsConstructor
public class Core_Users implements Serializable{

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
	
	@NotNull(message = "must not be null")
	@Size(max=100, message = "size too long... Max 100 characters")
	@Size(min=3, message = "size too short... At least 3 characters")
	private String firstname;
	
	@NotNull(message = "must not be null")
	@Size(max=100, message = "size too long... Max 100 characters")
	@Size(min=3, message = "size too short... At least 3 characters")
	private String lastname;

	@NotNull
	@NumberFormat
	@Min(message = "must be greater than or equal to 1", value = 1)
	private int role;
	
	@Nullable
	@Size(max=10, message = "size too long... Max 10 characters")
	private String language;
	
	@Nullable
	@Size(max=64, message = "size too long... Max 64 characters")
	private String timezone;
	
	@NotNull
	@Size(max=255, message = "size too long... Max 255 characters")
	private String browser;
	
	@NotNull
	@Size(max=64, message = "size too long... Max 64 characters")
	private String ip_address;
	
	@NotNull
	@Size(max=64, message = "size too long... Max 64 characters")
	private String email;
	
	@NotNull
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime last_auth;
	
	@Nullable
	@Size(max=64, message = "size too long... Max 64 characters")
	private String photo;
	
	@NotNull
	@NumberFormat
	@Min(message = "must be greater than or equal to 1", value = 1)
	private int has_token;
	
	@NotNull
	@NumberFormat
	@Min(message = "must be greater than or equal to 1", value = 1)
	private int status;
	
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Core_Logs> core_logs=new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Core_Users_Instances> core_users_instances=new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Core_Users_Modules> core_users_modules=new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Core_Users_Permissions> core_users_permissions=new ArrayList<>();
	
	
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Core_Users_Security> core_users_security=new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Core_Users_Metrics> core_users_metrics=new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Core_Users_Tokens> core_users_tokens=new ArrayList<>();
}

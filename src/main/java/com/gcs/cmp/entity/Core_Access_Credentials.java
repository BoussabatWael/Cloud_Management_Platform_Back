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
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="core_access_credentials")
@Data @AllArgsConstructor @NoArgsConstructor
public class Core_Access_Credentials implements Serializable{
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
	private String name;
	
	@NotNull
	@NumberFormat
	@Min(message = "must be greater than or equal to 1", value = 1)
	private int element;
	
	@NotNull
	@NumberFormat
	@Min(message = "must be greater than or equal to 1", value = 1)
	private int element_id;
	
	@NotNull
	@NumberFormat
	@Min(message = "must be greater than or equal to 1", value = 1)
	private int classe;
	
	@Nullable
	@Size(max=100, message = "size too long... Max 100 characters")
	private String url;
	
	@Nullable
	@Size(max=14, message = "size too long... Max 14 characters")
	private String port;
	
	@NotNull(message = "must not be null")
	@Size(max=100, message = "size too long... Max 100 characters")
	@Size(min=3, message = "size too short... At least 3 characters")
	private String login;
	
	@NotNull(message = "must not be null")
	@Size(max=255, message = "size too long... Max 255 characters")
	@Size(min=3, message = "size too short... At least 3 characters")
	private String password;
	
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

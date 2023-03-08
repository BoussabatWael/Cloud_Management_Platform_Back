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
@Table(name="inventory_applications_sources")
@Data @AllArgsConstructor @NoArgsConstructor
public class Inventory_Applications_Sources implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="application_id",nullable = false )
	private Inventory_Applications application;
	
	@NotNull
	@NumberFormat
	@Min(message = "must be greater than or equal to 1", value = 1)
	private int classe;
	
	@Nullable
	@NumberFormat
	@Min(message = "must be greater than or equal to 0", value = 0)
	private int source_type;
	
	@Nullable
	@Size(max=65, message = "size too long... Max 65 characters")
	private String source_account;
	
	@Nullable
	@Size(max=255, message = "size too long... Max 255 characters")
	private String source_url;
	
	@Nullable
	@Size(max=64, message = "size too long... Max 64 characters")
	private String source_build;
	
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

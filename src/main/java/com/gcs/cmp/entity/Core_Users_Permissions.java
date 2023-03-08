package com.gcs.cmp.entity;

import java.io.Serializable;

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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="core_users_permissions")
@Data @AllArgsConstructor @NoArgsConstructor
public class Core_Users_Permissions implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="user_id",nullable = false )
	private Core_Users user;
	
	@NotNull(message = "must not be null")
	@Size(max=500, message = "size too long... Max 255 characters")
	@Size(min=3, message = "size too short... At least 3 characters")
	private String code;
	
	@Nullable
	@NumberFormat
	@Min(message = "must be greater than or equal to 0", value = 0)
	private int dependency;
	
	@NotNull
	@NumberFormat
	@Min(message = "must be greater than or equal to 1", value = 1)
	private int status;
}

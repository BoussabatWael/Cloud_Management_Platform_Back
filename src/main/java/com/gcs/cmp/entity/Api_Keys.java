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
@Table(name="api_keys")
@Data @AllArgsConstructor @NoArgsConstructor
public class Api_Keys implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Nullable
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="account_id",nullable = true )
	private Core_Accounts account;
	
	@NotNull(message="must not be null")
	@Size(max=255, message = "size too long... Max 255 characters")
	@Size(min=6, message = "size too short... At least 6 characters")
	private String key_value;
	
	@NotNull
	@NumberFormat
	@Min(message = "must be greater than or equal to 1", value = 1)
	private int type;
	
	@NotNull
	@NumberFormat
	@Min(message = "must be greater than or equal to 1", value = 1)
	private int status;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime start_date;
	
	@Nullable
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime end_date;
	
	@OneToMany(mappedBy = "apikey")
	@JsonIgnore
	private List<Api_Keys_Permissions> api_keys_permissions=new ArrayList<>();
	
	@OneToMany(mappedBy = "apikey")
	@JsonIgnore
	private List<Core_Api_Logs> core_Api_Logs=new ArrayList<>();
	
}

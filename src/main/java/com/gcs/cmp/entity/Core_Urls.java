package com.gcs.cmp.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="core_urls")
@Data @AllArgsConstructor @NoArgsConstructor
public class Core_Urls implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
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
	
	@NotNull(message = "must not be null")
	@Size(max=100, message = "size too long... Max 100 characters")
	@Size(min=3, message = "size too short... At least 3 characters")
	private String url;
}

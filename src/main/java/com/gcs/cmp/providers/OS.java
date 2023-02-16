package com.gcs.cmp.providers;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class OS implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private String arch;
	private String family;
}

package com.gcs.cmp.providers;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CustomerBalance implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String month_to_date_balance;
	private String account_balance;
	private String month_to_date_usage;
	private Date generated_at;
}

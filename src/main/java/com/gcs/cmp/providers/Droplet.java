package com.gcs.cmp.providers;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Droplet implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private Integer memory;
	private Integer vcpus;
	private Integer disk;
	private Boolean locked;
	private String status;
	private Object kernel;
	private Date creted_at;
	private List<String> features;
	private List<Integer> backup_ids;
	private Object next_backup_window;
	private List<Integer> snapshot_ids;
	private Object image;
	private List<String> volume_ids;
	private Object size;
	private String size_slug;
	private Object networks;
	private Object region;
	private List<String> tags;
	private String vpc_uuid;
}

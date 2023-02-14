package com.gcs.cmp.providers.OVH;

import java.sql.SQLException;

import org.springframework.http.ResponseEntity;

public interface OVH_Service {

	public ResponseEntity<Object> getDomainsList() throws SQLException;
	public ResponseEntity<Object> getDomainProperties(String service_name) throws SQLException;
	public ResponseEntity<Object> getDomainZoneProperties(String zone_name) throws SQLException;
	public ResponseEntity<Object> getDomainRecords(String zone_name) throws SQLException;
	public ResponseEntity<Object> getDomainRecordsZoneProperties(String zone_name, Long id) throws SQLException;
	public ResponseEntity<Object> getDomainServiceProperties(String zone_name) throws SQLException;
	public ResponseEntity<Object> getHostingList() throws SQLException;
	public ResponseEntity<Object> getHostingAttachedDomain(String domain) throws SQLException;
	public ResponseEntity<Object> getDomainAttachedToHost(String service_name) throws SQLException;
	public ResponseEntity<Object> getSSLProperties(String service_name) throws SQLException;
	
}

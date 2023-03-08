package com.gcs.cmp.providers.OVH;

import java.sql.SQLException;


public interface OVH_Service {

	public Object getDomainsList() throws SQLException;
	public Object getDomainProperties(String service_name) throws SQLException;
	public Object getDomainZoneProperties(String zone_name) throws SQLException;
	public Object getDomainRecords(String zone_name) throws SQLException;
	public Object getDomainRecordsZoneProperties(String zone_name, Long id) throws SQLException;
	public Object getDomainServiceProperties(String zone_name) throws SQLException;
	public Object getHostingList() throws SQLException;
	public Object getHostingAttachedDomain(String domain) throws SQLException;
	public Object getDomainAttachedToHost(String service_name) throws SQLException;
	public Object getSSLProperties(String service_name) throws SQLException;
}

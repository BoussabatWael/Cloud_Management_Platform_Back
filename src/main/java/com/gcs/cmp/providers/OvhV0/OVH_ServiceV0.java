package com.gcs.cmp.providers.OvhV0;

import java.sql.SQLException;


public interface OVH_ServiceV0 {

	public Object getDomainsList() throws SQLException;
	public Object getDomainProperties(String serviceName) throws SQLException;
	public Object getDomainZoneProperties(String zoneName) throws SQLException;
	public Object getDomainRecords(String zoneName) throws SQLException;
	public Object getDomainRecordsZoneProperties(String zoneName, Long id) throws SQLException;
	public Object getDomainServiceProperties(String zoneName) throws SQLException;
	public Object getHostingList() throws SQLException;
	public Object getHostingAttachedDomain(String domain) throws SQLException;
	public Object getDomainAttachedToHost(String serviceName) throws SQLException;
	public Object getSSLProperties(String serviceName) throws SQLException;
}

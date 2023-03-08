package com.gcs.cmp.providers.Vultr;

import java.sql.SQLException;


public interface Vultr_Service {

	public Object getAccountInfo() throws SQLException;
	public Object getApplicationList() throws SQLException;
	public Object getBackupsList() throws SQLException;
	public Object getBillingHistoryList() throws SQLException;
	public Object getInvoicesList() throws SQLException;
	public Object getDnsDomainsList() throws SQLException;
	public Object getSoaInformations(String dns_domain) throws SQLException;
	public Object getDnsSecInfo(String dns_domain) throws SQLException;
	public Object getRecordsList(String dns_domain) throws SQLException;
	public Object getFirewallGroupsList() throws SQLException;
	public Object getFirewallRulesList(String firewall_group_id) throws SQLException;
	public Object getInstancesList() throws SQLException;
	public Object getInstance(String instance_id) throws SQLException;
	public Object getIPv4InstanceInformationList(String instance_id) throws SQLException;
	public Object getIPv6InstanceInformation(String instance_id) throws SQLException;
	public Object getIPv6InstanceReverseList(String instance_id) throws SQLException;
	public Object getOsList() throws SQLException;
	public Object getPlansList() throws SQLException;
	public Object getRegionsList() throws SQLException;
	public Object getAvailablePlansInRegionList(String region_id) throws SQLException;
	public Object getSnapshotsList() throws SQLException;
}

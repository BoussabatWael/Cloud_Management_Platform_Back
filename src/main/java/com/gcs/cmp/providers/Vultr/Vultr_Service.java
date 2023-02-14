package com.gcs.cmp.providers.Vultr;

import java.sql.SQLException;

import org.springframework.http.ResponseEntity;

public interface Vultr_Service {

	public ResponseEntity<Object> getAccountInfo() throws SQLException;
	public ResponseEntity<Object> getApplicationList() throws SQLException;
	public ResponseEntity<Object> getBackupsList() throws SQLException;
	public ResponseEntity<Object> getBillingHistoryList() throws SQLException;
	public ResponseEntity<Object> getInvoicesList() throws SQLException;
	public ResponseEntity<Object> getDnsDomainsList() throws SQLException;
	public ResponseEntity<Object> getSoaInformations(String dns_domain) throws SQLException;
	public ResponseEntity<Object> getDnsSecInfo(String dns_domain) throws SQLException;
	public ResponseEntity<Object> getRecordsList(String dns_domain) throws SQLException;
	public ResponseEntity<Object> getFirewallGroupsList() throws SQLException;
	public ResponseEntity<Object> getFirewallRulesList(String firewall_group_id) throws SQLException;
	public ResponseEntity<Object> getInstancesList() throws SQLException;
	public ResponseEntity<Object> getInstance(String instance_id) throws SQLException;
	public ResponseEntity<Object> getIPv4InstanceInformationList(String instance_id) throws SQLException;
	public ResponseEntity<Object> getIPv6InstanceInformation(String instance_id) throws SQLException;
	public ResponseEntity<Object> getIPv6InstanceReverseList(String instance_id) throws SQLException;
	public ResponseEntity<Object> getOsList() throws SQLException;
	public ResponseEntity<Object> getPlansList() throws SQLException;
	public ResponseEntity<Object> getRegionsList() throws SQLException;
	public ResponseEntity<Object> getAvailablePlansInRegionList(String region_id) throws SQLException;
	public ResponseEntity<Object> getSnapshotsList() throws SQLException;
}

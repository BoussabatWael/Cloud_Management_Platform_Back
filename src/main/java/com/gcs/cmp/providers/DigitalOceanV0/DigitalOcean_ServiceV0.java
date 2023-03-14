package com.gcs.cmp.providers.DigitalOceanV0;

import java.sql.SQLException;


public interface DigitalOcean_ServiceV0 {

	public Object getActionsList() throws SQLException;
	public Object getAppsList() throws SQLException;
	public Object getExistingApp(String id) throws SQLException;
	public Object getCustomerBalance() throws SQLException;
	public Object getBillingHistoryList() throws SQLException;
	public Object getInvoicesList() throws SQLException;
	public Object getDomainRecordsList(String domain_name) throws SQLException;
	public Object getExistingDomainRecord(String domain_name, Long domain_record_id) throws SQLException;
	public Object getDomainsList() throws SQLException;
	public Object getExistingDomain(String domain_name) throws SQLException;
	public Object getActionsDropletList(Long droplet_id) throws SQLException;
	public Object getDropletsList() throws SQLException;
	public Object getExistingDroplet(Long droplet_id) throws SQLException;
	public Object getBackupsDropletList(Long droplet_id) throws SQLException;
	public Object getSnapshotsDropletList(Long droplet_id) throws SQLException;
	public Object getFirewallDropletList(Long droplet_id) throws SQLException;
	public Object getFirewallsList() throws SQLException;
	public Object getExistingFirewall(String firewall_id) throws SQLException;
	public Object getRegionsList() throws SQLException;
	public Object getDropletSizesList() throws SQLException;
}

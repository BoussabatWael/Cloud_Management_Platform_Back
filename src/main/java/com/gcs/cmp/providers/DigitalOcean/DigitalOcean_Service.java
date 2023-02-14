package com.gcs.cmp.providers.DigitalOcean;

import java.sql.SQLException;

import org.springframework.http.ResponseEntity;

public interface DigitalOcean_Service {

	public ResponseEntity<Object> getActionsList() throws SQLException;
	public ResponseEntity<Object> getAppsList() throws SQLException;
	public ResponseEntity<Object> getExistingApp(String id) throws SQLException;
	public ResponseEntity<Object> getCustomerBalance() throws SQLException;
	public ResponseEntity<Object> getBillingHistoryList() throws SQLException;
	public ResponseEntity<Object> getInvoicesList() throws SQLException;
	public ResponseEntity<Object> getDomainRecordsList(String domain_name) throws SQLException;
	public ResponseEntity<Object> getExistingDomainRecord(String domain_name, Long domain_record_id) throws SQLException;
	public ResponseEntity<Object> getDomainsList() throws SQLException;
	public ResponseEntity<Object> getExistingDomain(String domain_name) throws SQLException;
	public ResponseEntity<Object> getActionsDropletList(Long droplet_id) throws SQLException;
	public ResponseEntity<Object> getDropletsList() throws SQLException;
	public ResponseEntity<Object> getExistingDroplet(Long droplet_id) throws SQLException;
	public ResponseEntity<Object> getBackupsDropletList(Long droplet_id) throws SQLException;
	public ResponseEntity<Object> getSnapshotsDropletList(Long droplet_id) throws SQLException;
	public ResponseEntity<Object> getFirewallDropletList(Long droplet_id) throws SQLException;
	public ResponseEntity<Object> getFirewallsList() throws SQLException;
	public ResponseEntity<Object> getExistingFirewall(String firewall_id) throws SQLException;
	public ResponseEntity<Object> getRegionsList() throws SQLException;
	public ResponseEntity<Object> getDropletSizesList() throws SQLException;
}

package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gcs.cmp.entity.Networks_SSL_Certificates;

public interface Networks_SSL_Certificates_Repo extends JpaRepository<Networks_SSL_Certificates, Long>{
	
	@Query(value="SELECT networks_ssl_certificates.* FROM networks_ssl_certificates INNER JOIN networks_domain_names ON networks_ssl_certificates.domain_id = networks_domain_names.id INNER JOIN cloud_providers_accounts ON cloud_providers_accounts.id = networks_ssl_certificates.cloud_provider_id WHERE networks_ssl_certificates.status IN (1,2,3) AND networks_domain_names.account_id=?1 AND networks_domain_names.status IN (1,2,3) AND networks_ssl_certificates.status IN (1,2,3)",nativeQuery=true)
	public List<Networks_SSL_Certificates> getNetworks_SSL_CertificatesList(Long account_id);
	
	@Query(value="SELECT a.* FROM networks_ssl_certificates a INNER JOIN networks_domain_names b ON a.domain_id=b.id WHERE a.domain_id=?2 AND a.status IN (1,2,3) AND b.account_id=?1 AND b.status IN (1,2,3)",nativeQuery=true)
	public List<Networks_SSL_Certificates> getDomainNetworks_SSL_CertificatesList(Long account_id, Long domain_name_id);
	
}

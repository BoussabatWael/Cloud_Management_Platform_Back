package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;
import com.gcs.cmp.entity.Networks_SSL_Certificates;

public interface Networks_SSL_Certificates_Service {

	public Networks_SSL_Certificates addNetworks_SSL_Certificates(Networks_SSL_Certificates networks_SSL_Certificates) ;
	public List<Networks_SSL_Certificates> getNetworks_SSL_CertificatesList(Long account_id);
	public List<Networks_SSL_Certificates> getDomainNetworks_SSL_CertificatesList(Long account_id, Long domain_name_id);
	public Networks_SSL_Certificates updateNetworks_SSL_Certificates(Networks_SSL_Certificates networks_SSL_Certificates);
	public Optional<Networks_SSL_Certificates> findNetworks_SSL_CertificatesById(Long id) ;
	public String deleteNetworks_SSL_Certificates(Long id) ;
}

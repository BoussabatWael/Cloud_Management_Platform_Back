package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Networks_SSL_Certificates;
import com.gcs.cmp.repository.Networks_SSL_Certificates_Repo;

@Service
public class Networks_SSL_Certificates_ServiceImpl implements Networks_SSL_Certificates_Service{

	@Autowired
	private Networks_SSL_Certificates_Repo networks_SSL_Certificates_Repo;

	
	@Override
	public Networks_SSL_Certificates addNetworks_SSL_Certificates(Networks_SSL_Certificates networks_SSL_Certificates) {
		// TODO Auto-generated method stub
		return networks_SSL_Certificates_Repo.save(networks_SSL_Certificates);
	}

	@Override
	public List<Networks_SSL_Certificates> getNetworks_SSL_CertificatesList(Long account_id) {
		// TODO Auto-generated method stub
		return networks_SSL_Certificates_Repo.getNetworks_SSL_CertificatesList(account_id);
	}

	@Override
	public Networks_SSL_Certificates updateNetworks_SSL_Certificates(
			Networks_SSL_Certificates networks_SSL_Certificates) {
		// TODO Auto-generated method stub
		return networks_SSL_Certificates_Repo.save(networks_SSL_Certificates);
	}

	@Override
	public Optional<Networks_SSL_Certificates> findNetworks_SSL_CertificatesById(Long id) {
		// TODO Auto-generated method stub
		return networks_SSL_Certificates_Repo.findById(id);
	}

	@Override
	public String deleteNetworks_SSL_Certificates(Long id) {
		try {
			// TODO Auto-generated method stub
			networks_SSL_Certificates_Repo.deleteById(id);
			return "SSL certificate "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}
		
	}

	@Override
	public List<Networks_SSL_Certificates> getDomainNetworks_SSL_CertificatesList(Long account_id,
			Long domain_name_id) {
		// TODO Auto-generated method stub
		return networks_SSL_Certificates_Repo.getDomainNetworks_SSL_CertificatesList(account_id, domain_name_id);
	}

}

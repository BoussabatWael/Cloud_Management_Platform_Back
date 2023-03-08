package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcs.cmp.entity.Core_Notifications;
import com.gcs.cmp.repository.Core_Notifications_Repo;

@Service
public class Core_Notifications_ServiceImpl implements Core_Notifications_Service{

	@Autowired
	private Core_Notifications_Repo core_Notifications_Repo;
	
	@Override
	public Core_Notifications addCore_Notifications(Core_Notifications core_Notifications) {
		// TODO Auto-generated method stub
		return core_Notifications_Repo.save(core_Notifications);
	}

	@Override
	public List<Core_Notifications> getCore_NotificationsList(Long account_id) {
		// TODO Auto-generated method stub
		return core_Notifications_Repo.getCore_NotificationsList(account_id);
	}

	@Override
	public Core_Notifications updateCore_Notifications(Core_Notifications core_Notifications) {
		// TODO Auto-generated method stub
		return core_Notifications_Repo.save(core_Notifications);
	}

	@Override
	public Optional<Core_Notifications> findCore_NotificationsById(Long id) {
		// TODO Auto-generated method stub
		return core_Notifications_Repo.findById(id);
	}

	@Override
	public String deleteCore_Notifications(Long id) {
		try {
			// TODO Auto-generated method stub
			core_Notifications_Repo.deleteById(id);
			return "Notification "+id+" has been deleted!";
		}catch(Exception e) {
			return null;
		}	
	}
	
}

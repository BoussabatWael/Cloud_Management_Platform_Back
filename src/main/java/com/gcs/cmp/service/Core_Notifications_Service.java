package com.gcs.cmp.service;

import java.util.List;
import java.util.Optional;
import com.gcs.cmp.entity.Core_Notifications;

public interface Core_Notifications_Service {
	
	public Core_Notifications addCore_Notifications(Core_Notifications core_Notifications) ;
	public List<Core_Notifications> getCore_NotificationsList(Long account_id);
	public Core_Notifications updateCore_Notifications(Core_Notifications core_Notifications);
	public Optional<Core_Notifications> findCore_NotificationsById(Long id) ;
	public String deleteCore_Notifications(Long id) ;
	
}

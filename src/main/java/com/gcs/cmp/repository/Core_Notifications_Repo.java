package com.gcs.cmp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gcs.cmp.entity.Core_Notifications;

public interface Core_Notifications_Repo extends JpaRepository<Core_Notifications, Long>{

	@Query(value="SELECT * FROM core_notifications WHERE account_id=?1 AND status IN (1,2,3)",nativeQuery=true)
	public List<Core_Notifications> getCore_NotificationsList(Long account_id);
	
}

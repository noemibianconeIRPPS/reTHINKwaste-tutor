package it.cnr.rethinkwaste.repository;

import it.cnr.rethinkwaste.model.NotificationContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationContentRepository extends JpaRepository<NotificationContent, Long> {

    NotificationContent getNotificationContentByName(String name);

    NotificationContent getNotificationContentById(Long id);

}

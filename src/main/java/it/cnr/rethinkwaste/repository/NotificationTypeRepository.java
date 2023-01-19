package it.cnr.rethinkwaste.repository;

import it.cnr.rethinkwaste.model.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationTypeRepository extends JpaRepository<NotificationType, Long> {

    NotificationType getNotificationTypeByName(String name);

    NotificationType getNotificationTypeById(Long id);

}

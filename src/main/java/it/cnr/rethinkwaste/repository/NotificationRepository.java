package it.cnr.rethinkwaste.repository;

import it.cnr.rethinkwaste.model.Notification;
import it.cnr.rethinkwaste.model.NotificationType;
import it.cnr.rethinkwaste.model.User_;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Notification getNotificationByObjectIdAndTypeAndReceiver(Long objectId, NotificationType type, User_ receiver);

    void deleteByReceiverOrSender(User_ user1, User_ user2);

    List<Notification> findAllByReceiverAndReadOrderByCreationDateDesc(User_ receiver, boolean read);

    List<Notification> findAllByObjectIdAndType(Long objectId, NotificationType type);

}

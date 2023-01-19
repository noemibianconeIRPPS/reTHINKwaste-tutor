package it.cnr.rethinkwaste.service;

import it.cnr.rethinkwaste.model.Notification;
import it.cnr.rethinkwaste.model.User_;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

@Service
public interface NotificationService {

    TreeMap<Notification, List<Object>> findAllByReceiverAndReadOrderByCreationDateDesc(User_ receiver, boolean read);

    void saveNotification(Notification notification);

    Optional<Notification> getNotificationById(Long id);

    void createNotificationForUserRegistration(User_ sender);

}

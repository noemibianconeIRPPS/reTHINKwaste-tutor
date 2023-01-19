package it.cnr.rethinkwaste.service;

import it.cnr.rethinkwaste.model.*;
import it.cnr.rethinkwaste.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationContentRepository notificationContentRepository;

    @Override
    public TreeMap<Notification, List<Object>> findAllByReceiverAndReadOrderByCreationDateDesc(User_ receiver, boolean read) {
        TreeMap<Notification, List<Object>> result = new TreeMap<>();
        List<Notification> notifications = notificationRepository.findAllByReceiverAndReadOrderByCreationDateDesc(receiver, read);
        for(Notification n : notifications) {
            if(n.isRead()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(n.getCreationDate());
                LocalDate dbDate = LocalDate.parse(date);
                if(dbDate.isBefore(LocalDate.now().minusMonths(1))) {
                    notificationRepository.delete(n);
                }
            }

            if(n.getType().getId() == 10) {
                Optional<User_> objectUser = userRepository.findById(n.getObjectId());
                if(objectUser.isPresent()) {
                    List<Object> list = new ArrayList<>();
                    list.add(objectUser.get());
                    result.put(n, list);
                }
            }
        }
        return result;
    }

    @Override
    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    @Override
    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    @Override
    public void createNotificationForUserRegistration(User_ registeredUser) {
        Optional<Role> adminRole = roleRepository.findById(1L);
        if(adminRole.isPresent()) {
            List<User_> admins = userRepository.findAllByRolesContaining(adminRole.get());
            for (User_ admin : admins) {
                this.createAndSaveNotification("REGISTRATION", "7", registeredUser.getId(), admin, registeredUser);
            }
        }
    }

    public void createAndSaveNotification(String notificationType, String notificationId, Long objectId, User_ receiver, User_ sender) {
        Notification notification = new Notification();
        notification.setCreationDate(new Date());
        notification.setObjectId(objectId);
        notification.setType(notificationTypeRepository.getNotificationTypeByName(notificationType));
        notification.setReceiver(receiver);
        notification.setSender(sender);
        notification.setContent(notificationContentRepository.getNotificationContentById(Long.parseLong(notificationId)));
        notification.setRead(false);
        notificationRepository.save(notification);
    }

}

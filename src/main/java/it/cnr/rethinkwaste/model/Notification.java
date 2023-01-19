package it.cnr.rethinkwaste.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Notification implements Comparable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "notification_join_notification_content",
            joinColumns = @JoinColumn(
                    name = "notification_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "notification_content_id", referencedColumnName = "id"))
    private NotificationContent content;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User_.class)
    private User_ receiver;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User_.class)
    private User_ sender;

    private Date creationDate;

    private boolean read;

    @Column(nullable = true)
    private Boolean accepted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "notification_join_notification_type",
            joinColumns = @JoinColumn(
                    name = "notification_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "notification_type_id", referencedColumnName = "id"))
    private NotificationType type;

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    private Long objectId;

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public NotificationContent getContent() {
        return content;
    }

    public void setContent(NotificationContent content) {
        this.content = content;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User_ getReceiver() {
        return receiver;
    }

    public void setReceiver(User_ receiver) {
        this.receiver = receiver;
    }

    public User_ getSender() {
        return sender;
    }

    public void setSender(User_ sender) {
        this.sender = sender;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public int compareTo(Object object) {
        Notification notification = (Notification) object;
        return notification.getCreationDate().compareTo(this.getCreationDate());
    }

}

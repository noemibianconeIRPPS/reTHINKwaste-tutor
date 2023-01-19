package it.cnr.rethinkwaste.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Message implements Comparable {

    @Id
    @SequenceGenerator(name = "message_sequence_generator", sequenceName = "message_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "message_sequence_generator")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User_.class)
    private User_ sender;
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = MessageType.class)
    private MessageType type;
    @Column(columnDefinition = "text")
    private String body;
    @Transient
    private boolean read;
    private Date creationDate;

    @Override
    public int compareTo(Object o) {
        Message m = (Message) o;
        return this.creationDate.compareTo(m.getCreationDate());
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Message() {
    }

    public User_ getSender() {
        return sender;
    }

    public void setSender(User_ sender) {
        this.sender = sender;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}

package it.cnr.rethinkwaste.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Email implements Comparable {

    @Id
    @SequenceGenerator(name = "email_sequence_generator", sequenceName = "email_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "email_sequence_generator")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User_.class)
    private User_ sender;

    @Column(columnDefinition = "text")
    private String destination;
    @Column(columnDefinition = "text")
    private String object;
    @Column(columnDefinition = "text")
    private String body;

    private Date creationDate;

    @Override
    public int compareTo(Object o) {
        Email m = (Email) o;
        return this.creationDate.compareTo(m.getCreationDate());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Email() {
    }

    public User_ getSender() {
        return sender;
    }

    public void setSender(User_ sender) {
        this.sender = sender;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

}

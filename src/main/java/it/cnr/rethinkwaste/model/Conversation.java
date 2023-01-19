package it.cnr.rethinkwaste.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Conversation {

    @Id
    @SequenceGenerator(name = "conversation_sequence_generator", sequenceName = "conversation_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "conversation_sequence_generator")
    private Long id;

    @ManyToMany(targetEntity = User_.class)
    @JoinTable(
            name = "conversation_join_user",
            joinColumns = @JoinColumn(
                    name = "conversation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"))
    private List<User_> users;

    @ManyToMany(targetEntity = User_.class)
    @JoinTable(
            name = "conversation_join_participant",
            joinColumns = @JoinColumn(
                    name = "conversation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "participant_id", referencedColumnName = "id"))
    private List<User_> participants;

    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "conversation_join_message",
            joinColumns = @JoinColumn(
                    name = "conversation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "message_id", referencedColumnName = "id"))
    private List<Message> messages;

    public List<User_> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User_> participants) {
        this.participants = participants;
    }

    private Date creationDate;

    private Date updateDate;

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<User_> getUsers() {
        return users;
    }

    public void setUsers(List<User_> users) {
        this.users = users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}

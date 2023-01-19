package it.cnr.rethinkwaste.model;

import javax.persistence.*;

@Entity
public class MessageDetail {

    @Id
    @SequenceGenerator(name = "message_detail_sequence_generator", sequenceName = "message_detail_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "message_detail_sequence_generator")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User_.class)
    private User_ notReadingUser;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Conversation.class)
    private Conversation conversation;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Message.class)
    private Message message;

    public User_ getNotReadingUser() {
        return notReadingUser;
    }

    public void setNotReadingUser(User_ notReadingUser) {
        this.notReadingUser = notReadingUser;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

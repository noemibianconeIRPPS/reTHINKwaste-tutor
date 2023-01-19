package it.cnr.rethinkwaste.service;

import it.cnr.rethinkwaste.model.Conversation;
import it.cnr.rethinkwaste.model.Message;
import it.cnr.rethinkwaste.model.User_;
import it.cnr.rethinkwaste.model.MessageDetail;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MessageService {

    void sendMessages(User_ sender, String messageBody, String[] usersArray);

    void deleteConversationByParticipantOrByUsers(User_ user);

    Message sendReply(User_ sender, String messageBody, Conversation conversation);

    List<Conversation> findAllByUsersContaining(User_ user);

    Optional<Conversation> findById(Long id);

    void deleteConversation(Conversation conversation, User_ user);

    List<MessageDetail> findAllByConversationAndNotReadingUser(Conversation conversation, User_ notReadingUser);

    void deleteAllByConversationAndNotReadingUser(Conversation conversation, User_ notReadingUser);

    List<MessageDetail> findAllByNotReadingUser(User_ notReadingUser);

    Long countByConversationAndNotReadingUser(Conversation conversation, User_ notReadingUser);
}

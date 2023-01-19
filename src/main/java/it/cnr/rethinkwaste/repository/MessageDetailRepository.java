package it.cnr.rethinkwaste.repository;

import it.cnr.rethinkwaste.model.Conversation;
import it.cnr.rethinkwaste.model.MessageDetail;
import it.cnr.rethinkwaste.model.User_;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDetailRepository extends JpaRepository<MessageDetail, Long> {

    List<MessageDetail> findAllByConversationAndNotReadingUser(Conversation conversation, User_ notReadingUser);
    void deleteAllByConversationAndNotReadingUser(Conversation conversation, User_ notReadingUser);
    List<MessageDetail> findAllByNotReadingUser(User_ notReadingUser);
    Long countByConversationAndNotReadingUser(Conversation conversation, User_ notReadingUser);

}

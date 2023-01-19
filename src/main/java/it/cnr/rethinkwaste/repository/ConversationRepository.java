package it.cnr.rethinkwaste.repository;

import it.cnr.rethinkwaste.model.Conversation;
import it.cnr.rethinkwaste.model.User_;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    List<Conversation> findAllByUsersContainingOrderByUpdateDateDesc(User_ user);

    void deleteByUsersContainingOrParticipantsContaining(User_ user1, User_ user2);

}

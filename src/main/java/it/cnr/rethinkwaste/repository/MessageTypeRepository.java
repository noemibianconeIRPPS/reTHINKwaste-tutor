package it.cnr.rethinkwaste.repository;

import it.cnr.rethinkwaste.model.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageTypeRepository extends JpaRepository<MessageType, Long> {

    Optional<MessageType> findById(Long id);
    MessageType findByName(String name);

}

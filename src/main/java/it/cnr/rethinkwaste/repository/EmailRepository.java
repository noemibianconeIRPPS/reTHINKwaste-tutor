package it.cnr.rethinkwaste.repository;

import it.cnr.rethinkwaste.model.Email;
import it.cnr.rethinkwaste.model.User_;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

    List<Email> findBySenderOrderByCreationDateDesc(User_ user);

    Optional<Email> findById(Long id);

}

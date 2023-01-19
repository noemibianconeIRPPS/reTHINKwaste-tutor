package it.cnr.rethinkwaste.repository;

import it.cnr.rethinkwaste.model.Role;
import it.cnr.rethinkwaste.model.User_;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User_, Long> {

    User_ findByEmail(String email);

    User_ findByResetToken(String resetToken);

    Optional<User_> findById(Long id);

    List<User_> findAllByRolesContaining(Role role);

    List<User_> findByOrganizationNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String search1, String search2);

}

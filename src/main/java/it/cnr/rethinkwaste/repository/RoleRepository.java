package it.cnr.rethinkwaste.repository;

import it.cnr.rethinkwaste.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findById(Long id);
    Role findByName(String name);

}

package it.cnr.rethinkwaste.repository;

import it.cnr.rethinkwaste.model.OrganizationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationTypeRepository  extends JpaRepository<OrganizationType, Long> {
}

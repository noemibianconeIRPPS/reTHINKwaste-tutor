package it.cnr.rethinkwaste.repository.assessment;

import it.cnr.rethinkwaste.model.assessment.SubprofileTranslationObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubprofileTranslationObjectRepository extends JpaRepository<SubprofileTranslationObject, Long> {
}

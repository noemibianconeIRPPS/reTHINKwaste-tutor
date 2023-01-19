package it.cnr.rethinkwaste.repository.assessment;

import it.cnr.rethinkwaste.model.assessment.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

    Language findByAcronym(String acronym);

}
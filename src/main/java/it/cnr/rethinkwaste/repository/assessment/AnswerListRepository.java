package it.cnr.rethinkwaste.repository.assessment;

import it.cnr.rethinkwaste.model.assessment.AnswerList;
import it.cnr.rethinkwaste.model.assessment.CategoryInstanceForMC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerListRepository extends JpaRepository<AnswerList, Long> {
}

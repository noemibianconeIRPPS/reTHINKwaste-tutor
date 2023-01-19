package it.cnr.rethinkwaste.repository.assessment;

import it.cnr.rethinkwaste.model.Conversation;
import it.cnr.rethinkwaste.model.User_;
import it.cnr.rethinkwaste.model.assessment.Category;
import it.cnr.rethinkwaste.model.assessment.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findAllByQuestionsContaining(Question question);

}

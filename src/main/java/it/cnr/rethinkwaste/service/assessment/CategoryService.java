package it.cnr.rethinkwaste.service.assessment;

import it.cnr.rethinkwaste.model.assessment.Category;
import it.cnr.rethinkwaste.model.assessment.Question;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CategoryService {

    Optional<Category> findById(Long id);

    Category findAllByQuestionsContaining(Question question);

    void save(Category category);

}

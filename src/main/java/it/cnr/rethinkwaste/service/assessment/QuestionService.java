package it.cnr.rethinkwaste.service.assessment;

import it.cnr.rethinkwaste.model.assessment.Question;
import it.cnr.rethinkwaste.model.assessment.QuestionTranslationObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface QuestionService {

    Optional<Question> findById(Long id);

    void save(QuestionTranslationObject questionTranslationObject);

}

package it.cnr.rethinkwaste.service.assessment;

import it.cnr.rethinkwaste.model.assessment.Question;
import it.cnr.rethinkwaste.model.assessment.QuestionTranslationObject;
import it.cnr.rethinkwaste.repository.assessment.QuestionRepository;
import it.cnr.rethinkwaste.repository.assessment.QuestionTranslationObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionTranslationObjectRepository questionTranslationObjectRepository;

    public Optional<Question> findById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public void save(QuestionTranslationObject questionTranslationObject) {
        questionTranslationObjectRepository.save(questionTranslationObject);
    }

}

package it.cnr.rethinkwaste.service.assessment;

import it.cnr.rethinkwaste.model.assessment.Answer;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AnswerService {

    Optional<Answer> findById(Long id);

    void save(Answer answer);

}

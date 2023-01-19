package it.cnr.rethinkwaste.service.assessment;

import it.cnr.rethinkwaste.model.assessment.AnswerList;
import it.cnr.rethinkwaste.model.assessment.CategoryInstanceForMC;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AnswerListService {

    AnswerList save(AnswerList answerList);

}

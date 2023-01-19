package it.cnr.rethinkwaste.service.assessment;

import it.cnr.rethinkwaste.model.assessment.AnswerList;
import it.cnr.rethinkwaste.model.assessment.CategoryInstanceForMC;
import it.cnr.rethinkwaste.repository.assessment.AnswerListRepository;
import it.cnr.rethinkwaste.repository.assessment.CategoryInstanceForMCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerListServiceImpl implements AnswerListService {

    @Autowired
    private AnswerListRepository answerListRepository;

    @Override
    public AnswerList save(AnswerList answerList) {
        return answerListRepository.save(answerList);
    }

}

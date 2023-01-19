package it.cnr.rethinkwaste.service.assessment;

import it.cnr.rethinkwaste.model.assessment.Language;
import it.cnr.rethinkwaste.model.assessment.LearningMaterial;
import it.cnr.rethinkwaste.repository.assessment.LanguageRepository;
import it.cnr.rethinkwaste.repository.assessment.LearningMaterialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    @Override
    public List<Language> findAll() { return languageRepository.findAll();    }

    @Override
    public Language findByAcronym(String acronym) { return languageRepository.findByAcronym(acronym); }

}

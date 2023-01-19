package it.cnr.rethinkwaste.service.assessment;

import it.cnr.rethinkwaste.model.assessment.Language;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LanguageService {

    List<Language> findAll();

    Language findByAcronym(String acronym);

}

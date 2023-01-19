package it.cnr.rethinkwaste.service.assessment;

import it.cnr.rethinkwaste.model.assessment.Report;
import it.cnr.rethinkwaste.model.assessment.Subprofile;
import it.cnr.rethinkwaste.model.assessment.SubprofileTranslationObject;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface SubprofileService {

    Optional<Subprofile> findById(Long id);

    void save(SubprofileTranslationObject subprofileTranslationObject);

}

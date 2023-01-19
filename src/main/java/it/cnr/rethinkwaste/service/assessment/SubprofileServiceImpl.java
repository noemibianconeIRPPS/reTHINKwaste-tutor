package it.cnr.rethinkwaste.service.assessment;

import it.cnr.rethinkwaste.model.assessment.Subprofile;
import it.cnr.rethinkwaste.model.assessment.SubprofileTranslationObject;
import it.cnr.rethinkwaste.repository.assessment.SubprofileRepository;
import it.cnr.rethinkwaste.repository.assessment.SubprofileTranslationObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubprofileServiceImpl implements SubprofileService {

    @Autowired
    private SubprofileRepository subprofileRepository;

    @Autowired
    private SubprofileTranslationObjectRepository subprofileTranslationObjectRepository;

    @Override
    public Optional<Subprofile> findById(Long id) {
        return subprofileRepository.findById(id);
    }

    @Override
    public void save(SubprofileTranslationObject subprofileTranslationObject) {
        subprofileTranslationObjectRepository.save(subprofileTranslationObject);
    }

}

package it.cnr.rethinkwaste.service.assessment;

import it.cnr.rethinkwaste.model.assessment.Language;
import it.cnr.rethinkwaste.model.assessment.LearningMaterialType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LearningMaterialTypeService {

    List<LearningMaterialType> findAll();

    LearningMaterialType findByType(String type);

    LearningMaterialType findById(Long id);

}

package it.cnr.rethinkwaste.service.assessment;

import it.cnr.rethinkwaste.model.assessment.LearningMaterial;
import it.cnr.rethinkwaste.model.assessment.Module;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LearningMaterialService {

    LearningMaterial findById(Long id);

    List<LearningMaterial> findAll();

    void save(LearningMaterial learningMaterial);

    void delete(LearningMaterial learningMaterial);

    List<LearningMaterial> findByModulesContains(Module module);

    List<LearningMaterial> findByVisible(boolean visible);

}

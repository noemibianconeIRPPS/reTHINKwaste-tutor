package it.cnr.rethinkwaste.service.assessment;

import it.cnr.rethinkwaste.model.assessment.LearningMaterial;
import it.cnr.rethinkwaste.model.assessment.Module;
import it.cnr.rethinkwaste.repository.assessment.LearningMaterialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LearningMaterialServiceImpl implements LearningMaterialService {

    @Autowired
    private LearningMaterialsRepository learningMaterialsRepository;

    @Override
    public LearningMaterial findById(Long id) {
        return learningMaterialsRepository.findById(id).get();
    }

    @Override
    public List<LearningMaterial> findAll() { return learningMaterialsRepository.findAll();    }

    @Override
    public void save(LearningMaterial learningMaterial) { learningMaterialsRepository.save(learningMaterial); }

    @Override
    public void delete(LearningMaterial learningMaterial) {
        learningMaterialsRepository.delete(learningMaterial);
    }

    @Override
    public List<LearningMaterial> findByModulesContains(Module module) { return learningMaterialsRepository.findByModulesContains(module); }

    @Override
    public List<LearningMaterial> findByVisible(boolean visible) {
        return learningMaterialsRepository.findByVisible(visible);
    }

}

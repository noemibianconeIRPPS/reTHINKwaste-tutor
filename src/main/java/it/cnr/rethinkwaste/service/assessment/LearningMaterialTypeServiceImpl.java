package it.cnr.rethinkwaste.service.assessment;

import it.cnr.rethinkwaste.model.assessment.Language;
import it.cnr.rethinkwaste.model.assessment.LearningMaterialType;
import it.cnr.rethinkwaste.repository.assessment.LearningMaterialTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LearningMaterialTypeServiceImpl implements LearningMaterialTypeService {

    @Autowired
    private LearningMaterialTypeRepository learningMaterialTypeRepository;

    @Override
    public List<LearningMaterialType> findAll() { return learningMaterialTypeRepository.findAll();    }

    @Override
    public LearningMaterialType findByType(String type) { return learningMaterialTypeRepository.findByType(type); }

    @Override
    public LearningMaterialType findById(Long id) { return learningMaterialTypeRepository.findById(id).get(); }

}

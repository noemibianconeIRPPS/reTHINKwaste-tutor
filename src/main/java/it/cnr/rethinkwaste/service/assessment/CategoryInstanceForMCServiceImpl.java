package it.cnr.rethinkwaste.service.assessment;

import it.cnr.rethinkwaste.model.assessment.CategoryInstance;
import it.cnr.rethinkwaste.model.assessment.CategoryInstanceForMC;
import it.cnr.rethinkwaste.repository.assessment.CategoryInstanceForMCRepository;
import it.cnr.rethinkwaste.repository.assessment.CategoryInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryInstanceForMCServiceImpl implements CategoryInstanceForMCService {

    @Autowired
    private CategoryInstanceForMCRepository categoryInstanceForMCRepository;

    @Override
    public Optional<CategoryInstanceForMC> findById(Long id) {
        return categoryInstanceForMCRepository.findById(id);
    }

    @Override
    public CategoryInstanceForMC save(CategoryInstanceForMC categoryInstanceForMC) {
        return categoryInstanceForMCRepository.save(categoryInstanceForMC);
    }

    @Override
    public void delete(CategoryInstanceForMC categoryInstanceForMC) {
        categoryInstanceForMCRepository.delete(categoryInstanceForMC);
    }

}

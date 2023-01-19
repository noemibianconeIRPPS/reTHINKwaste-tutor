package it.cnr.rethinkwaste.service.assessment;

import it.cnr.rethinkwaste.model.assessment.Category;
import it.cnr.rethinkwaste.model.assessment.CategoryInstance;
import it.cnr.rethinkwaste.repository.assessment.CategoryInstanceRepository;
import it.cnr.rethinkwaste.repository.assessment.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryInstanceServiceImpl implements CategoryInstanceService {

    @Autowired
    private CategoryInstanceRepository categoryInstanceRepository;

    @Override
    public Optional<CategoryInstance> findById(Long id) {
        return categoryInstanceRepository.findById(id);
    }

    @Override
    public CategoryInstance save(CategoryInstance categoryInstance) {
        return categoryInstanceRepository.save(categoryInstance);
    }

}

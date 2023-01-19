package it.cnr.rethinkwaste.service.assessment;

import it.cnr.rethinkwaste.model.assessment.CategoryInstance;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CategoryInstanceService {

    Optional<CategoryInstance> findById(Long id);

    CategoryInstance save(CategoryInstance categoryInstance);

}

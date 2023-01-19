package it.cnr.rethinkwaste.service.assessment;

import it.cnr.rethinkwaste.model.assessment.CategoryInstanceForMC;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CategoryInstanceForMCService {

    Optional<CategoryInstanceForMC> findById(Long id);

    CategoryInstanceForMC save(CategoryInstanceForMC categoryInstanceForMC);

    void delete(CategoryInstanceForMC categoryInstanceForMC);

}

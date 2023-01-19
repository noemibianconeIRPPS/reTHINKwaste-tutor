package it.cnr.rethinkwaste.repository.assessment;

import it.cnr.rethinkwaste.model.assessment.CategoryInstance;
import it.cnr.rethinkwaste.model.assessment.CategoryInstanceForMC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryInstanceForMCRepository extends JpaRepository<CategoryInstanceForMC, Long> {
}

package it.cnr.rethinkwaste.repository.assessment;

import it.cnr.rethinkwaste.model.assessment.LearningMaterialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LearningMaterialTypeRepository extends JpaRepository<LearningMaterialType, Long> {

    LearningMaterialType findByType(String type);

    Optional<LearningMaterialType> findById(Long id);

}

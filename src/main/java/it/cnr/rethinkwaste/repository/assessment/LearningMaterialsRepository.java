package it.cnr.rethinkwaste.repository.assessment;

import it.cnr.rethinkwaste.model.assessment.LearningMaterial;
import it.cnr.rethinkwaste.model.assessment.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningMaterialsRepository extends JpaRepository<LearningMaterial, Long> {

    List<LearningMaterial> findByModulesContains(Module module);

    List<LearningMaterial> findByVisible(boolean visible);

}

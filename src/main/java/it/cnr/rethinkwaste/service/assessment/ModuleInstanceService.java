package it.cnr.rethinkwaste.service.assessment;

import it.cnr.rethinkwaste.model.assessment.Answer;
import it.cnr.rethinkwaste.model.assessment.ModuleInstance;
import it.cnr.rethinkwaste.repository.assessment.ModuleInstanceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ModuleInstanceService {

    Optional<ModuleInstance> findById(Long id);

    ModuleInstance save(ModuleInstance moduleInstance);

}

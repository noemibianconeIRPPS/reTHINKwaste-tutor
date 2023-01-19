package it.cnr.rethinkwaste.service.assessment;

import it.cnr.rethinkwaste.model.assessment.Module;
import it.cnr.rethinkwaste.model.assessment.ModuleInstance;
import it.cnr.rethinkwaste.repository.assessment.ModuleInstanceRepository;
import it.cnr.rethinkwaste.repository.assessment.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ModuleInstanceServiceImpl implements ModuleInstanceService {

    @Autowired
    private ModuleInstanceRepository moduleInstanceRepository;

    @Override
    public Optional<ModuleInstance> findById(Long id) {
        return moduleInstanceRepository.findById(id);
    }

    @Override
    public ModuleInstance save(ModuleInstance moduleInstance) {
        return moduleInstanceRepository.save(moduleInstance);
    }

}

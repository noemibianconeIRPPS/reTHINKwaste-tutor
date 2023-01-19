package it.cnr.rethinkwaste.service.assessment;

import it.cnr.rethinkwaste.model.assessment.Module;
import it.cnr.rethinkwaste.repository.assessment.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

    @Override
    public Module findById(Long id) {
        return moduleRepository.findById(id).get();
    }

    @Override
    public List<Module> findAll() { return moduleRepository.findAll();    }

    @Override
    public List<String> findModulesText()  { return moduleRepository.findModulesText(); }

}

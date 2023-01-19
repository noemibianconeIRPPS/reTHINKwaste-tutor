package it.cnr.rethinkwaste.service.assessment;

import it.cnr.rethinkwaste.model.assessment.Module;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ModuleService {

    Module findById(Long id);

    List<Module> findAll();

    List<String> findModulesText();

}

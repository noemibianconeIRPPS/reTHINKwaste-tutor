package it.cnr.rethinkwaste.service;

import it.cnr.rethinkwaste.model.OrganizationType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrganizationTypeService {

    List<OrganizationType> findAll();

    OrganizationType findById(Long id);

}

package it.cnr.rethinkwaste.service;

import it.cnr.rethinkwaste.model.OrganizationType;
import it.cnr.rethinkwaste.repository.OrganizationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationTypeServiceImpl implements OrganizationTypeService{

    @Autowired
    private OrganizationTypeRepository organizationTypeRepository;


    @Override
    public List<OrganizationType> findAll() {
        return organizationTypeRepository.findAll();
    }

    @Override
    public OrganizationType findById(Long id) {
        Optional<OrganizationType> organizationType = organizationTypeRepository.findById(id);
        return organizationType.isPresent() ? organizationType.get() : new OrganizationType();
    }

}

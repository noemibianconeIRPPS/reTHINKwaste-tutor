package it.cnr.rethinkwaste.service.assessment;

import it.cnr.rethinkwaste.model.assessment.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProfileService {

    Profile getProfileById(Long id);

    void save(Profile profile);

    List<Profile> findAll();

}

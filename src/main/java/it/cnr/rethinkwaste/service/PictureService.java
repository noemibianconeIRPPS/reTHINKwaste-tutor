package it.cnr.rethinkwaste.service;

import it.cnr.rethinkwaste.model.Picture;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PictureService {

    Picture addPicture(Picture picture);

    Optional<Picture> getPictureById(Long id);

    void deletePicture(Picture picture);
}

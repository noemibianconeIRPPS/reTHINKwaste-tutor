package it.cnr.rethinkwaste.service;

import it.cnr.rethinkwaste.model.Picture;
import it.cnr.rethinkwaste.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureRepository pictureRepository;

    @Override
    public Picture addPicture(Picture picture) {
        return pictureRepository.save(picture);
    }

    @Override
    public Optional<Picture> getPictureById(Long id) {
        return pictureRepository.findById(id);
    }

    @Override
    public void deletePicture(Picture picture) {
        pictureRepository.delete(picture);
    }
}

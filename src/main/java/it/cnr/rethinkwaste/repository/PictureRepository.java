package it.cnr.rethinkwaste.repository;

import it.cnr.rethinkwaste.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Long> {
}

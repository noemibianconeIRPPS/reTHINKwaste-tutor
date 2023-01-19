package it.cnr.rethinkwaste.repository;

import it.cnr.rethinkwaste.model.Place;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends CrudRepository<Place, Long> {

    Optional<Place> findById(Long id);

    Place findByLatitudeAndLongitude(String latitude, String longitude);

    @Query(value = "SELECT p FROM Place p WHERE insertedByUser = :insertedByUser ORDER by country")
    List<Place> findAllPlacesByInsertedByUserOrderByCountry(@Param("insertedByUser") Boolean insertedByUser);

    @Query(value = "SELECT p FROM Place p WHERE insertedByUser = :insertedByUser and country like %:keyword% ORDER by country")
    List<Place> findAllPlacesByInsertedByUserAndCountryContainingOrderByCountry(@Param("insertedByUser") Boolean insertedByUser, @Param("keyword") String keyword);

    Place findByIdAndInsertedByUser(Long id, Boolean insertedByUser);

    @Query(value = "SELECT p FROM Place p WHERE country like %:country% and insertedByUser = :insertedByUser")
    Place findByCountryAndInsertedByUser(String country, Boolean insertedByUser);

    @Query(value = "SELECT p FROM Place p WHERE country like :country and insertedByUser = :insertedByUser")
    Place findByCountryIgnoreCaseAndInsertedByUser(String country, Boolean insertedByUser);

}

package it.cnr.rethinkwaste.service;

import it.cnr.rethinkwaste.model.Place;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlaceService {

    List<Place> findAllPlacesByInsertedByUserOrderByCountry(Boolean insertedByUser);

    Place savePlace(String latitude, String longitude, String administrativeAreaLevel3, String administrativeAreaLevel2, String administrativeAreaLevel1, String country, String locality, String postalCode, String route, String streetNumber);

    Place checkPlaceForOrganizationUsers(String country);

    Place checkPlaceForPrivateUsers(String countrySelect, String placeLatitude, String placeLongitude, String streetNumber, String route, String locality, String administrative_area_level_3, String administrative_area_level_2, String administrative_area_level_1, String country, String postal_code);

    List<Place> findAllPlacesByInsertedByUserAndCountryContainingOrderByCountry(Boolean insertedByUser, String keyword);

    Place findByCountryIgnoreCaseAndInsertedByUser(String country, Boolean insertedByUser);
}

package it.cnr.rethinkwaste.service;

import it.cnr.rethinkwaste.model.Place;
import it.cnr.rethinkwaste.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    public List<Place> findAllPlacesByInsertedByUserOrderByCountry(Boolean insertedByUser) {
        return placeRepository.findAllPlacesByInsertedByUserOrderByCountry(insertedByUser);
    }

    public Place savePlace(String latitude, String longitude, String administrativeAreaLevel3, String administrativeAreaLevel2, String administrativeAreaLevel1, String country, String locality, String postalCode, String route, String streetNumber) {
        Place place = new Place();
        place.setLatitude(latitude);
        place.setLongitude(longitude);
        place.setAdministrativeAreaLevel1(administrativeAreaLevel1);
        place.setAdministrativeAreaLevel2(administrativeAreaLevel2);
        place.setAdministrativeAreaLevel3(administrativeAreaLevel3);
        place.setCountry(country);
        place.setLocality(locality);
        place.setPostalCode(postalCode);
        place.setRoute(route);
        place.setStreetNumber(streetNumber);
        place.setInsertedByUser(true);
        placeRepository.save(place);
        return place;
    }

    public Place checkPlaceForOrganizationUsers(String country) {
        return placeRepository.findByCountryAndInsertedByUser(country, false);
    }

    public Place checkPlaceForPrivateUsers(String countrySelect, String placeLatitude, String placeLongitude, String streetNumber, String route, String locality, String administrative_area_level_3, String administrative_area_level_2, String administrative_area_level_1, String country, String postal_code) {
        Place place = null;
        if(placeLatitude.equals("") && placeLatitude.equals("")) {
            place = placeRepository.findByIdAndInsertedByUser(Long.parseLong(countrySelect), false);
        }
        else {
            place = placeRepository.findByLatitudeAndLongitude(placeLatitude, placeLongitude);
            if(place == null) {
                place = savePlace(placeLatitude, placeLongitude, administrative_area_level_3, administrative_area_level_2, administrative_area_level_1, country, locality, postal_code, route, streetNumber);
            }
        }
        return place;
    }

    public List<Place> findAllPlacesByInsertedByUserAndCountryContainingOrderByCountry(Boolean insertedByUser, String keyword) {
        return placeRepository.findAllPlacesByInsertedByUserAndCountryContainingOrderByCountry(insertedByUser, keyword);
    }

    public Place findByCountryIgnoreCaseAndInsertedByUser(String country, Boolean insertedByUser) {
        return placeRepository.findByCountryIgnoreCaseAndInsertedByUser(country, insertedByUser);
    }

}

package it.cnr.rethinkwaste.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Place {

    @Id
    @SequenceGenerator(name = "place_sequence_generator", sequenceName = "place_sequence", initialValue = 246, allocationSize = 1)
    @GeneratedValue(generator = "place_sequence_generator")
    private Long id;

    private String latitude, longitude, streetNumber, route, locality, administrativeAreaLevel3;
    private String administrativeAreaLevel2, administrativeAreaLevel1, country, postalCode;
    private Boolean insertedByUser;

    public Place() {
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", route='" + route + '\'' +
                ", locality='" + locality + '\'' +
                ", administrativeAreaLevel3='" + administrativeAreaLevel3 + '\'' +
                ", administrativeAreaLevel2='" + administrativeAreaLevel2 + '\'' +
                ", administrativeAreaLevel1='" + administrativeAreaLevel1 + '\'' +
                ", country='" + country + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", insertedByUser=" + insertedByUser +
                '}';
    }

    public Boolean getInsertedByUser() {
        return insertedByUser;
    }

    public void setInsertedByUser(Boolean insertedByUser) {
        this.insertedByUser = insertedByUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getAdministrativeAreaLevel3() {
        return administrativeAreaLevel3;
    }

    public void setAdministrativeAreaLevel3(String administrativeAreaLevel3) {
        this.administrativeAreaLevel3 = administrativeAreaLevel3;
    }

    public String getAdministrativeAreaLevel2() {
        return administrativeAreaLevel2;
    }

    public void setAdministrativeAreaLevel2(String administrativeAreaLevel2) {
        this.administrativeAreaLevel2 = administrativeAreaLevel2;
    }

    public String getAdministrativeAreaLevel1() {
        return administrativeAreaLevel1;
    }

    public void setAdministrativeAreaLevel1(String administrativeAreaLevel1) {
        this.administrativeAreaLevel1 = administrativeAreaLevel1;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}

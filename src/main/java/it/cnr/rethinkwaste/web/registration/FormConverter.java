package it.cnr.rethinkwaste.web.registration;

import it.cnr.rethinkwaste.web.registration.dto.*;
import it.cnr.rethinkwaste.web.registration.form.UserRegistrationForm;

public class FormConverter {

    public UserDto convertFormToUser(UserRegistrationForm userForm) {
        UserDto userDto = new UserDto();
        userDto.setConfirmEmail(userForm.getConfirmEmail());
        userDto.setConfirmPassword(userForm.getConfirmPassword());
        userDto.setEmail(userForm.getEmail());
        userDto.setPassword(userForm.getPassword());
        userDto.setTerms(userForm.getTerms());
        userDto.setFirstname(userForm.getFirstname());
        userDto.setLastname(userForm.getLastname());
        userDto.setOrganizationName(userForm.getOrganizationName());
        userDto.setWebsite(userForm.getWebsite());
        userDto.setOrganizationType(userForm.getOrganizationType());
        userDto.setOtherOrganizationType(userForm.getOtherOrganizationType());

        userDto.setAdministrative_area_level_1(userForm.getAdministrative_area_level_1());
        userDto.setAdministrative_area_level_2(userForm.getAdministrative_area_level_2());
        userDto.setAdministrative_area_level_3(userForm.getAdministrative_area_level_3());
        userDto.setCountry(userForm.getCountryFromGoogle());
        userDto.setLocality(userForm.getLocality());
        userDto.setPlaceLatitude(userForm.getPlaceLatitude());
        userDto.setPlaceLongitude(userForm.getPlaceLongitude());
        userDto.setPostal_code(userForm.getPostal_code());
        userDto.setRoute(userForm.getRoute());
        userDto.setStreetNumber(userForm.getStreetNumber());

        return userDto;

    }

}

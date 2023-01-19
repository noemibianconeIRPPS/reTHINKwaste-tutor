package it.cnr.rethinkwaste.validation.registration;

import it.cnr.rethinkwaste.model.CustomUserDetails;
import it.cnr.rethinkwaste.model.User_;
import it.cnr.rethinkwaste.service.PlaceService;
import it.cnr.rethinkwaste.service.UserService;
import it.cnr.rethinkwaste.web.registration.form.UserRegistrationForm;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserFormValidator implements Validator {

    @Autowired
    private UserService userService;

    @Autowired
    private PlaceService placeService;

    @Override
    public boolean supports(Class clazz) {
        return UserRegistrationForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegistrationForm userRegistrationForm = (UserRegistrationForm) target;
        UserDetails existingUserDetails = userService.findByEmail(userRegistrationForm.getEmail());
        CustomUserDetails existingCustomUserDetails = (CustomUserDetails) existingUserDetails;
        User_ existing = existingCustomUserDetails.getUser();
        if(existing != null) {
            errors.rejectValue("email", "register.error.emailAlreadyRegistered");
        }
        else {
            if (!userRegistrationForm.getEmail().equals(userRegistrationForm.getConfirmEmail())) {
                errors.rejectValue("email","register.error.differentEmail");
            }

            if (!userRegistrationForm.getPassword().equals(userRegistrationForm.getConfirmPassword())) {
                errors.rejectValue ("password", "register.error.differentPassword" );
            }

            if (userRegistrationForm.getEmail().isEmpty()) {
                errors.rejectValue("email","register.error.emptyField");
            }
            if (userRegistrationForm.getConfirmEmail().isEmpty()) {
                errors.rejectValue("confirmEmail","register.error.emptyField");
            }
            if (userRegistrationForm.getPassword().isEmpty()) {
                errors.rejectValue("password","register.error.emptyField");
            }
            if (userRegistrationForm.getConfirmPassword().isEmpty()) {
                errors.rejectValue("confirmPassword","register.error.emptyField");
            }
            if(!userRegistrationForm.getTerms()) {
                errors.rejectValue("terms","register.error.emptyTerms");
            }
            if (userRegistrationForm.getFirstname().isEmpty()) {
                errors.rejectValue("firstname", "register.error.emptyField");
            }
            if (userRegistrationForm.getLastname().isEmpty()) {
                errors.rejectValue("lastname", "register.error.emptyField");
            }
            if (userRegistrationForm.getOrganizationName().isEmpty()) {
                errors.rejectValue("organizationName", "register.error.emptyField");
            }
            if (userRegistrationForm.getOrganizationType() == null) {
                errors.rejectValue("organizationType", "register.error.emptyField");
            }
            if (userRegistrationForm.getCountryFromGoogle().isEmpty()) {
                errors.rejectValue("countryFromGoogle", "register.error.emptyField");
            }

            Pattern p = Pattern.compile("^(https?|http|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
            Matcher m = p.matcher(userRegistrationForm.getWebsite());
            if(!m.matches()) {
                errors.rejectValue("website", "register.error.invalidURL");
            }

            if(placeService.findByCountryIgnoreCaseAndInsertedByUser(userRegistrationForm.getCountryFromGoogle(), false) == null) {
                errors.rejectValue("countryFromGoogle", "register.error.countryNotExists");
            }
        }
    }


}

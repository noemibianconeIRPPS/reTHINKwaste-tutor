package it.cnr.rethinkwaste.web;

import it.cnr.rethinkwaste.model.CustomUserDetails;
import it.cnr.rethinkwaste.model.Place;
import it.cnr.rethinkwaste.model.User_;
import it.cnr.rethinkwaste.service.*;
import it.cnr.rethinkwaste.validation.registration.UserFormValidator;
import it.cnr.rethinkwaste.web.registration.FormConverter;
import it.cnr.rethinkwaste.web.registration.dto.UserDto;
import it.cnr.rethinkwaste.web.registration.form.UserRegistrationForm;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.Collection;

@Controller
@RequestMapping("/register")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationTypeService organizationTypeService;

    @Autowired
    private PlaceService placeService;

    @Autowired
    UserFormValidator validator;

    private FormConverter converter;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @ModelAttribute("userRegistrationForm")
    public UserRegistrationForm userRegistrationForm() {
        return new UserRegistrationForm();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("organizationTypes", organizationTypeService.findAll());
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("userRegistrationForm") @Valid UserRegistrationForm userForm,
                                      BindingResult result, Model model) throws ParseException {

        UserDetails existingUserDetails = userService.findByEmail(userForm.getEmail());
        CustomUserDetails existingCustomUserDetails = (CustomUserDetails) existingUserDetails;
        User_ existing = existingCustomUserDetails.getUser();
        converter = new FormConverter();
        if (existing != null){
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()){
            model.addAttribute("organizationTypes", organizationTypeService.findAll());
            return "registration";
        }

        UserDto userDto = converter.convertFormToUser(userForm);
        User_ registeredUser = userService.saveUser(userDto);

        return "redirect:/login?registrationSuccess";
    }

    @GetMapping(value = "/getSuggestionsForPlaces")
    public @ResponseBody
    String getSuggestionsForPlaces(@RequestParam("keyword") String keyword) {
        Collection<Place> places = placeService.findAllPlacesByInsertedByUserAndCountryContainingOrderByCountry(false, keyword);
        JSONArray jsonArray = new JSONArray();

        for(Place p : places) {
            jsonArray.put(new JSONObject()
                    .put("label", p.getCountry())
                    .put("value", p.getId()));
        }

        return jsonArray.toString();
    }

}

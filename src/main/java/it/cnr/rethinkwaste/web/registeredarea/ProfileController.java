package it.cnr.rethinkwaste.web.registeredarea;

import it.cnr.rethinkwaste.model.CustomUserDetails;
import it.cnr.rethinkwaste.model.Place;
import it.cnr.rethinkwaste.model.User_;
import it.cnr.rethinkwaste.model.assessment.LearningMaterial;
import it.cnr.rethinkwaste.model.assessment.ModuleInstance;
import it.cnr.rethinkwaste.model.assessment.Profile;
import it.cnr.rethinkwaste.model.assessment.Report;
import it.cnr.rethinkwaste.service.OrganizationTypeService;
import it.cnr.rethinkwaste.service.PlaceService;
import it.cnr.rethinkwaste.service.UserService;
import it.cnr.rethinkwaste.service.assessment.LearningMaterialService;
import it.cnr.rethinkwaste.web.registration.FormConverter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/registeredarea/profile")
public class ProfileController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private LearningMaterialService learningMaterialService;

    @Autowired
    private OrganizationTypeService organizationTypeService;

    @Autowired
    private PlaceService placeService;


    private FormConverter converter = new FormConverter();

    @GetMapping
    public String profile(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetails user = userService.loadUserByUsername(email);
        CustomUserDetails customUser = (CustomUserDetails) user;
        model.addAttribute("loggedUser", customUser.getUser());

        List<LearningMaterial> suggestedLearningMaterials = new ArrayList<>();
        double[] orderedScoresArray = orderedScoreScale(customUser);

        for(ModuleInstance moduleInstance : customUser.getUser().getModuleInstanceList()) {
            model.addAttribute("moduleInstance"+moduleInstance.getModule().getId(), moduleInstance);
             if(String.valueOf(moduleInstance.getPercentage()).equals("100.0")) {
                if(orderedScoresArray[(int) (moduleInstance.getModule().getId()-1)] <= 2.0) {
                    suggestedLearningMaterials.addAll(learningMaterialService.findByModulesContains(moduleInstance.getModule()));
                }
             }
        }
        model.addAttribute("suggestedLearningMaterials", suggestedLearningMaterials);

        double[] scoresArray = orderedScoreScale(customUser);
        model.addAttribute("scoresArray", scoresArray);
        return "/registeredarea/profile/profile";
    }

    @GetMapping(value = "/radarFillIn")
    public @ResponseBody double[] radarFillIn() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetails user = userService.loadUserByUsername(email);
        CustomUserDetails customUser = (CustomUserDetails) user;
        double[] scoresArray = orderedScoreScale(customUser);

        return scoresArray;

    }

    public double[] orderedScoreScale(CustomUserDetails customUser) {
        double[] scoresArray = new double[6];
        Optional<ModuleInstance> ttrModule = customUser.getUser().getModuleInstanceList().stream().
                filter(p -> p.getModule().getId().equals(1L)).
                findFirst();
        if(ttrModule.isPresent()) {
            scoresArray[0] = new BigDecimal((ttrModule.get().getScore() * 3.0) / 72.0).setScale(1, RoundingMode.HALF_UP).doubleValue();
        }
        else {
            scoresArray[0] = 0;
        }
        Optional<ModuleInstance> armModule = customUser.getUser().getModuleInstanceList().stream().
                filter(p -> p.getModule().getId().equals(2L)).
                findFirst();
        if(armModule.isPresent()) {
            scoresArray[1] = new BigDecimal((armModule.get().getScore() * 3.0) / 36.0).setScale(1, RoundingMode.HALF_UP).doubleValue();
        }
        else {
            scoresArray[1] = 0;
        }
        Optional<ModuleInstance> pmModule = customUser.getUser().getModuleInstanceList().stream().
                filter(p -> p.getModule().getId().equals(3L)).
                findFirst();
        if(pmModule.isPresent()) {
            scoresArray[2] = new BigDecimal((pmModule.get().getScore() * 3.0) / 3.0).setScale(1, RoundingMode.HALF_UP).doubleValue();
        }
        else {
            scoresArray[2] = 0;
        }
        Optional<ModuleInstance> igppModule = customUser.getUser().getModuleInstanceList().stream().
                filter(p -> p.getModule().getId().equals(4L)).
                findFirst();
        if(igppModule.isPresent()) {
            scoresArray[3] = Math.round(((double) igppModule.get().getScore() * 3.0) / 20.0);
        }
        else {
            scoresArray[3] = 0;
        }

        Optional<ModuleInstance> mncModule = customUser.getUser().getModuleInstanceList().stream().
                filter(p -> p.getModule().getId().equals(5L)).
                findFirst();
        if(mncModule.isPresent()) {
            scoresArray[4] = new BigDecimal((mncModule.get().getScore() * 3.0) / 30.0).setScale(1, RoundingMode.HALF_UP).doubleValue();
        }
        else {
            scoresArray[4] = 0;
        }
        Optional<ModuleInstance> obpModule = customUser.getUser().getModuleInstanceList().stream().
                filter(p -> p.getModule().getId().equals(6L)).
                findFirst();
        if(obpModule.isPresent()) {
            scoresArray[5] = new BigDecimal((obpModule.get().getScore() * 3.0) / 40.0).setScale(1, RoundingMode.HALF_UP).doubleValue();
        }
        else {
            scoresArray[5] = 0;
        }
        return scoresArray;
    }

    public double[] scoreScale(CustomUserDetails customUser) {
        double[] scoresArray = new double[6];
        Optional<ModuleInstance> ttrModule = customUser.getUser().getModuleInstanceList().stream().
                filter(p -> p.getModule().getId().equals(1L)).
                findFirst();
        if(ttrModule.isPresent()) {
            scoresArray[0] = new BigDecimal((ttrModule.get().getScore() * 3.0) / 72.0).setScale(1, RoundingMode.HALF_UP).doubleValue();
        }
        else {
            scoresArray[0] = 0;
        }
        Optional<ModuleInstance> mncModule = customUser.getUser().getModuleInstanceList().stream().
                filter(p -> p.getModule().getId().equals(5L)).
                findFirst();
        if(mncModule.isPresent()) {
            scoresArray[1] = new BigDecimal((mncModule.get().getScore() * 3.0) / 30.0).setScale(1, RoundingMode.HALF_UP).doubleValue();
        }
        else {
            scoresArray[1] = 0;
        }
        Optional<ModuleInstance> armModule = customUser.getUser().getModuleInstanceList().stream().
                filter(p -> p.getModule().getId().equals(2L)).
                findFirst();
        if(armModule.isPresent()) {
            scoresArray[2] = new BigDecimal((armModule.get().getScore() * 3.0) / 36.0).setScale(1, RoundingMode.HALF_UP).doubleValue();
        }
        else {
            scoresArray[2] = 0;
        }
        Optional<ModuleInstance> igppModule = customUser.getUser().getModuleInstanceList().stream().
                filter(p -> p.getModule().getId().equals(4L)).
                findFirst();
        if(igppModule.isPresent()) {
            scoresArray[3] = Math.round(((double) igppModule.get().getScore() * 3.0) / 20.0);
        }
        else {
            scoresArray[3] = 0;
        }
        Optional<ModuleInstance> pmModule = customUser.getUser().getModuleInstanceList().stream().
                filter(p -> p.getModule().getId().equals(3L)).
                findFirst();
        if(pmModule.isPresent()) {
            scoresArray[4] = new BigDecimal((pmModule.get().getScore() * 3.0) / 3.0).setScale(1, RoundingMode.HALF_UP).doubleValue();
        }
        else {
            scoresArray[4] = 0;
        }
        Optional<ModuleInstance> obpModule = customUser.getUser().getModuleInstanceList().stream().
                filter(p -> p.getModule().getId().equals(6L)).
                findFirst();
        if(obpModule.isPresent()) {
            scoresArray[5] = new BigDecimal((obpModule.get().getScore() * 3.0) / 40.0).setScale(1, RoundingMode.HALF_UP).doubleValue();
        }
        else {
            scoresArray[5] = 0;
        }
        return scoresArray;
    }

    @GetMapping(value = "/edit")
    public String edit(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetails user = userService.loadUserByUsername(email);
        CustomUserDetails customUser = (CustomUserDetails) user;
        model.addAttribute("loggedUser", customUser.getUser());
        model.addAttribute("organizationTypes", organizationTypeService.findAll());

        return "/registeredarea/profile/change-profile";
    }

    @GetMapping(value = "/edit/getSuggestionsForPlaces")
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

    @GetMapping(value = "/edit/saveChanges")
    public String saveChanges(Model model, @RequestParam("firstname") String firstname,
                              @RequestParam("lastname") String lastname,
                              @RequestParam("organizationName") String organizationName,
                              @RequestParam("organizationType") String organizationType,
                              @RequestParam("otherOrganizationType") String otherOrganizationType,
                              @RequestParam("website") String website,
                              @RequestParam("country") String country,
                              @RequestParam("newPassword") String newPassword) throws IOException {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetails user = userService.loadUserByUsername(email);
        CustomUserDetails customUser = (CustomUserDetails) user;
        User_ loggedUser = customUser.getUser();
        loggedUser.setFirstname(firstname);
        loggedUser.setLastname(lastname);
        loggedUser.setOrganizationName(organizationName);
        loggedUser.setOrganizationType(organizationTypeService.findById(Long.parseLong(organizationType)));
        if(!otherOrganizationType.isEmpty()) {
            loggedUser.setOtherOrganizationType(otherOrganizationType);
        }
        else {
            loggedUser.setOtherOrganizationType("");
        }
        loggedUser.setWebsite(website);
        loggedUser.setPlace(placeService.checkPlaceForOrganizationUsers(country));
        if(!newPassword.isEmpty()) {
            loggedUser.setPassword(passwordEncoder.encode(newPassword));
        }

        userService.saveUser(loggedUser);

        return "redirect:/registeredarea/profile";
    }

}

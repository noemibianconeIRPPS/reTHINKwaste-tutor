package it.cnr.rethinkwaste.web.registeredarea.arm;

import it.cnr.rethinkwaste.model.CustomUserDetails;
import it.cnr.rethinkwaste.model.assessment.Module;
import it.cnr.rethinkwaste.model.assessment.ModuleInstance;
import it.cnr.rethinkwaste.service.UserService;
import it.cnr.rethinkwaste.service.assessment.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registeredarea/arm")
public class ARMController {

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String arm(Model model) {
        String language = LocaleContextHolder.getLocale().getLanguage();
        model.addAttribute("language", language.toUpperCase());
        Module armModule = moduleService.findById(2L);
        model.addAttribute("armModule", armModule);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetails user = userService.loadUserByUsername(email);
        CustomUserDetails customUser = (CustomUserDetails) user;
        model.addAttribute("loggedUser", customUser.getUser());
        if(customUser.getUser().getModuleInstanceList().stream().filter(m -> m.getModule().getId() == 2).findFirst().isPresent()) {
            ModuleInstance moduleInstance = customUser.getUser().getModuleInstanceList().stream().filter(m -> m.getModule().getId() == 2).findFirst().get();
            model.addAttribute("moduleInstance", moduleInstance);
        }
        return "/registeredarea/arm/arm";
    }
}

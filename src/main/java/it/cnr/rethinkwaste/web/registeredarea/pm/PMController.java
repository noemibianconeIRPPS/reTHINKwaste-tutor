package it.cnr.rethinkwaste.web.registeredarea.pm;

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
@RequestMapping("/registeredarea/pm")
public class PMController {

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String pm(Model model) {
        String language = LocaleContextHolder.getLocale().getLanguage();
        model.addAttribute("language", language.toUpperCase());
        Module pmModule = moduleService.findById(3L);
        model.addAttribute("pmModule", pmModule);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetails user = userService.loadUserByUsername(email);
        CustomUserDetails customUser = (CustomUserDetails) user;
        model.addAttribute("loggedUser", customUser.getUser());
        if(customUser.getUser().getModuleInstanceList().stream().filter(m -> m.getModule().getId() == 3).findFirst().isPresent()) {
            ModuleInstance moduleInstance = customUser.getUser().getModuleInstanceList().stream().filter(m -> m.getModule().getId() == 3).findFirst().get();
            model.addAttribute("moduleInstance", moduleInstance);
        }
        return "/registeredarea/pm/pm";
    }
}

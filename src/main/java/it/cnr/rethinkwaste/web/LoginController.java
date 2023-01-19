package it.cnr.rethinkwaste.web;

import it.cnr.rethinkwaste.model.CustomUserDetails;
import it.cnr.rethinkwaste.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/login"})
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        String returnString = "";
        if(error != null) {
            model.addAttribute("errorMsg", error);
            returnString = "login";
        }
        if(!email.equals("anonymousUser")) {
            UserDetails user = userService.loadUserByUsername(email);
            CustomUserDetails customUser = (CustomUserDetails) user;
            model.addAttribute("loggedUser", customUser.getUser());
            returnString = "redirect:/registeredarea/profile";
        }
        else {
            returnString = "login";
        }

        return returnString;
    }

}

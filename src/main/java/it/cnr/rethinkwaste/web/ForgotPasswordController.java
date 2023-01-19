package it.cnr.rethinkwaste.web;

import it.cnr.rethinkwaste.model.CustomUserDetails;
import it.cnr.rethinkwaste.model.User_;
import it.cnr.rethinkwaste.service.EmailService;
import it.cnr.rethinkwaste.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping
    public String forgotPassword(Model model) {
        return "forgotPassword";
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public String displayResetPasswordPage(Model model, @RequestParam("token") String token) {

        UserDetails loggedUser = userService.findByResetToken(token);
        CustomUserDetails customLoggedUser = (CustomUserDetails) loggedUser;
        User_ user = customLoggedUser.getUser();

        if (user != null) {
            model.addAttribute("resetToken", token);
        } else {
            model.addAttribute("errorMessage", "Oops!  This is an invalid password reset link.");
        }

        return "resetPassword";
    }

}

package it.cnr.rethinkwaste.web;

import it.cnr.rethinkwaste.model.CustomUserDetails;
import it.cnr.rethinkwaste.model.Response;
import it.cnr.rethinkwaste.model.User_;
import it.cnr.rethinkwaste.service.EmailService;
import it.cnr.rethinkwaste.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/forgotPassword")
public class ForgotPasswordRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/recoverAccount")
    public ResponseEntity<?> recoverAccount(@RequestParam("accountEmail") String accountEmail) throws Exception {
        UserDetails loggedUser = userService.findByEmail(accountEmail);
        CustomUserDetails customLoggedUser = (CustomUserDetails) loggedUser;
        Response response = new Response();
        if(customLoggedUser.getUser() == null) {
            response.setStatus("0");
        }
        else {
            User_ user = customLoggedUser.getUser();
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            userService.saveUser(user);
            emailService.recoverPasswordEmail(accountEmail, token);
            response.setStatus("1");
        }

        return ResponseEntity.ok(response);

    }

    @GetMapping(value = "/confirmReset")
    public ResponseEntity<?> confirmReset(@RequestParam("token") String token, @RequestParam("passwordReset") String passwordReset) {

        UserDetails loggedUser = userService.findByResetToken(token);
        CustomUserDetails customUserDetails = (CustomUserDetails) loggedUser;
        User_ user = customUserDetails.getUser();
        Response response = new Response();
        if (!user.equals(null)) {

            userService.changeUserPassword(user, passwordReset);
            user.setResetToken(null);
            userService.saveUser(user);

        }

        return ResponseEntity.ok(response);
    }
}

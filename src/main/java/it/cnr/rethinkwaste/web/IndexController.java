package it.cnr.rethinkwaste.web;

import it.cnr.rethinkwaste.model.CustomUserDetails;
import it.cnr.rethinkwaste.model.MessageDetail;
import it.cnr.rethinkwaste.model.Notification;
import it.cnr.rethinkwaste.service.*;
import it.cnr.rethinkwaste.web.registration.FormConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.TreeMap;

@Controller
@RequestMapping({"/"})
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private PlaceService placeService;

    private FormConverter converter = new FormConverter();

    @Autowired
    private EmailService emailService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        return "index";
    }

}

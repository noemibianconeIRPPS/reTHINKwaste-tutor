package it.cnr.rethinkwaste.service;

import it.cnr.rethinkwaste.model.Role;
import it.cnr.rethinkwaste.model.User_;
import it.cnr.rethinkwaste.web.registration.dto.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.text.ParseException;
import java.util.List;

public interface UserService extends UserDetailsService {

    UserDetails findByEmail(String email);

    UserDetails findByResetToken(String resetToken);

    User_ saveUser(UserDto registration) throws ParseException;

    void saveUser(User_ user);

    List<User_> findByDataUserOrganization_CompanyContainingOrEmailContainingByRole(String search, Role role);

    List<User_> findByDataUserOrganization_CompanyContainingOrEmailContaining(String search);

    boolean checkPasswordMatch(String password, User_ user);

    void changeUserPassword(User_ user, String newPassword);

    List<User_> findAll();

    void deleteUser(User_ user);

}

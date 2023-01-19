package it.cnr.rethinkwaste.service;

import it.cnr.rethinkwaste.model.*;
import it.cnr.rethinkwaste.repository.*;
import it.cnr.rethinkwaste.web.registration.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationTypeRepository organizationTypeRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PlaceService placeService;

    @Override
    public boolean checkPasswordMatch(String password, User_ user) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    public User_ saveUser(UserDto registration) {
        User_ user = new User_(registration.getEmail(), passwordEncoder.encode(registration.getPassword()));
        user.setOrganizationName(registration.getOrganizationName());
        user.setOrganizationType(registration.getOrganizationType());
        user.setOtherOrganizationType(registration.getOtherOrganizationType());
        user.setFirstname(registration.getFirstname());
        user.setLastname(registration.getLastname());
        user.setRoles(Arrays.asList(roleRepository.findByName("USER")));
        user.setWebsite(registration.getWebsite());
        user.setProgressPercentage(0.0);
        Place place = placeService.checkPlaceForOrganizationUsers(registration.getCountry());
        user.setPlace(place);
        return userRepository.save(user);
    }

    public UserDetails findByEmail(String email){
        User_ user = userRepository.findByEmail(email);
        return new CustomUserDetails(user);
    }

    @Override
    public void changeUserPassword(User_ user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public List<User_> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User_ user = userRepository.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new CustomUserDetails(user);
    }

    public UserDetails findUserById(Long id) throws UsernameNotFoundException {
        Optional<User_> user = userRepository.findById(id);
        if (user.isEmpty()){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new CustomUserDetails(user.get());
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails findByResetToken(String resetToken){
        User_ user = userRepository.findByResetToken(resetToken);
        return new CustomUserDetails(user);
    }

    public void saveUser(User_ user) {
        userRepository.save(user);
    }

    public List<User_> findByDataUserOrganization_CompanyContainingOrEmailContaining(String search) {
        String[] searchSplit = search.split(" ");
        Set<User_> usersSet = new HashSet<User_>();
        for(String s : searchSplit) {
            usersSet.addAll(userRepository.findByOrganizationNameContainingIgnoreCaseOrEmailContainingIgnoreCase(s, s));
        }
        List<User_> users = new ArrayList<User_>();
        users.addAll(usersSet);
        return users;
    }

    public List<User_> findByDataUserOrganization_CompanyContainingOrEmailContainingByRole(String search, Role role) {
        String[] searchSplit = search.split(" ");
        Set<User_> usersSet = new HashSet<User_>();
        for(String s : searchSplit) {
            usersSet.addAll(userRepository.findByOrganizationNameContainingIgnoreCaseOrEmailContainingIgnoreCase(s, s));
        }
        List<User_> users = new ArrayList<User_>();
        users.addAll(usersSet);
        List<User_> usersResults = new ArrayList<>();
        for(User_ u : users) {
            if(u.getRoles().contains(role)) {
                usersResults.add(u);
            }
        }
        return usersResults;
    }

    @Transactional
    public void deleteUser(User_ user) {
        notificationRepository.deleteByReceiverOrSender(user, user);
        conversationRepository.deleteByUsersContainingOrParticipantsContaining(user, user);
        userRepository.delete(user);
    }


}

package it.cnr.rethinkwaste.model;

import it.cnr.rethinkwaste.model.assessment.ModuleInstance;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User_ {

    @Id
    @SequenceGenerator(name = "user_sequence_generator", sequenceName = "user_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "user_sequence_generator")
    private Long id;
    private String email;
    private String password, resetToken;
    private String firstname, lastname, website, organizationName, otherOrganizationType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_join_organization_type",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "organization_type_id", referencedColumnName = "id"))
    private OrganizationType organizationType;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private UserProfilePicture userProfilePicture;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_join_module_instance",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "module_instance_id", referencedColumnName = "id"))
    private List<ModuleInstance> moduleInstanceList;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_join_place",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "place_id", referencedColumnName = "id"))
    private Place place;

    private double progressPercentage;

    @ManyToMany
    @JoinTable(
            name = "user_join_role",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    public List<ModuleInstance> getModuleInstanceList() {
        return moduleInstanceList;
    }

    public double getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(double progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public void setModuleInstanceList(List<ModuleInstance> moduleInstanceList) {
        this.moduleInstanceList = moduleInstanceList;
    }

    public String getOtherOrganizationType() {
        return otherOrganizationType;
    }

    public void setOtherOrganizationType(String otherOrganizationType) {
        this.otherOrganizationType = otherOrganizationType;
    }

    public UserProfilePicture getUserProfilePicture() {
        return userProfilePicture;
    }

    public void setUserProfilePicture(UserProfilePicture userProfilePicture) {
        this.userProfilePicture = userProfilePicture;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public OrganizationType getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(OrganizationType organizationType) {
        this.organizationType = organizationType;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public User_() {
    }

    public User_(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User_(String email, String password, Collection<Role> roles) {
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + "*********" + '\'' +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object other){
        if (!(other instanceof User_))
            return false;
        User_ user = (User_) other;
        return (this.getEmail().equals(user.getEmail()));
    }
}

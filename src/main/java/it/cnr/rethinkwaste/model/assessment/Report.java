package it.cnr.rethinkwaste.model.assessment;


import javax.persistence.*;
import java.util.List;

@Entity
public class Report {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "report_join_profile",
            joinColumns = @JoinColumn(
                    name = "report_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "profile_id", referencedColumnName = "id"))
    private List<Profile> profileList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Profile> getProfileList() {
        return profileList;
    }

    public void setProfileList(List<Profile> profileList) {
        this.profileList = profileList;
    }
}

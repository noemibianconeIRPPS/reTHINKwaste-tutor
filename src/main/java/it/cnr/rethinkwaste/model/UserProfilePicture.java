package it.cnr.rethinkwaste.model;

import javax.persistence.*;

@Entity
public class UserProfilePicture {

    @Id
    @SequenceGenerator(name = "user_profile_picture_sequence_generator", sequenceName = "user_profile_picture_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "user_profile_picture_sequence_generator")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String path;

    public UserProfilePicture() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

package it.cnr.rethinkwaste.model.assessment;

import it.cnr.rethinkwaste.model.User_;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
public class Profile {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "text")
    private String generalComment;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Category.class)
    private Category category;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "profile_join_subprofile",
            joinColumns = @JoinColumn(
                    name = "profile_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "subprofile_id", referencedColumnName = "id"))
    private List<Subprofile> subprofiles;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="profile_translations", joinColumns=@JoinColumn(name="profile_id"))
    @MapKeyJoinColumn(name="profile_id")
    @MapKeyColumn(name="language")
    @Column(name="translation")
    private Map<String, String> translations;

    public Map<String, String> getTranslations() {
        return translations;
    }

    public void setTranslations(Map<String, String> translations) {
        this.translations = translations;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Subprofile> getSubprofiles() {
        return subprofiles;
    }

    public void setSubprofiles(List<Subprofile> subprofiles) {
        this.subprofiles = subprofiles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGeneralComment() {
        return generalComment;
    }

    public void setGeneralComment(String generalComment) {
        this.generalComment = generalComment;
    }
}

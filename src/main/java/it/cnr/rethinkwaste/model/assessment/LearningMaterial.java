package it.cnr.rethinkwaste.model.assessment;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class LearningMaterial {

    @Id
    @SequenceGenerator(name = "learning_material_sequence_generator", sequenceName = "learning_material_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="learning_material_sequence_generator")
    private Long id;
    @Column(columnDefinition = "text")
    private String link, title;

    private boolean visible;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "learning_material_join_learning_material_type",
            joinColumns = @JoinColumn(
                    name = "learning_material_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "learning_material_type_id", referencedColumnName = "id"))
    private Collection<LearningMaterialType> learningMaterialTypes;

    @ManyToMany
    @JoinTable(
            name = "learning_material_join_module",
            joinColumns = @JoinColumn(
                    name = "learning_material_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "module_id", referencedColumnName = "id"))
    private Collection<Module> modules;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "learning_material_join_language",
            joinColumns = @JoinColumn(
                    name = "learning_material_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "language_id", referencedColumnName = "id"))
    private Language language;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Collection<LearningMaterialType> getLearningMaterialTypes() {
        return learningMaterialTypes;
    }

    public void setLearningMaterialTypes(Collection<LearningMaterialType> learningMaterialTypes) {
        this.learningMaterialTypes = learningMaterialTypes;
    }

    public Collection<Module> getModules() {
        return modules;
    }

    public void setModules(Collection<Module> modules) {
        this.modules = modules;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}

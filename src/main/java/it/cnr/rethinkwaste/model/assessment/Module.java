package it.cnr.rethinkwaste.model.assessment;


import javax.persistence.*;
import java.util.*;

@Entity
public class Module {

    @Id
    @GeneratedValue
    private Long id;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="module_translations", joinColumns=@JoinColumn(name="module_id"))
    @MapKeyJoinColumn(name="module_id")
    @MapKeyColumn(name="language")
    @Column(name="translation")
    private Map<String, String> translations;

    private String text, acronym;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "module_join_category",
            joinColumns = @JoinColumn(
                    name = "module_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "category_id", referencedColumnName = "id"))
    private List<Category> categoryList;

    public Map<String, String> getTranslations() {
        return translations;
    }

    public void setTranslations(Map<String, String> translations) {
        this.translations = translations;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Category> getCategoryList() {
        Collections.sort(categoryList, Comparator.comparingLong(p -> p.getId()));
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
}

package it.cnr.rethinkwaste.model.assessment;

import javax.persistence.*;
import java.util.Map;

@Entity
public class Subprofile {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "text")
    private String color, module3answers, module3questions;

    @javax.persistence.OrderBy("id")
    @OneToMany
    private Map<String, SubprofileTranslationObject> translations;

    public Map<String, SubprofileTranslationObject> getTranslations() {
        return translations;
    }

    public void setTranslations(Map<String, SubprofileTranslationObject> translations) {
        this.translations = translations;
    }

    private double lowerBoundScore, upperBoundScore;

    public double getLowerBoundScore() {
        return lowerBoundScore;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModule3answers() {
        return module3answers;
    }

    public void setModule3answers(String module3answers) {
        this.module3answers = module3answers;
    }

    public String getModule3questions() {
        return module3questions;
    }

    public void setModule3questions(String module3questions) {
        this.module3questions = module3questions;
    }

    public void setLowerBoundScore(double lowerBoundScore) {
        this.lowerBoundScore = lowerBoundScore;
    }

    public double getUpperBoundScore() {
        return upperBoundScore;
    }

    public void setUpperBoundScore(double upperBoundScore) {
        this.upperBoundScore = upperBoundScore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

package it.cnr.rethinkwaste.model.assessment;

import javax.persistence.*;
import java.util.*;

@Entity
public class Category implements Comparable<Category> {

    @Id
    @GeneratedValue
    private Long id;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="category_translations", joinColumns=@JoinColumn(name="category_id"))
    @MapKeyJoinColumn(name="category_id")
    @MapKeyColumn(name="language")
    @Column(name="translation")
    private Map<String, String> translations;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "category_join_question",
            joinColumns = @JoinColumn(
                    name = "category_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "question_id", referencedColumnName = "id"))
    private List<Question> questions;

    public Map<String, String> getTranslations() {
        return translations;
    }

    public void setTranslations(Map<String, String> translations) {
        this.translations = translations;
    }

    public List<Question> getQuestions() {
        Collections.sort(questions, (Question q1, Question q2) -> q1.getId().compareTo(q2.getId()));
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    private double redScore, yellowScore, greenScore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getRedScore() {
        return redScore;
    }

    public void setRedScore(double redScore) {
        this.redScore = redScore;
    }

    public double getYellowScore() {
        return yellowScore;
    }

    public void setYellowScore(double yellowScore) {
        this.yellowScore = yellowScore;
    }

    public double getGreenScore() {
        return greenScore;
    }

    public void setGreenScore(double greenScore) {
        this.greenScore = greenScore;
    }

    @Override
    public int compareTo(Category o) {
        return Long.compare(this.getId(),o.getId());
    }
}

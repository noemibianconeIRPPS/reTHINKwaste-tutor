package it.cnr.rethinkwaste.model.assessment;

import javax.persistence.*;
import java.util.Map;

@Entity
public class Answer {

    @Id
    @GeneratedValue
    private Long id;

    private String text;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="answer_translations", joinColumns=@JoinColumn(name="answer_id"))
    @MapKeyJoinColumn(name="answer_id")
    @MapKeyColumn(name="language")
    @Column(name="translation")
    private Map<String, String> translations;

    private double score;

    public Map<String, String> getTranslations() {
        return translations;
    }

    public void setTranslations(Map<String, String> translations) {
        this.translations = translations;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
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
}

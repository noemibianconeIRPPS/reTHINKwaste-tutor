package it.cnr.rethinkwaste.model.assessment;

import javax.persistence.*;
import java.util.SortedMap;

@Entity
public class CategoryInstanceForMC {

    @Id
    @GeneratedValue
    private Long id;

    private double categoryScore;

    @javax.persistence.OrderBy("id")
    @ElementCollection
    @OneToMany(cascade = CascadeType.ALL)
    private SortedMap<Question, AnswerList> questionAnswerSortedMap;

    public double getCategoryScore() {
        return categoryScore;
    }

    public void setCategoryScore(double categoryScore) {
        this.categoryScore = categoryScore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SortedMap<Question, AnswerList> getQuestionAnswerSortedMap() {
        return questionAnswerSortedMap;
    }

    public void setQuestionAnswerSortedMap(SortedMap<Question, AnswerList> questionAnswerSortedMap) {
        this.questionAnswerSortedMap = questionAnswerSortedMap;
    }
}

package it.cnr.rethinkwaste.model.assessment;

import javax.persistence.*;
import java.util.SortedMap;

@Entity
public class CategoryInstance {

    @Id
    @GeneratedValue
    private Long id;

    private double categoryScore;

    @javax.persistence.OrderBy("id")
    @OneToMany
    private SortedMap<Question, Answer> questionAnswerSortedMap;

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

    public SortedMap<Question, Answer> getQuestionAnswerSortedMap() {
        return questionAnswerSortedMap;
    }

    public void setQuestionAnswerSortedMap(SortedMap<Question, Answer> questionAnswerSortedMap) {
        this.questionAnswerSortedMap = questionAnswerSortedMap;
    }
}

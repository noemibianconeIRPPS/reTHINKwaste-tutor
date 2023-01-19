package it.cnr.rethinkwaste.model.assessment;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
public class AnswerList {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany
    private List<Answer> answers;

    public List<Answer> getAnswers() {
        Collections.sort(answers, (Answer a1, Answer a2) -> a1.getId().compareTo(a2.getId()));
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

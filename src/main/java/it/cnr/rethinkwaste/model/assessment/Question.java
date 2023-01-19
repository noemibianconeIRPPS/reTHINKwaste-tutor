package it.cnr.rethinkwaste.model.assessment;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Entity
public class Question implements Comparable<Question> {

    @Id
    @GeneratedValue
    private Long id;

    @javax.persistence.OrderBy("id")
    @OneToMany
    private Map<String, QuestionTranslationObject> translations;

    @Column(columnDefinition = "text")
    private String guideline;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "question_join_answer",
            joinColumns = @JoinColumn(
                    name = "question_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "answer_id", referencedColumnName = "id"))
    private List<Answer> answerList;

    @ManyToMany
    @JoinTable(
            name = "question_join_learning_material",
            joinColumns = @JoinColumn(
                    name = "question_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "learning_material_id", referencedColumnName = "id"))
    private List<LearningMaterial> learningMaterials;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "question_join_question",
            joinColumns = @JoinColumn(
                    name = "parent_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "child_id", referencedColumnName = "id"))
    private List<Question> questionList;

    public List<LearningMaterial> getLearningMaterials() {
        return learningMaterials;
    }

    public void setLearningMaterials(List<LearningMaterial> learningMaterials) {
        this.learningMaterials = learningMaterials;
    }

    public void setTranslations(Map<String, QuestionTranslationObject> translations) {
        this.translations = translations;
    }

    private Long weight;

    private boolean multipleChoice;

    public boolean mandatory, parent;

    public Map<String, QuestionTranslationObject> getTranslations() {
        return translations;
    }

    public boolean isMultipleChoice() {
        return multipleChoice;
    }

    public void setMultipleChoice(boolean multipleChoice) {
        this.multipleChoice = multipleChoice;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public boolean isParent() {
        return parent;
    }

    public void setParent(boolean parent) {
        this.parent = parent;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public String getGuideline() {
        return guideline;
    }

    public void setGuideline(String guideline) {
        this.guideline = guideline;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Answer> getAnswerList() {
        Collections.sort(answerList, (Answer a1, Answer a2) -> a1.getId().compareTo(a2.getId()));
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    @Override
    public int compareTo(Question o) {
        return Long.compare(this.getId(),o.getId());
    }
}

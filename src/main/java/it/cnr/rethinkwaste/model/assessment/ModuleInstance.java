package it.cnr.rethinkwaste.model.assessment;

import javax.persistence.*;
import java.util.SortedMap;

@Entity
public class ModuleInstance {

    @Id
    @GeneratedValue
    private Long id;

    @javax.persistence.OrderBy("id")
    @OneToMany
    private SortedMap<Category, CategoryInstance> categoryCategoryInstanceSortedMap;

    @javax.persistence.OrderBy("id")
    @OneToMany
    private SortedMap<Category, CategoryInstanceForMC> categoryCategoryInstanceForMCSortedMap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "module_instance_join_module",
            joinColumns = @JoinColumn(
                    name = "module_instance_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "module_id", referencedColumnName = "id"))
    private Module module;

    public SortedMap<Category, CategoryInstanceForMC> getCategoryCategoryInstanceForMCSortedMap() {
        return categoryCategoryInstanceForMCSortedMap;
    }

    public void setCategoryCategoryInstanceForMCSortedMap(SortedMap<Category, CategoryInstanceForMC> categoryCategoryInstanceForMCSortedMap) {
        this.categoryCategoryInstanceForMCSortedMap = categoryCategoryInstanceForMCSortedMap;
    }

    public SortedMap<Category, CategoryInstance> getCategoryCategoryInstanceSortedMap() {
        return categoryCategoryInstanceSortedMap;
    }

    public void setCategoryCategoryInstanceSortedMap(SortedMap<Category, CategoryInstance> categoryCategoryInstanceSortedMap) {
        this.categoryCategoryInstanceSortedMap = categoryCategoryInstanceSortedMap;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    private double score;

    private double percentage;

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
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

}

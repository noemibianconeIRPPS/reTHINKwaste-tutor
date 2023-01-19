package it.cnr.rethinkwaste.model;

import javax.persistence.*;

@Entity
public class Picture {

    @Id
    @SequenceGenerator(name = "picture_sequence_generator", sequenceName = "picture_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "picture_sequence_generator")
    private Long id;
    @Column(columnDefinition = "text")
    private String name;
    @Column(columnDefinition = "text")
    private String path;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

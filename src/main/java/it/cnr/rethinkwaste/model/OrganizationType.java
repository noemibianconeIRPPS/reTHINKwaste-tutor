package it.cnr.rethinkwaste.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OrganizationType {

    @Id
    private Long id;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}

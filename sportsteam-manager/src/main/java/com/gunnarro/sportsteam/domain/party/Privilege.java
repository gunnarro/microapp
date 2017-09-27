package com.gunnarro.sportsteam.domain.party;

import java.util.Collection;

public class Privilege {

    private Long id;

    private String name;

    private Collection<Role> roles;

    /**
     * Default constructor
     */
    public Privilege() {
    }

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

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

}

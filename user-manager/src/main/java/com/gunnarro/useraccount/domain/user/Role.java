package com.gunnarro.useraccount.domain.user;

import java.util.List;

/**
 * Role represents the high-level roles of the user in the system; each role will have a set of low-level privileges.
 * Example:
 * ROLE_ADMIN
 * ROLE_USER
 * ROLE_GUEST
 * ROLE_ANONYMOUS
 * 
 * @see Privilege
 * 
 */
public class Role {
    private Integer id;
    private String name;
    private List<Privilege> privileges;

    /**
     * @param id
     * @param name
     */
    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }

    @Override
    public String toString() {
        return "Role [id=" + id + ", name=" + name + ", privileges=" + privileges + "]";
    }

}

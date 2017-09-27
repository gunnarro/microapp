package com.gunnarro.useraccount.domain.user;

/**
 * Privilege represents a low-level, granular privilege/authority in the system.
 * Example:
 * READ_PRIVILEGE : read permission only
 * WRITE_PRIVILEGE: write permission
 * ALL_PRIVILEGE  : both read and write permission
 * 
 * @see Role
 *
 */
public class Privilege {
    private Integer id;
    private String name;

    /**
     * @param id
     * @param name
     */
    public Privilege(Integer id, String name) {
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

    @Override
    public String toString() {
        return "Privilege [id=" + id + ", name=" + name + "]";
    }

}

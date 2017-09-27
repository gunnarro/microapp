package com.gunnarro.dietmanager.domain.diet;

import java.io.Serializable;

public class Type implements Serializable {

    public static final String TYPE_DEFAULT = "DEFAULT";
    private static final long serialVersionUID = 6292046439999471027L;

    public static Type createDefaultType() {
        return new Type(TYPE_DEFAULT);
    }

    private String description;
    private Integer id;

    private String name;

    public Type(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isDefaultType() {
        return this.name.equalsIgnoreCase(TYPE_DEFAULT);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Use by spring select list
     */
    @Override
    public String toString() {
        return name;
    }

}

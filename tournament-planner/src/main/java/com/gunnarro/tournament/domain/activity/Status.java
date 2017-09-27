package com.gunnarro.tournament.domain.activity;

import java.io.Serializable;


public class Status implements Serializable {
    
    private static final long serialVersionUID = -7544153239152704657L;
    private Integer id;
    private String name;

    public Status() {
    }

    public Status(String name) {
        this.name = name;
    }

    public Status(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Status [id=" + id + ", name=" + name + "]";
    }

}

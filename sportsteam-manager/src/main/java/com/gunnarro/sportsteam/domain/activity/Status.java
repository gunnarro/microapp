package com.gunnarro.sportsteam.domain.activity;

import java.io.Serializable;

import com.gunnarro.sportsteam.domain.party.Contact.StatusEnum;

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

    public static Status createActiveStatus() {
        return new Status(StatusEnum.ACTIVE.name());
    }

    @Override
    public String toString() {
        return "Status [id=" + id + ", name=" + name + "]";
    }

}

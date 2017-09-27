package com.gunnarro.tournament.domain;

import java.io.Serializable;


public class Team extends BaseDomain implements Serializable {

    private static final long serialVersionUID = -7342682849751732634L;

    public Team() {
    }

    public Team(String name) {
        this(null, name);
    }

    public Team(Integer id, String name) {
        super(id);
        setName(name);
    }
  
}

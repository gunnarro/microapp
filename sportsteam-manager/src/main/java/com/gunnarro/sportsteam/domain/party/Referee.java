package com.gunnarro.sportsteam.domain.party;

import java.io.Serializable;

import com.gunnarro.sportsteam.domain.league.League;

public class Referee extends Contact implements Serializable {

    private static final long serialVersionUID = 1461128504220656911L;

    private League ligas;

    /**
     * Default constructor
     */
    public Referee() {
    }

    public Referee(int id) {
        super(id);
    }

    public Referee(String firstName, String middleName, String lastName) {
        super(firstName, middleName, lastName);
    }

    public League getLigas() {
        return ligas;
    }

    public void setLigas(League ligas) {
        this.ligas = ligas;
    }

}

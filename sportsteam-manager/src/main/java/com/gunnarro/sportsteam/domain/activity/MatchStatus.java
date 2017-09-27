package com.gunnarro.sportsteam.domain.activity;

import java.io.Serializable;

public class MatchStatus extends Status implements Serializable {

    private static final long serialVersionUID = 2759273530954124033L;
    public static final String STATUS_NOT_PLAYED = "NOT PLAYED";
    public static final String STATUS_PLAYED = "PLAYED";
    public static final String STATUS_POSTPONED = "POSTPONED";
    public static final String STATUS_CANCELLED = "CANCELLED";
    public static final String STATUS_ONGOING = "ONGOING";

    /**
     * Default constructor
     */
    public MatchStatus() {
    }

    public MatchStatus(String name) {
        super(name);
    }

    public MatchStatus(Integer id, String name) {
        super(name);
    }

    public boolean isPlayed() {
        return getName().equals(STATUS_PLAYED);
    }

    public boolean isNotPlayed() {
        return getName().equals(STATUS_NOT_PLAYED);
    }

    public boolean isPostponed() {
        return getName().equals(STATUS_POSTPONED);
    }

    public boolean isCancelled() {
        return getName().equals(STATUS_CANCELLED);
    }

    public boolean isOngoing() {
        return getName().equals(STATUS_ONGOING);
    }

    public static MatchStatus createDefault() {
        return new MatchStatus(1, STATUS_NOT_PLAYED);
    }
    
    public static MatchStatus createPlayed() {
        return new MatchStatus(1, STATUS_PLAYED);
    }
    
    @Override
    public String toString() {
        return "MatchStatus [getId()=" + getId() + ", getName()=" + getName() + "]";
    }

}

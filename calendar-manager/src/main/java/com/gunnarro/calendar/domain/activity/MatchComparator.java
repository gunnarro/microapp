package com.gunnarro.calendar.domain.activity;

import java.util.Comparator;

public class MatchComparator implements Comparator<Match> {

    @Override
    public int compare(Match m1, Match m2) {
        return m2.getStartDate().compareTo(m1.getStartDate());
    }
}

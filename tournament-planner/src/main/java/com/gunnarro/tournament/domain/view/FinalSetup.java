package com.gunnarro.tournament.domain.view;

import java.io.Serializable;

import com.gunnarro.tournament.domain.activity.Match;

public class FinalSetup implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2584357240700534795L;

    private String tournamentId;

    private Match quarterFinal1;
    private Match quarterFinal2;
    private Match quarterFinal3;
    private Match quarterFinal4;
    private Match semiFinal1;
    private Match semiFinal2;
    private Match bronseFinal;
    private Match goldFinal;

    /**
     * Default constructor
     */
    public FinalSetup() {
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public Match getQuarterFinal1() {
        return quarterFinal1;
    }

    public void setQuarterFinal1(Match quarterFinal1) {
        this.quarterFinal1 = quarterFinal1;
    }

    public Match getQuarterFinal2() {
        return quarterFinal2;
    }

    public void setQuarterFinal2(Match quarterFinal2) {
        this.quarterFinal2 = quarterFinal2;
    }

    public Match getQuarterFinal3() {
        return quarterFinal3;
    }

    public void setQuarterFinal3(Match quarterFinal3) {
        this.quarterFinal3 = quarterFinal3;
    }

    public Match getQuarterFinal4() {
        return quarterFinal4;
    }

    public void setQuarterFinal4(Match quarterFinal4) {
        this.quarterFinal4 = quarterFinal4;
    }

    public Match getSemiFinal1() {
        return semiFinal1;
    }

    public void setSemiFinal1(Match semiFinal1) {
        this.semiFinal1 = semiFinal1;
    }

    public Match getSemiFinal2() {
        return semiFinal2;
    }

    public void setSemiFinal2(Match semiFinal2) {
        this.semiFinal2 = semiFinal2;
    }

    public Match getBronseFinal() {
        return bronseFinal;
    }

    public void setBronseFinal(Match bronseFinal) {
        this.bronseFinal = bronseFinal;
    }

    public Match getGoldFinal() {
        return goldFinal;
    }

    public void setGoldFinal(Match goldFinal) {
        this.goldFinal = goldFinal;
    }

    @Override
    public String toString() {
        return "FinalSetup [tournamentId=" + tournamentId + ", quarterFinal1=" + quarterFinal1 + ", quarterFinal2=" + quarterFinal2 + ", quarterFinal3=" + quarterFinal3
                + ", quarterFinal4=" + quarterFinal4 + ", semiFinal1=" + semiFinal1 + ", semiFinal2=" + semiFinal2 + ", bronseFinal=" + bronseFinal + ", goldFinal=" + goldFinal
                + "]";
    }

}

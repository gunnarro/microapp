package com.gunnarro.tournament.domain.activity;

import java.util.Date;
import java.util.List;

import com.gunnarro.tournament.domain.view.FinalSetup;

public class Tournament extends Activity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String tournamentName;
    private String organizerName;
    private String venue;
    private Date deadlineDate;
    private List<Group> groups;
    private FinalSetup finalsSetup;

    /**
     * Default constructor - needed by form
     */
    public Tournament() {
    }

    public Tournament(String name) {
        this.tournamentName = name;
    }

    @Override
    public String getName() {
        return getTournamentName();
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getVenue() {
        return venue;
    }

    @Override
    public String getType() {
        return ActivityTypesEnum.Tournament.name();
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public FinalSetup getFinalsSetup() {
        return finalsSetup;
    }

    public void setFinalsSetup(FinalSetup finalsSetup) {
        this.finalsSetup = finalsSetup;
    }

    public boolean isGroupPlayFinished() {
        return false;
    }

    public boolean isQuarterFinalsFinished() {
        return false;
    }

    public boolean isSemiFinalsFinished() {
        return false;
    }

    public boolean isBronseFinalFinished() {
        return false;
    }

    public boolean isGoldFinalFinished() {
        return false;
    }

    public Group getGroupById(Integer groupId) {
        Group group = null;
        for (Group g : groups) {
            if (groupId == g.getId()) {
                group = g;
                break;
            }
        }
        return group;
    }
    
    public Group getGroupByName(String groupName) {
        Group group = null;
        for (Group g : groups) {
            if (groupName.equals(g.getName())) {
                group = g;
                break;
            }
        }
        return group;
    }

    @Override
    public String toString() {
        return "Tournament [id=" + id + ", tournamentName=" + tournamentName + ", groups=" + groups + ", finalsSetup=" + finalsSetup + "]";
    }
}

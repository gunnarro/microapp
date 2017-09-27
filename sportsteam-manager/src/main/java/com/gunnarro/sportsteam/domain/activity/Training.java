package com.gunnarro.sportsteam.domain.activity;

import java.util.Date;

import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.view.ActivityView;
import com.gunnarro.sportsteam.utility.Utility;

public class Training extends Activity {

    public final static int DEFAULT_TRAINING_TIME_MINUTES = 90;
    private Team team;
    private String venue;

    public Training() {
        super(null);
    }

    public Training(Season season, Date startDate, Date endDate, Team team, String venue) {
        super(season);
        setStartDate(startDate);
        setEndDate(endDate);
        this.team = team;
        this.venue = venue;
    }

    public Training(int id, Season season, Date startDate, Date endDate, Team team, String venue) {
        this(season, startDate, endDate, team, venue);
        setId(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return ActivityTypesEnum.Training.name();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return ActivityTypesEnum.Training.name();
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public static Training createTraining(Team team) {
        return new Training(null, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), team, team.getClub().getStadiumName());
    }

    public Object mapToActivityView() {
        ActivityView activity = new ActivityView();
        activity.setId(getId());
        activity.setStartDate(getStartDate());
        activity.setDescription(getType() + ", " + getVenue());
        activity.setStatus(getStatus().getName());
        activity.setNumberOfSignedPlayers(getNumberOfSignedPlayers());
        return activity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        sb.append(" [id=").append(getId());
        sb.append(", startDate=").append(Utility.formatTime(getStartDate().getTime(), Utility.DATE_PATTERN));
        sb.append(", endTime=").append(Utility.formatTime(getEndDate().getTime(), Utility.TIME_PATTERN));
        sb.append(", team=").append(getTeam() != null ? getTeam().getName() : null);
        sb.append(", venue=").append(venue).append("]");
        sb.append("\n").append(super.toString());
        return sb.toString();
    }

}

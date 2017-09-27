package com.gunnarro.sportsteam.domain.activity;

import java.util.Calendar;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.gunnarro.sportsteam.domain.view.ActivityView;
import com.gunnarro.sportsteam.utility.Utility;

public class Cup extends Activity {

    private static final long serialVersionUID = 1L;
    
    @Size(min = 2, max = 30)
    @NotNull
//    @Pattern(regexp = Utility.VALIDATOR_PATTERN_STRING)
    private String cupName;
    @Size(min = 2, max = 30)
    @NotNull
//    @Pattern(regexp = Utility.VALIDATOR_PATTERN_STRING)
    private String clubName;
    @Size(min = 2, max = 30)
    @NotNull
//    @Pattern(regexp = Utility.VALIDATOR_PATTERN_STRING)
    private String venue;

    @Size(min = 8, max = 30)
    @NotNull
//    @Pattern(regexp = Utility.VALIDATOR_PATTERN_EMAIL)
    private String email;

    @Size(min = 10, max = 30)
    @NotNull
//    @Pattern(regexp = Utility.VALIDATOR_PATTERN_HOMEPAGE)
    private String homePage;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @NotNull
    private Date deadlineDate = Calendar.getInstance().getTime();

    public Cup() {
        super(null);
    }

    public Cup(Season season, Date startDate, String cupName, String clubName, String venue, Date deadlineDate) {
        super(season);
        setStartDate(startDate);
        this.cupName = cupName;
        this.clubName = clubName;
        this.venue = venue;
    }

    public Cup(Integer id, Season season, Date startDate, String cupName, String clubName, String venue, Date deadlineDate) {
        this(season, startDate, cupName, clubName, venue, deadlineDate);
        setId(id);
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public String getCupName() {
        return cupName;
    }

    public String getClubName() {
        return clubName;
    }

    public String getVenue() {
        return venue;
    }

    public void setCupName(String cupName) {
        this.cupName = cupName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    @Override
    public String getName() {
        return getType();
    }

    @Override
    public String getType() {
        return ActivityTypesEnum.Cup.name();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public Object mapToActivityView() {
        ActivityView activity = new ActivityView();
        activity.setId(getId());
        activity.setStartDate(getStartDate());
        activity.setDescription(getCupName());
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
        sb.append(", startDate=").append(Utility.formatTime(getStartDate().getTime(), null));
        sb.append(", endDate=").append(Utility.formatTime(getEndDate().getTime(), null));
        sb.append(", deadLine=").append(Utility.formatTime(getDeadlineDate().getTime(), null));
        sb.append(", cupName=").append(cupName);
        sb.append(", clubName=").append(clubName);
        sb.append(", email=").append(email);
        sb.append(", homePage=").append(homePage);
        sb.append(", venue=").append(venue).append("]");
        sb.append("\n").append(super.toString());
        super.toString();
        return sb.toString();
    }

}

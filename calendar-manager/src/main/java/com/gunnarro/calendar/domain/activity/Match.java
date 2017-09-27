package com.gunnarro.calendar.domain.activity;

import java.io.Serializable;
import java.util.Date;

import com.gunnarro.calendar.domain.activity.Type.MatchTypesEnum;
import com.gunnarro.calendar.utility.Utility;

public class Match extends Activity implements Comparable<Match>, Serializable {

	private static final long serialVersionUID = 1127082650249886729L;

	private String startDateTime;
	private Integer matchTypeId;
	private String venue;
	private Integer numberOfGoalsHome;
	private Integer numberOfGoalsAway;
	private MatchTypesEnum matchType = MatchTypesEnum.LEAGUE;

	private String tournamentPhase;

	/**
	 * default constructor
	 */
	public Match() {
	}

	/**
	 * default constructor
	 */
	public Match(Date startDate, String name) {
		super.setName(name);
		setStartDate(startDate);
	}

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getVenue() {
		return venue;
	}

	public String getTeamVersus() {
		return null;
	}


	public boolean isPlayed() {
		// return matchStatus.isPlayed();
		return numberOfGoalsHome != null && numberOfGoalsAway != null;
	}

	
	public Integer getNumberOfGoalsHome() {
		return numberOfGoalsHome;
	}

	public Integer getNumberOfGoalsAway() {
		return numberOfGoalsAway;
	}

	public void setNumberOfGoalsHome(Integer numberOfGoalsHome) {
		this.numberOfGoalsHome = numberOfGoalsHome;
	}

	public void setNumberOfGoalsAway(Integer numberOfGoalsAway) {
		this.numberOfGoalsAway = numberOfGoalsAway;
	}

	public String getResult() {
		if (numberOfGoalsHome != null && numberOfGoalsAway != null) {
			return numberOfGoalsHome + " - " + numberOfGoalsAway;
		} else {
			return null;
		}
	}

	public void setMatchType(MatchTypesEnum matchType) {
		this.matchType = matchType;
	}

	public MatchTypesEnum getMatchType() {
		return matchType;
	}

	public Integer getMatchTypeId() {
		return matchTypeId;
	}

	public void setMatchTypeId(Integer matchTypeId) {
		this.matchTypeId = matchTypeId;
	}

	@Override
	public String getName() {
		return getTeamVersus();
	}

	@Override
	public String getType() {
		return ActivityTypesEnum.Match.name();
	}

	public String getMatchInfo() {
		return Utility.formatTime(getStartDate().getTime(),
				"EEE dd.MM.yyyy HH:mm")
				+ " "
				+ getStartTime()
				+ " "
				+ getTeamVersus() + ", " + getVenue();
	}

	public boolean isStartDateBeforeToDay() {
		return getStartDate().before(new Date());
	}

	public String getTournamentPhase() {
		return tournamentPhase;
	}

	public void setTournamentPhase(String tournamentPhase) {
		this.tournamentPhase = tournamentPhase;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getSimpleName());
		sb.append(" [id=").append(getId());
		sb.append(", startDate=").append(
				Utility.formatTime(getStartDate().getTime(),
						Utility.DATE_TIME_PATTERN));
		sb.append(", versus=").append(getTeamVersus());
		sb.append(", result=").append(getResult());
		sb.append(", venue=").append(venue).append("]");
		// sb.append("\n").append(super.toString());
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public int compareTo(Match m2) {
		return this.getStartDate().compareTo(m2.getStartDate());
	}

}

package com.gunnarro.tournament.domain.activity;

import java.io.Serializable;
import java.util.Date;

import com.gunnarro.tournament.domain.Team;
import com.gunnarro.tournament.domain.activity.Type.MatchTypesEnum;
import com.gunnarro.tournament.utility.Utility;

public class Match extends Activity implements Comparable<Match>, Serializable {

	private static final long serialVersionUID = 1127082650249886729L;

	private String startDateTime;
	private Integer matchTypeId;
	private Team team;
	private Team homeTeam;
	private Team awayTeam;
	private String venue;
	private MatchStatus matchStatus = MatchStatus.createDefault();
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

	public Match(Date startDate, Team homeTeam, Team awayTeam, String venue) {
		setStartDate(startDate);
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.venue = venue;
	}

	public MatchStatus getMatchStatus() {
		return matchStatus;
	}

	public String getStatus() {
		return matchStatus.getName();
	}

	public void setMatchStatus(MatchStatus matchStatus) {
		this.matchStatus = matchStatus;
	}

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
	}

	public void setAwayTeam(Team awayTeam) {
		this.awayTeam = awayTeam;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public Team getTeam() {
		return team;
	}

	public Team getHomeTeam() {
		return homeTeam;
	}

	public Team getAwayTeam() {
		return awayTeam;
	}

	public String getVenue() {
		return venue;
	}

	public String getTeamVersus() {
		if (homeTeam != null && awayTeam != null) {
			return this.homeTeam.getName() + " - " + this.awayTeam.getName();
		}
		return null;
	}

	public boolean isPlayed() {
		// return matchStatus.isPlayed();
		return numberOfGoalsHome != null && numberOfGoalsAway != null;
	}

	public boolean isCancelled() {
		return matchStatus.isCancelled();
	}

	public boolean isPostponed() {
		return matchStatus.isPostponed();
	}

	public boolean isOngoing() {
		return matchStatus.isOngoing();
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
		return Utility.formatTime(getStartDate().getTime(), "EEE dd.MM.yyyy HH:mm") + " " + getStartTime() + " " + getTeamVersus() + ", " + getVenue();
	}

	public boolean isStartDateBeforeToDay() {
		return getStartDate().before(new Date());
	}

	public static Match createMatch(String tournamnetStage, String homeTeamName, String awayTeamName, String venue) {
		Match m = new Match(new Date(), new Team(homeTeamName), new Team(awayTeamName), venue);
		m.setTournamentPhase(tournamnetStage);
		return m;
	}

	public String getTournamentPhase() {
		return tournamentPhase;
	}

	public void setTournamentPhase(String tournamentPhase) {
		this.tournamentPhase = tournamentPhase;
	}

	public String getWinnerTeamName() {
		if (numberOfGoalsHome == numberOfGoalsAway) {
			return this.getHomeTeam().getName() + "/" + this.getAwayTeam().getName();
		}
		if (numberOfGoalsHome > numberOfGoalsAway) {
			return this.getHomeTeam().getName();
		}
		return this.getAwayTeam().getName();
	}

	public String getLooserTeamName() {
		if (numberOfGoalsHome == numberOfGoalsAway) {
			return this.getHomeTeam().getName() + "/" + this.getAwayTeam().getName();
		}
		if (numberOfGoalsHome > numberOfGoalsAway) {
			return this.getAwayTeam().getName();
		}
		return this.getHomeTeam().getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getSimpleName());
		sb.append(" [id=").append(getId());
		sb.append(", startDate=").append(Utility.formatTime(getStartDate().getTime(), Utility.DATE_TIME_PATTERN));
		sb.append(", status=").append(matchStatus.getName());
		sb.append(", team=").append(team != null ? team.getName() : null);
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

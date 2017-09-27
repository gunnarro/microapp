package com.gunnarro.tournament.service;

import java.util.List;

import com.gunnarro.tournament.domain.activity.Match;
import com.gunnarro.tournament.domain.activity.Tournament;
import com.gunnarro.tournament.domain.party.User;
import com.gunnarro.tournament.domain.view.FinalSetup;
import com.gunnarro.tournament.domain.view.TournamentInput;

public interface TournamentPlannerService {

	public User getUser(String username);
	
	public Tournament generateTournament(TournamentInput tournament);

    public Tournament getTournament(String tournamentId);

    public List<Tournament> getTournaments(String token);

    public void saveTournament(Tournament tournament);

    public boolean deleteTournament(String tournamentId);

    public void scheduleMatches(List<Match> matches, int playTime, int pause);

    public FinalSetup generateFinalsSetup(String tournamentId, int numberOfGroups);

    public void updateFinalSetup(FinalSetup finalsSetup);

    public void updateGroupStage(Tournament tournament);

}

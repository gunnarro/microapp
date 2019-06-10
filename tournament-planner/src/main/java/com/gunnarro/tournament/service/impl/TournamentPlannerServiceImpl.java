package com.gunnarro.tournament.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.gunnarro.tournament.domain.Team;
import com.gunnarro.tournament.domain.activity.Group;
import com.gunnarro.tournament.domain.activity.Match;
import com.gunnarro.tournament.domain.activity.Tournament;
import com.gunnarro.tournament.domain.activity.Type.TounamentMatchTypes;
import com.gunnarro.tournament.domain.party.User;
import com.gunnarro.tournament.domain.view.FinalSetup;
import com.gunnarro.tournament.domain.view.TournamentInput;
import com.gunnarro.tournament.service.TournamentPlannerService;
import com.gunnarro.tournament.service.exception.ApplicationException;

/**
 * see for example http://challonge.com/tournament/bracket_generator diagram:
 * http://gojs.net/latest/samples/tournament.html
 * 
 * @author admin
 *
 */
@Service(value = "tournamentPlannerService")
@CacheConfig(cacheNames = { "tournamentCache" })
public class TournamentPlannerServiceImpl implements TournamentPlannerService {

	private static final Logger LOG = LoggerFactory.getLogger(TournamentPlannerServiceImpl.class);

	// @Autowired
	// @Qualifier("cacheHandler")
	// private CacheHandler cacheHandler;

	@Autowired
	private CacheManager cacheManager;

	// FIXME not implemented
	@Override
	public User getUser(String username) {
		return null;
	}

	@Override
	public void saveTournament(Tournament tournament) {
		// cacheHandler.putTournamentIntoCache(tournament.getTournamentName(),
		// tournament);
	}

	@Override
	public boolean deleteTournament(String tournamentId) {
		// cacheHandler.invalidateTournamentCacheElement(tournamentId);
		return false;
	}

	@Override
	public List<Tournament> getTournaments(String name) {
		List<Tournament> list = new ArrayList<>();
		Cache springCache = cacheManager.getCache("tournamentCache");
		ConcurrentHashMap<?, ?> ehcache = (ConcurrentHashMap<?, ?>) springCache.getNativeCache();
		Enumeration<?> elements = ehcache.elements();
		while (elements.hasMoreElements()) {
			list.add((Tournament) elements.nextElement());
		}
		return list;
	}

	
	public void getGroupResult(String groupId) {
		Group group = getTournament("*").getGroupById(23);
		for (Match match : group.getMatches()) {
			if (match.getNumberOfGoalsAway() == null) {
				
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Cacheable(value = "tournamentCache", key = "#tournamentId")
	public Tournament getTournament(String tournamentId) {
		return new Tournament(tournamentId + "no hit in cache");
	}

	/**
	 * Steps: 1. Split teamnames into groups 2. Generate matches for each group
	 * 
	 * {@inheritDoc}
	 */
	@Override
	@Cacheable(key = "#tournamentInput.name")
	public Tournament generateTournament(TournamentInput tournamentInput) {
		try {
			List<String> teamNames = tournamentInput.getTeamNames();
			List<String> permutations = new ArrayList<String>();

			int t = teamNames.size() % tournamentInput.getNumberOfGroups();
			if (t != 0) {
				throw new RuntimeException("groups and treams must be a even numer! teams=" + teamNames.size());
			}
			List<Group> generatedGroups = new ArrayList<Group>();
			int numberOfTeamsPrGroup = teamNames.size() / tournamentInput.getNumberOfGroups();
			int n = 0;
			int i = 0;
			int numberOfTeams = teamNames.size();
			while (i <= numberOfTeams) {
				LOG.debug("teams=" + teamNames.size() + ", sublist: " + (n * numberOfTeamsPrGroup) + " " + (numberOfTeamsPrGroup * (n + 1)));
				List<String> names = new ArrayList<String>(teamNames.subList(n * numberOfTeamsPrGroup, numberOfTeamsPrGroup * (n + 1)));// pickRandomTeamNames(teamNames,
				Group group = new Group(n, "group_" + n, null);
				LOG.debug("group. {}, teams: {}", group.getName(), names);
				group.setTournamentId(tournamentInput.getName());
				group.setTeamNames(names);
				LOG.debug("{}", group);
				List<Match> generatedMatches = generateMatches(group.getTeamNames());
				scheduleMatches(generatedMatches, tournamentInput.getPlayTime(), tournamentInput.getPauseTimeBetweenMatches());
				group.setMatches(generatedMatches);
				generatedGroups.add(group);
				verifyNumberOfMatches(group.getName(), group.getMatches(), names.size());
				n++;
				i = (i + 1) + numberOfTeamsPrGroup;
				LOG.debug("{}", group);
			}
			LOG.debug("permutations=" + permutations);
			LOG.debug("permutations=" + permutations.size());
			Tournament genTournament = new Tournament(tournamentInput.getName());
			genTournament.setId(tournamentInput.getName().hashCode());
			genTournament.setOrganizerName(tournamentInput.getOrganizerName());
			genTournament.setCreatedTime(System.currentTimeMillis());
			genTournament.setStatus("NOT_STARTED");
			genTournament.setGroups(generatedGroups);
			genTournament.setFinalsSetup(generateFinalsSetup(tournamentInput.getName(), 4));
			return genTournament;
		} catch (Exception e) {
			LOG.error(null, e);
			e.printStackTrace();
			throw new ApplicationException(e.getMessage());
		}
	}

	public FinalSetup generateFinalsSetup(String tournamentId, int numberOfGroups) {
		FinalSetup finalSetup = new FinalSetup();
		finalSetup.setTournamentId(tournamentId);
		List<Match> generateQuarterFinals = generateQuarterFinals(numberOfGroups);
		finalSetup.setQuarterFinal1(generateQuarterFinals.get(0));
		finalSetup.setQuarterFinal2(generateQuarterFinals.get(1));
		finalSetup.setQuarterFinal3(generateQuarterFinals.get(2));
		finalSetup.setQuarterFinal4(generateQuarterFinals.get(3));
		// Set semi finals
		finalSetup.setSemiFinal1(Match.createMatch(TounamentMatchTypes.SEMI_FINAL_1.name(), "winner-quarter-final-1", "winner-quarter-final-3", null));
		finalSetup.setSemiFinal2(Match.createMatch(TounamentMatchTypes.SEMI_FINAL_2.name(), "winner-quarter-final-2", "winner-quarter-final-4", null));
		finalSetup.setBronseFinal(Match.createMatch(TounamentMatchTypes.BRONSE_FINAL.name(), "looser-semi-final-1", "looser-semi-final-2", null));
		finalSetup.setGoldFinal(Match.createMatch(TounamentMatchTypes.GOLD_FINAL.name(), "winner-semi-final-1", "winner-semi-final-2", null));
		return finalSetup;
	}

	private List<String> pickRandomTeamNames(List<String> teamNames, int numberOfTeams) {
		List<String> names = new ArrayList<String>();
		Random rand = new Random();
		for (int i = 0; i < numberOfTeams; i++) {
			names.add(teamNames.remove(rand.nextInt(teamNames.size())));
		}
		return names;
	}

	private List<Match> generateQuarterFinals(int numberOfGroups) {
		List<Match> quarterFinals = new ArrayList<Match>();
		List<String> groupWinners = new ArrayList<String>();
		List<String> groupSeconds = new ArrayList<String>();
		for (int i = 0; i < numberOfGroups; i++) {
			groupWinners.add("gr" + i + "_1");
			groupSeconds.add("gr" + i + "_2");
		}

		Random rand = new Random();
		for (int j = 0; j < 4; j++) {
			int w1 = rand.nextInt(4 - j);
			int s2 = rand.nextInt(4 - j);
			LOG.debug("indexes: " + w1 + " " + s2);
			try {
				if (groupWinners.size() > 1) {
					while (groupWinners.get(w1).startsWith(groupSeconds.get(s2).substring(0, 3))) {
						w1 = rand.nextInt(4 - j);
						s2 = rand.nextInt(4 - j);
						LOG.debug("rerun rand: " + w1 + " " + s2 + ", list size: " + groupWinners.size());
					}
				}
				quarterFinals.add(Match.createMatch(null, groupWinners.get(w1), groupSeconds.get(s2), null));
				groupWinners.remove(w1);
				groupSeconds.remove(s2);
			} catch (Exception e) {
				System.out.println(groupWinners.get(w1) + " " + groupSeconds.get(s2));
				throw e;
			}
		}
		quarterFinals.get(0).setTournamentPhase(TounamentMatchTypes.QUARTER_FINAL_1.name());
		quarterFinals.get(1).setTournamentPhase(TounamentMatchTypes.QUARTER_FINAL_2.name());
		quarterFinals.get(2).setTournamentPhase(TounamentMatchTypes.QUARTER_FINAL_3.name());
		quarterFinals.get(3).setTournamentPhase(TounamentMatchTypes.QUARTER_FINAL_4.name());
		return quarterFinals;
	}

	/**
	 * 
	 * @param numberOfMatches
	 * @param playTime
	 *            in minutes
	 * @param pause
	 *            in minutes
	 */
	public void scheduleMatches(List<Match> matches, int playTime, int pause) {
		// DateTimeFormatter dtf =
		// DateTimeFormat.forPattern("dd.MM.YYYY HH:mm");
		DateTime today = new DateTime(new Date());
		DateTime dt = new DateTime(today.year().get(), today.monthOfYear().get(), today.dayOfMonth().get(), 10, 0, 0, 0);
		for (Match m : matches) {
			dt = dt.plusMinutes(playTime + pause);
			m.setStartDate(dt.toDate());
			// System.out.println(i + ". " + dt.toString(dtf));
		}
	}

	@Override
	public void updateGroupStage(Tournament tournament) {
		tournament.getGroups();
	}

	@Override
	public void updateFinalSetup(FinalSetup finalsSetup) {
		// Semi finals
		if (finalsSetup.getQuarterFinal1().isPlayed()) {
			finalsSetup.getSemiFinal1().setHomeTeam(new Team(finalsSetup.getQuarterFinal1().getWinnerTeamName()));
			LOG.debug("WINNER: " + finalsSetup.getQuarterFinal1().getWinnerTeamName());
		}
		if (finalsSetup.getQuarterFinal2().isPlayed()) {
			finalsSetup.getSemiFinal1().setAwayTeam(new Team(finalsSetup.getQuarterFinal2().getWinnerTeamName()));
		}

		if (finalsSetup.getQuarterFinal3().isPlayed()) {
			finalsSetup.getSemiFinal2().setHomeTeam(new Team(finalsSetup.getQuarterFinal3().getWinnerTeamName()));
		}
		if (finalsSetup.getQuarterFinal4().isPlayed()) {
			finalsSetup.getSemiFinal2().setAwayTeam(new Team(finalsSetup.getQuarterFinal4().getWinnerTeamName()));
		}

		// Gold and bronse final
		if (finalsSetup.getSemiFinal1().isPlayed()) {
			finalsSetup.getGoldFinal().setHomeTeam(new Team(finalsSetup.getSemiFinal1().getWinnerTeamName()));
			finalsSetup.getBronseFinal().setHomeTeam(new Team(finalsSetup.getSemiFinal1().getLooserTeamName()));
		}
		if (finalsSetup.getSemiFinal2().isPlayed()) {
			finalsSetup.getGoldFinal().setAwayTeam(new Team(finalsSetup.getSemiFinal2().getWinnerTeamName()));
			finalsSetup.getBronseFinal().setAwayTeam(new Team(finalsSetup.getSemiFinal2().getLooserTeamName()));
		}
	}

	private List<Match> generateMatches(List<String> teamNames) {
		List<String> permutations = new ArrayList<String>();
		List<Match> matches = new ArrayList<Match>();
		int numberOfProducts = teamNames.size() * (teamNames.size() - 1);
		int numberOfPermutations = fakultet(teamNames.size());
		LOG.debug("Fakultet:" + numberOfPermutations);
		int n = 0;
		for (int i = 0; i < numberOfPermutations; i++) {
			if (i < teamNames.size()) {
				for (int j = 0; j < teamNames.size(); j++) {
					// Skip when name are equals
					if (teamNames.get(i) != teamNames.get(j)) {
						// System.out.println("generated match: " +
						// teamNames.get(i) + teamNames.get(j));
						n++;
						// One way permutation only, ie if contains A-B
						// combination, then skip B-A combination
						if (!permutations.contains(flip(teamNames.get(i), teamNames.get(j)))) {
							permutations.add(teamNames.get(i) + " - " + teamNames.get(j));
							matches.add(new Match(new Date(), new Team(teamNames.get(i).trim()), new Team(teamNames.get(j).trim()), "venue_" + i));
							// System.out.println("generated match nr. " +
							// (permutations.size() - 1) + " " +
							// permutations.get(permutations.size() - 1));
						} else {// to way

						}
					}
				}
			}
		}
		LOG.debug("n=" + n);
		LOG.debug("f=" + numberOfPermutations);
		LOG.debug("p=" + numberOfProducts);
		return matches;
	}

	private void verifyNumberOfMatches(String groupName, List<Match> matches, int numberOfTeams) {
		LOG.debug("grupName: {}, teams: {}, matches: {}", groupName, numberOfTeams, matches.size());
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (Match match : matches) {
			updateMap(map, match.getHomeTeam().getName());
			updateMap(map, match.getAwayTeam().getName());
		}

		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() != numberOfTeams - 1) {
				throw new RuntimeException("ERROR Validation failed, number of matches per team is not equal to " + (numberOfTeams - 1) + ": " + entry.toString());
			}
		}
		LOG.debug("Group Name                : {}", groupName);
		LOG.debug("Number of Teams           : {}", map.size());
		LOG.debug("Number of matches         : {}", matches.size());
		LOG.debug("Number of matches per team: {}", map.entrySet().isEmpty() ? null : "");
		LOG.debug("Number of matches,  verified OK: {}", groupName);
	}

	private void updateMap(Map<String, Integer> map, String key) {
		if (!map.containsKey(key)) {
			map.put(key, new Integer(1));
		} else {
			Integer value = map.get(key);
			map.put(key, new Integer(value + 1));
		}
	}

	private String flip(String a, String b) {
		return b + " - " + a;
	}

	private static int fakultet(int maxTall) {
		int nFak = 1;
		for (int tall = 1; tall <= maxTall; tall++) {
			nFak = nFak * tall;
		}
		return nFak;
	}

	@Override
	public void clearCache() {
		cacheManager.getCache("tournamentCache").clear();
	}
}

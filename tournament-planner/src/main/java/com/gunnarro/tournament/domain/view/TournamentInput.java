package com.gunnarro.tournament.domain.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.gunnarro.tournament.domain.view.list.Item;

public class TournamentInput {

	private enum TournamentTypesEnum {
		SINGLE, SINGLE_AND_GROUP_PLAY;
	}

	public enum TeamNamesEnum {
		Capricornus, Leo, Eridanus, Virgo, Cygnus, Auriga, Ophiuchus, Sagittarius, Cetus, Corvus, Canis, Ursa, Orion, Andromeda, Scorpius, Perseus, Hydra, Draco, Triangulum, Pisces, Canes, Pegasus, Serpens, Delphinus, Aquila;
	}

	@Size(min = 1, max = 32, message = "Name must be between 1 and 32 characters")
	private String name;

	private String organizerName;

	@NotNull
	private String type;
	@NotNull
	private List<@NotBlank(message = "Team name must not be blank") String> teamNames;
	@NotNull
	@Min(1)
	private Integer numberOfGroups = 2;
	/**
	 * The playing surface for the game
	 */
	@NotNull
	@Min(2)
	private Integer numberOfFields = 2;
	/**
	 * The length of a match in minutes.
	 */
	@NotNull
	@Min(5)
	private Integer playTime = 20;
	@NotNull
	private Integer pauseTimeBetweenMatches = 5;
	private Integer bracketSize;
	private boolean generateTeamNamesForGroup = false;

	public TournamentInput() {
		// use 4 team as default
		List<String> names = Stream.of(TeamNamesEnum.values()).map(Enum::name)
				.collect(Collectors.toList());
		Random r = new Random();
		int low = 0;
		int high = names.size() - 1;
		teamNames = new ArrayList<>();
		while (teamNames.size() < 8) {
			int x = r.nextInt(high - low) + low;
			if (!teamNames.contains(names.get(x))) {
				teamNames.add(names.get(x));
			}
		}
	}

	public TournamentInput(String name, String type, List<String> teamNames) {
		this.name = name;
		this.type = type;
		this.teamNames = teamNames;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrganizerName() {
		return organizerName;
	}

	public void setOrganizerName(String organizerName) {
		this.organizerName = organizerName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getTeamNames() {
		return teamNames;
	}

	public void setTeamNames(List<String> teamNames) {
		this.teamNames = teamNames;
	}

	public Integer getBracketSize() {
		return bracketSize;
	}

	public void setBracketSize(Integer bracketSize) {
		this.bracketSize = bracketSize;
	}

	public Integer getNumberOfGroups() {
		return numberOfGroups;
	}

	public void setNumberOfGroups(Integer numberOfGroups) {
		this.numberOfGroups = numberOfGroups;
	}

	public Integer getNumberOfFields() {
		return numberOfFields;
	}

	public void setNumberOfFields(Integer numberOfFields) {
		this.numberOfFields = numberOfFields;
	}

	public Integer getPlayTime() {
		return playTime;
	}

	public void setPlayTime(Integer playTime) {
		this.playTime = playTime;
	}

	public Integer getPauseTimeBetweenMatches() {
		return pauseTimeBetweenMatches;
	}

	public void setPauseTimeBetweenMatches(Integer pauseTimeBetweenMatches) {
		this.pauseTimeBetweenMatches = pauseTimeBetweenMatches;
	}

	public static List<Item> getTournamentTypeOptions() {
		List<Item> typeItems = new ArrayList<Item>();
		typeItems.add(new Item("Single", "Single"));
		typeItems.add(new Item("Double", "Double"));
		return typeItems;
	}

	@Override
	public String toString() {
		return "TournamentInput [name=" + name + ", type=" + type
				+ ", teamNames=" + teamNames + ", numberOfGroups="
				+ numberOfGroups + "]";
	}

}

package com.gunnarro.sportsteam.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.gunnarro.sportsteam.domain.league.League;
import com.gunnarro.sportsteam.domain.party.Contact;
import com.gunnarro.sportsteam.domain.party.Contact.GenderEnum;
import com.gunnarro.sportsteam.domain.party.Player;
import com.gunnarro.sportsteam.domain.view.list.Item;

public class Team extends BaseDomain implements Serializable {

    private static final long serialVersionUID = -7342682849751732634L;

    private int teamYearOfBirth;
    private String gender = GenderEnum.MALE.name();
    private Club club;
    private League league;
    private List<Contact> conatctList;
    private List<Player> playerList;
    private Contact coach;
    private Contact teamLead;
    private int numberOfPlayers;
    private int numberOfContacts;

    public Team() {
    }

    public Team(String name) {
        this(null, name);
    }

    public Team(Integer id, String name) {
        super(id);
        setName(name);
    }

    public Team(String name, Club club, int teamYearOfBirth, String gender) {
        this(null, name);
        this.club = club;
        this.teamYearOfBirth = teamYearOfBirth;
        this.gender = gender;
    }

    public Team(int id, String name, Club club, int teamYearOfBirth, String gender) {
        this(name, club, teamYearOfBirth, gender);
    }

    public void setTeamYearOfBirth(int teamYearOfBirth) {
        this.teamYearOfBirth = teamYearOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Contact getCoach() {
        return coach;
    }

    public void setCoach(Contact coach) {
        this.coach = coach;
    }

    public Integer getTeamYearOfBirth() {
        return teamYearOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public boolean isMale() {
        return gender.equalsIgnoreCase(GenderEnum.MALE.name());
    }

    public List<Contact> getConatctList() {
        return conatctList;
    }

    public void setConatctList(List<Contact> conatctList) {
        this.conatctList = conatctList;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public List<Item> getPlayerItemList() {
        List<Item> playerItemList = new ArrayList<Item>();
        for (Player player : playerList) {
            playerItemList.add(new Item(player.getId(), player.getFullName(), false));
        }
        return playerItemList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public Contact getTeamLead() {
        return teamLead;
    }

    public void setTeamLead(Contact teamLead) {
        this.teamLead = teamLead;
    }

    public String[] getMobileNrForContacts() {
        List<String> mobileNumbers = new ArrayList<String>();
        for (Contact contact : conatctList) {
            mobileNumbers.add(contact.getMobileNumber());
        }
        return mobileNumbers.toArray(new String[mobileNumbers.size()]);
    }

    public String[] getEmailAddresseForContacts() {
        List<String> emailAddreses = new ArrayList<String>();
        for (Contact contact : conatctList) {
            emailAddreses.add(contact.getEmailAddress());
        }
        return emailAddreses.toArray(new String[emailAddreses.size()]);
    }

    public boolean hasTeamLead() {
        return teamLead != null && teamLead.getId() != null;
    }

    public boolean hasCoach() {
        return coach != null && coach.getId() != null;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfContacts() {
        return numberOfContacts;
    }

    public void setNumberOfContacts(int numberOfContacts) {
        this.numberOfContacts = numberOfContacts;
    }

    public boolean hasTeamleadChanged(Contact currentTeamlead) {
        return hasIdChanged(currentTeamlead == null ? null : currentTeamlead.getId(), getFkTeamleadId());
    }

    public boolean hasCoachChanged(Contact currentCoach) {
        return hasIdChanged(currentCoach == null ? null : currentCoach.getId(), getFkCoachId());
    }

    @Override
    public String toString() {
        return "Team [id=" + getId() + ", name=" + getName() + ", teamYearOfBirth=" + teamYearOfBirth + ", gender=" + gender + ", club=" + club
                + ", fkLeagueId=" + getFkLeagueId() + ", fkClubId=" + getFkClubId() + ", coach=" + coach + ", teamLead=" + teamLead + "]";
    }
}

package com.gunnarro.sportsteam.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gunnarro.sportsteam.domain.party.Address;

//@Entity
//@Table(name = "Clubs")
public class Club extends BaseDomain implements Serializable {

    private static final long serialVersionUID = 1343434333L;

    // public static enum DepartmentNamesEnum {
    // Bandy, Baseball, Hockey, Soccer, Volleyball, Other;
    //
    // public String[] asArray() {
    // return new String[] { Bandy.name(), Baseball.name(), Hockey.name(),
    // Soccer.name(), Volleyball.name(), Other.name() };
    // }
    // }

    // @Id
    // @GeneratedValue
    // @Column(name = "club_department_name")
    private String departmentName;
    // @Column(name = "club_stadium_name")
    private String stadiumName;
    // @Column(name = "club_name_abbreviation")
    private String clubNameAbbreviation;
    // @Column(name = "fk_address_id")
    private Address address;
    // @Column(name = "club_url_home_page")
    private String homePageUrl;

    private int numberOfTeams;
    private int numberOfReferees;
    private int numberOfMembers;
    private int numberOfPlayers;

    /**
     * Default constructor used by json mapping
     */
    public Club() {
    }

    public Club(Integer id, String name, String departmentName) {
        super(id, name);
        this.departmentName = departmentName;
    }

    public Club(String name, String departmentName) {
        this(null, name, departmentName);
    }

    public Club(Integer id, String name, String departmentName, String clubNameAbbreviation, String stadiumName, Address address, String homePageUrl) {
        this(id, name, departmentName);
        this.clubNameAbbreviation = clubNameAbbreviation;
        this.stadiumName = stadiumName;
        this.address = address;
        this.homePageUrl = homePageUrl;

    }

    @JsonIgnore
    public String getFullName() {
        return getName() + " " + departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setClubNameAbbreviation(String clubNameAbbreviation) {
        this.clubNameAbbreviation = clubNameAbbreviation;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getClubNameAbbreviation() {
        return clubNameAbbreviation;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public String getHomePageUrl() {
        return homePageUrl;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public void setHomePageUrl(String homePageUrl) {
        this.homePageUrl = homePageUrl;
    }

    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(int numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public int getNumberOfMembers() {
        return numberOfMembers;
    }

    public void setNumberOfMembers(int numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfReferees() {
        return numberOfReferees;
    }

    public void setNumberOfReferees(int numberOfReferees) {
        this.numberOfReferees = numberOfReferees;
    }

    public boolean hasAddress() {
        return address != null;
    }

    public boolean isAddressNew() {
        return hasAddress() && address.isNew();
    }

    @Override
    public String toString() {
        return "Club [name=" + getName() + ", departmentName=" + departmentName + ", stadiumName=" + stadiumName + ", clubNameAbbreviation="
                + clubNameAbbreviation + ", address=" + address + ", homePageUrl=" + homePageUrl + "]";
    }

}

package com.gunnarro.sportsteam.domain.party;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.view.list.Item;

public class Player extends Contact {

    private static final long serialVersionUID = 4710658470614911565L;
    
    private StatusEnum status = StatusEnum.ACTIVE;
    private List<Contact> parents;
    private List<Item> parentItemList;
    private Date dateOfBirth;
    private String schoolName;
    private String position;
    private int jerseyNumber;

    public Player() {
        super();
    }

    public Player(Integer id) {
        super(id);
    }

    @Deprecated
    public Player(Team team, String firstName, String middleName, String lastName, String gender, StatusEnum status, List<Contact> parents, Date dateOfBirth,
            Address address) {
        super(team, firstName, middleName, lastName, gender, address);
        this.status = status;
        this.parents = parents;
        this.dateOfBirth = new Date(dateOfBirth.getTime());
    }

    public Player(Team team, String firstName, String middleName, String lastName, String gender, Address address) {
        super(team, firstName, middleName, lastName, gender, address);
    }

    public List<Contact> getParents() {
        return parents;
    }

    public void setParents(List<Contact> parents) {
        this.parents = parents;
    }

    public List<String> getParentNames() {
        List<String> names = new ArrayList<String>();
        for (Contact c : getParents()) {
            names.add(c.getFullName());
        }
        return names;
    }

    public void setParentItemList(List<Item> parentItemList) {
        this.parentItemList = parentItemList;
    }

    public List<Item> getParentItemList() {
        // parentItemList = new ArrayList<Item>();
        // for (Contact parent : parents) {
        // parentItemList.add(new Item(parent.getId(), parent.getFullName(),
        // false));
        // }
        return parentItemList;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = new Date(dateOfBirth.getTime());
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(int jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    public boolean hasParents() {
        return parents != null && !parents.isEmpty();
    }

    @Override
    public String toString() {
        return super.toString() + "+nPlayer [status=" + status + ", parents=" + parents + ", parentItemList=" + parentItemList + ", dateOfBirth=" + dateOfBirth
                + ", schoolName=" + schoolName + "]";
    }

}

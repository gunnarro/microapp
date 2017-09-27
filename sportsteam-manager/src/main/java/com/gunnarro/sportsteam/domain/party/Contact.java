package com.gunnarro.sportsteam.domain.party;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.StringUtils;

import com.gunnarro.sportsteam.domain.BaseDomain;
import com.gunnarro.sportsteam.domain.Club;
import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.activity.Status;
import com.gunnarro.sportsteam.domain.activity.Type;
import com.gunnarro.sportsteam.domain.party.Role.RoleTypesEnum;
import com.gunnarro.sportsteam.domain.view.list.Item;

public class Contact extends BaseDomain {

    private static final long serialVersionUID = -7277610828595783286L;

    public enum GenderEnum {
        MALE, FEMALE;

        public static String[] asArray() {
            return new String[] { GenderEnum.MALE.name(), GenderEnum.FEMALE.name() };
        }
    };

    public enum StatusEnum {
        ACTIVE(1), PASSIVE(2), INJURED(3), QUIT(4);
        int id;

        StatusEnum(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    private Club club;
    private Team team;
    private List<Type> teamRoles = new ArrayList<Type>();
    private String[] teamRolesStr;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender = GenderEnum.MALE.name();
    private String mobileNumber;
    private String emailAddress;
    private Person personalia;
    private Address address;
    private Status status = Status.createActiveStatus();
    private List<Item> relationItemList;

    public Contact() {
        super(null);
    }

    public Contact(Integer id) {
        super(id);
    }

    public Contact(String firstName, String middleName, String lastName) {
        this.firstName = firstName != null ? firstName.toUpperCase() : null;
        this.middleName = middleName != null ? middleName.toUpperCase() : null;
        this.lastName = lastName != null ? lastName.toUpperCase() : null;
    }

    public Contact(Integer id, Team team, String firstName, String middleName, String lastName, String gender) {
        super(id);
        this.team = team;
        this.firstName = firstName != null ? firstName.toUpperCase() : null;
        this.middleName = middleName != null ? middleName.toUpperCase() : null;
        this.lastName = lastName != null ? lastName.toUpperCase() : null;
        this.gender = gender;
    }

    public Contact(Team team, String firstName, String middleName, String lastName, String gender, Address address) {
        this(null, team, firstName, middleName, lastName, gender);
        this.address = address;
    }

    // only for test
    @Deprecated
    public Contact(Team team, List<Type> teamRoles, String firstName, String middleName, String lastName, String gender, String mobileNumber,
            String emailAddress, Address address) {
        this.team = team;
        this.teamRoles = teamRoles;
        this.firstName = firstName.toUpperCase();
        this.middleName = middleName != null ? middleName.toUpperCase() : null;
        this.lastName = lastName.toUpperCase();
        this.gender = gender;
        this.mobileNumber = mobileNumber;
        this.emailAddress = emailAddress;
        this.address = address;
    }

    public Person getPersonalia() {
        return personalia;
    }

    public void setPersonalia(Person personalia) {
        this.personalia = personalia;
    }

    public String[] getTeamRolesStr() {
        return teamRolesStr;
    }

    public void setTeamRolesStr(String[] teamRolesStr) {
        this.teamRolesStr = teamRolesStr;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<Type> getTeamRoleList() {
        return teamRoles;
    }

    public void setTeamRoleList(List<Type> teamRoles) {
        this.teamRoles = teamRoles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Hack for support BeanPropertyRowMapper
     * 
     * @return
     */
    public void setMobile(String mobile) {
        this.mobileNumber = mobile;
    }

    /**
     * Hack for support BeanPropertyRowMapper
     * 
     * @return
     */
    public String getMobile() {
        return this.mobileNumber;
    }

    /**
     * Hack for support BeanPropertyRowMapper
     * 
     * @return
     */
    public void setEmail(String email) {
        this.emailAddress = email;
    }

    /**
     * Hack for support BeanPropertyRowMapper
     * 
     * @return
     */
    public String getEmail() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFullName() {
        String fullName = getFirstName() + " " + (middleName != null ? getMiddleName() + " " : "") + getLastName();
        return StringUtils.capitalize(fullName);
    }

    public String getShortName() {
        return getFirstName() + " " + (middleName != null ? getMiddleName().substring(0, 1) + " " : "") + getLastName().substring(0, 1);
    }

    public String getNameAbbreviation() {
        return getFirstName().substring(0, 1) + (middleName != null ? getMiddleName().substring(0, 1) : "") + getLastName().substring(0, 1);
    }

    public boolean hasEmailAddress() {
        return StringUtils.isEmpty(emailAddress);
    }

    public boolean hasMobileNumber() {
        return StringUtils.isEmpty(mobileNumber);
    }

    public boolean hasTeamRoles() {
        return teamRoles != null && !teamRoles.isEmpty() ? true : false;
    }

    public boolean isTeamRoles() {
        return teamRoles != null && !teamRoles.isEmpty() ? true : false;
    }

    public boolean isCoach() {
        return hasTeamRole(RoleTypesEnum.COACH.name());
    }

    public boolean isTeamLead() {
        return hasTeamRole(RoleTypesEnum.TEAMLEAD.name());
    }

    public boolean isMale() {
        return gender.equalsIgnoreCase(GenderEnum.MALE.name()) || gender.toUpperCase().startsWith("M");
    }

    public boolean isActive() {
        return status.getName().equalsIgnoreCase(StatusEnum.ACTIVE.name());
    }

    public List<Item> getRelationItemList() {
        return relationItemList;
    }

    private boolean hasTeamRole(String roleName) {
        if (hasTeamRoles()) {
            for (Type role : teamRoles) {
                if (role.getName().equalsIgnoreCase(roleName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<Type> mapNewTeamRolesNameToTypeList(String[] teamRoleNames) {
        List<Type> list = new ArrayList<Type>();
        if (teamRoleNames != null) {
            for (String roleName : teamRoleNames) {
                list.add(new Type(roleName));
            }
        }
        return list;
    }

    public void setRelationItemList(List<Item> relationItemList) {
        this.relationItemList = relationItemList;
    }

    public static Contact createContact(String fullName) {
        String[] split = fullName.split(" ");
        Contact contact = null;
        if (split.length == 3) {
            contact = new Contact(split[0], split[2], split[2]);
        } else if (split.length == 2) {
            contact = new Contact(split[0], null, split[1]);
        }
        return contact;
    }

    @Override
    public String toString() {
        return "Contact [FkClubId=" + getFkClubId() + ", FkTeamId=" + getFkTeamId() + ", teamRoles=" + teamRoles + ", teamRolesStr="
                + Arrays.toString(teamRolesStr) + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName + ", gender=" + gender
                + ", mobileNumber=" + mobileNumber + ", emailAddress=" + emailAddress + ", address=" + address + ", status=" + status + "]";
    }

}

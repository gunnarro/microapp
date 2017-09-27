package com.gunnarro.calendar.domain.party;

import org.springframework.util.StringUtils;

import com.gunnarro.calendar.domain.BaseDomain;

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

    private String[] teamRolesStr;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender = GenderEnum.MALE.name();
    private String mobileNumber;
    private String emailAddress;
    private Person personalia;
    private Address address;

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

    public Contact(Integer id, String firstName, String middleName, String lastName, String gender) {
        super(id);
        this.firstName = firstName != null ? firstName.toUpperCase() : null;
        this.middleName = middleName != null ? middleName.toUpperCase() : null;
        this.lastName = lastName != null ? lastName.toUpperCase() : null;
        this.gender = gender;
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



    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

   
}

package com.gunnarro.dietmanager.domain.togetherness;

import java.util.Date;

import org.springframework.util.StringUtils;

public class Person {

    private static final long serialVersionUID = -3520608827932011225L;

    public enum GenderEnum {
        MALE, FEMALE;
    }

    private Integer id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender = GenderEnum.MALE.name();
    private String mobileNumber;
    private String emailAddress;
    private Date dateOfBirth;
    private Address address;

    public Person() {
    }

    public Person(Integer id, String firstName, String lastName, String mobileNumber, String emailAddress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.emailAddress = emailAddress;
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

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = new Date(dateOfBirth.getTime());
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

}

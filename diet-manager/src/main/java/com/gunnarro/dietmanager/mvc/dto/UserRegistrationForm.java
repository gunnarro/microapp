package com.gunnarro.dietmanager.mvc.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.gunnarro.dietmanager.endpoint.rest.RestResponse;

@JsonInclude(Include.NON_NULL)
public class UserRegistrationForm extends RestResponse implements Serializable {

    private static final long serialVersionUID = 8537329147398084965L;

    private String userId;
    private String firstName;
    private String lastName;
    private String phoneno;
    private String email;
    private String password;
    private String socialProvider;

    public UserRegistrationForm() {
    }

    public UserRegistrationForm(final String userId, final String firstName, final String lastName, final String phoneno, final String email,
            final String password, final String socialProvider) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneno = phoneno;
        this.email = email;
        this.password = password;
        this.socialProvider = socialProvider;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSocialProvider() {
        return socialProvider;
    }

    public void setSocialProvider(String socialProvider) {
        this.socialProvider = socialProvider;
    }

    @Override
    public String toString() {
        return "UserRegistrationForm [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", phoneno=" + phoneno + ", email=" + email
                + ", password=" + password + ", socialProvider=" + socialProvider + "]";
    }

}
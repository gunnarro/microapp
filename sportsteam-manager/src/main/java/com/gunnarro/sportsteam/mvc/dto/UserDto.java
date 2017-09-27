package com.gunnarro.sportsteam.mvc.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.gunnarro.sportsteam.mvc.validator.PasswordMatcher;
import com.gunnarro.sportsteam.mvc.validator.ValidEmail;
import com.gunnarro.sportsteam.mvc.validator.ValidPassword;
import com.gunnarro.sportsteam.mvc.validator.ValidUserName;

@PasswordMatcher
public class UserDto {

	@ValidUserName
    private String userName;

    @ValidPassword
    private String password;
    private String passwordRepeat;

    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;
    
    boolean activated = true;

    /**
     * Default constructor
     */
    public UserDto() {
    }

    /**
     * 
     * @param userName
     * @param password
     * @param matchingPassword
     * @param email
     */
    public UserDto(String userName, String password, String passwordRepeat, String email) {
        super();
        this.userName = userName;
        this.password = password;
        this.passwordRepeat = passwordRepeat;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
    
    

}

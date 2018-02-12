package com.gunnarro.dietmanager.mvc.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.gunnarro.useraccount.domain.user.UserLog;
import com.gunnarro.useraccount.repository.table.user.RolesTable.RolesEnum;

//@PasswordMatcher
public class UserDto implements Serializable {

    private static final long serialVersionUID = 7118036883910268055L;

    private Integer id;
    private Date createdDate;
    private Date lastModifiedDate;
    // @ValidUserName
    private String username;
    // @ValidPassword
    private String password;
    private String passwordRepeat;
    private String socialProvider;
    @NotNull
    // @NotEmpty
    // @ValidEmail
    private String email = "";

    boolean activated = true;

    boolean adminRole = false;
    boolean userRole = false;

    private UserLog userLog;

    /**
     * Default constructor
     */
    public UserDto() {
    }

    public UserDto(Integer id, String username) {
        this.id = id;
        this.username = username;
    }

    /**
     * 
     * @param userName
     * @param password
     * @param matchingPassword
     * @param email
     */
    public UserDto(String username, String password, String passwordRepeat, String email) {
        super();
        this.username = username;
        this.password = password;
        this.passwordRepeat = passwordRepeat;
        this.email = email;
    }

    public boolean isNew() {
        return id == null ? true : false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getSocialProvider() {
        return socialProvider;
    }

    public void setSocialProvider(String socialProvider) {
        this.socialProvider = socialProvider;
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

    public boolean isAdminRole() {
        return adminRole;
    }

    public void setAdminRole(boolean adminRole) {
        this.adminRole = adminRole;
    }

    public boolean isUserRole() {
        return userRole;
    }

    public boolean isGuestRole() {
        return !isAdminRole() && !isUserRole();
    }

    public void setUserRole(boolean userRole) {
        this.userRole = userRole;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public void setRoles(List<String> roles) {
        if (roles != null && !roles.isEmpty()) {
            if (roles.contains(RolesEnum.ROLE_USER.name())) {
                setUserRole(true);
            }
            if (roles.contains(RolesEnum.ROLE_ADMIN.name())) {
                setAdminRole(true);
            }
        }
    }

    public List<String> getRoles() {
        List<String> roles = new ArrayList<String>();
        if (isUserRole()) {
            roles.add(RolesEnum.ROLE_USER.name());
        }
        if (isAdminRole()) {
            roles.add(RolesEnum.ROLE_ADMIN.name());
        }
        return roles;
    }

    public UserLog getUserLog() {
        return userLog;
    }

    public void setUserLog(UserLog userLog) {
        this.userLog = userLog;
    }

    @Override
    public String toString() {
        return "UserDto [id=" + id + ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + ", username=" + username + ", password="
                + password + ", passwordRepeat=" + passwordRepeat + ", email=" + email + ", activated=" + activated + ", roles=" + getRoles() + "]";
    }

}

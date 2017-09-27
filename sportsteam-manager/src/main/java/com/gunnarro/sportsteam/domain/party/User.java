package com.gunnarro.sportsteam.domain.party;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import com.gunnarro.sportsteam.domain.BaseDomain;
import com.gunnarro.sportsteam.service.exception.ApplicationException;

public class User extends BaseDomain {

    private static final long serialVersionUID = -3112437958212912495L;

    private String userName;
    private String password;
    private String passwordRepeat;
    private String email;
    private List<String> roles;
    boolean activated = true;
    boolean enabled = true;
    boolean accountNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean accountNonLocked = true;

    public User() {
    }

    public User(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    public boolean isAdmin() {
        return roles != null && roles.size() == 1 && "ROLE_ADMIN".equals(roles.get(0));
    }

    public boolean isUser() {
        return roles != null && roles.size() == 1 && "ROLE_USER".equals(roles.get(0));
    }

    public boolean isGuest() {
        return roles != null && roles.size() == 1 && "ROLE_GUEST".equals(roles.get(0));
    }

    public void checkPasword() {
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(passwordRepeat)) {
            throw new ApplicationException("Password or password repeat not set!");
        }
        if (!password.equals(passwordRepeat)) {
            throw new ApplicationException("Password missmatch!");
        }
    }

    @Override
    public String toString() {
        return "User [userName=" + userName + ", password=" + getPassword() + ", email=" + email + ", roles=" + roles + ", activated=" + activated + ", enabled=" + enabled
                + ", accountNonExpired=" + accountNonExpired + ", credentialsNonExpired=" + credentialsNonExpired + ", accountNonLocked=" + accountNonLocked
                + "]";
    }

}

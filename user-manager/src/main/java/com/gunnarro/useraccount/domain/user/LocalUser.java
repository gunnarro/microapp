package com.gunnarro.useraccount.domain.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.gunnarro.useraccount.repository.table.user.RolesTable.RolesEnum;
import com.gunnarro.useraccount.service.exception.ApplicationException;

/**
 * Holds user login details.
 * 
 * @see Role
 * @see Privilege
 */
public class LocalUser implements UserDetails, Serializable {

    private static final long serialVersionUID = -3112437958212912495L;

    private Integer id;
    private Date createdDate;
    private Date lastModifiedDate;
    private String email;
    private String password;
    private String passwordRepeat;
    private String username;
    private String userId;
    boolean accountNonExpired = true;
    boolean accountNonLocked = true;
    boolean activated = true;
    boolean credentialsNonExpired = true;
    boolean enabled = true;
    private List<Role> roles;
    private String socialProvider;

    /**
     * default constructor
     */
    public LocalUser() {
    }

    /**
     * for unit testing only
     * 
     * @param id
     */
    public LocalUser(Integer id) {
        this.id = id;
    }

    public LocalUser(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        for (String privilege : getPrivileges(roles)) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
    
    private List<String> getPrivileges(Collection<Role> roles) {
        List<String> privileges = new ArrayList<String>();
        List<Privilege> collection = new ArrayList<Privilege>();
        for (Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public String getEmail() {
        return email;
    }

    public Integer getId() {
        return id;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public String getSocialProvider() {
        return socialProvider;
    }

    public void setSocialProvider(String socialProvider) {
        this.socialProvider = socialProvider;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public String getUsername() {
        return username;
    }

    
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public boolean isActivated() {
        return activated;
    }

    public boolean isAdmin() {
        return roles != null && roles.size() == 1 && RolesEnum.ROLE_ADMIN.name().equals(roles.get(0).getName());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public boolean isGuest() {
        return roles != null && roles.size() == 1 && RolesEnum.ROLE_GUEST.name().equals(roles.get(0).getName());
    }

    public boolean isNew() {
        return id == null ? true : false;
    }

    public boolean isUser() {
        return roles != null && roles.size() == 1 && RolesEnum.ROLE_USER.name().equals(roles.get(0).getName());
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LocalUser other = (LocalUser) obj;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return "LocalUser [createdDate=" + createdDate + ", email=" + email + ", id=" + id + ", lastModifiedDate=" + lastModifiedDate + ", password=" + password
                + ", passwordRepeat=" + passwordRepeat + ", roles=" + roles + ", username=" + username + ", userId=" + userId + ", accountNonExpired=" + accountNonExpired
                + ", accountNonLocked=" + accountNonLocked + ", activated=" + activated + ", credentialsNonExpired=" + credentialsNonExpired + ", enabled=" + enabled + "]";
    }

}

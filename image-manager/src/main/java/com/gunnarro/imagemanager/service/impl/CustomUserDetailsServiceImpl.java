package com.gunnarro.imagemanager.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gunnarro.imagemanager.domain.party.User;
import com.gunnarro.imagemanager.repository.ImageManagerRepository;

/**
 * Custom implementation of spring security userDetailsService. Used by spring
 * security
 *
 * @author admin
 *
 */
@Service
@Transactional
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomUserDetailsServiceImpl.class);

    @Autowired
    @Qualifier("imagemanagerRepository")
    private ImageManagerRepository imagemanagerRepository;

    @Autowired
    private LoginAttemptService loginAttemptService;

    // @Autowired
    // private HttpServletRequest request;

    /**
     * default constructor
     */
    public CustomUserDetailsServiceImpl() {
        super();
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(final String userName) {
        // final String ip = request.getRemoteAddr();
        // if (loginAttemptService.isBlocked(ip)) {
        // throw new
        // ApplicationException("Blocked, exceeded number of attempts!");
        // }

        if (LOG.isDebugEnabled()) {
            LOG.debug("get username: " + userName);
        }

        User user = imagemanagerRepository.getUser(userName);
        if (user == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("User not found!" + userName);
            }
            throw new UsernameNotFoundException("User not found!");
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("return user: " + user.toString());
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(),
                user.isCredentialsNonExpired(), user.isAccountNonLocked(), user.getAuthorities());
    }

    // public final Collection<? extends GrantedAuthority> getAuthorities(final
    // Collection<Role> roles) {
    // return getGrantedAuthorities(getPrivileges(roles));
    // }
    //
    // private final List<String> getPrivileges(final Collection<Role> roles) {
    // final List<String> privileges = new ArrayList<String>();
    // final List<Privilege> collection = new ArrayList<Privilege>();
    // for (final Role role : roles) {
    // collection.addAll(role.getPrivileges());
    // }
    // for (final Privilege item : collection) {
    // privileges.add(item.getName());
    // }
    // return privileges;
    // }
    //
    // private final List<GrantedAuthority> getGrantedAuthorities(final
    // List<String> privileges) {
    // final List<GrantedAuthority> authorities = new
    // ArrayList<GrantedAuthority>();
    // for (final String privilege : privileges) {
    // authorities.add(new SimpleGrantedAuthority(privilege));
    // }
    // return authorities;
    // }

   
}

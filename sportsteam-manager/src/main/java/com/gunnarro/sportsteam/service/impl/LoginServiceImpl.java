package com.gunnarro.sportsteam.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gunnarro.sportsteam.domain.party.User;
import com.gunnarro.sportsteam.repository.SportsTeamRepository;
import com.gunnarro.sportsteam.service.LoginService;

/**
 * NOT IN USE
 * 
 * @author admin
 *
 */
@Deprecated
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    @Qualifier("sportsTeamRepository")
    private SportsTeamRepository sportsTeamRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String userName) {
        
        User user = sportsTeamRepository.getUser(userName);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        // #org.springframework.security.core.userdetails.User should be
        // returned from this method
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
    }

    @Override
    public boolean login(String userName, String password) {
        UserDetails user = loadUserByUsername(userName);
        if (password.equals(user.getPassword())) {
            return true;
        }
        return false;
    }

    @Override
    public void logout(String userName) {
        // TODO Auto-generated method stub
    }
}

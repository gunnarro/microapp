package com.gunnarro.dietmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import com.gunnarro.dietmanager.config.DefaultTestConfig;
import com.gunnarro.dietmanager.config.TestMariDBDataSourceConfiguration;
import com.gunnarro.dietmanager.service.impl.LocalUserDetailsServiceImpl;

@ContextConfiguration(classes = { LocalUserDetailsServiceImpl.class, TestMariDBDataSourceConfiguration.class })
@Rollback
// @Ignore
public class LocalUserDetailsServiceTest extends DefaultTestConfig {

    @Autowired
    protected UserDetailsService userService;

    // @Mock
    // private SportsTeamRepository sportsTeamRepositoryMock;

    @Test
    public void loadUserByUsername() {
        // User user = new User("admin", "pwd", "my@mail.no");
        // when(sportsTeamRepositoryMock.getUser("admin")).thenReturn(user);
        UserDetails userD = userService.loadUserByUsername("admin");
        assertEquals("admin", userD.getUsername());
        assertNotNull(userD.getPassword());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameNotFound() {
        // when(sportsTeamRepositoryMock.getUser("usernotreg")).thenReturn(null);
        userService.loadUserByUsername("usernotreg");
    }

    @Before
    public void setUp() throws Exception {
        // MockitoAnnotations.initMocks(this);
        // userService.setSportsTeamRepository(sportsTeamRepositoryMock);
    }

}

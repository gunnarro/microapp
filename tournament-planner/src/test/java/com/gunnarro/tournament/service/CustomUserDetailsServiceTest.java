package com.gunnarro.dietmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/test-spring.xml" })
@TransactionConfiguration(defaultRollback = true)
//@Ignore
public class CustomUserDetailsServiceTest {

    @Autowired
    @Qualifier("customUserDetailsService")
    protected UserDetailsService userService;

//    @Mock
//    private SportsTeamRepository sportsTeamRepositoryMock;

    @Before
    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//        userService.setSportsTeamRepository(sportsTeamRepositoryMock);
    }

    @Ignore
    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameNotFound() {
      //  when(sportsTeamRepositoryMock.getUser("usernotreg")).thenReturn(null);
        userService.loadUserByUsername("usernotreg");
    }

    @Ignore
    @Test
    public void loadUserByUsername() {
//        User user = new User("admin", "pwd", "my@mail.no");
      //  when(sportsTeamRepositoryMock.getUser("admin")).thenReturn(user);
        UserDetails userD = userService.loadUserByUsername("admin");
        assertEquals("admin", userD.getUsername());
        assertNotNull(userD.getPassword());
    }

}

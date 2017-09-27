package com.gunnarro.dietmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.social.security.SocialAuthenticationException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/test-spring.xml" })
@TransactionConfiguration(defaultRollback = true)
//@Ignore
public class CustomSocialUserDetailsServiceTest {

    @Autowired
    @Qualifier("socialUserDetailsService")
    protected SocialUserDetailsService socialUserDetailsService;

//    @Mock
//    private SportsTeamRepository sportsTeamRepositoryMock;

    @Test
    public void loadUserByUserId() {
//        User user = new User("admin", "pwd", "my@mail.no");
      //  when(sportsTeamRepositoryMock.getUser("admin")).thenReturn(user);
    	SocialUserDetails user = socialUserDetailsService.loadUserByUserId("admin");
        assertEquals("admin", user.getUsername());
        assertNotNull(user.getPassword());
    }

    @Test(expected = SocialAuthenticationException.class)
    public void loadUserByUserIdNotFound() {
      //  when(sportsTeamRepositoryMock.getUser("usernotreg")).thenReturn(null);
    	socialUserDetailsService.loadUserByUserId("usernotreg");
    }

    @Before
    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//        userService.setSportsTeamRepository(sportsTeamRepositoryMock);
    }

}

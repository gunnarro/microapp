package com.gunnarro.dietmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gunnarro.dietmanager.mvc.dto.SocialProvider;
import com.gunnarro.dietmanager.mvc.dto.UserRegistrationForm;
import com.gunnarro.useraccount.domain.user.LocalUser;
import com.gunnarro.useraccount.repository.UserAccountRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/test-spring.xml" })
// @Ignore
public class RegistrationUserDetailsServiceTest {

    @Autowired
    @Qualifier("registrationUserDetailsService")
    protected UserService registrationUserDetailsService;

    @Mock
    private UserAccountRepository userAccountRepositoryMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Ignore
    @Test
    public void registerNewUser() {
        UserRegistrationForm userForm = new UserRegistrationForm();
        userForm.setUserId("userId");
        userForm.setEmail("email@gmail.com");
        userForm.setPassword("password");
        userForm.setFirstName("firstname");
        userForm.setLastName("lastname");
        userForm.setPhoneno("22334455");
        userForm.setSocialProvider(SocialProvider.FACEBOOK.name());
        LocalUser localUser = new LocalUser();
        when(userAccountRepositoryMock.getUser("userId")).thenReturn(localUser);
        UserDetails newLocalUser = registrationUserDetailsService.registerNewUser(userForm);
        assertEquals("admin", newLocalUser.getUsername());
        assertNotNull(newLocalUser.getPassword());
    }

}

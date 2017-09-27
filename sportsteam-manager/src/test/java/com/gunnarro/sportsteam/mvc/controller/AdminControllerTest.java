package com.gunnarro.sportsteam.mvc.controller;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.sportsteam.domain.party.User;
import com.gunnarro.sportsteam.mvc.dto.UserDto;
import com.gunnarro.sportsteam.service.SportsTeamService;
import com.gunnarro.sportsteam.service.exception.ApplicationException;


public class AdminControllerTest extends SpringTestSetup {

    @Mock
    private SportsTeamService sportsTeamServiceMock;

    private AdminController controller;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        controller = new AdminController();
        controller.setSportsTeamService(sportsTeamServiceMock);
    }

    @Test
    public void adminView() throws Exception {
        ModelAndView modelAndView = controller.adminView();
        Assert.assertEquals("secured/admin/admin-view", modelAndView.getViewName());
    }
    
    @Test
    public void deleteData() throws Exception {
        ModelAndView modelAndView = controller.deleteData();
        Assert.assertEquals("secured/admin/admin-view", modelAndView.getViewName());
    }

  
  @Test
  public void listUserAccounts() throws Exception {
      when(sportsTeamServiceMock.getUsers()).thenReturn(Arrays.asList(new User()));
      ModelAndView modelAndView = controller.listUserAccounts();
      Assert.assertEquals("secured/admin/account/list-user-accounts", modelAndView.getViewName());
      Assert.assertNotNull(modelAndView.getModel());
      List<User> list = (List<User>) modelAndView.getModel().get("userList");
      Assert.assertNotNull(list);
      Assert.assertEquals(1, list.size());
  }

  @Test
  public void viewUser() {
      int userId = 2;
      when(sportsTeamServiceMock.getUser(userId)).thenReturn(new User("test-user", "pwd", "my@mail.com"));
      ModelAndView modelAndView = controller.viewUserAccount(userId);
      Assert.assertEquals("secured/admin/account/view-user-account", modelAndView.getViewName());
      UserDto user = (UserDto) modelAndView.getModel().get("user");
      Assert.assertNotNull(user);
      Assert.assertEquals("test-user", user.getUserName());
      Assert.assertNull(user.getPassword());
      Assert.assertNull(user.getPasswordRepeat());
      Assert.assertEquals("my@mail.com", user.getEmail());
  }


  @Test(expected = ApplicationException.class)
  public void viewUserError() {
      int userId = 22;
      when(sportsTeamServiceMock.getUser(userId)).thenReturn(null);
      controller.viewUserAccount(222);
  }
    
}
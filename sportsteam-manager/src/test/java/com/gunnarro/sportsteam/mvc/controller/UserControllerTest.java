package com.gunnarro.sportsteam.mvc.controller;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.gunnarro.sportsteam.service.SportsTeamService;

public class UserControllerTest extends SpringTestSetup {

    @Mock
    private SportsTeamService sportsTeamServiceMock;

    private UserController controller;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        controller = new UserController();
        controller.setSportsTeamService(sportsTeamServiceMock);
    }

//    @Test
//    public void listUsers() throws Exception {
//        when(sportsTeamServiceMock.getUsers()).thenReturn(Arrays.asList(new User()));
//        ModelAndView modelAndView = controller.listUsers();
//        Assert.assertEquals("user/list-users", modelAndView.getViewName());
//        Assert.assertNotNull(modelAndView.getModel());
//        List<User> list = (List<User>) modelAndView.getModel().get("userList");
//        Assert.assertNotNull(list);
//        Assert.assertEquals(1, list.size());
//    }
//
//    @Test
//    public void viewUser() {
//        int userId = 2;
//        when(sportsTeamServiceMock.getUser(userId)).thenReturn(new User("test-user", "pwd"));
//        ModelAndView modelAndView = controller.viewUser(userId);
//        Assert.assertEquals("user/view-user", modelAndView.getViewName());
//        User user = (User) modelAndView.getModel().get("user");
//        Assert.assertNotNull(user);
//    }
//
//
//    @Test(expected = ApplicationException.class)
//    public void viewUserError() {
//        int userId = 22;
//        when(sportsTeamServiceMock.getUser(userId)).thenReturn(null);
//        controller.viewUser(222);
//    }

}
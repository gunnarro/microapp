package com.gunnarro.sportsteam.mvc.controller;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.gunnarro.sportsteam.service.SportsTeamService;

public class SMSControllerTest extends SpringTestSetup {

    @Mock
    private SportsTeamService sportsTeamServiceMock;

    private SmsController controller;
    
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        controller = new SmsController();
        controller.setSportsTeamService(sportsTeamServiceMock);
    }


}
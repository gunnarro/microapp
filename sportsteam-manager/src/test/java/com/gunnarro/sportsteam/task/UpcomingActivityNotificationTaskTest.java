package com.gunnarro.sportsteam.task;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.gunnarro.sportsteam.domain.Club;
import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.task.ScheduledTask;
import com.gunnarro.sportsteam.service.MessageService;
import com.gunnarro.sportsteam.service.SportsTeamService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/test-spring.xml" })
@TransactionConfiguration(defaultRollback = true)
public class UpcomingActivityNotificationTaskTest {

    private UpcomingActivityNotificationTask task;
    
    @Mock
    private SportsTeamService sportsTeamServiceMock;
    
    @Mock
    private MessageService messageServiceMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        task = new UpcomingActivityNotificationTask();
        task.setSportsTeamService(sportsTeamServiceMock);
        task.setMessageService(messageServiceMock);
    }

    @Test
    public void run() {
        Team team = new Team(23,"uil");
        team.setClub(new Club("Ullevål", "Bandy"));
        List<ScheduledTask> tasks = new ArrayList<ScheduledTask>();
        tasks.add(new ScheduledTask(23, "uil 2003 laget","UPCOMING_ACTIVITIES", true));
        when(sportsTeamServiceMock.getTeamScheduledTasks()).thenReturn(tasks);
        when(sportsTeamServiceMock.getTeam(23)).thenReturn(team);
        when(sportsTeamServiceMock.composeEmail(team)).thenReturn("email message....");
        when(messageServiceMock.sendEmail(null, "","")).thenReturn(true);
        task.run();
        assertTrue(true);
    }

}

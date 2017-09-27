package com.gunnarro.sportsteam.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.gunnarro.sportsteam.domain.statistic.Statistic;
import com.gunnarro.sportsteam.domain.view.ActivityView;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/test-spring.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
@Ignore
public class SportsTeamRepositoryViewTest {

    @Autowired
    @Qualifier("sportsTeamRepository")
    private SportsTeamRepository sportsTeamRepository;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void searchMatches() {
        assertNotNull(sportsTeamRepository.searchMatches(1, new Date(), new Date()));
    }

    @Test
    public void teamStatistic() {
        Statistic teamStatistic = sportsTeamRepository.getTeamStatistic(1, 1);
        assertNotNull(teamStatistic);
        assertEquals(0, teamStatistic.getMatchStatisticList().size());
        assertEquals(0, teamStatistic.getNumberOfTeamCups().intValue());
        assertEquals(0, teamStatistic.getNumberOfTeamMatches().intValue());
        assertEquals(0, teamStatistic.getNumberOfTeamTrainings().intValue());
    }

    @Test
    public void getTopPlayerStatisticList() {
        List<Statistic> playerStatisticList = sportsTeamRepository.getTopPlayerStatisticList(1, 1);
        assertNotNull(playerStatisticList);
        // assertTrue(playerStatisticList.size() > 0);
        // assertEquals(0,
        // playerStatisticList.get(0).getNumberOfPlayerCups().intValue());
        // assertEquals(0,
        // playerStatisticList.get(0).getNumberOfPlayerMatches().intValue());
        // assertEquals(0,
        // playerStatisticList.get(0).getNumberOfPlayerTrainings().intValue());
        // assertEquals(0,
        // playerStatisticList.get(0).getNumberOfTeamCups().intValue());
        // assertEquals(0,
        // playerStatisticList.get(0).getNumberOfTeamMatches().intValue());
        // assertEquals(0,
        // playerStatisticList.get(0).getNumberOfTeamTrainings().intValue());
    }

    @Test
    public void playerStatistic() {
        Statistic playerStatistic = sportsTeamRepository.getPlayerStatistic(1, 1, 1);
        assertNull(playerStatistic);
        // assertEquals(0, playerStatistic.getNumberOfPlayerCups().intValue());
        // assertEquals(0,
        // playerStatistic.getNumberOfPlayerMatches().intValue());
        // assertEquals(0,
        // playerStatistic.getNumberOfPlayerTrainings().intValue());
        // assertEquals(0, playerStatistic.getNumberOfTeamCups().intValue());
        // assertEquals(0, playerStatistic.getNumberOfTeamMatches().intValue());
        // assertEquals(0,
        // playerStatistic.getNumberOfTeamTrainings().intValue());
    }

    @Test
    public void getActivities() {
        List<ActivityView> activities = sportsTeamRepository.getActivitiesUpcomingWeek(1, 1);
        assertNotNull(activities);
    }
}

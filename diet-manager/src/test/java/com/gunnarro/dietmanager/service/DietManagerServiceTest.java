package com.gunnarro.dietmanager.service;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.gunnarro.dietmanager.config.TestDataSourceConfiguration;
import com.gunnarro.dietmanager.config.SecurityConfiguration;
import com.gunnarro.dietmanager.domain.diet.MenuItem;
import com.gunnarro.dietmanager.domain.statistic.Key;
import com.gunnarro.dietmanager.domain.statistic.KeyValuePair;
import com.gunnarro.dietmanager.domain.statistic.MealStatistic;
import com.gunnarro.dietmanager.endpoint.rest.ChartData;
import com.gunnarro.dietmanager.repository.impl.DietManagerRepositoryImpl;
import com.gunnarro.dietmanager.repository.impl.LogEventRepositoryImpl;
import com.gunnarro.dietmanager.service.impl.DietManagerServiceImpl;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={TestDataSourceConfiguration.class, SecurityConfiguration.class, DietManagerServiceImpl.class, DietManagerRepositoryImpl.class, LogEventRepositoryImpl.class})
@Transactional
@Rollback
public class DietManagerServiceTest {

    @Autowired
//    @Qualifier("dietManagerService")
    protected DietManagerService dietManagerService;

    @Before
    public void setUp() throws Exception {
        // Because of security we have to set user and pwd before every unit
        // test
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("admin", "uiL2oo3");
        SecurityContext ctx = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(ctx);
        ctx.setAuthentication(authRequest);
    }

    @After
    public void terminate() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void getBmiChartData() {
        List<ChartData> bmiChartData = dietManagerService.getBmiChartData(1);
        Assert.assertEquals(168, bmiChartData.size());
        // for (ChartData data : bmiChartData) {
        // System.out.println(data);
        // }
    }

    @Test
    public void getMyChoicesStatisticByWeek() {
        MenuItem menuItem = new MenuItem();
        menuItem.setFkDietMenuId(1);
        menuItem.setCreatedTime(System.currentTimeMillis());
        menuItem.setControlledByUserId(4);
        menuItem.setPreparedByUserId(4);
        menuItem.setControlledByUsername("pepilie");
        menuItem.setId(12);
        menuItem.setCausedConflict(0);
        dietManagerService.saveSelectedFoodForUser(4, menuItem);
        Map<String, List<KeyValuePair>> myChoicesStatistic = dietManagerService.getMyChoicesStatisticByWeek();
        Assert.assertTrue(myChoicesStatistic.size() == 1);
        // System.out.println(myChoicesStatistic);
    }

    @Ignore
    @Test
    public void getMealStatistic() {
        List<MealStatistic> mealStatsticList = dietManagerService.getMealStatsticForUsers(5, 30);
        Map<Key, List<MealStatistic>> mealStatisticByWeekNumberMap = DietManagerServiceImpl.mapMealStatisticByWeekNumber(mealStatsticList);
        for (Map.Entry<Key, List<MealStatistic>> entry : mealStatisticByWeekNumberMap.entrySet()) {
            System.out.println(entry.getKey());
            for (MealStatistic m : entry.getValue()) {
                System.out.println("   " + m);
            }
        }
        
        System.out.println("----------------------------- summary -------------------------------------");
        List<MealStatistic> sumMealStatisticByUserName = DietManagerServiceImpl.sumMealStatisticByUserName(mealStatsticList);
        for (MealStatistic m: sumMealStatisticByUserName) {
           System.out.println(m); 
           System.out.println(m.getFromDate() + " - " + m.getToDate()); 
        }
    }

}

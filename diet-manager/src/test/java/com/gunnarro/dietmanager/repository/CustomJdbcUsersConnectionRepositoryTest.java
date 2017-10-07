package com.gunnarro.dietmanager.repository;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.gunnarro.dietmanager.config.DataSourceConfiguration;
import com.gunnarro.dietmanager.repository.impl.CustomJdbcUsersConnectionRepository;
import com.gunnarro.dietmanager.repository.impl.DietManagerRepositoryImpl;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={DietManagerRepositoryImpl.class, DataSourceConfiguration.class})
@Transactional
@Rollback
public class CustomJdbcUsersConnectionRepositoryTest {

    @Autowired
//    @Qualifier("jdbcUsersConnectionRepository")
    private CustomJdbcUsersConnectionRepository jdbcUsersConnectionRepository;
    
    @Test
    public void findConnectedUsers() {
        Set<String> users = jdbcUsersConnectionRepository.findUserIdsConnectedTo("providerId", null);
        Assert.assertEquals(0, users.size());
    }
}

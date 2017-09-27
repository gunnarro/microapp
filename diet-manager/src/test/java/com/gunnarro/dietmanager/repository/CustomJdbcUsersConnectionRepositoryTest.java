package com.gunnarro.dietmanager.repository;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.gunnarro.dietmanager.repository.impl.CustomJdbcUsersConnectionRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/test-spring.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class CustomJdbcUsersConnectionRepositoryTest {

    @Autowired
    @Qualifier("jdbcUsersConnectionRepository")
    private CustomJdbcUsersConnectionRepository jdbcUsersConnectionRepository;
    
    @Test
    public void findConnectedUsers() {
        Set<String> users = jdbcUsersConnectionRepository.findUserIdsConnectedTo("providerId", null);
        Assert.assertEquals(0, users.size());
    }
}

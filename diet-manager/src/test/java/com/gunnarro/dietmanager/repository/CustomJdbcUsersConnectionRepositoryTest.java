package com.gunnarro.dietmanager.repository;

import java.util.Set;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.gunnarro.dietmanager.config.TestMariDBDataSourceConfiguration;
import com.gunnarro.dietmanager.config.TestRepositoryConfiguration;
import com.gunnarro.dietmanager.repository.impl.CustomJdbcUsersConnectionRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { TestMariDBDataSourceConfiguration.class, TestRepositoryConfiguration.class })
@Transactional
@Rollback
@TestPropertySource(locations = "classpath:test-application.properties")
@Ignore
public class CustomJdbcUsersConnectionRepositoryTest {

    @Autowired
    private CustomJdbcUsersConnectionRepository jdbcUsersConnectionRepository;

    @Test
    public void findConnectedUsers() {
        Set<String> users = jdbcUsersConnectionRepository.findUserIdsConnectedTo("providerId", null);
        Assert.assertEquals(0, users.size());
    }
}

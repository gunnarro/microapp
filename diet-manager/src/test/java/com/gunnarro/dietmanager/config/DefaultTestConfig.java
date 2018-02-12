package com.gunnarro.dietmanager.config;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:test-application.properties")
public class DefaultTestConfig {

    @Before
    public void init() {
    }

    @Test
    public void dummy() {
    }
}

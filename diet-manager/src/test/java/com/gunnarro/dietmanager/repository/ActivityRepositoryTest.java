package com.gunnarro.dietmanager.repository;

import static org.junit.Assert.assertFalse;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.gunnarro.dietmanager.config.DefaultTestConfig;
import com.gunnarro.dietmanager.config.TestCloudSqlDataSourceConfiguration;

@ContextConfiguration(classes = { TestCloudSqlDataSourceConfiguration.class})
//@Transactional
//@Rollback
@Ignore
public class ActivityRepositoryTest extends DefaultTestConfig {

    @Autowired
    private ActivityRepository activityRepository;

    @Before
    public void setUp() throws Exception {
    	System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", "/home/mentos/dietmanager/src/test/resources/cloudsql-microdb.json");

// service account: gvmobe7h25dxjk24dzvvlbrevi@speckle-umbrella-24.iam.gserviceaccount.com

    	
//    	GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(System.getProperty("GOOGLE_APPLICATION_CREDENTIALS")))
//    	        .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
//    	  Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

    }

    @After
    public void tearDown() throws Exception {
    }

//    private static String getAccessToken() throws IOException {
//    	  GoogleCredential googleCredential = GoogleCredential
//    	      .fromStream(new FileInputStream("service-account.json"))
//    	      .createScoped(Arrays.asList(SCOPES));
//    	  googleCredential.refreshToken();
//    	  return googleCredential.getAccessToken();
//    	}
    
    @Test
    public void hasPermission_access_denied() throws SQLException {
//    	 Connection connection = DriverManager.getConnection("jdbc:mysql://google/microdb?cloudSqlInstance=big-genre-214416:europe-north1:microdb&socketFactory=com.google.cloud.sql.mysql.SocketFactory", "root", "ABcd2o1o");
    	System.out.println(System.getProperty("GOOGLE_APPLICATION_CREDENTIALS"));
        assertFalse(activityRepository.hasPermission(200, "per"));
    }

//    @Test
//    public void hasPermission_access_ok() {
//        assertTrue(activityRepository.hasPermission(3, "pepilie"));
//    }
//
//    @Test
//    public void getActivityLogs() {
//        assertNotNull(activityRepository.getActivityLogs(1));
//    }
}

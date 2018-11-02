package com.gunnarro.dietmanager.utility;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.Enumeration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.gunnarro.dietmanager.service.exception.ApplicationException;


@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:test-application.properties")
@ContextConfiguration(classes = { })
//@EnableEncryptableProperties
public class ReadKeyStoreTest {

    @Autowired
    private Environment env;

    @Test
    public void readIdentityKeyStore() throws KeyStoreException {
        KeyStore keyStore = getKeyStore(env.getProperty("server.ssl.key-store"), env.getProperty("server.ssl.key-store-password"));
        Enumeration<String> aliases = keyStore.aliases();
//        while (aliases.hasMoreElements()) {
//            String alias = aliases.nextElement();
//            System.out.println("alias: " + alias);
//        }
        Assert.assertEquals("gunnarro",aliases.nextElement());
    }

    private KeyStore getKeyStore(String fileName, String pass) {
        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            KeyStore trustkeyStore = KeyStore.getInstance("jks");
            trustkeyStore.load(fileInputStream, pass.toCharArray());
            return trustkeyStore;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}

package com.gunnarro.imagemanager.utility;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UtilityTest {

    @Test
    public void validateInput() {
        assertTrue(Utility.validateValue("23:59", Utility.REGEXP_TIME_24));
        assertFalse(Utility.validateValue("2359", Utility.REGEXP_TIME_24));
        assertFalse(Utility.validateValue("23:5", Utility.REGEXP_TIME_24));
        assertTrue(Utility.validateValue("02:59", Utility.REGEXP_TIME_24));
        assertTrue(Utility.validateValue("00:00", Utility.REGEXP_TIME_24));
    }

    @Test
    public void encodePwd() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        String pwd = passwordEncoder.encode("guest");
        System.out.println("Encoded admin: " + passwordEncoder.encode("adm2016"));
        System.out.println("Encoded stro: " + passwordEncoder.encode("stro62"));
        System.out.println("Encoded siro: " + passwordEncoder.encode("siro63"));
        System.out.println("Encoded guro: " + passwordEncoder.encode("guro66"));
        System.out.println("Encoded: " + passwordEncoder.encode("guest"));
        System.out.println("match: " + passwordEncoder.matches("guest", pwd));
        System.out.println("match: " + passwordEncoder.matches("guest1", pwd));
    }
  }

//$2a$13$5jjhNCo.VNumJ2/XXJrm..kk.oIiktLGKrT3UN.Tpb3cAHxQ51crC
//$2a$13$wXB0SgINC3VP7/P7cZMjf.MhCXmiEhjdEVB5SXvWlThoFOfQrtzhe
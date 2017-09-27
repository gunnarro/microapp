package com.gunnarro.sportsteam.domain.party;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RefereeTest {

    @Test
    public void testConstructorNew() {
        Address address = new Address("streetname", "25", "c", "2323", "city", "country");
        Referee newReferee = new Referee("referee-firstname", "referee-middlename", "referee-lastname");
        newReferee.setMobileNumber("+471234567891");
        newReferee.setEmailAddress("referee@mail.com");
        newReferee.setAddress(address);
        assertTrue(newReferee.isNew());
        assertTrue(newReferee.isActive());
        assertEquals("Referee-firstname Referee-middlename Referee-lastname".toUpperCase(), newReferee.getFullName());
        assertEquals("MALE", newReferee.getGender());
        assertEquals("+471234567891", newReferee.getMobileNumber());
        assertEquals("referee@mail.com", newReferee.getEmailAddress());
        assertEquals("City".toUpperCase(), newReferee.getAddress().getCity());
        assertEquals("Country".toUpperCase(), newReferee.getAddress().getCountry());
        assertEquals("Streetname 25C".toUpperCase(), newReferee.getAddress().getFullStreetName());
        assertEquals("2323", newReferee.getAddress().getPostCode());
        assertTrue(newReferee.getAddress().isAddressValid());
    }

    @Test
    public void testConstructorId() {
        Referee referee = new Referee("referee-firstname", "referee-middlename", "referee-lastname");
        referee.setId(123);
        assertFalse(referee.isNew());
        assertEquals(123, referee.getId().intValue());
    }

    @Test
    public void testConstructorNull() {
        Referee referee = new Referee(null, null, null);
        assertNotNull(referee);
    }
}

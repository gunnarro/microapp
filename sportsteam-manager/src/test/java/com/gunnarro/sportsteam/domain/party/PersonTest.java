package com.gunnarro.sportsteam.domain.party;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

public class PersonTest {

    @Test
    public void testConstructorNew() {
        Address address = new Address("streetname", "25", "c", "1111", "city", "country");
        Date dateOfBirth = new Date();
        Person newPerson = new Person();
        newPerson.setFirstName("person first name");
        newPerson.setMiddleName("person middle name");
        newPerson.setLastName("person last name");
        newPerson.setEmailAddress("person@email.com");
        newPerson.setMobileNumber("99887766");
        newPerson.setDateOfBirth(dateOfBirth);
        newPerson.setAddress(address);

        assertEquals("Person first name person middle name person last name", newPerson.getFullName());
        assertEquals("person first name p p", newPerson.getShortName());
        assertEquals("person@email.com", newPerson.getEmailAddress());
        assertNotNull(newPerson.getDateOfBirth());
        assertEquals("99887766", newPerson.getMobileNumber());
        assertEquals("MALE", newPerson.getGender());
        assertEquals("City".toUpperCase(), newPerson.getAddress().getCity());
        assertEquals("Country".toUpperCase(), newPerson.getAddress().getCountry());
        assertEquals("Streetname 25C".toUpperCase(), newPerson.getAddress().getFullStreetName());
        assertEquals("1111", newPerson.getAddress().getPostCode());
        assertTrue(newPerson.getAddress().isAddressValid());
    }

}

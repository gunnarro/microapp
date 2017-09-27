package com.gunnarro.sportsteam.domain.party;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AddressTest {

    @Test
    public void testConstructorNew() {
        Address address = new Address("streetname", "25", "c", "2323", "city", "country");
        assertEquals("streetname".toUpperCase(), address.getStreetName());
        assertEquals("25", address.getStreetNumber());
        assertEquals("c".toUpperCase(), address.getStreetNumberPostfix());
        assertEquals("2323", address.getPostCode());
        assertEquals("city".toUpperCase(), address.getCity());
        assertEquals("country".toUpperCase(), address.getCountry());
        assertEquals("STREETNAME 25C, 2323 CITY COUNTRY".toUpperCase(), address.getFullAddress());
        assertEquals("STREETNAME 25C".toUpperCase(), address.getFullStreetName());
        assertTrue(address.isAddressValid());
        assertTrue(address.isNew());
    }

    @Test
    public void testConstructorNew2() {
        Address address = new Address("streetname", "25", null, "2323", "city", "country");
        address.setId(1);
        assertFalse(address.isNew());
        assertEquals("streetname".toUpperCase(), address.getStreetName());
        assertEquals("25", address.getStreetNumber());
        assertNull(address.getStreetNumberPostfix());
        assertEquals("2323", address.getPostCode());
        assertEquals("city".toUpperCase(), address.getCity());
        assertEquals("country".toUpperCase(), address.getCountry());
        assertEquals("STREETNAME 25, 2323 CITY COUNTRY".toUpperCase(), address.getFullAddress());
        assertEquals("STREETNAME 25".toUpperCase(), address.getFullStreetName());
        assertTrue(address.isAddressValid());
    }

    @Test
    public void testConstructorNull() {
        Address address = new Address(null, null, null, null, null, null);
        assertNull(address.getStreetName());
        assertNull(address.getStreetNumber());
        assertNull(address.getStreetNumberPostfix());
        assertNull(address.getPostCode());
        assertNull(address.getCity());
        assertNull(address.getCountry());
        assertNull(address.getFullStreetName());
        assertNull(address.getFullAddress());
    }

}

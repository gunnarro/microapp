package com.gunnarro.sportsteam.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

import com.gunnarro.sportsteam.domain.party.Address;

public class ClubTest {

    @Test
    public void testConstructorNew() {
        Address address = new Address("Stavangergt", "22", null, "9090", "oslo", "norge");
        Club club = new Club(null, "SportsClub", "Bandy", "CK", "Stadium", address, "http://club.homepage.org");
        assertTrue(club.isNew());
        assertTrue(club.hasName());
        assertEquals("SportsClub", club.getName());
        assertEquals("Bandy", club.getDepartmentName());
        assertEquals("CK", club.getClubNameAbbreviation());
        assertEquals("Stadium", club.getStadiumName());
        assertEquals("http://club.homepage.org", club.getHomePageUrl());
        assertEquals("Stavangergt 22".toUpperCase(), club.getAddress().getFullStreetName());
        assertEquals("9090", club.getAddress().getPostCode());
        assertEquals("Oslo".toUpperCase(), club.getAddress().getCity());
        assertEquals("Norge".toUpperCase(), club.getAddress().getCountry());
    }

    @Test
    public void isAddressNew() {
        Club club = new Club("name", "dep");
        assertFalse(club.hasAddress());
        assertFalse(club.isAddressNew());
        club.setAddress(new Address());
        assertTrue(club.hasAddress());
        assertTrue(club.isAddressNew());
        club.setAddress(new Address(2));
        assertTrue(club.hasAddress());
        assertFalse(club.isAddressNew());
    }

    @Test
    public void testConstructorId() {
        Club club = new Club(345, "name", "departmentName", "clubNameAbbreviation", "stadiumName", null, "homepageUrl");
        assertFalse(club.isNew());
        assertEquals(345, club.getId().intValue());
    }

    @Test
    public void testConstructorName() {
        Club club = new Club(null, "", "departmentName", "clubNameAbbreviation", "stadiumName", null, "homepageUrl");
        assertFalse(club.hasName());
        assertEquals("", club.getName());

        club = new Club(null, "x", "departmentName", "clubNameAbbreviation", "stadiumName", null, "homepageUrl");
        assertTrue(club.hasName());
        assertEquals("x", club.getName());

        club = new Club(null, null, "departmentName", "clubNameAbbreviation", "stadiumName", null, "homepageUrl");
        assertFalse(club.hasName());
        assertNull(club.getName());
    }

}

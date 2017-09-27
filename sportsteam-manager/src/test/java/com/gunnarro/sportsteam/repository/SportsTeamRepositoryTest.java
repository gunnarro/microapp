package com.gunnarro.sportsteam.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.gunnarro.sportsteam.domain.Club;
import com.gunnarro.sportsteam.domain.activity.Match;
import com.gunnarro.sportsteam.domain.party.Address;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/test-spring.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
@Ignore
public class SportsTeamRepositoryTest {

    @Autowired
    @Qualifier("sportsTeamRepository")
    private SportsTeamRepository sportsTeamRepository;

    @Ignore
    @Test
    public void createAddress() {
        Address address = new Address();
        address.setStreetName("stavangergata");
        address.setStreetNumber("34");
        address.setStreetNumberPostfix("d");
        address.setCity("oslo");
        address.setCountry("norway");
        address.setPostCode("0880");
        int id = sportsTeamRepository.createAddress(address);
        Assert.assertEquals(11, id);
    }

    @Ignore
    @Test
    public void createClub() {
        Club club = new Club();
        club.setName("Ullevål Idrettslag");
        club.setDepartmentName("bandy");
        club.setClubNameAbbreviation("UIL");
        club.setHomePageUrl("http://uil.no");

        Address address = new Address();
        address.setStreetName("stavangergata");
        address.setStreetNumber("34");
        address.setStreetNumberPostfix("d");
        address.setCity("oslo");
        address.setCountry("norway");
        address.setPostCode("0880");

        club.setAddress(address);
        int rows = sportsTeamRepository.createClub(club);
        Assert.assertEquals(1, rows);
    }

    @Ignore
    @Test
    public void deleteClubById() {
        int rows = sportsTeamRepository.deleteClub(7);
        Assert.assertEquals(0, rows);
    }

    @Ignore
    @Test
    public void updateClub() {
        Club club = new Club();
        club.setId(10);
        club.setName("Ullevål Idrettslag");
        club.setDepartmentName("bandy");
        club.setClubNameAbbreviation("UIL");
        club.setHomePageUrl("http://uil.no");
        int rows = sportsTeamRepository.updateClub(club);
        Assert.assertEquals(0, rows);
    }

    @Test
    public void findClubById() {
        Club club = sportsTeamRepository.getClub(1);
        Assert.assertNull(club);
    }

    @Test
    public void getMatchListForTeam() {
        List<Match> matchList = sportsTeamRepository.getMatchList(10, 2);
        Assert.assertNotNull(matchList);
        Assert.assertEquals(0, matchList.size());
    }

    @Test
    public void getAddressById() {
        Address address = sportsTeamRepository.getAddress(10);
        System.out.println(address);
        Assert.assertNull(address);
    }
}
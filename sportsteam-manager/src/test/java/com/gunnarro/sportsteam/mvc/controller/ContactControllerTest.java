package com.gunnarro.sportsteam.mvc.controller;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.activity.Type;
import com.gunnarro.sportsteam.domain.party.Contact;
import com.gunnarro.sportsteam.service.SportsTeamService;
import com.gunnarro.sportsteam.service.exception.ApplicationException;

public class ContactControllerTest extends SpringTestSetup {

    @Mock
    private SportsTeamService sportsTeamServiceMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void listContacts() throws Exception {
        int clubId = 1;
        int teamId = 1;
        ContactController controller = new ContactController();
        controller.setSportsTeamService(sportsTeamServiceMock);
        when(sportsTeamServiceMock.getContacts(clubId)).thenReturn(Arrays.asList(new Contact(1010)));
        when(sportsTeamServiceMock.getTeam(teamId)).thenReturn(new Team());
        ModelAndView modelAndView = controller.listContacts(clubId);
        Assert.assertEquals("contact/list-contacts", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        Assert.assertNotNull(modelAndView.getModel().get("team"));
        List<Contact> list = (List<Contact>) modelAndView.getModel().get("contactList");
        Assert.assertNotNull(list);
        Assert.assertEquals(1010, list.get(0).getId().intValue());
    }

    @Test
    public void viewContact() {
        int contactId = 11;
        ContactController controller = new ContactController();
        controller.setSportsTeamService(sportsTeamServiceMock);
        when(sportsTeamServiceMock.getContact(contactId)).thenReturn(new Contact(contactId));
        ModelAndView modelAndView = controller.viewContact(contactId);
        Assert.assertEquals("contact/view-contact", modelAndView.getViewName());
        Contact contact = (Contact) modelAndView.getModel().get("contact");
        Assert.assertNotNull(contact);
        Assert.assertEquals(11, contact.getId().intValue());
    }

    @Test
    public void newContact() {
        int teamId = 1;
        Team team = new Team();
        team.setId(teamId);
        ContactController controller = new ContactController();
        controller.setSportsTeamService(sportsTeamServiceMock);
        when(sportsTeamServiceMock.getTeam(teamId)).thenReturn(team);
        when(sportsTeamServiceMock.getTeamRoleTypes()).thenReturn(Arrays.asList(new Type()));
        String url = controller.initNewContactForm(teamId, new HashMap<String, Object>());
        Assert.assertEquals("contact/edit-contact", url);
    }

    @Test(expected=ApplicationException.class)
    public void newContactError() {
        int teamId = 1;
        Team team = new Team();
        team.setId(teamId);
        ContactController controller = new ContactController();
        controller.setSportsTeamService(sportsTeamServiceMock);
        controller.initNewContactForm(teamId, new HashMap<String, Object>());
    }

    @Ignore
    @Test
    public void updateContact() {
        int teamId = 1;
        int contactId = 11;
        Contact contact = new Contact(contactId);
        contact.setFkTeamId(teamId);
        ContactController controller = new ContactController();
        controller.setSportsTeamService(sportsTeamServiceMock);
        when(sportsTeamServiceMock.getContact(contactId)).thenReturn(contact);
        String url = controller.initUpdateContactForm(contactId, null);
        Assert.assertEquals("contact/edit-contact", url);
    }

    @Test
    public void deleteContact() {
        int contactId = 11;
        int teamId = 1;
        Contact contact = new Contact(contactId);
        contact.setFkTeamId(teamId);
        ContactController controller = new ContactController();
        controller.setSportsTeamService(sportsTeamServiceMock);
        when(sportsTeamServiceMock.getContact(contactId)).thenReturn(contact);
        when(sportsTeamServiceMock.deleteContact(contactId)).thenReturn(1);
        String url = controller.deleteContact(contactId);
        Assert.assertEquals("redirect:/listcontacts/" + teamId, url);
    }

    @Test(expected=ApplicationException.class)
    public void deleteContactError() {
        int contactId = 11;
        int teamId = 1;
        Contact contact = new Contact(contactId);
        contact.setFkTeamId(teamId);
        ContactController controller = new ContactController();
        controller.setSportsTeamService(sportsTeamServiceMock);
        when(sportsTeamServiceMock.deleteContact(contactId)).thenReturn(1);
        controller.deleteContact(contactId);
    }
}
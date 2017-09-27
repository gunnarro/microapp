package com.gunnarro.sportsteam.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gunnarro.sportsteam.domain.Club;
import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.activity.Cup;
import com.gunnarro.sportsteam.domain.activity.Match;
import com.gunnarro.sportsteam.domain.activity.Season;
import com.gunnarro.sportsteam.domain.activity.Status;
import com.gunnarro.sportsteam.domain.activity.Training;
import com.gunnarro.sportsteam.domain.activity.Type;
import com.gunnarro.sportsteam.domain.activity.Type.MatchTypesEnum;
import com.gunnarro.sportsteam.domain.league.League;
import com.gunnarro.sportsteam.domain.party.Address;
import com.gunnarro.sportsteam.domain.party.Contact;
import com.gunnarro.sportsteam.domain.party.Contact.StatusEnum;
import com.gunnarro.sportsteam.domain.party.Player;
import com.gunnarro.sportsteam.domain.party.Referee;
import com.gunnarro.sportsteam.repository.table.party.ContactsTable.GenderEnum;
import com.gunnarro.sportsteam.service.SportsTeamService;
import com.gunnarro.sportsteam.service.exception.ApplicationException;
import com.gunnarro.sportsteam.utility.Utility;

public class XmlDocumentParser {

    private static final Logger LOG = LoggerFactory.getLogger(XmlDocumentParser.class);
    public final static String SPORT_TYPE_BANDY = "BANDY";
    private final static String ATTR_DATE = "date";
    private final static String ATTR_START_DATE = "startDate";

    private final static String ATTR_END_DATE = "endDate";
    private final static String ATTR_START_TIME = "startTime";
    private final static String ATTR_BIRTH_DATE = "birthDate";
    private final static String ATTR_FIRST_NAME = "firstName";
    private final static String ATTR_MIDDLE_NAME = "middleName";
    private final static String ATTR_LAST_NAME = "lastName";
    private final static String ATTR_STATUS = "status";
    private final static String ATTR_GENDER = "gender";

    private final static String ATTR_MOBILE = "mobile";

    private final static String ATTR_EMAIL = "email";

    private final static String ATTR_CUP_NAME = "cupName";

    public static String getAttributeValue(Node node, String attrName) {
        String value = null;
        if (node == null) {
            throw new ApplicationException("Error node is null! sttribute name=" + attrName);
        }
        if (node.hasAttributes() && node.getAttributes().getNamedItem(attrName) != null) {
            value = node.getAttributes().getNamedItem(attrName).getNodeValue().trim();
        } else {
            LOG.warn("node=" + node.getNodeName() + ", hasAttributes=" + node.hasAttributes() + ", value=" + node.getNodeValue() + ", text="
                    + node.getTextContent() + ", attribute=" + attrName + ", is missing!");
        }
        return StringUtils.isEmpty(value) ? null : value;
    }

    private static InputStream getHttpsInput(String httpsUrl) {
        try {
            HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                    return hv.verify("raw.github.com", session);
                }
            };
            URL url = new URL(httpsUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setHostnameVerifier(hostnameVerifier);
            return urlConnection.getInputStream();
        } catch (Exception e) {
            LOG.error(null, e);
            return null;
        }
    }

    public static Document loadDocument(String filePath) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setValidating(false);
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputStream inputStream = null;
            LOG.debug("Start loading: " + filePath);
            if (filePath.startsWith("http")) {
                inputStream = getHttpsInput(filePath);
                if (inputStream == null) {
                    throw new ApplicationException("File not found! " + filePath);
                }
                return db.parse(inputStream);
            } else {
                InputStream is = new FileInputStream(filePath);
                return db.parse(is);
            }
        } catch (Exception e) {
            LOG.error(null, e);
            throw new ApplicationException("Error loading file!" + e.getMessage());
        }
    }

    private List<Match> getCupMatchList(XPath xpath, Document doc, Node matchesNode, Season season, String cupName, SportsTeamService sportsteamService)
            throws XPathExpressionException {

        String teamName = getAttributeValue(matchesNode, "teamName");
        Team team = sportsteamService.getTeam(null, teamName);
        if (team == null) {
            LOG.error("No team found for team name: " + teamName);
        }

        String leagueName = getAttributeValue(matchesNode, "league");
        League league = null;
        try {
            league = sportsteamService.getLeague(SPORT_TYPE_BANDY, leagueName);
        } catch (ApplicationException ae) {
            LOG.error(null, ae);
        }

        String xpathExprParents = "/seasons/season[@period='" + season.getPeriod() + "']/cups/cup[@" + ATTR_CUP_NAME + "='" + cupName + "']/matches/match";
        LOG.debug("xpath expr=" + xpathExprParents);
        NodeList nodeList = (NodeList) xpath.evaluate(xpathExprParents, doc, XPathConstants.NODESET);
        List<Match> matchList = new ArrayList<Match>();
        for (int j = 0; j < nodeList.getLength(); j++) {
            Node matchNode = nodeList.item(j);
            try {
                matchList.add(mapNodeToMatch(team, league, season, matchNode));
            } catch (Exception e) {
                LOG.error("cup=" + cupName + ", Invalid status: " + matchNode.getNodeName() + "=" + matchNode.getTextContent());
                LOG.error(null, e);
            }
        }
        return matchList;
    }

    private String getNodeValue(XPath xpath, Document doc, String xpathExpr) throws XPathExpressionException {
        NodeList nodeList = (NodeList) xpath.evaluate(xpathExpr, doc, XPathConstants.NODESET);
        if (nodeList != null && nodeList.getLength() == 1) {
            return nodeList.item(0).getTextContent();
        } else {
            return null;
        }
    }

    private List<Contact> getParentList(XPath xpath, Document doc, String firstName, String lastName) throws XPathExpressionException {
        String xpathExprParents = "/team/players/player[@" + ATTR_FIRST_NAME + "='" + firstName + "' and @" + ATTR_LAST_NAME + "='" + lastName
                + "']/parents/parent";
        LOG.debug("xpath expr=" + xpathExprParents);
        NodeList nodeList = (NodeList) xpath.evaluate(xpathExprParents, doc, XPathConstants.NODESET);
        List<Contact> parentList = new ArrayList<Contact>();
        for (int j = 0; j < nodeList.getLength(); j++) {
            Node parentNode = nodeList.item(j);
            try {
                parentList.add(new Contact(null, null, getAttributeValue(parentNode, ATTR_FIRST_NAME), getAttributeValue(parentNode, ATTR_MIDDLE_NAME),
                        getAttributeValue(parentNode, ATTR_LAST_NAME), getAttributeValue(parentNode, ATTR_GENDER)));
            } catch (Exception e) {
                LOG.error("contact=" + firstName + ", Invalid status: " + parentNode.getNodeName() + "=" + parentNode.getTextContent());
                LOG.error(null, e);
            }
        }
        return parentList;
    }

    private List<Type> getRoleList(XPath xpath, Document doc, String firstName, String lastName) throws XPathExpressionException {
        String xpathExprRoles = "/team/contacts/contact[@" + ATTR_FIRST_NAME + "='" + firstName + "' and @" + ATTR_LAST_NAME + "='" + lastName
                + "']/roles/role";
        NodeList nodeList = (NodeList) xpath.evaluate(xpathExprRoles, doc, XPathConstants.NODESET);
        List<Type> roles = new ArrayList<Type>();
        for (int j = 0; j < nodeList.getLength(); j++) {
            Node roleNode = nodeList.item(j);
            // Read only text node
            if (LOG.isDebugEnabled()) {
                LOG.debug("node=" + roleNode.getNodeName() + " " + roleNode.getNodeType() + " " + roleNode.getTextContent());
            }
            try {
                Type type = new Type();
                type.setName(roleNode.getTextContent().toUpperCase());
                roles.add(type);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Contact role: " + firstName + " " + lastName + " - " + type.toString());
                }
            } catch (Exception e) {
                LOG.error("contact=" + firstName + ", Invalid status: " + roleNode.getNodeName() + "=" + roleNode.getTextContent());
                LOG.error(null, e);
            }
        }
        return roles;
    }

    private void loadAndSaveTeam(Document doc, Club club, SportsTeamService sportsteamService, XPath xpath) throws XPathExpressionException {
        String expression = "/team";
        NodeList teamNodeList = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
        Team team = mapAndSaveTeamNode(club, teamNodeList, sportsteamService);
        if (team == null) {
            throw new ApplicationException("Tema not found!");
        }

        expression = "/team/contacts/contact";
        NodeList contactNodeList = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
        mapAndSaveContacts(xpath, doc, team, contactNodeList, sportsteamService);

        // get seasons
        expression = "/team/seasons/season";
        NodeList seasonsNodeList = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
        if (seasonsNodeList != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Load number of seasons: " + seasonsNodeList.getLength());
            }
            for (int i = 0; i < seasonsNodeList.getLength(); i++) {
                if (seasonsNodeList.item(i).getNodeName().equalsIgnoreCase("season")) {
                    Season season = mapAndSaveSeasonNode(seasonsNodeList.item(i), sportsteamService);

                    expression = "/team/seasons/season[@period='" + season.getPeriod() + "']/matches";
                    LOG.debug("matches xpath: " + expression);
                    Node matchesNode = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
                    League league = null;
                    String leagueName = getAttributeValue(matchesNode, "league");
                    if (!StringUtils.isEmpty(leagueName)) {
                        league = sportsteamService.getLeague(SPORT_TYPE_BANDY, leagueName);
                    }

                    expression = "/team/seasons/season[@period='" + season.getPeriod() + "']/matches/match";
                    LOG.debug("matches xpath: " + expression);
                    NodeList tempNodeList = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
                    mapAndSaveMatchNodes(team, league, season, tempNodeList, sportsteamService);

                    expression = "/team/seasons/season[@period='" + season.getPeriod() + "']/trainings/training";
                    LOG.debug("trainings xpath: " + expression);
                    tempNodeList = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
                    mapAndSaveTrainingNodes(team, season, tempNodeList, sportsteamService);
                } else {
                    LOG.error("Error parsing XML, Not a season node! nodeName=" + seasonsNodeList.item(i).getNodeName() + ", i=" + i + ", node list size="
                            + seasonsNodeList.getLength());
                    LOG.error("Error parsing XML, Not a season node! node=" + seasonsNodeList.item(i) + ", i=" + i);
                }
            }
        }
        expression = "/team/players/player";
        NodeList playerNodeList = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
        mapAndSavePlayerNodes(xpath, doc, team, playerNodeList, sportsteamService);
    }

    public boolean loadClub(String filePath, String fileName, SportsTeamService sportsteamService) throws XPathExpressionException {
        String file = filePath + File.separator + fileName;
        if (!new File(file).exists()) {
            throw new ApplicationException("File not found! " + file);
        }
        Document doc = loadDocument(file);
        LOG.info("Downloaded data file..." + filePath);
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();

        String expression = "/club";
        NodeList nodeList = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
        if (nodeList == null || nodeList.getLength() == 0) {
            throw new ApplicationException("No Club element found in " + filePath);
        }
        Club club = mapAndSaveClubNode(xpath, doc, nodeList, sportsteamService);
        if (club == null) {
            throw new ApplicationException("Invalid xml document, Club node is missing!");
        }

        expression = "/club/teams/team";
        nodeList = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++) {
            String teamFileName = getAttributeValue(nodeList.item(i), "file");
            System.out.println("Load file: " + filePath + File.separator + teamFileName);
            Document teamDoc = loadDocument(filePath + File.separator + teamFileName);
            loadAndSaveTeam(teamDoc, club, sportsteamService, xpath);
        }
        return true;
    }

    public void loadCups(String filePath, String fileName, SportsTeamService sportsteamService) throws XPathExpressionException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Start loading data for cups: " + fileName);
        }
        String file = filePath + File.separator + fileName;
        if (!new File(file).exists()) {
            throw new ApplicationException("File not found! " + file);
        }
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        Document cupsDoc = loadDocument(filePath + File.separator + fileName);
        NodeList seasonsNodeList = (NodeList) xpath.evaluate("/seasons/season", cupsDoc, XPathConstants.NODESET);
        LOG.debug("Load number of seasons: " + seasonsNodeList.getLength());
        for (int i = 0; i < seasonsNodeList.getLength(); i++) {
            if (seasonsNodeList.item(i).getNodeName().equalsIgnoreCase("season")) {
                Season season = mapAndSaveSeasonNode(seasonsNodeList.item(i), sportsteamService);
                String expression = "/seasons/season[@period='" + season.getPeriod() + "']/cups/cup";
                LOG.debug("cups xpath: " + expression);
                NodeList cupsNodeList = (NodeList) xpath.evaluate(expression, cupsDoc, XPathConstants.NODESET);
                mapAndSaveCupNodes(xpath, cupsDoc, season, cupsNodeList, sportsteamService);
            }
        }
    }

    public void loadTeam(String filePath, String fileName, SportsTeamService sportsteamService) throws XPathExpressionException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Start loading data for team: " + fileName);
        }
        String file = filePath + File.separator + fileName;
        if (!new File(file).exists()) {
            throw new ApplicationException("File not found! " + file);
        }
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        Document teamDoc = loadDocument(filePath + File.separator + fileName);
        NodeList nodeList = (NodeList) xpath.evaluate("/team/club", teamDoc, XPathConstants.NODESET);
        if (nodeList == null || nodeList.getLength() == 0) {
            throw new ApplicationException("No Club element found in " + filePath);
        }

        String clubName = getAttributeValue(nodeList.item(0), "name");
        String departmentName = getAttributeValue(nodeList.item(0), "department");
        Club club = sportsteamService.getClub(clubName, departmentName);
        if (club == null) {
            throw new ApplicationException("No Club found for: " + clubName + " " + departmentName);
        }
        loadAndSaveTeam(teamDoc, club, sportsteamService, xpath);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Finished loading data for team: " + fileName);
        }
    }

    private Address mapAddress(XPath xpath, Document doc, String xpathExprAddress) throws XPathExpressionException {
        Node addressNode = (Node) xpath.evaluate(xpathExprAddress, doc, XPathConstants.NODE);
        if (addressNode != null) {
            Address address = new Address();
            address.setStreetName(getAttributeValue(addressNode, "streetName"));
            address.setStreetNumber(getAttributeValue(addressNode, "streetNumber"));
            address.setStreetNumberPostfix(getAttributeValue(addressNode, "streetNumberPrefix"));
            address.setPostCode(getAttributeValue(addressNode, "zipCode"));
            address.setCity(getAttributeValue(addressNode, "city"));
            address.setCountry(getAttributeValue(addressNode, "country"));
            LOG.debug("mappedXmlAddress=" + address.toString());
            return address;
        }
        return null;
    }

    private Club mapAndSaveClubNode(XPath xpath, Document doc, NodeList nodeList, SportsTeamService sportsteamService) throws XPathExpressionException {
        LOG.debug("node=" + nodeList.item(0).getNodeName());
        Club club = new Club();
        club.setName(getAttributeValue(nodeList.item(0), "name"));
        club.setDepartmentName(getAttributeValue(nodeList.item(0), "department"));
        if (sportsteamService.getClub(club.getName(), club.getDepartmentName()) == null) {
            club.setAddress(mapAddress(xpath, doc, "/club/address"));
            club.setClubNameAbbreviation(getAttributeValue(nodeList.item(0), "abbrivation"));
            club.setStadiumName(getNodeValue(xpath, doc, "/club/stadium"));
            club.setHomePageUrl(getNodeValue(xpath, doc, "/club/homepage"));
            sportsteamService.saveClub(club);
            LOG.debug("Created new club: " + club.getFullName());
        }
        return sportsteamService.getClub(club.getName(), club.getDepartmentName());
    }

    private void mapAndSaveContacts(XPath xpath, Document doc, Team team, NodeList nodeList, SportsTeamService sportsteamService)
            throws XPathExpressionException {
        for (int i = 0; i < nodeList.getLength(); i++) {
            // Get the contact node roles child node
            List<Type> roleList = getRoleList(xpath, doc, getAttributeValue(nodeList.item(i), ATTR_FIRST_NAME),
                    getAttributeValue(nodeList.item(i), ATTR_LAST_NAME));
            Address address = Address.createEmptyAddress();
            if (nodeList.item(i).hasChildNodes()) {
                String firstName = getAttributeValue(nodeList.item(i), ATTR_FIRST_NAME);
                String lastName = getAttributeValue(nodeList.item(i), ATTR_LAST_NAME);
                String xpathExprAddress = "/team/contacts/contact[@" + ATTR_FIRST_NAME + "='" + firstName + "' and @" + ATTR_LAST_NAME + "='" + lastName
                        + "']/address";
                address = mapAddress(xpath, doc, xpathExprAddress);
            }
            String status = getAttributeValue(nodeList.item(i), ATTR_STATUS);
            Contact contact = new Contact();
            contact.setFkTeamId(team.getId());
            contact.setTeamRoleList(roleList);
            contact.setFirstName(getAttributeValue(nodeList.item(i), ATTR_FIRST_NAME));
            contact.setMiddleName(getAttributeValue(nodeList.item(i), ATTR_MIDDLE_NAME));
            contact.setLastName(getAttributeValue(nodeList.item(i), ATTR_LAST_NAME));
            contact.setGender(getAttributeValue(nodeList.item(i), ATTR_GENDER));
            contact.setFkStatusId(StatusEnum.valueOf(status.toUpperCase()).getId());
            contact.setMobileNumber(getAttributeValue(nodeList.item(i), ATTR_MOBILE));
            contact.setEmailAddress(getAttributeValue(nodeList.item(i), ATTR_EMAIL));
            contact.setAddress(address);
            try {
                int contactId = sportsteamService.saveContact(contact);
                if (LOG.isDebugEnabled() && contact.hasTeamRoles()) {
                    Contact newContact = sportsteamService.getContact(contactId);
                    Team t = sportsteamService.getTeam(team.getId());
                    LOG.debug("saved contact: " + newContact.toString());
                    LOG.debug("Coach   : " + t.getCoach());
                    LOG.debug("TeamLead: " + t.getTeamLead());
                }
            } catch (ApplicationException ae) {
                LOG.error(null, ae);
            }
        }
    }

    private void mapAndSaveCupNodes(XPath xpath, Document doc, Season season, NodeList nodeList, SportsTeamService sportsteamService)
            throws XPathExpressionException {
        for (int i = 0; i < nodeList.getLength(); i++) {
            String startDateTimeStr = getAttributeValue(nodeList.item(i), ATTR_START_DATE) + " " + getAttributeValue(nodeList.item(i), ATTR_START_TIME);
            String endDateStr = getAttributeValue(nodeList.item(i), ATTR_END_DATE);
            String deadLineDateStr = getAttributeValue(nodeList.item(i), "deadlineDate");
            Cup cup = new Cup();
            cup.setFkSeasonId(season.getId());
            cup.setCupName(getAttributeValue(nodeList.item(i), "cupName"));
            cup.setVenue(getAttributeValue(nodeList.item(i), "venue"));
            cup.setClubName(getAttributeValue(nodeList.item(i), "organizingClub"));
            cup.setHomePage(getAttributeValue(nodeList.item(i), "homePage"));
            cup.setEmail(getAttributeValue(nodeList.item(i), "email"));
            if (deadLineDateStr != null) {
                cup.setDeadlineDate(Utility.timeToDate(deadLineDateStr, "dd.MM.yyyy"));
            }
            cup.setStartDate(Utility.timeToDate(startDateTimeStr, "dd.MM.yyyy hh:mm"));
            cup.setEndDate(Utility.timeToDate(endDateStr, "dd.MM.yyyy"));
            LOG.debug(cup.toString());
            int cupId = sportsteamService.saveCup(cup);
            // Add matches for this cup, if any...
            String xpathExprMatchesNode = "/seasons/season[@period='" + season.getPeriod() + "']/cups/cup[@" + ATTR_CUP_NAME + "='" + cup.getCupName()
                    + "']/matches";
            LOG.debug("xpath expr=" + xpathExprMatchesNode);
            Node matchesNode = (Node) xpath.evaluate(xpathExprMatchesNode, doc, XPathConstants.NODE);
            if (matchesNode != null) {
                List<Match> cupMatchList = this.getCupMatchList(xpath, doc, matchesNode, season, cup.getCupName(), sportsteamService);
                for (Match match : cupMatchList) {
                    sportsteamService.createMatchForCup(match, cupId);
                }
            }
        }
    }

    private void mapAndSaveMatchNodes(Team team, League league, Season season, NodeList nodeList, SportsTeamService sportsteamService) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Match match = mapNodeToMatch(team, league, season, nodeList.item(i));
            LOG.debug(match.toString());
            sportsteamService.saveMatch(match);
        }
    }

    private void mapAndSavePlayerNodes(XPath xpath, Document doc, Team team, NodeList nodeList, SportsTeamService sportsteamService)
            throws XPathExpressionException {
        for (int i = 0; i < nodeList.getLength(); i++) {
            String firstName = getAttributeValue(nodeList.item(i), ATTR_FIRST_NAME);
            String lastName = getAttributeValue(nodeList.item(i), ATTR_LAST_NAME);
            String xpathExprAddress = "/team/players/player[@" + ATTR_FIRST_NAME + "='" + firstName + "' and @" + ATTR_LAST_NAME + "='" + lastName
                    + "']/address";
            Address address = mapAddress(xpath, doc, xpathExprAddress);
            List<Contact> parentList = getParentList(xpath, doc, getAttributeValue(nodeList.item(i), ATTR_FIRST_NAME),
                    getAttributeValue(nodeList.item(i), ATTR_LAST_NAME));
            String status = getAttributeValue(nodeList.item(i), ATTR_STATUS);
            Player player = new Player();
            player.setFkTeamId(team.getId());
            player.setFirstName(getAttributeValue(nodeList.item(i), ATTR_FIRST_NAME));
            player.setMiddleName(getAttributeValue(nodeList.item(i), ATTR_MIDDLE_NAME));
            player.setLastName(getAttributeValue(nodeList.item(i), ATTR_LAST_NAME));
            player.setGender(getAttributeValue(nodeList.item(i), ATTR_GENDER));
            player.setFkStatusId(StatusEnum.valueOf(status.toUpperCase()).getId());
            String bDate = getAttributeValue(nodeList.item(i), ATTR_BIRTH_DATE);
            if (bDate != null) {
                player.setDateOfBirth(Utility.timeToDate(bDate, Utility.DATE_PATTERN));
            }
            player.setAddress(address);
            player.setParents(parentList);
            try {
                sportsteamService.savePlayer(player);
                LOG.debug(player.toString());
            } catch (ApplicationException ae) {
                LOG.error(null, ae);
            }
        }
    }

    private Season mapAndSaveSeasonNode(Node node, SportsTeamService sportsteamService) {
        LOG.debug("node=" + node.getNodeName());
        String period = getAttributeValue(node, "period");
        if (period == null) {
            return null;
        }
        Season existingSeason = sportsteamService.getSeason(period);
        if (existingSeason == null) {
            Season newSeason = new Season(0, 0);
            LOG.debug("create new season: " + newSeason.toString());
            int seasonId = sportsteamService.createSeason(newSeason);
            LOG.debug("return season id: " + seasonId);
            return sportsteamService.getSeason(seasonId);
        }
        LOG.debug("return: " + existingSeason);
        return existingSeason;
    }

    private Team mapAndSaveTeamNode(Club club, NodeList nodeList, SportsTeamService sportsteamService) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("save team for club: " + club.toString());
        }
        Team team = new Team();
        team.setName(getAttributeValue(nodeList.item(0), "name"));
        // Check if it's a existing team
        Team existingTeam = sportsteamService.getTeam(club.getId(), team.getName());
        int teamId;
        if (existingTeam == null) {
            // This was a new team, so create it.
            team.setFkClubId(club.getId());
            String leagueName = getAttributeValue(nodeList.item(0), "league");
            if (!StringUtils.isEmpty(leagueName)) {
                League league = sportsteamService.getLeague(SPORT_TYPE_BANDY, leagueName);
                team.setFkLeagueId(league.getId());
            }
            team.setGender(GenderEnum.MALE.name());
            team.setTeamYearOfBirth(Integer.parseInt(getAttributeValue(nodeList.item(0), "yearOfBirth")));
            teamId = sportsteamService.saveTeam(team);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Created new team: " + club.getFullName() + " - " + team.toString() + ", teamId=" + teamId);
            }
        } else {
            teamId = existingTeam.getId();
        }
        return sportsteamService.getTeam(teamId);
    }

    private void mapAndSaveTrainingNodes(Team team, Season season, NodeList nodeList, SportsTeamService sportsteamService) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            String dateTimeStr = getAttributeValue(nodeList.item(i), ATTR_DATE) + " " + getAttributeValue(nodeList.item(i), "fromTime");
            String toTimeStr = getAttributeValue(nodeList.item(i), "toTime");
            Training training = new Training();
            training.setFkTeamId(team.getId());
            training.setFkSeasonId(season.getId());
            training.setStartDate(Utility.timeToDate(dateTimeStr, "dd.MM.yyyy HH:mm"));
            training.setEndDate(Utility.timeToDate(toTimeStr, "HH:mm"));
            training.setVenue("Place");
            if (LOG.isDebugEnabled()) {
                LOG.debug(training.toString());
            }
            sportsteamService.saveTraining(training);
        }
    }

    private Match mapNodeToMatch(Team team, League league, Season season, Node matchNode) {
        String dateTimeStr = getAttributeValue(matchNode, ATTR_DATE) + " " + getAttributeValue(matchNode, ATTR_START_TIME);
        MatchTypesEnum matchType = MatchTypesEnum.valueOf(getAttributeValue(matchNode, "type"));
        Match match = new Match();
        if (league != null) {
            match.setFkLeagueId(league.getId());
        }
        match.setFkTeamId(team.getId());
        match.setFkSeasonId(season.getId());
        match.setStartDate(Utility.timeToDate(dateTimeStr, "dd.MM.yyyy HH:mm"));
        match.setTeam(new Team(team.getId(), team.getName()));
        match.setHomeTeam(new Team(getAttributeValue(matchNode, "homeTeam")));
        match.setAwayTeam(new Team(getAttributeValue(matchNode, "awayTeam")));
        match.setNumberOfGoalsHome(Integer.parseInt(getAttributeValue(matchNode, "goalsHomeTeam")));
        match.setNumberOfGoalsAway(Integer.parseInt(getAttributeValue(matchNode, "goalsAwayTeam")));
        match.setVenue(getAttributeValue(matchNode, "venue"));
        match.setReferee(new Referee(getAttributeValue(matchNode, "referee"), null, getAttributeValue(matchNode, "referee")));
        match.setMatchType(matchType);
        match.setStatus(new Status(null, getAttributeValue(matchNode, "status")));
        return match;
    }

    private void printDocument(Document doc) throws TransformerException {
        DOMSource domSource = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(domSource, result);
        System.out.println(writer.toString());
    }

    private void printInputStream(FileInputStream is) throws IOException {
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        is.close();
    }
}
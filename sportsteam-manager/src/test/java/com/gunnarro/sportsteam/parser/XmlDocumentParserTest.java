package com.gunnarro.sportsteam.parser;

import static org.junit.Assert.assertNotNull;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.gunnarro.sportsteam.parser.XmlDocumentParser;

@Ignore
public class XmlDocumentParserTest {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final static String teamDataFile = "C:\\code\\openshift\\git\\jbossews\\data\\uil\\team-2003.xml";

    @Test
    public void parserSeasons() throws XPathExpressionException {
        Document doc = XmlDocumentParser.loadDocument(teamDataFile);
        logger.info("Downloaded data file..." + teamDataFile);
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        // get seasons
        String expression = "/team/seasons/season";
        NodeList nodeList = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
        logger.debug("Load number of seasons: " + nodeList.getLength());
        for (int i = 0; i < nodeList.getLength(); i++) {
            String period = XmlDocumentParser.getAttributeValue(nodeList.item(i), "period");
            logger.debug("node=" + nodeList.item(i).getNodeName() + " period=" + XmlDocumentParser.getAttributeValue(nodeList.item(i), "period")
                    + " startDate=" + XmlDocumentParser.getAttributeValue(nodeList.item(i), "startDate") + " endDate="
                    + XmlDocumentParser.getAttributeValue(nodeList.item(i), "endDate"));

            assertNotNull(period);
            assertNotNull(nodeList.item(i).getNodeName());
            assertNotNull(XmlDocumentParser.getAttributeValue(nodeList.item(i), "period"));
            assertNotNull(XmlDocumentParser.getAttributeValue(nodeList.item(i), "startDate"));
            assertNotNull(XmlDocumentParser.getAttributeValue(nodeList.item(i), "endDate"));
            
            expression = "/team/seasons/season[@period='" + period + "']/matches/match";
        }
    }
}

package com.gunnarro.sportsteam.service.impl;

import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaMailReader {

	private static final Logger LOG = LoggerFactory.getLogger(JavaMailReader.class);
	 
	public static void check(String host, String port, String storeType, String user, String password) {
		try {
			// create properties field
			Properties properties = new Properties();
			properties.put("mail.pop3.host", host);
			properties.put("mail.pop3.port", port);
			properties.put("mail.pop3.starttls.enable", "true");
			Session emailSession = Session.getDefaultInstance(properties);

			// create the POP3 store object and connect with the pop server
			Store store = emailSession.getStore(storeType);
			store.connect(host,user,password);

			Folder[] folders = store.getDefaultFolder().list("*");
			for (javax.mail.Folder folder : folders) {
				LOG.info(folder.getFullName());
			}

			// create the folder object and open it
			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);

			// retrieve the messages from the folder in an array and print it
			// search for all "unseen" messages
		    Flags seen = new Flags(Flags.Flag.SEEN);
		    FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
		    Message[] unreadMessages = inbox.search(unseenFlagTerm);
		    LOG.info("Number of unread emails:" + unreadMessages.length);

		   // Message[] messages = inbox.getMessages();
		   // LOG.info("Number of emails:" + messages.length);
			for (int i = 0, n = unreadMessages.length; i < n; i++) {
				Message message = unreadMessages[i];
				LOG.info("---------------------------------");
				LOG.info("Email Number " + (i + 1));
				LOG.info("Subject: " + message.getSubject());
				LOG.info("From: " + message.getFrom()[0]);
				//LOG.info("Text: " + message.getContent().toString());
			}
			// close the store and folder objects
			inbox.close(false);
			store.close();
		} catch (NoSuchProviderException e) {
			LOG.error("", e);
			e.printStackTrace();
		} catch (MessagingException e) {
			LOG.error("", e);
			e.printStackTrace();
		} catch (Exception e) {
			LOG.error("", e);
			e.printStackTrace();
		} finally {
//			inbox.close(false);
//			store.close();
		}
	}

	
	public static void main(String[] args) {
		String host = "pop.mail.yahoo.com";
		String port = "995";
		String mailStoreType = "pop3s";
		String username = "sport4team@yahoo.com";
		String password = "ABcd2o1o";
		check(host, port, mailStoreType, username, password);
	}
}

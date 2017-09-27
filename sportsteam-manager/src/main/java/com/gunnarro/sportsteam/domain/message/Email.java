package com.gunnarro.sportsteam.domain.message;

import java.util.Arrays;

public class Email {

	private String to;
	private String[] emailAddresses;
	private String from;
	private String subject;
	private String msg;

	public Email() {
	}

	public Email(String[] emailAddresses, String from, String msg) {
		super();
		this.emailAddresses = emailAddresses;
		this.from = from;
		this.msg = msg;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String[] getEmailAddresses() {
		return emailAddresses;
	}

	public void setEmailAddresses(String[] emailAddresses) {
		this.emailAddresses = emailAddresses;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "Email [emailAddresses=" + Arrays.toString(emailAddresses)
				+ ", from=" + from + ", msg=" + msg + "]";
	}

	public boolean isNew() {
		return this.to == null;
	}
}

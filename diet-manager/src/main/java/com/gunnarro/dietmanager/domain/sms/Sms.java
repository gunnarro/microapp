package com.gunnarro.dietmanager.domain.sms;

import java.util.Date;

public class Sms {

	private Date date;
	private String from;
	private String to;
	private String message;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Sms [date=" + date + ", from=" + from + ", to=" + to + ", message=" + message + "]";
	}

}

package com.gunnarro.calendar.domain.calendar;

import java.io.Serializable;
import java.util.Date;

import com.gunnarro.calendar.utility.Utility;

public class Reminder implements Serializable {

	private static final long serialVersionUID = -7582546757296463504L;

	private Integer id;
	private Date sentDate;
	private String to;
	private String msg;

	public Reminder(Date sentDate, String to, String msg) {
		super();
		this.sentDate = sentDate;
		this.to = to;
		this.msg = msg;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSummary() {
		return Utility.formatTime(sentDate.getTime(),
				Utility.DATE_DEFAULT_PATTERN) + " " + to;
	}
}

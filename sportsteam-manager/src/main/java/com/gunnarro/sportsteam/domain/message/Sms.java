package com.gunnarro.sportsteam.domain.message;

import java.util.Arrays;

public class Sms {

	private String to;
	private String[] toPhoneNumbers;
	private String from;
	private String msg;


	public Sms(){
	}
	
	public Sms(String[] toPhoneNumbers, String from, String msg) {
		super();
		this.toPhoneNumbers = toPhoneNumbers;
		this.from = from;
		this.msg = msg;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String[] getToPhoneNumbers() {
		return toPhoneNumbers;
	}

	public void setToPhoneNumbers(String[] toPhoneNumbers) {
		this.toPhoneNumbers = toPhoneNumbers;
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
		return "Sms [toPhoneNumbers=" + Arrays.toString(toPhoneNumbers)
				+ ", from=" + from + ", msg=" + msg + "]";
	}

	 public boolean isNew() {
		 return this.to == null;
	 }
}

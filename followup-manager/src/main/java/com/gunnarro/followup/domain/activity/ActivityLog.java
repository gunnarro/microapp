package com.gunnarro.followup.domain.activity;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.gunnarro.followup.domain.BaseDomain;

public class ActivityLog extends BaseDomain {

	private static final long serialVersionUID = -180659968576477898L;

	private String createdByUser;
	private String lastModifiedByUser;
	private Activity activity;
	// hh:mm
	private LocalTime fromTime;
	// hh:mm
	private LocalTime toTime;
	// HIGH, MEDION, LOW
	private Integer intensitivity;
	// how I feel 1 to 10, where 1 is BAD and 10 is GREATE
	private Integer emotions;

	public String getCreatedByUser() {
		return createdByUser;
	}

	public void setCreatedByUser(String createdByUser) {
		this.createdByUser = createdByUser;
	}

	public String getLastModifiedByUser() {
		return lastModifiedByUser;
	}

	public void setLastModifiedByUser(String lastModifiedByUser) {
		this.lastModifiedByUser = lastModifiedByUser;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public LocalTime getFromTime() {
		return fromTime;
	}

	public void setFromTime(LocalTime fromTime) {
		this.fromTime = fromTime;
	}

	public LocalTime getToTime() {
		return toTime;
	}

	public void setToTime(LocalTime toTime) {
		this.toTime = toTime;
	}

	public Integer getIntensitivity() {
		return intensitivity;
	}

	public void setIntensitivity(Integer intensitivity) {
		this.intensitivity = intensitivity;
	}

	public Integer getEmotions() {
		return emotions;
	}

	public void setEmotions(Integer emotions) {
		this.emotions = emotions;
	}

	@Override
	public String toString() {
		return "ActivityLog [createdByUser=" + createdByUser + ", lastModifiedByUser=" + lastModifiedByUser
				+ ", activity=" + activity + ", fromTime=" + fromTime + ", toTime=" + toTime + ", intensitivity="
				+ intensitivity + ", emotions=" + emotions + "]";
	}

}

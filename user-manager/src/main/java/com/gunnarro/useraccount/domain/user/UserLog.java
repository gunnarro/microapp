package com.gunnarro.useraccount.domain.user;

import java.io.Serializable;
import java.util.Date;

public class UserLog implements Serializable {

    private static final long serialVersionUID = -3112437958212912495L;

    private Integer id;
    private Integer userId;
    private Date createdDate;
    private Date lastModifiedDate;
    private Date loggedInDate;
    private String loggedInFromIpAddress;
    private String loggedInFromDevice;
    private int numberOfLoginAttemptSuccess;
    private int numberOfLoginAttemptFailures;
    private boolean isUserBlocked;

    public UserLog() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Date getLoggedInDate() {
        return loggedInDate;
    }

    public void setLoggedInDate(Date loggedInDate) {
        this.loggedInDate = loggedInDate;
    }

    public String getLoggedInFromIpAddress() {
        return loggedInFromIpAddress;
    }

    public void setLoggedInFromIpAddress(String loggedInFromIpAddress) {
        this.loggedInFromIpAddress = loggedInFromIpAddress;
    }

    public String getLoggedInFromDevice() {
        return loggedInFromDevice;
    }

    public void setLoggedInFromDevice(String loggedInFromDevice) {
        this.loggedInFromDevice = loggedInFromDevice;
    }

    public int getNumberOfLoginAttemptSuccess() {
        return numberOfLoginAttemptSuccess;
    }

    public void setNumberOfLoginAttemptSuccess(int numberOfLoginAttemptSuccess) {
        this.numberOfLoginAttemptSuccess = numberOfLoginAttemptSuccess;
    }

    public int getNumberOfLoginAttemptFailures() {
        return numberOfLoginAttemptFailures;
    }

    public void setNumberOfLoginAttemptFailures(int numberOfLoginAttemptFailures) {
        this.numberOfLoginAttemptFailures = numberOfLoginAttemptFailures;
    }

    public boolean isUserBlocked() {
        return isUserBlocked;
    }

    public void setUserBlocked(boolean isUserBlocked) {
        this.isUserBlocked = isUserBlocked;
    }

    @Override
    public String toString() {
        return "UserLog [id=" + id + ", userId=" + userId + ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + ", loggedInDate=" + loggedInDate
                + ", loggedInFromIpAddress=" + loggedInFromIpAddress + ", loggedInFromDevice=" + loggedInFromDevice + ", numberOfLoginAttemptSuccess="
                + numberOfLoginAttemptSuccess + ", numberOfLoginAttemptFailures=" + numberOfLoginAttemptFailures + "]";
    }

}

package com.gunnarro.dietmanager.domain.togetherness;

import java.util.Date;
import java.util.List;

public class TogethernessLog {

    private Integer id;
    private Integer fkUserId;
    private Date fromDate;
    private Date toDate;

    private Person parent;
    private List<Person> childeren;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFkUserId() {
        return fkUserId;
    }

    public void setFkUserId(Integer fkUserId) {
        this.fkUserId = fkUserId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

}

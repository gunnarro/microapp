package com.gunnarro.calendar.domain.view;

import java.util.Date;
import java.util.List;

import com.gunnarro.calendar.domain.view.list.Item;

public class CommonFormInputData {

    private Integer id;
    private String name;
    private Date fromDate;
    private Date toDate;
    private List<Item> items;

    public CommonFormInputData() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "CommonFormInputData [id=" + id + ", name=" + name + ", fromDate=" + fromDate + ", toDate=" + toDate + "]";
    }

}

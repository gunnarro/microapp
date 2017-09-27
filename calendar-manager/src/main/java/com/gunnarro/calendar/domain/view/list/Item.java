package com.gunnarro.calendar.domain.view.list;

import java.util.Date;

/**
 * Class for holding list items.
 * 
 * @author gunnarro
 * 
 */
public class Item {

    private String actionResult;
    private Integer id;
    private String name;
    private String value;
    private String type;
    private Date startDate;
    private boolean isEnabled = true;
    private boolean isSelected = false;

    public Item() {
    }

    public Item(Integer id, String value) {
        this.id = id;
        this.value = value;
    }

    public Item(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public Item(Integer id, String value, boolean isEnabled) {
        this(id, value);
        this.isEnabled = isEnabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActionResult() {
        return actionResult;
    }

    public void setActionResult(String actionResult) {
        this.actionResult = actionResult;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public Boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public void toggleEnabled() {
        this.isEnabled = !this.isEnabled;
    }

    public String getValue() {
        return value;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public int hashCode() {
        final int multiplier = 23;
        int hashCode = 0;
        if (hashCode == 0) {
            int code = 133;
            code = multiplier * code + this.value.hashCode();
            hashCode = code;
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Item)) {
            return false;
        }
        final Item other = (Item) obj;
        if (this.value.equals(other.value)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Item [id=" + id + ", value=" + value + ", isSelected=" + isSelected + "]";
    }

}

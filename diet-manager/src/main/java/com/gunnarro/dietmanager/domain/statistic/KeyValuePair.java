package com.gunnarro.dietmanager.domain.statistic;

import java.io.Serializable;
import java.util.Date;

public class KeyValuePair implements Serializable {
    private static final long serialVersionUID = 5162452282598219646L;

    private Integer id;
    private String name;
    private Integer count;
    private Integer totalCount;
    private Integer keyId;
    private Integer valueId;
    private String key;
    private String periode;
    private String value;
    private Date createdDate;
    private Date toDate;
    private Date fromDate;

    public KeyValuePair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public KeyValuePair(String key, String value, Integer count) {
        this(key, value);
        this.count = count;
    }

    public KeyValuePair(String name, String key, String value) {
        this(key, value);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public String getKey() {
        return key;
    }

    public Integer getKeyId() {
        return keyId;
    }

    public String getPeriode() {
        return periode;
    }

    public Date getToDate() {
        return toDate;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public String getValue() {
        return value;
    }

    public Integer getValueId() {
        return valueId;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setKeyId(Integer keyId) {
        this.keyId = keyId;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setValueId(Integer valueId) {
        this.valueId = valueId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        KeyValuePair other = (KeyValuePair) obj;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "KeyValuePair [fromDate=" + fromDate + ", toDate=" + toDate + ", key=" + key + ", value=" + value + ", count=" + count + ", totalCount="
                + totalCount + "]";
    }

}

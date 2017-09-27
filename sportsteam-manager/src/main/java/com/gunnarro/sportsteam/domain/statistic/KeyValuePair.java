package com.gunnarro.sportsteam.domain.statistic;

import java.util.Date;

public class KeyValuePair {
    private Date createdDate;
    private Integer keyId;
    private Integer valueId;
    private String key;
    private String value;

    public KeyValuePair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getKeyId() {
        return keyId;
    }

    public void setKeyId(Integer keyId) {
        this.keyId = keyId;
    }

    public Integer getValueId() {
        return valueId;
    }

    public void setValueId(Integer valueId) {
        this.valueId = valueId;
    }

    @Override
    public String toString() {
        return "KeyValuePair [key=" + key + ", value=" + value + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return this.key.equals(((KeyValuePair) obj).key);
    }

}

package com.gunnarro.dietmanager.domain.statistic;

import java.io.Serializable;

public class KeyValue implements Comparable<KeyValue>, Serializable {

    private static final long serialVersionUID = -2899831161022093258L;

    private String key;
    private String value;

    /**
     * @param key
     * @param value
     */
    public KeyValue(String key, String value) {
        this.key = key;
        this.value = value;
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

    @Override
    public int compareTo(KeyValue o) {
        return this.key.compareTo(o.getKey());
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
        KeyValue other = (KeyValue) obj;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Key [key=" + key + ", value=" + value + "]";
    }

}

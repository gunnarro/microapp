package com.gunnarro.dietmanager.domain.view;

import java.io.Serializable;
import java.util.List;

public class KeyValuePairList implements Serializable {

    private static final long serialVersionUID = -1367843295321427705L;

    private Integer id;
    private String name;
    private String key;
    private List<String> value;

    public KeyValuePairList(String key, List<String> value) {
        this.key = key;
        this.value = value;
    }

    public KeyValuePairList(Integer id, String key, List<String> value) {
        this(key, value);
        this.id = id;
    }

    public KeyValuePairList(String name, String key, List<String> value) {
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

    public String getKey() {
        return key;
    }

    /**
     * Hack to sort correct for dietplan items, which have 'Frokost (0900-0930)'
     * format
     * 
     * @return
     */
    public String getKeyToCompare() {
        if (key != null && key.contains("(")) {
            return key.split("\\(")[1];
        } else {
            return key;
        }
    }

    public List<String> getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "KeyValuePair [key=" + key + ", value=" + value + "]";
    }

}

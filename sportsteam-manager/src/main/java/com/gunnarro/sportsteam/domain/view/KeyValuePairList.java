package com.gunnarro.sportsteam.domain.view;

import java.util.List;

public class KeyValuePairList {

    private Integer id;
    private String key;
    private List<String> value;

    public KeyValuePairList(Integer id, String key, List<String> value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public KeyValuePairList(String key, List<String> value) {
        this.key = key;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
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

    @Override
    public String toString() {
        return "KeyValuePair [key=" + key + ", value=" + value + "]";
    }

}

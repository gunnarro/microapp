package com.gunnarro.sportsteam.domain;

public class Setting {

    private String name;
    private String value;

    public Setting(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + ": [type=" + this.name + ", value=" + this.value + "]";
    }
}

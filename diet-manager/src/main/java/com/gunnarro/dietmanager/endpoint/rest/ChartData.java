package com.gunnarro.dietmanager.endpoint.rest;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ChartData extends RestResponse implements Comparable<ChartData>, Serializable {

    private static final long serialVersionUID = -3789431120521785723L;

    private String key;
    private Integer id;
    private String label;
    private Double value1;
    private Double value2;
    private Double value3;
    private Double value4;

    public ChartData() {
    }

    public ChartData(String label, double value1) {
        super();
        this.label = label;
        this.value1 = value1;
    }

    public ChartData(String key, String label, double value1, double value2, double value3) {
        this(label, value1);
        this.key = key;
        this.value2 = value2;
        this.value3 = value3;
    }

    public ChartData(Integer id, String label, double value1, double value2, double value3) {
        this.id = id;
        this.label = label;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
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

    public String getLabel() {
        return label;
    }

    public Double getValue1() {
        return value1;
    }

    public Double getValue2() {
        return value2;
    }

    public Double getValue3() {
        return value3;
    }

    public Double getValue4() {
        return value4;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setValue1(Double value1) {
        this.value1 = value1;
    }

    public void setValue2(Double value2) {
        this.value2 = value2;
    }

    public void setValue3(Double value3) {
        this.value3 = value3;
    }

    public void setValue4(Double value4) {
        this.value4 = value4;
    }

    @Override
    public String toString() {
        return "ChartData [id=" + id + ", label=" + label + ", value1=" + value1 + ", value2=" + value2 + ", value3=" + value3 + ", value4=" + value4 + "]";
    }

    @Override
    public int compareTo(ChartData o) {
        return this.key.compareTo(o.getKey());
    }

}
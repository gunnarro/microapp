package com.gunnarro.sportsteam.domain.league;

public class Dimension {
    private enum MetrixEnum {
        KM, M, CM, MM
    }

    private Integer length;
    private Integer width;
    private Integer lengthMin;
    private Integer widthMin;
    private Integer lengthMax;
    private Integer widthMax;
    private String metrix = MetrixEnum.M.name();

    public Dimension(Integer length, Integer width) {
        this.length = length;
        this.width = width;
    }

    public Dimension(Integer length, Integer width, String metrix) {
        this(length, width);
        this.metrix = metrix;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getLengthMin() {
        return lengthMin;
    }

    public void setLengthMin(Integer lengthMin) {
        this.lengthMin = lengthMin;
    }

    public Integer getWidthMin() {
        return widthMin;
    }

    public void setWidthMin(Integer widthMin) {
        this.widthMin = widthMin;
    }

    public Integer getLengthMax() {
        return lengthMax;
    }

    public void setLengthMax(Integer lengthMax) {
        this.lengthMax = lengthMax;
    }

    public Integer getWidthMax() {
        return widthMax;
    }

    public void setWidthMax(Integer widthMax) {
        this.widthMax = widthMax;
    }

    public String getMetrix() {
        return metrix;
    }

    public void setMetrix(String metrix) {
        this.metrix = metrix;
    }

}

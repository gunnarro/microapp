package com.gunnarro.dietmanager.domain.togetherness;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Address {

    private static final long serialVersionUID = -4991551857818703470L;

    private Integer id;
    private String streetName;
    private String streetNumber;
    private String streetNumberPostfix;
    private String postCode;
    private String city;
    private String country;

    public Address() {
    }

    public Address(int id) {
        this.id = id;
    }

    public Address(Integer id, String streetName, String streetNumber, String streetNumberPostfix, String postalCode, String city, String country) {
        this(id);
        if (streetName != null) {
            this.streetName = streetName.toUpperCase();
        }
        this.streetNumber = streetNumber;
        if (streetNumberPostfix != null) {
            this.streetNumberPostfix = streetNumberPostfix.toUpperCase();
        }
        this.postCode = postalCode;
        if (city != null) {
            this.city = city.toUpperCase();
        }
        if (country != null) {
            this.country = country.toUpperCase();
        }
    }

    public Address(String streetName, String streetNumber, String streetNumberPrefix, String postalCode, String city, String country) {
        this(null, streetName, streetNumber, streetNumberPrefix, postalCode, city, country);
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName != null ? streetName.toUpperCase() : null;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postalCode) {
        this.postCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city != null ? city.toUpperCase() : null;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country != null ? country.toUpperCase() : null;
    }

    public String getStreetNumberPostfix() {
        return streetNumberPostfix != null ? streetNumberPostfix.toUpperCase() : null;
    }

    public void setStreetNumberPostfix(String streetNumberPostfix) {
        this.streetNumberPostfix = streetNumberPostfix;
    }

    @JsonIgnore
    public String getFullStreetName() {
        if (!StringUtils.isEmpty(streetName) && !StringUtils.isEmpty(streetNumber)) {
            return streetName + " " + streetNumber + (streetNumberPostfix != null ? getStreetNumberPostfix() : "");
        }
        return null;
    }

    @JsonIgnore
    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        if (isAddressValid()) {
            sb.append(getFullStreetName()).append(", ");
            sb.append(postCode).append(" ").append(city).append(" ");
            sb.append(country);
            return sb.toString();
        }
        return null;
    }

    @JsonIgnore
    public boolean isAddressValid() {
        return !StringUtils.isEmpty(streetName) && !StringUtils.isEmpty(streetNumber) && !StringUtils.isEmpty(postCode) && !StringUtils.isEmpty(country);
    }

    public boolean isAddressEmpty() {
        return StringUtils.isEmpty(streetName) && StringUtils.isEmpty(streetNumber) && StringUtils.isEmpty(postCode) && StringUtils.isEmpty(country);
    }

    public static Address createEmptyAddress() {
        return new Address(-1, "", "", "", "", "", "");
    }

}

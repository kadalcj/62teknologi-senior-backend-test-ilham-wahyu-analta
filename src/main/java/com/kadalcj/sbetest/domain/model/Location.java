package com.kadalcj.sbetest.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;

import java.util.List;

@Embeddable
public class Location {
    @JsonProperty("address1")
    private String address1;
    @JsonProperty("address2")
    private String address2;
    @JsonProperty("address3")
    private String address3;
    @JsonProperty("city")
    private String city;
    @JsonProperty("zip_code")
    private String zipCode;
    @JsonProperty("country")
    private String country;
    @JsonProperty("state")
    private String state;
    @ElementCollection
    @JsonProperty("display_address")
    private List<String> displayAddress;
}

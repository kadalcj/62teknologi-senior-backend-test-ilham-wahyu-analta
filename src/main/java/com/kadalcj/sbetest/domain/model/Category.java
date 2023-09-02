package com.kadalcj.sbetest.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;

@Embeddable
public class Category {
    @JsonProperty("alias")
    private String alias;
    @JsonProperty("title")
    private String title;
}

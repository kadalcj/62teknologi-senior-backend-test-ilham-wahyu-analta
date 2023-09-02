package com.kadalcj.sbetest.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Business {
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("alias")
    @NotNull(message = "alias can't be null")
    @NotBlank(message = "alias can't be blank")
    private String alias;

    @JsonProperty("name")
    @NotNull(message = "name can't be null")
    @NotBlank(message = "name can't be null")
    private String name;

    @JsonProperty("image_url")
    @NotNull(message = "image_url can't be null")
    private String imageUrl;

    @JsonProperty("is_closed")
    @NotNull(message = "is_closed can't be null")
    private boolean isClosed;

    @JsonProperty("url")
    private String url;

    @JsonProperty("review_count")
    @NotNull(message = "review_count can't be null")
    private long reviewCount;

    @ElementCollection
    @JsonProperty("categories")
    @NotNull(message = "categories can't be null")
    private List<Category> categories;

    @JsonProperty("rating")
    @NotNull(message = "rating can't be null")
    private double rating;

    @Embedded
    @JsonProperty("coordinates")
    @NotNull(message = "coordinates can't be null")
    private Coordinate coordinates;

    @ElementCollection
    @JsonProperty("transactions")
    private List<String> transactions;

    @JsonProperty("price")
    private String price;

    @Embedded
    @JsonProperty("location")
    @NotNull(message = "Location can't be null")
    private Location location;
}

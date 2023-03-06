package com.forcepoint.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UrlCategory {

    @JsonProperty(value = "url_category_id")
    String urlCategoryId;
    String name;
    @JsonProperty(value = "url_patterns")
    List<UrlPattern> urlPatterns;

}

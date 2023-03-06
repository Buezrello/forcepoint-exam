package com.forcepoint.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RootUrlCategories {
    @JsonProperty(value = "url_categories")
    List<UrlCategory> urlCategories;
}

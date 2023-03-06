package com.forcepoint.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonPropertyOrder({
        "match_type",
        "host"
})
public class UrlPattern {
    @JsonProperty(value = "match_type")
    String matchType;
    String host;
}

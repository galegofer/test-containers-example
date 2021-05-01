package org.galegofer.testcontainers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Value
@Builder
public class Weather {
    @JsonProperty("title")
    private String title;
    @JsonProperty("type")
    private String type;
    @JsonProperty("properties")
    private Properties properties;
}


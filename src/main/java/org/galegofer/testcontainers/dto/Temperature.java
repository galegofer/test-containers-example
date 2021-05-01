package org.galegofer.testcontainers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Value
@Builder
public class Temperature {
    @JsonProperty("type")
    private String type;
    @JsonProperty("description")
    private String description;
}

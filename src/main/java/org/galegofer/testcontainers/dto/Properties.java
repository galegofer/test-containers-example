package org.galegofer.testcontainers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Value
@Builder
public class Properties {
    @JsonProperty("condition")
    private Condition condition;
    @JsonProperty("temperature")
    private Temperature temperature;
    @JsonProperty("wind_speed")
    private WindSpeed windSpeed;
}

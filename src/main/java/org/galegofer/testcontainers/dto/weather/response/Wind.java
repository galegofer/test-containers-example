package org.galegofer.testcontainers.dto.weather.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Value
@Builder
public class Wind {

    @JsonProperty("speed")
    private Float speed;
    @JsonProperty("deg")
    private Integer deg;
}

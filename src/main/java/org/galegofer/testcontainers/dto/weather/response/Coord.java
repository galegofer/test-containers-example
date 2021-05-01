package org.galegofer.testcontainers.dto.weather.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Value
@Builder
public class Coord {
    @JsonProperty("lon")
    private Float lon;
    @JsonProperty("lat")
    private Float lat;
}

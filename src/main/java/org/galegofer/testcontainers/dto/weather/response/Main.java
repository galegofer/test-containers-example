package org.galegofer.testcontainers.dto.weather.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Value
@Builder
public class Main {
    @JsonProperty("temp")
    private Float temp;
    @JsonProperty("feels_like")
    private Float feelsLike;
    @JsonProperty("temp_min")
    private Float tempMin;
    @JsonProperty("temp_max")
    private Float tempMax;
    @JsonProperty("pressure")
    private Integer pressure;
    @JsonProperty("humidity")
    private Integer humidity;
}

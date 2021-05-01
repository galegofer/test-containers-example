package org.galegofer.testcontainers.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.galegofer.testcontainers.dto.Weather;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ExampleController {

    @Value("${serviceHost}")
    private String host;

    @Value("${apiKey}")
    private String apiKey;

    @GetMapping("/weather/city/{cityName}")
    public Mono<Weather> getWeatherByCityName(@PathVariable("cityName") final String cityName) {
        return WebClient.create()
                .get()
                .uri(host + "/weather?q={cityName}&appid={apiKey}&units=metric", cityName, apiKey)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Weather.class)
                .doOnSuccess(result -> log.info("Successfully got response from service url: {}, with id: {}, response was: {}", host, cityName, result))
                .doOnError(error -> log.error("Error while fetching endpoint with url: {}, with id: {}, error was: {}", host, cityName, error));
    }
}

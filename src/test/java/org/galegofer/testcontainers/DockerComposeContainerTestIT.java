package org.galegofer.testcontainers;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.google.common.io.ByteStreams;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.http.ContentType.JSON;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

@Testable
public class DockerComposeContainerTestIT {

    private static final int APPLICATION_PORT = 8080;
    private static final int WIREMOCK_PORT = 8000;

    private static final String APPLICATION_SERVICE_NAME = "application";
    private static final String WIREMOCK_SERVICE_NAME = "wiremock";
    private static final String HTTP = "http";

    @ClassRule
    public static final DockerComposeContainer composer =
            new DockerComposeContainer<>(new File("src/test/resources/docker-compose.yml"))
                    .withBuild(true)
                    .withExposedService(APPLICATION_SERVICE_NAME, APPLICATION_PORT,
                            Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)))
                    .withExposedService(WIREMOCK_SERVICE_NAME, WIREMOCK_PORT,
                            Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(90)))
                    .withLocalCompose(true);

    static {
        composer.start();

        WireMock.configureFor(HTTP, composer.getServiceHost(WIREMOCK_SERVICE_NAME, WIREMOCK_PORT), WIREMOCK_PORT);

        RestAssured.defaultParser = Parser.JSON;
        RestAssured.baseURI = UriComponentsBuilder.newInstance()
                .host("localhost")
                .scheme(HTTP)
                .build()
                .toString();
        RestAssured.port = composer.getServicePort(APPLICATION_SERVICE_NAME, APPLICATION_PORT);
    }

    protected static void mockGet(final String url, final String responseResource) throws IOException {
        WireMock.stubFor(get(urlEqualTo(url))
                .willReturn(aResponse().withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBody(readResource(responseResource))));
    }

    protected static byte[] readResource(String resourcePath) throws IOException {
        try (InputStream resource = new ClassPathResource(resourcePath).getInputStream()) {
            return ByteStreams.toByteArray(resource);
        }
    }

    @Test
    void validCityNameSuccessfulResponse() throws IOException {
        mockGet(fromPath("/weather?q={cityName}&appid={apiKey}&units=metric")
                        .buildAndExpand("Cordoba", "testApiKey")
                        .getPath()
                , "/it/response.json");

        RestAssured.given()
                .contentType(JSON)
                .when()
                .get(fromPath("/weather/city/{cityName}")
                        .buildAndExpand("Cordoba")
                        .getPath())
                .then()
                .statusCode(OK.value());
    }
}

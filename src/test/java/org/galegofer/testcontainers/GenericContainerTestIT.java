package org.galegofer.testcontainers;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.http.ContentType.JSON;

@Testcontainers
public class GenericContainerTestIT {

    public static final int SERVICE_PORT = 80;

    @Container
    public static final GenericContainer simpleWebServer =
            new GenericContainer("alpine:3.13.5")
                    .withExposedPorts(SERVICE_PORT)
                    .withCommand("/bin/sh", "-c", "while true; do echo "
                            + "\"HTTP/1.1 200 OK\" | nc -l -p 80; done");

    static {
        RestAssured.defaultParser = Parser.JSON;
    }

    public GenericContainerTestIT() {
        RestAssured.baseURI = "http://" + simpleWebServer.getContainerIpAddress();
        RestAssured.port = simpleWebServer.getMappedPort(SERVICE_PORT);
    }

    @Test
    public void givenSimpleWebServerContainer_whenGetRequest_thenReturnsResponse() {
        RestAssured.given()
                .contentType(JSON)
                .when()
                .get()
                .then()
                .statusCode(200);
    }
}

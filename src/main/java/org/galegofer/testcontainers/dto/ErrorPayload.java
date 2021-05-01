package org.galegofer.testcontainers.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class ErrorPayload {
    int code;
    String message;
}

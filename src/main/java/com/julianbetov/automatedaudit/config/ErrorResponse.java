package com.julianbetov.automatedaudit.config;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ErrorResponse {
    private int code;
    private String value;
    private String message;
    private LocalDateTime timestamp;
    private String path;
}

package com.julianbetov.automatedaudit.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RegistryRequest(

    @NotBlank
    String question,
    @NotBlank
    String answer,
    String remarks

) {
}

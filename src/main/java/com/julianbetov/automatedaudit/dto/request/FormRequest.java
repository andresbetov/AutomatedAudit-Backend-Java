package com.julianbetov.automatedaudit.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record FormRequest(

    @NotNull
    List<RegistryRequest> registries

) {
}

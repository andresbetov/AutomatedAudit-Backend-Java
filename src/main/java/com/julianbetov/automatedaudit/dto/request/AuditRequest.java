package com.julianbetov.automatedaudit.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AuditRequest(

    @NotBlank
    String company,
    List<String> departments,
    @NotNull @NotEmpty List<RegistryRequest> registries

) {
}

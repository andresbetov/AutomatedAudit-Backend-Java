package com.julianbetov.automatedaudit.dto.response;

import java.util.List;

public record FormResponse(

    Long id,
    int questionCount,
    List<RegistryResponse> registries

) {
}

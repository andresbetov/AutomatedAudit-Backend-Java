package com.julianbetov.automatedaudit.mapper;

import com.julianbetov.automatedaudit.dto.request.RegistryRequest;
import com.julianbetov.automatedaudit.dto.response.RegistryResponse;
import com.julianbetov.automatedaudit.model.Registry;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RegistryMapper {

    Registry toEntity(RegistryRequest request);

    RegistryResponse toResponse(Registry registry);

    @AfterMapping
    default void remarksValue(@MappingTarget Registry registry) {
        if (registry.getRemarks() != null && registry.getRemarks().isBlank()) {
            registry.setRemarks(null);
        }
    }
}
